package ru.unibell.task.controller;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import ru.unibell.task.dto.ClientRequestDto;
import ru.unibell.task.dto.ClientResponseDto;
import ru.unibell.task.dto.ContactRequestDto;
import ru.unibell.task.service.ClientService;
import ru.unibell.task.service.ContactService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/client")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ClientController {
    ClientService clientService;
    ContactService contactService;

    @PostMapping
    public ClientResponseDto create(@Valid @RequestBody ClientRequestDto client) {
        log.info("Received request to create new client: {}", client);
        ClientResponseDto createdClient = clientService.create(client);
        log.info("Client created with ID: {}", createdClient.getId());
        return createdClient;
    }

    @GetMapping
    public Page<ClientResponseDto> getList(Pageable pageable) {
        log.info("Fetching client list with page: {} size: {}", pageable.getPageNumber(), pageable.getPageSize());
        return clientService.getAll(pageable);
    }

    @PostMapping(value = "/{id}/contact")
    public ClientResponseDto createClientContact(@PathVariable("id") Long id, @Valid @RequestBody ContactRequestDto clientContact) {
        log.info("Received request to add contact to client ID: {} with contact details: {}", id, clientContact);
        ClientResponseDto updatedClient = contactService.createClientContact(id, clientContact);
        log.info("Contact added to client ID: {}", id);
        return updatedClient;
    }
}