package com.bancocuscatlan.services.implement;

import com.bancocuscatlan.persistence.entities.AuditoriaEntity;
import com.bancocuscatlan.persistence.entities.ClienteEntity;
import com.bancocuscatlan.persistence.entities.CobroEntity;
import com.bancocuscatlan.persistence.enums.Estado;
import com.bancocuscatlan.persistence.repositories.AuditoriaRepository;
import com.bancocuscatlan.persistence.repositories.ClienteRepository;
import com.bancocuscatlan.persistence.repositories.CobroRepository;
import com.bancocuscatlan.services.interfaces.ICobroServices;
import com.bancocuscatlan.services.models.dto.CobroDto;
import com.bancocuscatlan.services.response.BusinessException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class CobroServices implements ICobroServices {

    @Autowired
    private CobroRepository cobroRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    AuditoriaRepository auditoriaRepository;

    @Override
    @Transactional
    public CobroEntity createCobro(CobroDto cobroDto) {

        if(cobroDto.getMonto().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException("El monto debe ser mayor a 0");
        }

        Optional<ClienteEntity> cliente = clienteRepository.findById(cobroDto.getCliente_id());
        if(cliente.isEmpty()) {
            throw new BusinessException("El id del cliente ingresado no existe: verificar id antes de registrar cobro");
        }

        CobroEntity newCobro = new CobroEntity();
        newCobro.setCliente_id(cobroDto.getCliente_id());
        newCobro.setMonto(cobroDto.getMonto());
        newCobro.setMoneda(cobroDto.getMoneda());
        newCobro.setReferencia_externa(cobroDto.getReferencia_externa());
        newCobro.setEstado(Estado.PENDIENTE);
        newCobro.setFecha_creacion(LocalDateTime.now());
        cobroRepository.save(newCobro);
        return newCobro;
    }

    @Override
    @Transactional
    public CobroEntity procesarCobro(int id) {
        Optional<CobroEntity> cobroOpt = cobroRepository.findById(id);

        if(cobroOpt.isEmpty()) {
            throw new BusinessException("El ID de cobro registrado no existe");
        }

        CobroEntity cobro = cobroOpt.get();

        if(cobro.getFecha_proceso() != null) {
            throw new BusinessException("El cobro se encuentra procesado " + cobro.getFecha_proceso());
        }

        BigDecimal limite = new BigDecimal("1000");
        if (cobro.getMonto().compareTo(limite) <= 0) {
            cobro.setEstado(Estado.PROCESADO);
        } else {
            cobro.setEstado(Estado.FALLIDO);
        }
        cobro.setFecha_proceso(LocalDateTime.now());
        cobroRepository.save(cobro);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String payload = objectMapper.writeValueAsString(cobro);

            AuditoriaEntity auditoria = new AuditoriaEntity();
            auditoria.setEvento("Procesar cobros");
            auditoria.setResumen_payload(payload);
            auditoria.setFecha(LocalDateTime.now());
            auditoria.setUsuario_sistema(1);
        } catch (JsonProcessingException e) {
            // En lugar de detener todo el proceso, logueamos el error de auditoría
            System.err.println("Error al serializar auditoría: " + e.getMessage());
        }

        return cobro;
    }

    @Override
    @Transactional
    public Map<Estado, String> procesarLote(List<Integer> ids) {
        int procesados = 0;
        int fallidos = 0;
        BigDecimal totalProcesado = BigDecimal.ZERO;
        BigDecimal totalFallidos = BigDecimal.ZERO;

        for (Integer id : ids) {
            try {
                Optional<CobroEntity> cobroOpt = cobroRepository.findById(id);

                if (cobroOpt.isPresent()) {
                    CobroEntity cobro = cobroOpt.get();
                    BigDecimal limite = new BigDecimal("1000");
                    if (cobro.getMonto().compareTo(limite) <= 0) {
                        cobro.setEstado(Estado.PROCESADO);
                        totalProcesado = totalProcesado.add(cobro.getMonto());
                        procesados++;
                    } else {
                        cobro.setEstado(Estado.FALLIDO);
                        totalFallidos = totalFallidos.add(cobro.getMonto());
                        fallidos++;
                    }
                    cobro.setFecha_proceso(LocalDateTime.now());
                    cobroRepository.save(cobro);
                }
            } catch (Exception e) {
                fallidos++;
            }
        }

        Map<Estado, String> resumen = new HashMap<>();
        resumen.put(Estado.PROCESADO, procesados + ": monto " + totalProcesado );
        resumen.put(Estado.FALLIDO, fallidos + ": monto " + totalFallidos);
        return resumen;
    }
}
