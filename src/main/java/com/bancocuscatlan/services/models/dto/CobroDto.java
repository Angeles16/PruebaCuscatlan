package com.bancocuscatlan.services.models.dto;

import com.bancocuscatlan.persistence.enums.Estado;
import com.bancocuscatlan.persistence.enums.Moneda;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CobroDto {
    private int cliente_id;
    private BigDecimal monto;
    private Moneda moneda;
    private String referencia_externa;
}
