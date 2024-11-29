package ru.unibell.task.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.unibell.task.entity.ClientEntity;
import ru.unibell.task.entity.ContactEntity;
import ru.unibell.task.entity.ContactType;

import java.util.List;

public interface ContactRepository extends JpaRepository<ContactEntity, Long> {

    List<ContactEntity> findByClient(ClientEntity client);

    List<ContactEntity> findByClient_Id(Long clientId);

    boolean existsByTypeAndValue(ContactType type, String value);

    List<ContactEntity> findByClient_IdAndType(Long clientId, ContactType type);
}