package service_client.service_client.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import service_client.service_client.repository.ClientRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class ClientTest {

    @Autowired
    private ClientRepository clientRepository;

    @Test
    void testSaveClient_ShouldPersistData() {
        Client client = new Client();
        client.setPassword("securePass123");
        client.setState(true);

        Client savedClient = clientRepository.save(client);
        assertNotNull(savedClient.getId());
        assertEquals("securePass123", savedClient.getPassword());
        assertTrue(savedClient.getState());
    }

    @Test
    void testFindById_ShouldReturnClient() {
        Client client = new Client();
        client.setPassword("password123");
        client.setState(true);
        Client savedClient = clientRepository.save(client);

        Optional<Client> retrievedClient = clientRepository.findById(savedClient.getId());
        assertTrue(retrievedClient.isPresent());
        assertEquals("password123", retrievedClient.get().getPassword());
    }

    @Test
    void testDeleteClient_ShouldRemoveClient() {
        Client client = new Client();
        client.setPassword("toDeletePass");
        client.setState(false);
        Client savedClient = clientRepository.save(client);

        clientRepository.deleteById(savedClient.getId());
        Optional<Client> deletedClient = clientRepository.findById(savedClient.getId());
        assertFalse(deletedClient.isPresent());
    }
}