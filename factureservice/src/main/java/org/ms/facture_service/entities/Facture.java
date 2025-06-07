package org.ms.facture_service.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.ms.facture_service.model.Client;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Facture {
	public Facture(Long id, Date dateFacture, List<FactureLigne> factureLines, Client client, Long clientId) {
	    this.id = id;
	    this.dateFacture = dateFacture;
	    this.factureLines = factureLines;
	    this.client = client;
	    this.clientID = clientId;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Date dateFacture;
	@OneToMany(mappedBy = "facture")
	private Collection<FactureLigne> factureLines;
	@Transient
	private Client client;
	private Long clientID;
	
}
