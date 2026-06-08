package com.bancocuscatlan.persistence.repositories;

import com.bancocuscatlan.persistence.entities.CobroEntity;
import com.bancocuscatlan.persistence.enums.Estado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CobroRepository extends JpaRepository<CobroEntity, Integer>, JpaSpecificationExecutor<CobroEntity> {

}
