package ru.unibell.task.mapper;

import ru.unibell.task.dto.ClientRequestDto;
import ru.unibell.task.dto.ClientResponseDto;
import ru.unibell.task.dto.ContactRequestDto;
import ru.unibell.task.entity.ClientEntity;
import ru.unibell.task.entity.ContactEntity;

import java.util.List;

public interface ClientMapper {

    ClientEntity toEntity(ClientRequestDto dto);

    ClientResponseDto toDto(ClientEntity entity, List<ContactEntity> contacts);

    ContactRequestDto toContactDto(ContactEntity entity);
}