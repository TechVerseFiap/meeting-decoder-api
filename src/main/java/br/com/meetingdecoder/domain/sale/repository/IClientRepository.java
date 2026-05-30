package br.com.meetingdecoder.domain.sale.repository;

import br.com.meetingdecoder.domain.sale.model.Client;
import br.com.meetingdecoder.domain.sale.valueobject.ClientId;

import java.util.List;
import java.util.Optional;

public interface IClientRepository {
    Client save(Client client);
    boolean existsById(ClientId id);
    boolean existsByName(String name);
    List<Client> findAll();
    Optional<Client> findById(ClientId id);
    void deleteById(ClientId id);
}
