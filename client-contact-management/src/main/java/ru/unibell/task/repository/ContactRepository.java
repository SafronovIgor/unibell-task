package ru.unibell.task.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.unibell.task.entity.ClientEntity;
import ru.unibell.task.entity.ContactEntity;

import java.util.List;

public interface ContactRepository extends JpaRepository<ContactEntity, Long> {
    List<ContactEntity> findByClient(ClientEntity client);
}