package com.product_service.product_service.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "delete_inventories")
@Getter
@Setter
@NoArgsConstructor
public class DeleteInventories {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;
    @JoinColumn(name = "inventory_id", nullable = false)
    @OneToOne
    private Inventory inventoryId;
    private String userId;
    private String reason;
     private LocalDateTime deletedAt;
    @PrePersist
    public void prePersist() {
        this.deletedAt = LocalDateTime.now();
    }
}
