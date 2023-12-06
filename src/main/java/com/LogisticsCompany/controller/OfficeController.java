package com.LogisticsCompany.controller;

import com.LogisticsCompany.dto.OfficeDTOnoCompany;
import com.LogisticsCompany.dto.OrderDTOnoOffice;
import com.LogisticsCompany.enums.DeliveryStatus;
import com.LogisticsCompany.error.OfficeNotFoundException;
import com.LogisticsCompany.service.OfficeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
public class OfficeController {

    @Autowired
    private OfficeService officeService;

    @GetMapping("/office/{id}")
    public OfficeDTOnoCompany getOfficeById(@PathVariable("id") Long officeId)
            throws OfficeNotFoundException {

        return officeService.fetchOfficeById(officeId);
    }


    @GetMapping("/office_orders/{id}/{status}")
    public List<OrderDTOnoOffice> getOrders(@PathVariable("id") Long officeId,
                                            @PathVariable("status") DeliveryStatus deliveryStatus)
            throws OfficeNotFoundException {

        return officeService.getOrdersByDeliveryStatus(officeId,deliveryStatus);
    }


}
