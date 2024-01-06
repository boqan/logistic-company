package com.LogisticsCompany.controller;

import com.LogisticsCompany.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class ClientController {
    @Autowired
    ClientService clientService;

}
