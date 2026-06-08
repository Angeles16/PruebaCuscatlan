package com.bancocuscatlan.services.interfaces;

import com.bancocuscatlan.persistence.entities.CobroEntity;
import com.bancocuscatlan.persistence.enums.Estado;
import com.bancocuscatlan.services.models.dto.CobroDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public interface ICobroServices {

    public CobroEntity createCobro(CobroDto cobroDto);

    public CobroEntity procesarCobro(int id);

    public Map<Estado, String> procesarLote(List<Integer> ids);


}
