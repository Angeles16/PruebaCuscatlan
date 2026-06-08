package com.bancocuscatlan.services.implement;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import com.bancocuscatlan.persistence.entities.ClienteEntity;
import com.bancocuscatlan.persistence.entities.CobroEntity;
import com.bancocuscatlan.persistence.repositories.ClienteRepository;
import com.bancocuscatlan.persistence.repositories.CobroRepository;
import com.bancocuscatlan.services.models.dto.ClienteDto;
import com.bancocuscatlan.services.response.BusinessException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

@ExtendWith(MockitoExtension.class)
public class ClienteServicesTest {

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private CobroRepository cobroRepository;

    @InjectMocks
    private ClienteServices clienteServices;

    @Test
    void crearCliente_DebeLanzarExcepcion_SiDpiYaExiste() {
        ClienteDto dto = new ClienteDto();
        dto.setDpi("12345");
        when(clienteRepository.findByDpi("12345")).thenReturn(Optional.of(new ClienteEntity()));

        assertThrows(BusinessException.class, () -> clienteServices.crearCliente(dto));
    }

    @Test
    void crearCliente_DebeRetornarExito_SiDpiEsNuevo() {
        ClienteDto dto = new ClienteDto();
        dto.setDpi("99999");
        when(clienteRepository.findByDpi("99999")).thenReturn(Optional.empty());

        String resultado = clienteServices.crearCliente(dto);

        assertEquals("Cliente creado con exito", resultado);
        verify(clienteRepository, times(1)).save(any(ClienteEntity.class));
    }

    @Test
    void consultarCobroCliente_DebeLlamarAlRepositorioConSpecification() {
        int clienteId = 1;
        when(cobroRepository.findAll(any(Specification.class))).thenReturn(List.of(new CobroEntity()));

        List<CobroEntity> resultados = clienteServices.consultarCobroCliente(clienteId, null, null, null);

        assertNotNull(resultados);
        verify(cobroRepository, times(1)).findAll(any(Specification.class));
    }
}
