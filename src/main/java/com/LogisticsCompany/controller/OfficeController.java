package com.LogisticsCompany.controller;

import com.LogisticsCompany.dto.OfficeDTOnoCompany;
import com.LogisticsCompany.dto.OrderDTOnoOffice;
import com.LogisticsCompany.enums.DeliveryStatus;
import com.LogisticsCompany.error.OfficeNotFoundException;
import com.LogisticsCompany.model.Office;
import com.LogisticsCompany.service.OfficeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
public class OfficeController {

    @Autowired
    private OfficeService officeService;

    @PostMapping("/offices")
    public Office saveOffice(@Valid @RequestBody Office office){
        return officeService.saveOffice(office);
    }

    @GetMapping("/offices")
    public List<Office> fetchOfficeList(){
        return officeService.fetchOfficeList();

    }


    @GetMapping("/office/{id}")
    public OfficeDTOnoCompany getOfficeById(@PathVariable("id") Long officeId)
            throws OfficeNotFoundException {

        return officeService.fetchOfficeById(officeId);
    }


    @GetMapping("/office_orders/{id}/{status}")
    public List<OrderDTOnoOffice> getOrdersByDeliveryStatus(@PathVariable("id") Long officeId,
                                            @PathVariable("status") DeliveryStatus deliveryStatus)
            throws OfficeNotFoundException {

        return officeService.getOrdersByDeliveryStatus(officeId,deliveryStatus);
    }

    @GetMapping("/office_orders/{id}/{status}/receiver/{clientId}")
    public List<OrderDTOnoOffice> getOrdersByDeliveryStatusAndReceiverId(@PathVariable("id") Long officeId,
                                            @PathVariable("status") DeliveryStatus deliveryStatus ,
                            @PathVariable("clientId") Long clientId)
            throws OfficeNotFoundException {

        return officeService.getOrdersByDeliveryStatusAndReceiverId(officeId,deliveryStatus,clientId);
    }

    @GetMapping("/office_orders/{id}/{status}/sender/{clientId}")
    public List<OrderDTOnoOffice> getOrdersByDeliveryStatusAndSenderId(@PathVariable("id") Long officeId,
                                                                       @PathVariable("status") DeliveryStatus deliveryStatus ,
                                                                       @PathVariable("clientId") Long clientId)
            throws OfficeNotFoundException {

        return officeService.getOrdersByDeliveryStatusAndSenderId(officeId,deliveryStatus,clientId);
    }


    @DeleteMapping("/office/{id}")
    public String deleteOfficeById(@PathVariable("id")Long officeId) throws  OfficeNotFoundException {
        officeService.deleteOfficeById(officeId);
        return "Logistic Company deleted successfully!!";
    }

    @PutMapping("/office/{id}")
    public Office updateOffice(@PathVariable("id")Long officeId ,
                                         @RequestBody Office office) throws OfficeNotFoundException {
        return officeService.updateOffice(officeId,office);
    }


}
