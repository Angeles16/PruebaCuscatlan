package com.bancocuscatlan.services.implement;

import com.bancocuscatlan.persistence.entities.ClienteEntity;
import com.bancocuscatlan.persistence.entities.CobroEntity;
import com.bancocuscatlan.persistence.enums.Estado;
import com.bancocuscatlan.persistence.repositories.ClienteRepository;
import com.bancocuscatlan.persistence.repositories.CobroRepository;
import com.bancocuscatlan.services.interfaces.IClienteServices;
import com.bancocuscatlan.services.models.dto.ClienteDto;
import com.bancocuscatlan.services.response.BusinessException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import jakarta.persistence.criteria.Predicate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteServices implements IClienteServices {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private CobroRepository cobroRepository;

    @Override
    @Transactional
    public String crearCliente(ClienteDto clientedto) {
        Optional<ClienteEntity> cliente = clienteRepository.findByDpi(clientedto.getDpi());
        if(cliente.isPresent()) {
            throw new BusinessException("El cliente ya existe, intente con otro DPI.");
        }

        ClienteEntity newClient = new ClienteEntity();
        newClient.setNombre(clientedto.getNombre());
        newClient.setDpi(clientedto.getDpi());
        newClient.setEmail(clientedto.getEmail());
        newClient.setTelefono(clientedto.getTelefono());
        clienteRepository.save(newClient);

        return "Cliente creado con exito";
    }

    @Override
    @Transactional
    public List<CobroEntity> consultarCobroCliente(int clienteId, Estado estado, LocalDateTime desde, LocalDateTime hasta) {
        Specification<CobroEntity> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            predicates.add(cb.equal(root.get("cliente_id"), clienteId));

            if (estado != null) {
                predicates.add(cb.equal(root.get("estado"), estado));
            }
            if (desde != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("fecha_creacion"), desde));
            }
            if (hasta != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("fecha_creacion"), hasta));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        return cobroRepository.findAll(spec);
    }
}
