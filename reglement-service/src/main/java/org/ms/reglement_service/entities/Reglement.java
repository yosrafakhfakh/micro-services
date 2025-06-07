package org.ms.reglement_service.entities;
import java.time.LocalDate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reglement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "facture_id")
    private Long factureId;
    private Long clientId;
    private Double montantPaye;
    private Double montantRestant;
    private LocalDate dateReglement;
    private String statutPaiement; // PAYE, PARTIEL, NON_PAYE

    @Transient
    private Client client;
    @Transient
    private Facture facture;
} 