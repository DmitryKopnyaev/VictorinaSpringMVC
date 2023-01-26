package com.dimentor.victorina.service;

import com.dimentor.victorina.model.Client;

import java.util.List;

public interface ClientService {
    Client getClientById(Long id);
    void saveClient(Client client);
    void updateClient(Client client);
    Client deleteClient(Long id);
    List<Client> getAll();

    Client getByLoginAndPassword(String login, String password);
    Client getByHash(String hash);
//    Client getByLogin(String login);
}