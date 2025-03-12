package service_client.service_client.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import service_client.service_client.service.ClientService;
import service_client.service_client.utils.dto.ClientTO;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/clientes")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping
    public List<ClientTO> getAll() {
        return clientService.getAll();
    }
    @GetMapping("/{id}")
    public ClientTO getClientById(@PathVariable UUID id) {
        return clientService.getClientById(id);
    }
    @PostMapping
    public ClientTO create(@RequestBody ClientTO clientTO) {
        return clientService.createClient(clientTO);
    }
    @PutMapping("/{id}")
    public ClientTO update(@PathVariable UUID id, @RequestBody ClientTO clientTO) {
        return clientService.updateClient(clientTO, id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        clientService.deleteClient(id);
    }

}
