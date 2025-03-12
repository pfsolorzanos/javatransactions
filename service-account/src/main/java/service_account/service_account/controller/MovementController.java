package service_account.service_account.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import service_account.service_account.service.MovementService;
import service_account.service_account.utils.dto.MovementTO;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/movimientos")
public class MovementController {

    @Autowired
    private MovementService movementService;

    @GetMapping("/{id}")
    public MovementTO getById(@PathVariable UUID id) {
        return movementService.getById(id);
    }
    @GetMapping
    public List<MovementTO> getAll() {
        return movementService.getAll();
    }
    @PostMapping
    public MovementTO create(@RequestBody MovementTO movementTO) {
        return movementService.createMovement(movementTO);
    }
    @PutMapping("/{id}")
    public MovementTO update(@PathVariable UUID id, @RequestBody MovementTO movementTO) {
        return movementService.updateMovement(movementTO, id);
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        movementService.deleteMovement(id);
    }
}
