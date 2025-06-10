package edu.uptc.swii.login_service.infrastructure.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.uptc.swii.login_service.infrastructure.entities.LoginEntity;

@Repository
public interface JpaLoginRepository extends JpaRepository<LoginEntity, Long> {
    Optional<LoginEntity> findByUserId(Long userId);
}
