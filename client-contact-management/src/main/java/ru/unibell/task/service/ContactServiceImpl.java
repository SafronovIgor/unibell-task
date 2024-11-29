package ru.unibell.task.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import ru.unibell.task.dto.ClientDto;
import ru.unibell.task.dto.ContactDto;
import ru.unibell.task.entity.ContactEntity;
import ru.unibell.task.entity.ContactType;
import ru.unibell.task.mapper.ContactMapper;
import ru.unibell.task.repository.ClientRepository;
import ru.unibell.task.repository.ContactRepository;

import java.util.List;

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
    public ClientDto createClientContact(Long id, ContactDto contactDto) {
        String contactTypeStr = contactDto.getType();
        String contactValue = contactDto.getValue();
        ContactType contactType;

        log.info("Attempting to add new contact for client with ID: {}", id);

        var clientEntity = clientRepository.findById(id).orElseThrow(() -> {
            log.error("Client with ID: {} not found", id);
            return new ResponseStatusException(HttpStatus.NOT_FOUND, "Client with ID %s not found".formatted(id));
        });
        log.info("Found client with ID: {} and name: {}", clientEntity.getId(), clientEntity.getName());


        try {
            contactType = ContactType.valueOf(contactTypeStr);
        } catch (IllegalArgumentException e) {
            log.error("Invalid contact type: {}", contactTypeStr, e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid contact type: " + contactTypeStr);
        }

        if (contactRepository.existsByTypeAndValue(contactType, contactValue)) {
            log.error("Contact with type: {} and value: {} already exists", contactTypeStr, contactValue);
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Contact with such type and value already exists");
        }

        log.info("Mapping ContactRequestDto to ContactEntity for client ID: {}", id);
        var contactEntity = contactMapper.toEntity(contactDto);
        contactEntity.setClient(clientEntity);
        log.info("Saving new contact for client ID: {}", id);
        contactRepository.save(contactEntity);

        log.info("Retrieving all contacts for client with ID: {}", id);
        var contactEntities = contactRepository.findByClient(clientEntity);
        var contactDtos = contactMapper.toDtoList(contactEntities);

        ClientDto response = ClientDto.builder()
                .id(clientEntity.getId())
                .name(clientEntity.getName())
                .contacts(contactDtos)
                .build();
        log.info("Returning response with updated contact list for client ID: {}", id);

        return response;
    }

    @Override
    public List<ContactDto> getContactsByClientId(Long id) {
        List<ContactEntity> contactEntities = contactRepository.findByClient_Id(id);
        return contactMapper.toDtoList(contactEntities);
    }

    @Override
    public List<ContactDto> getContactsByClientIdAndType(Long id, String type) {
        try {
            ContactType contactType = ContactType.valueOf(type);
            List<ContactEntity> contactEntities = contactRepository.findByClient_IdAndType(id, contactType);
            return contactMapper.toDtoList(contactEntities);
        } catch (IllegalArgumentException e) {
            log.error("Invalid contact type: {}", type, e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid contact type: " + type);
        }
    }
}