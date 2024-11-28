package ru.unibell.task.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import ru.unibell.task.dto.ClientResponseDto;
import ru.unibell.task.dto.ContactRequestDto;
import ru.unibell.task.mapper.ContactMapper;
import ru.unibell.task.repository.ClientRepository;
import ru.unibell.task.repository.ContactRepository;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ContactServiceImpl implements ContactService {
    ContactMapper contactMapper;
    ClientRepository clientRepository;
    ContactRepository contactRepository;

    @Override
    public ClientResponseDto createClientContact(Long id, ContactRequestDto contactRequestDto) {
        log.info("Attempting to add new contact for client with ID: {}", id);

        var clientEntity = clientRepository.findById(id).orElseThrow(() -> {
            log.error("Client with ID: {} not found", id);
            return new ResponseStatusException(HttpStatus.NOT_FOUND, "Client with ID %s not found".formatted(id));
        });
        log.info("Found client with ID: {} and name: {}", clientEntity.getId(), clientEntity.getName());


        log.info("Mapping ContactRequestDto to ContactEntity for client ID: {}", id);
        var contactEntity = contactMapper.toEntity(contactRequestDto);
        contactEntity.setClient(clientEntity);
        log.info("Saving new contact for client ID: {}", id);
        contactRepository.save(contactEntity);

        log.info("Retrieving all contacts for client with ID: {}", id);
        var contactEntities = contactRepository.findByClient(clientEntity);
        var contactDtos = contactMapper.toDtoList(contactEntities);

        ClientResponseDto response = ClientResponseDto.builder()
                .id(clientEntity.getId())
                .name(clientEntity.getName())
                .contacts(contactDtos)
                .build();
        log.info("Returning response with updated contact list for client ID: {}", id);

        return response;
    }
}