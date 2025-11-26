package com.smartshop.smartshop.controller;


import com.smartshop.smartshop.dto.client.RequestClient;
import com.smartshop.smartshop.dto.client.ResponseClient;
import com.smartshop.smartshop.service.implementation.ClientServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/clients")

public class ClientController {
    private final ClientServiceImpl clientService;

    @PostMapping
    public ResponseEntity<ResponseClient> createClient(@Valid @RequestBody RequestClient requestClient) {
        ResponseClient responseClient = clientService.createClient(requestClient);
        return new ResponseEntity<>(responseClient, HttpStatus.CREATED);
    }

}
