package dev.kaldiroglu.hexagonal.ayvalikbank.adapter.out.persistence.repository;

import dev.kaldiroglu.hexagonal.ayvalikbank.adapter.out.persistence.entity.TransactionJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TransactionJpaRepository extends JpaRepository<TransactionJpaEntity, UUID> {
    List<TransactionJpaEntity> findByAccountIdOrderByTimestampDesc(UUID accountId);
}
