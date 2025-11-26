package com.smartshop.smartshop.controller;


import com.smartshop.smartshop.dto.client.RequestClient;
import com.smartshop.smartshop.dto.client.ResponseClient;
import com.smartshop.smartshop.service.implementation.ClientServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{id}")
    public ResponseEntity<ResponseClient> getClientById(@PathVariable Long id) {
        ResponseClient responseClient = clientService.findClientById(id);
        return new ResponseEntity<>(responseClient, HttpStatus.OK);
    }

}
