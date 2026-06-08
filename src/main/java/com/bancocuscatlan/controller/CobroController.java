package com.bancocuscatlan.controller;

import com.bancocuscatlan.persistence.entities.CobroEntity;
import com.bancocuscatlan.persistence.enums.Estado;
import com.bancocuscatlan.services.interfaces.ICobroServices;
import com.bancocuscatlan.services.models.dto.CobroDto;
import com.bancocuscatlan.services.models.dto.LoteCobroDto;
import com.bancocuscatlan.services.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("cobros")
public class CobroController {

    @Autowired
    private ICobroServices iCobroServices;

    @PostMapping()
    private ResponseEntity<ApiResponse<CobroEntity>> createCobro(@RequestBody CobroDto cobroDto){
        CobroEntity resp = iCobroServices.createCobro(cobroDto);
        return ApiResponse.success(resp, "Cobro creado exitosamente", HttpStatus.CREATED );
    }

    @PostMapping("/{id}/procesar")
    private ResponseEntity<ApiResponse<CobroEntity>> procesarCobro(@PathVariable int id){
        CobroEntity resp = iCobroServices.procesarCobro(id);
        return ApiResponse.success(resp, "Cobro procesado con exito", HttpStatus.OK);
    }

    @PostMapping("/lotes/procesar")
    public ResponseEntity<ApiResponse<Map<Estado, String>>> procesarLote(@RequestBody LoteCobroDto dto) {
        Map<Estado, String> resumen = iCobroServices.procesarLote(dto.getIds());
        return ApiResponse.success(resumen, "Procesamiento de lote finalizado", HttpStatus.OK);
    }

}
