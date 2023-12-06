package com.LogisticsCompany.service;

import com.LogisticsCompany.dto.OfficeDTOnoCompany;
import com.LogisticsCompany.dto.OrderDTOnoOffice;
import com.LogisticsCompany.enums.DeliveryStatus;
import com.LogisticsCompany.error.OfficeNotFoundException;

import java.util.List;

public interface OfficeService {

    List<OrderDTOnoOffice> getOrdersByDeliveryStatus(Long officeId, DeliveryStatus deliveryStatus) throws OfficeNotFoundException;

    OfficeDTOnoCompany fetchOfficeById(Long officeId) throws OfficeNotFoundException;
}
