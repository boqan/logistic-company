package com.LogisticsCompany.controller;

import com.LogisticsCompany.dto.AuthenticationResponce;
import com.LogisticsCompany.dto.ClientDTO;
import com.LogisticsCompany.dto.RegisterClientRequest;
import com.LogisticsCompany.dto.RegisterEmployeeRequest;
import com.LogisticsCompany.error.EntityAlreadyExistsInDbException;
import com.LogisticsCompany.error.InvalidDTOException;
import com.LogisticsCompany.model.Client;
import com.LogisticsCompany.service.ClientService;
import com.LogisticsCompany.service.CredentialsService;
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

    @Autowired
    CredentialsService credentialsService;

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
    public ResponseEntity<String> deleteClient(@PathVariable Long clientId) {

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
    public ResponseEntity<String> updateClient(@RequestBody ClientDTO newClient,@PathVariable Long clientId) {
        clientService.updateClient(newClient,clientId);
        return new ResponseEntity<>("Client updated successfully \n" + newClient, HttpStatus.CREATED);
    }

    @PostMapping("/register_client")
    public ResponseEntity<AuthenticationResponce> saveClientCredentials(@RequestBody RegisterClientRequest registerClientRequest) throws EntityAlreadyExistsInDbException, InvalidDTOException {
        return ResponseEntity.ok(credentialsService.registerClient(registerClientRequest));
    }


}