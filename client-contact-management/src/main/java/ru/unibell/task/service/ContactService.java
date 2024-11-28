package ru.unibell.task.service;

import ru.unibell.task.dto.ClientResponseDto;
import ru.unibell.task.dto.ContactRequestDto;

public interface ContactService {
    ClientResponseDto createClientContact(Long id, ContactRequestDto clientContact);
}