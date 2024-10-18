package tn.esprit.projectbackend.Controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.projectbackend.Entity.Ordre;
import tn.esprit.projectbackend.Service.IOrdreService;

import java.util.List;
@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/Ordre")
public class OrdreController {

    private IOrdreService orderService;

    @PostMapping("/add-Orders")
    public ResponseEntity<?> addOrder(@Valid @RequestBody Ordre ordre) {
        try {
            ordre.validate();
            orderService.save(ordre);
            return ResponseEntity.status(HttpStatus.CREATED).body(ordre);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/modify-Orders")
    public Ordre modifyOrder(@RequestBody Ordre o) {
        Ordre ordre =orderService.modifyOrder(o);
        return ordre;
    }
    @GetMapping("/retrieve-all-Orders")
    @Operation(description = "WS de récuperation de tous les ordres ")
    public List<Ordre> getOrders() {
        return orderService.retrieveAllOrders();
        //return listBlocs;
    }
    @DeleteMapping("/remove-Order/{ordre-id}")
    public void removeOrder(@PathVariable("ordre-id") Long id) {
        orderService.removeOrder(id);
    }

    @GetMapping("/retrieve-Order/{ordre-id}")
    public Ordre retrieveUniversite(@PathVariable("ordre-id") Long id) {
        Ordre ordre = orderService.retrieveOrder(id);
        return ordre;
    }
}
