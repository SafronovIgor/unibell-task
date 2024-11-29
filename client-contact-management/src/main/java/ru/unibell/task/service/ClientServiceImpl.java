package ru.unibell.task.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import ru.unibell.task.dto.ClientDto;
import ru.unibell.task.entity.ClientEntity;
import ru.unibell.task.mapper.ClientMapper;
import ru.unibell.task.repository.ClientRepository;
import ru.unibell.task.repository.ContactRepository;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ClientServiceImpl implements ClientService {
    ClientMapper clientMapper;
    ClientRepository clientRepository;
    ContactRepository contactRepository;

    @Override
    public ClientDto create(ClientDto clientDto) {
        log.info("Attempting to create a new client with name: {}", clientDto.getName());

        var clientEntity = clientMapper.toEntity(clientDto);
        log.info("Mapped ClientRequestDto to ClientEntity: {}", clientEntity);

        var savedClient = clientRepository.save(clientEntity);
        log.info("Successfully saved client with ID: {} to the database", savedClient.getId());

        var result = clientMapper.toDto(savedClient, contactRepository.findByClient(savedClient));
        log.info("Returning response for created client: {}", result);

        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ClientDto> getAll(Pageable pageable) {
        log.info("Retrieving all clients with pagination: page - {}, size - {}", pageable.getPageNumber(), pageable.getPageSize());

        var clientsPage = clientRepository.findAll(pageable)
                .map(clientEntity -> clientMapper.toDto(
                        clientEntity,
                        contactRepository.findByClient(clientEntity))
                );

        log.info("Retrieved {} clients from the database", clientsPage.getTotalElements());

        return clientsPage;
    }

    @Override
    @Transactional(readOnly = true)
    public ClientDto getById(Long id) {
        ClientEntity clientEntity = clientRepository.findById(id).orElseThrow(() -> {
            log.error("Client with ID: {} not found", id);
            return new ResponseStatusException(HttpStatus.NOT_FOUND, "Client with ID %s not found".formatted(id));
        });
        return clientMapper.toDto(clientEntity, contactRepository.findByClient(clientEntity));
    }
}