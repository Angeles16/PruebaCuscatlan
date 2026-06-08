package com.bancocuscatlan.services.interfaces;

import com.bancocuscatlan.persistence.entities.CobroEntity;
import com.bancocuscatlan.persistence.enums.Estado;
import com.bancocuscatlan.services.models.dto.ClienteDto;

import java.time.LocalDateTime;
import java.util.List;

public interface IClienteServices {

    public String crearCliente(ClienteDto clientedto);

    public List<CobroEntity> consultarCobroCliente(int id, Estado estado, LocalDateTime desde, LocalDateTime hasta);
}
