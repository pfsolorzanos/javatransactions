package service_client.service_client.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service_client.service_client.model.Client;
import service_client.service_client.repository.ClientRepository;
import service_client.service_client.utils.dto.ClientTO;
import service_client.service_client.utils.exceptions.CustomNotFoundException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
@Service
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;

    private Client getClient(UUID id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new CustomNotFoundException("Cliente no encontrado"));
    }

    public ClientTO getClientById(UUID id) {
        Client client = getClient(id);
        return ClientTO.builder()
                .name(client.getName())
                .identificationId(client.getIdentificationId())
                .password(client.getPassword())
                .phoneNumber(client.getPhoneNumber())
                .age(client.getAge())
                .id(client.getId().toString())
                .address(client.getAddress())
                .state(client.getState())
                .gender(client.getGender())
                .build();
    }

    public List<ClientTO> getAll() {
        return clientRepository.findAll().stream().map(item -> ClientTO.builder()
                .id(item.getId().toString())
                .name(item.getName())
                .identificationId(item.getIdentificationId())
                .phoneNumber(item.getPhoneNumber())
                .gender(item.getGender())
                .address(item.getAddress())
                .age(item.getAge())
                .password(item.getPassword())
                .state(item.getState())
                .build()).collect(Collectors.toList());
    }

    public ClientTO createClient(ClientTO clientTO) {
        Client client = new Client();
        client.setName(clientTO.getName());
        client.setGender(clientTO.getGender());
        client.setPhoneNumber(clientTO.getPhoneNumber());
        client.setAge(clientTO.getAge());
        client.setAddress(clientTO.getAddress());
        client.setIdentificationId(clientTO.getIdentificationId());
        client.setPassword(clientTO.getPassword());
        client.setState(clientTO.getState());
        clientRepository.saveAndFlush(client);
        return clientTO;

    }

    public ClientTO updateClient(ClientTO clientTO, UUID id) {
        Client client = getClient(id);
        client.setName(clientTO.getName());
        client.setAddress(clientTO.getAddress());
        client.setAge(clientTO.getAge());
        client.setGender(clientTO.getGender());
        client.setPhoneNumber(clientTO.getPhoneNumber());
        client.setPassword(clientTO.getPassword());
        client.setState(clientTO.getState());
        clientRepository.saveAndFlush(client);
        return clientTO;
    }

    public void deleteClient(UUID id) {
        clientRepository.deleteById(id);
    }
}
