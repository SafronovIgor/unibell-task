package ru.unibell.task.mapper;

import ru.unibell.task.dto.ContactDto;
import ru.unibell.task.entity.ContactEntity;

import java.util.List;

public interface ContactMapper {

    ContactEntity toEntity(ContactDto dto);

    ContactDto toDto(ContactEntity entity);

    List<ContactDto> toDtoList(List<ContactEntity> entities);
}