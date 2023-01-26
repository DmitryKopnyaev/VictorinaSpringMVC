package com.dimentor.victorina.service;

import com.dimentor.victorina.model.Client;
import com.dimentor.victorina.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {

    private ClientRepository clientRepository;

    @Autowired
    public void setClientRepository(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public Client getClientById(Long id) {
        return this.clientRepository.findById(id).orElse(null);
    }

    @Override
    public void saveClient(Client client) {
        try {
            this.clientRepository.save(client);
        } catch (Exception e) {
            throw new IllegalArgumentException("This client entity already exists");
        }
    }

    @Override
    public void updateClient(Client client) {
        Client byId = this.getClientById(client.getId());
        if (byId == null)
            throw new IllegalArgumentException("Client with this ID does not exist");
        this.clientRepository.save(client);
    }

    @Override
    public Client deleteClient(Long id) {
        Client clientById = this.getClientById(id);
        if (clientById == null) return null;
        this.clientRepository.deleteById(id);
        return clientById;
    }

    @Override
    public List<Client> getAll() {
        return this.clientRepository.findAll();
    }

    @Override
    public Client getByLoginAndPassword(String login, String password) {
        return this.clientRepository.getByLoginAndPassword(login, password).orElse(null);
    }

    @Override
    public Client getByHash(String hash) {
        return this.clientRepository.getByHash(hash).orElse(null);
    }

//    @Override
//    public Client getByLogin(String login) {
//        return this.clientRepository.getByLogin(login).orElse(null);
//    }
}