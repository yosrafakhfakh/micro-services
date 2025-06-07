package org.ms.reglement_service.repository;

import org.ms.reglement_service.entities.Reglement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReglementRepository extends JpaRepository<Reglement, Long> {
    List<Reglement> findByFactureId(Long factureId);
}

