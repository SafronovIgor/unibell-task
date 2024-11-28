package ru.unibell.task.mapper;

import org.springframework.stereotype.Component;
import ru.unibell.task.dto.ClientRequestDto;
import ru.unibell.task.dto.ClientResponseDto;
import ru.unibell.task.dto.ContactRequestDto;
import ru.unibell.task.entity.ClientEntity;
import ru.unibell.task.entity.ContactEntity;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ClientMapperImpl implements ClientMapper {

    @Override
    public ClientEntity toEntity(ClientRequestDto dto) {
        return ClientEntity.builder()
                .name(dto.getName())
                .build();
    }

    @Override
    public ClientResponseDto toDto(ClientEntity entity, List<ContactEntity> contacts) {
        List<ContactRequestDto> contactDtos = contacts.stream()
                .map(this::toContactDto)
                .collect(Collectors.toList());

        return ClientResponseDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .contacts(contactDtos)
                .build();
    }

    @Override
    public ContactRequestDto toContactDto(ContactEntity entity) {
        return ContactRequestDto.builder()
                .type(entity.getType())
                .value(entity.getValue())
                .build();
    }
}