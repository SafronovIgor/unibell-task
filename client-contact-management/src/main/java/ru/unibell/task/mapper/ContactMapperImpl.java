package ru.unibell.task.mapper;

import org.springframework.stereotype.Component;
import ru.unibell.task.dto.ContactDto;
import ru.unibell.task.entity.ContactEntity;
import ru.unibell.task.entity.ContactType;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ContactMapperImpl implements ContactMapper {

    @Override
    public ContactEntity toEntity(ContactDto dto) {
        return ContactEntity.builder()
                .type(ContactType.valueOf(dto.getType()))
                .value(dto.getValue())
                .build();
    }

    @Override
    public ContactDto toDto(ContactEntity entity) {
        return ContactDto.builder()
                .type(entity.getType().name())
                .value(entity.getValue())
                .build();
    }

    @Override
    public List<ContactDto> toDtoList(List<ContactEntity> entities) {
        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}