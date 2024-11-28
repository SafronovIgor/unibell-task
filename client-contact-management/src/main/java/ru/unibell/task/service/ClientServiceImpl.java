package ru.unibell.task.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.unibell.task.dto.ClientRequestDto;
import ru.unibell.task.dto.ClientResponseDto;
import ru.unibell.task.mapper.ClientMapper;
import ru.unibell.task.repository.ClientRepository;
import ru.unibell.task.repository.ContactRepository;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ClientServiceImpl implements ClientService {
    ClientMapper clientMapperImpl;
    ClientRepository clientRepository;
    ContactRepository contactRepository;

    @Override
    public ClientResponseDto create(ClientRequestDto clientDto) {
        log.info("Attempting to create a new client with name: {}", clientDto.getName());

        var clientEntity = clientMapperImpl.toEntity(clientDto);
        log.info("Mapped ClientRequestDto to ClientEntity: {}", clientEntity);

        var savedClient = clientRepository.save(clientEntity);
        log.info("Successfully saved client with ID: {} to the database", savedClient.getId());

        var result = clientMapperImpl.toDto(savedClient, contactRepository.findByClient(savedClient));
        log.info("Returning response for created client: {}", result);

        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ClientResponseDto> getAll(Pageable pageable) {
        log.info("Retrieving all clients with pagination: page - {}, size - {}", pageable.getPageNumber(), pageable.getPageSize());

        var clientsPage = clientRepository.findAll(pageable)
                .map(clientEntity -> clientMapperImpl.toDto(
                        clientEntity,
                        contactRepository.findByClient(clientEntity))
                );

        log.info("Retrieved {} clients from the database", clientsPage.getTotalElements());

        return clientsPage;
    }
}