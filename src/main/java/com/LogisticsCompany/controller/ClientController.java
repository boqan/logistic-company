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
/**
 * Handles client-related HTTP requests, providing CRUD operations and client registration.
 * Uses {@link ClientService} and {@link CredentialsService} to process client data and authentication respectively.
 */
@RestController
public class ClientController {
    @Autowired
    ClientService clientService;

    @Autowired
    CredentialsService credentialsService;

    /**
     * Retrieves a client by their ID.
     *
     * @param id The ID of the client to retrieve.
     * @return ResponseEntity containing the client data and the HTTP status.
     * @throws InterruptedException if thread sleep is interrupted.
     */
    @RequestMapping(value = {"/client/{id}"})
    @ResponseBody
    public ResponseEntity<ClientDTO> getClient(@PathVariable long id) throws InterruptedException {
        ClientDTO client = clientService.getClient(id);
        Thread.sleep(600);
        return new ResponseEntity<>(client, HttpStatus.OK);

    }
    /**
     * Retrieves a list of all clients.
     *
     * @return ResponseEntity containing the list of clients and the HTTP status.
     */
    @RequestMapping(value = {"/clients"})
    @ResponseBody //tells a controller that the object returned is automatically serialized into JSON and passed back into the HttpResponse object.
    public ResponseEntity<List<ClientDTO>> getClient(){
        List<ClientDTO> clients = clientService.getClients();
        return new ResponseEntity<>(clients, HttpStatus.OK);

    }
    /**
     * Deletes a client by their ID.
     *
     * @param clientId The ID of the client to delete.
     * @return ResponseEntity with confirmation message and HTTP status.
     */
    @DeleteMapping(value = {"/client/{clientId}"})
    @ResponseBody
    public ResponseEntity<String> deleteClient(@PathVariable Long clientId) {

        if (clientService.deleteClient(clientId)) {
            return new ResponseEntity<>("Client deleted successfully", HttpStatus.OK);
        } else {
            throw new EntityNotFoundException("Client not found.");
        }

    }
    /**
     * Creates a new client.
     *
     * @param newClient The client data to create.
     * @return ResponseEntity with confirmation message and HTTP status.
     */
    @PostMapping(value = {"/client"})
    @ResponseBody
    public ResponseEntity<String> createClient(@RequestBody ClientDTO newClient) {
        clientService.createClient(newClient);
        return new ResponseEntity<>("Client created successfully \n" + newClient, HttpStatus.CREATED);

    }
    /**
     * Updates an existing client.
     *
     * @param newClient The updated client data.
     * @param clientId The ID of the client to update.
     * @return ResponseEntity with confirmation message and HTTP status.
     */
    @PutMapping(value = {"/client/{clientId}"})
    @ResponseBody
    public ResponseEntity<String> updateClient(@RequestBody ClientDTO newClient,@PathVariable Long clientId) {
        clientService.updateClient(newClient,clientId);
        return new ResponseEntity<>("Client updated successfully \n" + newClient, HttpStatus.CREATED);
    }
    /**
     * Registers client credentials, typically used for client authentication.
     *
     * @param registerClientRequest The client registration request data.
     * @return ResponseEntity containing the authentication response and HTTP status.
     * @throws EntityAlreadyExistsInDbException if the client already exists.
     * @throws InvalidDTOException if the provided DTO is invalid.
     */
    @PostMapping("/register_client")
    public ResponseEntity<AuthenticationResponce> saveClientCredentials(@RequestBody RegisterClientRequest registerClientRequest) throws EntityAlreadyExistsInDbException, InvalidDTOException {
        return ResponseEntity.ok(credentialsService.registerClient(registerClientRequest));
    }


}