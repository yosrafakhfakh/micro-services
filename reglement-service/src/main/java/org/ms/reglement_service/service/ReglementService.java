package org.ms.reglement_service.service;
import org.ms.reglement_service.entities.Client;
import org.ms.reglement_service.entities.Facture;
import org.ms.reglement_service.entities.Reglement;
import org.ms.reglement_service.feign.ClientServiceClient;
import org.ms.reglement_service.feign.FactureServiceClient;
import org.ms.reglement_service.repository.ReglementRepository;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.lang.module.ResolutionException;
import java.time.LocalDate;
import java.util.List;
import org.springframework.http.ResponseEntity;

@Service
@RequiredArgsConstructor
public class ReglementService {

    private final ReglementRepository reglementRepository;
    private final ClientServiceClient clientServiceClient;
    private final FactureServiceClient factureServiceClient;

    public Reglement effectuerReglement(Reglement reglement) throws Exception {
        try {
            if (reglement.getMontantPaye() <= 0) {
                throw new IllegalArgumentException("Le montant payé doit être supérieur à 0");
            }

            // Vérifier si le client existe
            Client client = clientServiceClient.findClientById(reglement.getClientId());
            if (client == null) {
                throw new Exception("Client non trouvé avec l'ID: " + reglement.getClientId());
            }

            // Vérifier si la facture existe
            ResponseEntity<Facture> factureResponse;
            try {
                factureResponse = factureServiceClient.getFactureById(reglement.getFactureId());
            } catch (Exception e) {
                throw new Exception("Erreur lors de la récupération de la facture: " + e.getMessage());
            }

            if (!factureResponse.hasBody()) {
                throw new Exception("Facture non trouvée avec l'ID: " + reglement.getFactureId());
            }
            
            Facture facture = factureResponse.getBody();
            
            // Vérifier si la facture n'est pas déjà payée
            if ("PAYE".equals(facture.getStatus())) {
                throw new IllegalStateException("La facture est déjà payée");
            }

            // Vérifier le montant total
            System.out.println("Montant total de la facture: " + facture.getTotal());
            Double total = facture.getTotal();
            if (total == null || total == 0.0) {
                // Si pas de total et pas de lignes, on utilise le montant du règlement
                if (facture.getFactureLignes() == null || facture.getFactureLignes().isEmpty()) {
                    total = reglement.getMontantPaye();
                    System.out.println("Utilisation du montant du règlement comme total: " + total);
                } else {
                    // Calculer le total à partir des lignes de facture si disponible
                    total = facture.getFactureLignes().stream()
                        .mapToDouble(fl -> fl.getPrice() * fl.getQuantity())
                        .sum();
                }
                if (total == 0.0) {
                    throw new RuntimeException("Le montant total de la facture est invalide");
                }
            }

            // Mise à jour du statut de paiement
            System.out.println("Montant payé: " + reglement.getMontantPaye());
            if (reglement.getMontantPaye() < total) {
                reglement.setStatutPaiement("PARTIEL");
                reglement.setMontantRestant(total - reglement.getMontantPaye());
            } else {
                reglement.setStatutPaiement("PAYE");
                reglement.setMontantRestant(0.0);
            }

            reglement.setDateReglement(LocalDate.now());
            System.out.println("Règlement final à sauvegarder: " + reglement);
            return reglementRepository.save(reglement);
        } catch (Exception e) {
            throw new Exception("Erreur lors du traitement du règlement: " + e.getMessage());
        }
    }

    public List<Reglement> getAllReglements() {
        return reglementRepository.findAll();
    }

    public Reglement getReglementById(Long id) {
        return reglementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reglement non trouvé"));
    }

    public Reglement getFullReglement(Long id) {
        Reglement reglement = getReglementById(id);
        Client client = clientServiceClient.findClientById(reglement.getClientId());
        Facture facture = factureServiceClient.getFactureById(reglement.getFactureId()).getBody();
        reglement.setClient(client);
        reglement.setFacture(facture);
        return reglement;
    }
}
