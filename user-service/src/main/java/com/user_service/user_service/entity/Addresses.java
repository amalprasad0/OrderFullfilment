package com.user_service.user_service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name ="UserAddress")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Addresses {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;
   private String address;
    private String city;
    private String state;
    private String country;
    private String postalCode;
    private String type;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;
    private boolean isActive;
}
