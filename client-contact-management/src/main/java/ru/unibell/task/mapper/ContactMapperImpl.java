package ru.unibell.task.mapper;

import org.springframework.stereotype.Component;
import ru.unibell.task.dto.ContactRequestDto;
import ru.unibell.task.entity.ContactEntity;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ContactMapperImpl implements ContactMapper {

    @Override
    public ContactEntity toEntity(ContactRequestDto dto) {
        return ContactEntity.builder()
                .type(dto.getType())
                .value(dto.getValue())
                .build();
    }

    @Override
    public ContactRequestDto toDto(ContactEntity entity) {
        return ContactRequestDto.builder()
                .type(entity.getType())
                .value(entity.getValue())
                .build();
    }

    @Override
    public List<ContactRequestDto> toDtoList(List<ContactEntity> entities) {
        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}