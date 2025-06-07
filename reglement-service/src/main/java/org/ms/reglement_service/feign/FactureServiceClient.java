package org.ms.reglement_service.feign;

import java.util.List;
import org.ms.reglement_service.entities.Facture;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@FeignClient(name = "FACTURE-SERVICE")
public interface FactureServiceClient {

    @GetMapping(value = "/full-facture/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Facture> getFactureById(@PathVariable("id") Long id);

    @GetMapping(value = "/full-facture/client/{clientId}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<Facture>> getFacturesByClientId(@PathVariable("clientId") Long clientId);

    @PutMapping(value = "/full-facture/{id}/status", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Facture> updateFactureStatus(@PathVariable("id") Long id, @RequestParam("status") String status);
}
