package ru.unibell.task.mapper;

import ru.unibell.task.dto.ContactRequestDto;
import ru.unibell.task.entity.ContactEntity;

import java.util.List;

public interface ContactMapper {

    ContactEntity toEntity(ContactRequestDto dto);

    ContactRequestDto toDto(ContactEntity entity);

    List<ContactRequestDto> toDtoList(List<ContactEntity> entities);
}