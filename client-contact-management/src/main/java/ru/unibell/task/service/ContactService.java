package ru.unibell.task.service;

import ru.unibell.task.dto.ClientDto;
import ru.unibell.task.dto.ContactDto;

import java.util.List;

public interface ContactService {
    ClientDto createClientContact(Long id, ContactDto clientContact);

    List<ContactDto> getContactsByClientId(Long id);

    List<ContactDto> getContactsByClientIdAndType(Long id, String type);
}