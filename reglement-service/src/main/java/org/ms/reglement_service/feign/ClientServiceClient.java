package org.ms.reglement_service.feign;

import org.ms.reglement_service.entities.Client;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;

@FeignClient(name = "client-service")
public interface ClientServiceClient {
    @GetMapping(value = "/clients/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    Client findClientById(@PathVariable("id") Long id);
}
