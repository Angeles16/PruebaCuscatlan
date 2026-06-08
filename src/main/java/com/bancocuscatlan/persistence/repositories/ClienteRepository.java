package com.bancocuscatlan.persistence.repositories;

import com.bancocuscatlan.persistence.entities.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<ClienteEntity, Integer> {

    Optional<ClienteEntity> findByDpi(String dpi);

}
