package com.project.user_serivce.models;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
@Builder
public class User implements Serializable { // Bat buoc phai implements Serializable vi cache du lieu dang byte, dung Serializable de chuyen tu object -> day byte // Neu da config Redis thi khong can implements nua
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
}
