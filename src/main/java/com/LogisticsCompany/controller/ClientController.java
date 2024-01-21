package com.LogisticsCompany.controller;

import com.LogisticsCompany.dto.ClientDTO;
import com.LogisticsCompany.model.Client;
import com.LogisticsCompany.service.ClientService;
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
    public ResponseEntity<ClientDTO> getClient(@PathVariable long id) {
        try {
            ClientDTO client = clientService.getClient(id);
            return new ResponseEntity<>(client, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = {"/client"})
    @ResponseBody //tells a controller that the object returned is automatically serialized into JSON and passed back into the HttpResponse object.
    public ResponseEntity<List<ClientDTO>> getClient(){
        try{
            List<ClientDTO> clients = clientService.getClients();
            if (clients.isEmpty()){
                return  new ResponseEntity<>(clients, HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(clients, HttpStatus.OK);
        }catch (Exception e){
            return  new ResponseEntity<> (HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = {"/client/{clientId}"})
    @ResponseBody
    public ResponseEntity<String> deleteClient(@PathVariable long clientId) {
        try {
            if (clientService.deleteClient(clientId)) {
                return new ResponseEntity<>("Client deleted successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Client not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = {"/client"})
    @ResponseBody
    public ResponseEntity<String> createClient(@RequestBody ClientDTO newClient) {
        try {
            // Assuming clientService.createClient() returns the newly created client.
            clientService.createClient(newClient);

            // You might want to return the created client information or just a success message.
            return new ResponseEntity<>("Client created successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}