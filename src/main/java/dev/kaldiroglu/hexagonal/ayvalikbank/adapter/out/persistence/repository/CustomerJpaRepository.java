package dev.kaldiroglu.hexagonal.ayvalikbank.adapter.out.persistence.repository;

import dev.kaldiroglu.hexagonal.ayvalikbank.adapter.out.persistence.entity.CustomerJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CustomerJpaRepository extends JpaRepository<CustomerJpaEntity, UUID> {
    Optional<CustomerJpaEntity> findByEmail(String email);
}
