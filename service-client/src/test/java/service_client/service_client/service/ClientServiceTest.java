package service_client.service_client.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import service_client.service_client.model.Client;
import service_client.service_client.repository.ClientRepository;
import service_client.service_client.utils.dto.ClientTO;
import service_client.service_client.utils.exceptions.CustomNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientService clientService;

    private Client client;
    private ClientTO clientTO;

    @BeforeEach
    void setUp() {
        client = new Client();
        client.setId(UUID.randomUUID());
        client.setName("John Doe");
        client.setIdentificationId("12345678");
        client.setPassword("securePass");
        client.setPhoneNumber("123456789");
        client.setAge(30);
        client.setAddress("123 Main St");
        client.setState(true);
        client.setGender("M");

        clientTO = ClientTO.builder()
                .id(client.getId().toString())
                .name(client.getName())
                .identificationId(client.getIdentificationId())
                .password(client.getPassword())
                .phoneNumber(client.getPhoneNumber())
                .age(client.getAge())
                .address(client.getAddress())
                .state(client.getState())
                .gender(client.getGender())
                .build();
    }

    @Test
    void testGetClientById_ShouldReturnClientTO() {
        when(clientRepository.findById(client.getId())).thenReturn(Optional.of(client));

        ClientTO result = clientService.getClientById(client.getId());

        assertNotNull(result);
        assertEquals(client.getName(), result.getName());
        assertEquals(client.getIdentificationId(), result.getIdentificationId());
    }

    @Test
    void testGetClientById_NotFound_ShouldThrowException() {
        UUID invalidId = UUID.randomUUID();
        when(clientRepository.findById(invalidId)).thenReturn(Optional.empty());

        assertThrows(CustomNotFoundException.class, () -> clientService.getClientById(invalidId));
    }

    @Test
    void testGetAll_ShouldReturnClientList() {
        when(clientRepository.findAll()).thenReturn(List.of(client));

        List<ClientTO> result = clientService.getAll();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(client.getName(), result.get(0).getName());
    }

    @Test
    void testCreateClient_ShouldReturnCreatedClientTO() {
        when(clientRepository.saveAndFlush(any(Client.class))).thenReturn(client);

        ClientTO result = clientService.createClient(clientTO);

        assertNotNull(result);
        assertEquals(clientTO.getName(), result.getName());
    }

    @Test
    void testUpdateClient_ShouldReturnUpdatedClientTO() {
        when(clientRepository.findById(client.getId())).thenReturn(Optional.of(client));
        when(clientRepository.saveAndFlush(any(Client.class))).thenReturn(client);

        ClientTO result = clientService.updateClient(clientTO, client.getId());

        assertNotNull(result);
        assertEquals(clientTO.getName(), result.getName());
    }

    @Test
    void testDeleteClient_ShouldCallRepositoryDelete() {
        doNothing().when(clientRepository).deleteById(client.getId());

        assertDoesNotThrow(() -> clientService.deleteClient(client.getId()));
        verify(clientRepository, times(1)).deleteById(client.getId());
    }
}