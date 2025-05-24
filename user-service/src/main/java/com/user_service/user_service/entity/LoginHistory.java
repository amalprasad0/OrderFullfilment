package com.user_service.user_service.entity;

import java.time.LocalDateTime;

import jakarta.annotation.Generated;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "login_history")
@Getter
@Setter
@NoArgsConstructor

public class LoginHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;
    private LocalDateTime loginTime;
    private LocalDateTime logoutTime;
    private String loginStatus;
    private String loginMethod;
    @PrePersist
    protected void onCreate() {
        loginTime = LocalDateTime.now();
    }
    @PostPersist
    protected void onUpdate() {
        logoutTime = LocalDateTime.now();
    }
}
