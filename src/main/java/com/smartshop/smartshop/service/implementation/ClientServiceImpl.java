package com.smartshop.smartshop.service.implementation;

import com.smartshop.smartshop.dto.client.RequestClient;
import com.smartshop.smartshop.dto.client.ResponseClient;
import com.smartshop.smartshop.entity.Client;
import com.smartshop.smartshop.exception.DuplicateResourceException;
import com.smartshop.smartshop.mapper.client.ClientMapper;
import com.smartshop.smartshop.repository.ClientRepository;
import com.smartshop.smartshop.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    @Override
    public ResponseClient createClient(RequestClient requestClient) {
        if (clientRepository.findByEmail(requestClient.getEmail()).isPresent()) {
            List<String> errors = List.of("Email already in use");
            throw new DuplicateResourceException(errors);
        }
        Client client =clientMapper.toClient(requestClient);
        Client savedClient = clientRepository.save(client);
        return clientMapper.toResponseClient(savedClient);
    }
}
