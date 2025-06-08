package org.ms.facture_service.web;

import org.ms.facture_service.entities.Facture;
import org.ms.facture_service.feign.ClientServiceClient;
import org.ms.facture_service.feign.ProduitServiceClient;
import org.ms.facture_service.model.Client;
import org.ms.facture_service.model.Produit;
import org.ms.facture_service.repository.FactureLigneRepository;
import org.ms.facture_service.repository.FactureRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class FactureRestController {
	private FactureRepository factureRepository;
	private FactureLigneRepository factureLigneRepository;
	private ClientServiceClient clientServiceClient;
	private ProduitServiceClient produitServiceClient;

	public FactureRestController(FactureRepository factureRepository, FactureLigneRepository factureLigneRepository,
			ClientServiceClient clientServiceClient, ProduitServiceClient produitServiceClient) {
		this.factureRepository = factureRepository;
		this.factureLigneRepository = factureLigneRepository;
		this.clientServiceClient = clientServiceClient;
		this.produitServiceClient = produitServiceClient;
	}

	  @GetMapping(path = "/factures")
	    public List<Facture> getAllFactures() {
	        return factureRepository.findAll();
	    }
	  @GetMapping("/")
	    public String home() {
	        return "Bienvenue sur le service de facture";
	    }

	@GetMapping(path = "/full-facture/{id}")
	public Facture getFacture(@PathVariable(name = "id") Long id) {
		Facture facture = factureRepository.findById(id).get();
		Client client = clientServiceClient.findClientById(facture.getClientID());
		facture.setClient(client);
		facture.getFactureLines().forEach(fl -> {
			Produit product = produitServiceClient.findProductById(fl.getProduitID());
			fl.setProduit(product);
		});
		return facture;
	}
}