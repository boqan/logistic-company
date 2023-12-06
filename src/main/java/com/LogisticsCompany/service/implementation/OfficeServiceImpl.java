package com.LogisticsCompany.service.implementation;

import com.LogisticsCompany.dto.OfficeDTOnoCompany;
import com.LogisticsCompany.dto.OrderDTOnoOffice;
import com.LogisticsCompany.enums.DeliveryStatus;
import com.LogisticsCompany.error.OfficeNotFoundException;
import com.LogisticsCompany.mapper.EntityMapper;
import com.LogisticsCompany.model.Office;
import com.LogisticsCompany.repository.OfficeRepository;
import com.LogisticsCompany.service.OfficeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OfficeServiceImpl implements OfficeService {

    @Autowired
    private OfficeRepository officeRepository;
    @Autowired
    private EntityMapper entityMapper;

    @Override
    public List<OrderDTOnoOffice> getOrdersByDeliveryStatus(Long officeId, DeliveryStatus deliveryStatus) throws OfficeNotFoundException {
        Optional<Office> office = officeRepository.findById(officeId);
        if(!office.isPresent()){
            throw new OfficeNotFoundException("Office not available");
        }
        OfficeDTOnoCompany officeDTOnoCompany = entityMapper.mapToOfficeDTOnoCompany(office.get());
        return officeDTOnoCompany.getOrders().stream().
                filter(orderDTOnoOffice -> orderDTOnoOffice
                        .getDeliveryStatus()
                        .equals(deliveryStatus))
                        .collect(Collectors.toList());
    }

    @Override
    public OfficeDTOnoCompany fetchOfficeById(Long officeId) throws OfficeNotFoundException {
        Optional<Office> office = officeRepository.findById(officeId);
        if(!office.isPresent()){
            throw new OfficeNotFoundException("Office not available");
        }
        OfficeDTOnoCompany officeDTOnoCompany = entityMapper.mapToOfficeDTOnoCompany(office.get());
        return officeDTOnoCompany;
    }
}
