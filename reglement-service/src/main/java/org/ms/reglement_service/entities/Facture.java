package org.ms.reglement_service.entities;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.util.Date;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Facture {
    private Long id;
    private Date date;
    private Double total;
    private String status;
    private Long clientID;
    private List<FactureLigne> factureLignes;
}

