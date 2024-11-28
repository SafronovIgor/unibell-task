package ru.unibell.task.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.unibell.task.dto.ClientRequestDto;
import ru.unibell.task.dto.ClientResponseDto;

public interface ClientService {

    ClientResponseDto create(ClientRequestDto clientEntity);

    Page<ClientResponseDto> getAll(Pageable pageable);
}