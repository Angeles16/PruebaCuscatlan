package com.bancocuscatlan.controller;

import com.bancocuscatlan.persistence.entities.CobroEntity;
import com.bancocuscatlan.persistence.enums.Estado;
import com.bancocuscatlan.services.interfaces.IClienteServices;
import com.bancocuscatlan.services.models.dto.ClienteDto;
import com.bancocuscatlan.services.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("clientes")
public class ClienteController {

    @Autowired
    private IClienteServices iClienteServices;

    @PostMapping()
    private ResponseEntity<ApiResponse<String>> createClient(@RequestBody ClienteDto clienteDto) {
        String resp = iClienteServices.crearCliente(clienteDto);
        return ApiResponse.success(resp, "Cliente creado exitosamente", HttpStatus.CREATED );
    }

    @GetMapping("/{id}/cobros")
    public ResponseEntity<ApiResponse<List<CobroEntity>>> consultar(
            @PathVariable int id,
            @RequestParam(required = false) Estado estado,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime desde,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime hasta) {

        List<CobroEntity> lista = iClienteServices.consultarCobroCliente(id, estado, desde, hasta);
        return ApiResponse.success(lista, "Consulta exitosa", HttpStatus.OK);
    }
}
