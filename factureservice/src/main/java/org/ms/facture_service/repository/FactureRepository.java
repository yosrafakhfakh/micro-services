package org.ms.facture_service.repository;
import org.ms.facture_service.entities.Facture;
import org.springframework.data.jpa.repository.JpaRepository;
import
        org.springframework.data.rest.webmvc.RepositoryRestController;
@RepositoryRestController
public interface FactureRepository extends
        JpaRepository<Facture, Long> {
}