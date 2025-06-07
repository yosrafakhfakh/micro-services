package org.ms.reglement_service;
import org.ms.reglement_service.entities.Facture;
import org.ms.reglement_service.entities.Reglement;
import org.ms.reglement_service.entities.Client;
import org.ms.reglement_service.feign.ClientServiceClient;
import org.ms.reglement_service.feign.FactureServiceClient;
import org.ms.reglement_service.repository.ReglementRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EntityScan(basePackages = "org.ms.reglement_service.entities")
public class ReglementServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReglementServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner start(ReglementRepository reglementRepository,
	                        ClientServiceClient clientServiceClient,
	                        FactureServiceClient factureServiceClient) {
	    return args -> {
	        try {
	            // Récupérer le client avec ID 1
	            Client client = clientServiceClient.findClientById(1L);
	            // Récupérer la facture avec ID 1
	            ResponseEntity<Facture> factureResponse = factureServiceClient.getFactureById(1L);

	            if (client != null && factureResponse.hasBody()) {
	                Facture facture = factureResponse.getBody();
	                if (facture.getTotal() != null) {
	                    Reglement reglement = new Reglement();
	                    reglement.setClientId(client.getId());
	                    reglement.setFactureId(facture.getId());

	                    // Payer la moitié de la facture
	                    double montantPaye = facture.getTotal() * 0.5;
	                    reglement.setMontantPaye(montantPaye);
	                    reglement.setDateReglement(java.time.LocalDate.now());

	                    // Définir le statut
	                    reglement.setStatutPaiement("PARTIEL");
	                    reglement.setMontantRestant(facture.getTotal() - montantPaye);

	                    reglementRepository.save(reglement);
	                    System.out.println("Règlement ajouté pour la facture ID 1");
	                }
	            } else {
	                System.err.println("Client ou facture introuvable");
	            }
	        } catch (Exception e) {
	            System.err.println("Erreur d'insertion du règlement : " + e.getMessage());
	        }
	    };
	}
}