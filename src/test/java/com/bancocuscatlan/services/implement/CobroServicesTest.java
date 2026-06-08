package com.bancocuscatlan.services.implement;

import com.bancocuscatlan.persistence.entities.CobroEntity;
import com.bancocuscatlan.persistence.enums.Estado;
import com.bancocuscatlan.persistence.repositories.ClienteRepository;
import com.bancocuscatlan.persistence.repositories.CobroRepository;
import com.bancocuscatlan.services.models.dto.CobroDto;
import com.bancocuscatlan.services.response.BusinessException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class CobroServicesTest {

    @Mock
    private CobroRepository cobroRepository;

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private CobroServices cobroServices;

    @Test
    void createCobro_DebeLanzarExcepcion_SiMontoEsCero() {
        CobroDto dto = new CobroDto();
        dto.setMonto(BigDecimal.ZERO);

        assertThrows(BusinessException.class, () -> cobroServices.createCobro(dto));
    }

    @Test
    void procesarCobro_DebeCambiarAProcesado_SiMontoEsMenorOIgualA1000() {
        CobroEntity cobro = new CobroEntity();
        cobro.setMonto(new BigDecimal("500"));
        when(cobroRepository.findById(1)).thenReturn(Optional.of(cobro));

        CobroEntity resultado = cobroServices.procesarCobro(1);

        assertEquals(Estado.PROCESADO, resultado.getEstado());
        verify(cobroRepository, times(1)).save(cobro);
    }

    @Test
    void procesarCobro_DebeCambiarAFallido_SiMontoEsMayorA1000() {
        // Arrange
        CobroEntity cobro = new CobroEntity();
        cobro.setMonto(new BigDecimal("1500"));
        when(cobroRepository.findById(1)).thenReturn(Optional.of(cobro));

        CobroEntity resultado = cobroServices.procesarCobro(1);

        assertEquals(Estado.FALLIDO, resultado.getEstado());
    }
}
