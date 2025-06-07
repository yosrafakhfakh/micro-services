package org.ms.reglement_service.web;

import lombok.RequiredArgsConstructor;
import org.ms.reglement_service.entities.Reglement;
import org.ms.reglement_service.service.ReglementService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reglements")
@RequiredArgsConstructor
public class ReglementRestController {

    private final ReglementService reglementService;

    @PostMapping
    public Reglement createReglement(@RequestBody Reglement reglement) throws Exception {
        return reglementService.effectuerReglement(reglement);
    }

    @GetMapping
    public List<Reglement> getAllReglements() {
        return reglementService.getAllReglements();
    }

    @GetMapping("/{id}")
    public Reglement getReglementById(@PathVariable Long id) {
        return reglementService.getReglementById(id);
    }

    @GetMapping("/{id}/full")
    public Reglement getFullReglement(@PathVariable Long id) {
        return reglementService.getFullReglement(id);
    }
}
