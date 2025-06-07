package org.ms.facture_service.entities;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.ms.facture_service.model.Produit;
import jakarta.persistence.*;
@Entity
@Data @NoArgsConstructor @AllArgsConstructor @ToString
public class FactureLigne {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private Long produitID;
    private long quantity;
    private double price;
    @Transient
    private Produit produit;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne
    private Facture facture;

}