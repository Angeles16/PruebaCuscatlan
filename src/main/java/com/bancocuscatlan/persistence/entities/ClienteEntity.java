package com.bancocuscatlan.persistence.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "cliente", uniqueConstraints = @UniqueConstraint(columnNames = "id"))
public class ClienteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nombre;

    private String dpi;

    private String email;

    private String telefono;
}
