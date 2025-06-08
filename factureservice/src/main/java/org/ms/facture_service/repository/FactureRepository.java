package org.ms.facture_service.repository;
import java.util.List;

import org.ms.facture_service.entities.Facture;
import org.springframework.data.jpa.repository.JpaRepository;
import
        org.springframework.data.rest.webmvc.RepositoryRestController;
@RepositoryRestController
public interface FactureRepository extends
        JpaRepository<Facture, Long> {
	 List<Facture> findByClientID(Long clientID);
	 List<Facture> findByReglee(Boolean reglee);
}