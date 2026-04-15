package dev.kaldiroglu.hexagonal.ayvalikbank.adapter.out.persistence.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "settings")
public class SettingsJpaEntity {

    @Id
    @Column(name = "key", nullable = false)
    private String key;

    @Column(name = "value", nullable = false)
    private String value;

    public SettingsJpaEntity() {}

    public SettingsJpaEntity(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() { return key; }
    public void setKey(String key) { this.key = key; }

    public String getValue() { return value; }
    public void setValue(String value) { this.value = value; }
}
