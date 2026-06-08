package com.bancocuscatlan.persistence.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "auditoria", uniqueConstraints = @UniqueConstraint(columnNames = "id"))
public class AuditoriaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String evento;

    private String resumen_payload;

    private LocalDateTime fecha;

    private int usuario_sistema;

}

