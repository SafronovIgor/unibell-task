package ru.unibell.task.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.unibell.task.dto.ClientDto;

public interface ClientService {

    ClientDto create(ClientDto clientEntity);

    Page<ClientDto> getAll(Pageable pageable);

    ClientDto getById(Long id);
}