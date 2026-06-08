package com.bancocuscatlan.persistence.entities;

import com.bancocuscatlan.persistence.enums.Estado;
import com.bancocuscatlan.persistence.enums.Moneda;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "cobro", uniqueConstraints = @UniqueConstraint(columnNames = "id"))
public class CobroEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int cliente_id;

    private BigDecimal monto;

    @Enumerated(EnumType.STRING)
    private Moneda moneda;

    @Enumerated(EnumType.STRING)
    private Estado estado;

    private LocalDateTime fecha_creacion;

    private LocalDateTime fecha_proceso;

    private String referencia_externa;

}
