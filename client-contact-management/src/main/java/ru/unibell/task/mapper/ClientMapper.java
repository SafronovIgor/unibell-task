package ru.unibell.task.mapper;

import ru.unibell.task.dto.ClientDto;
import ru.unibell.task.dto.ContactDto;
import ru.unibell.task.entity.ClientEntity;
import ru.unibell.task.entity.ContactEntity;

import java.util.List;

public interface ClientMapper {

    ClientEntity toEntity(ClientDto dto);

    ClientDto toDto(ClientEntity entity, List<ContactEntity> contacts);

    ContactDto toContactDto(ContactEntity entity);
}