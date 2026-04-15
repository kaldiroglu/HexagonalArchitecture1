package dev.kaldiroglu.hexagonal.ayvalikbank.adapter.out.persistence.repository;

import dev.kaldiroglu.hexagonal.ayvalikbank.adapter.out.persistence.entity.SettingsJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SettingsJpaRepository extends JpaRepository<SettingsJpaEntity, String> {
}
