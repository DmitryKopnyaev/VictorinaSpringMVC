package com.dimentor.victorina.repository;

import com.dimentor.victorina.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
//    Optional<Client> getByLogin(String login);
    Optional<Client> getByLoginAndPassword(String login, String password);
    Optional<Client> getByHash(String hash);
}
