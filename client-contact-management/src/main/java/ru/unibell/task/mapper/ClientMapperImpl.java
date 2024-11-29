package ru.unibell.task.mapper;

import org.springframework.stereotype.Component;
import ru.unibell.task.dto.ClientDto;
import ru.unibell.task.dto.ContactDto;
import ru.unibell.task.entity.ClientEntity;
import ru.unibell.task.entity.ContactEntity;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ClientMapperImpl implements ClientMapper {

    @Override
    public ClientEntity toEntity(ClientDto dto) {
        return ClientEntity.builder()
                .name(dto.getName())
                .build();
    }

    @Override
    public ClientDto toDto(ClientEntity entity, List<ContactEntity> contacts) {
        List<ContactDto> contactDtos = contacts.stream()
                .map(this::toContactDto)
                .collect(Collectors.toList());

        return ClientDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .contacts(contactDtos)
                .build();
    }

    @Override
    public ContactDto toContactDto(ContactEntity entity) {
        return ContactDto.builder()
                .type(entity.getType().name())
                .value(entity.getValue())
                .build();
    }
}