package com.LogisticsCompany.controller;

import com.LogisticsCompany.dto.ClientDTO;
import com.LogisticsCompany.model.Client;
import com.LogisticsCompany.service.ClientService;
import com.sun.jdi.InternalException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ClientController {
    @Autowired
    ClientService clientService;

    @RequestMapping(value = {"/client/{id}"})
    @ResponseBody
    public ResponseEntity<ClientDTO> getClient(@PathVariable long id) throws InterruptedException {
        ClientDTO client = clientService.getClient(id);
        Thread.sleep(600);
        return new ResponseEntity<>(client, HttpStatus.OK);

    }

    @RequestMapping(value = {"/clients"})
    @ResponseBody //tells a controller that the object returned is automatically serialized into JSON and passed back into the HttpResponse object.
    public ResponseEntity<List<ClientDTO>> getClient(){
        List<ClientDTO> clients = clientService.getClients();
        return new ResponseEntity<>(clients, HttpStatus.OK);

    }

    @DeleteMapping(value = {"/client/{clientId}"})
    @ResponseBody
    public ResponseEntity<String> deleteClient(@PathVariable long clientId) {

        if (clientService.deleteClient(clientId)) {
            return new ResponseEntity<>("Client deleted successfully", HttpStatus.OK);
        } else {
            throw new EntityNotFoundException("Client not found.");
        }

    }

    @PostMapping(value = {"/client"})
    @ResponseBody
    public ResponseEntity<String> createClient(@RequestBody ClientDTO newClient) {
        clientService.createClient(newClient);
        return new ResponseEntity<>("Client created successfully \n" + newClient, HttpStatus.CREATED);

    }

    @PutMapping(value = {"/client/{clientId}"})
    @ResponseBody
    public ResponseEntity<String> updateClient(@RequestBody ClientDTO newClient) {
        clientService.updateClient(newClient);
        return new ResponseEntity<>("Client updated successfully \n" + newClient, HttpStatus.CREATED);
    }
}