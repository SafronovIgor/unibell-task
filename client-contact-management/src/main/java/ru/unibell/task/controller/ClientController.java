package ru.unibell.task.controller;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import ru.unibell.task.dto.ClientDto;
import ru.unibell.task.dto.ContactDto;
import ru.unibell.task.service.ClientService;
import ru.unibell.task.service.ContactService;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/client")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ClientController {
    ClientService clientService;
    ContactService contactService;

    @PostMapping
    public ClientDto create(@Valid @RequestBody ClientDto client) {
        log.info("Received request to create new client: {}", client);
        ClientDto createdClient = clientService.create(client);
        log.info("Client created with ID: {}", createdClient.getId());
        return createdClient;
    }

    @GetMapping
    public Page<ClientDto> getList(Pageable pageable) {
        log.info("Fetching client list with page: {} size: {}", pageable.getPageNumber(), pageable.getPageSize());
        return clientService.getAll(pageable);
    }

    @GetMapping("/{id}")
    public ClientDto getClientById(@PathVariable("id") Long id) {
        log.info("Fetching client information for ID: {}", id);
        ClientDto client = clientService.getById(id);
        log.info("Client information retrieved: {}", client);
        return client;
    }

    @GetMapping("/{id}/contacts")
    public List<ContactDto> getClientContacts(@PathVariable("id") Long id,
                                              @RequestParam(value = "type", required = false) String type) {
        if (type == null) {
            log.info("Fetching contacts for client ID: {}", id);
            List<ContactDto> contacts = contactService.getContactsByClientId(id);
            log.info("Contacts retrieved for client ID: {}: {}", id, contacts);
            return contacts;
        } else {
            log.info("Fetching contacts of type {} for client ID: {}", type, id);
            List<ContactDto> contacts = contactService.getContactsByClientIdAndType(id, type);
            log.info("Contacts of type {} retrieved for client ID: {}: {}", type, id, contacts);
            return contacts;
        }
    }

    @PostMapping(value = "/{id}/contact")
    public ClientDto createClientContact(@PathVariable("id") Long id, @Valid @RequestBody ContactDto clientContact) {
        log.info("Received request to add contact to client ID: {} with contact details: {}", id, clientContact);
        ClientDto updatedClient = contactService.createClientContact(id, clientContact);
        log.info("Contact added to client ID: {}", id);
        return updatedClient;
    }
}