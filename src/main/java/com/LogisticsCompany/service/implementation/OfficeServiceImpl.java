package com.LogisticsCompany.service.implementation;

import com.LogisticsCompany.dto.OfficeDTOnoCompany;
import com.LogisticsCompany.dto.OrderDTOnoOffice;
import com.LogisticsCompany.enums.DeliveryStatus;
import com.LogisticsCompany.error.LogisticCompanyNotFoundException;
import com.LogisticsCompany.error.OfficeNotFoundException;
import com.LogisticsCompany.mapper.EntityMapper;
import com.LogisticsCompany.model.LogisticCompany;
import com.LogisticsCompany.model.Office;
import com.LogisticsCompany.repository.OfficeRepository;
import com.LogisticsCompany.service.OfficeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
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

    @Override
    public List<OrderDTOnoOffice> getOrdersByDeliveryStatusAndReceiverId(Long officeId, DeliveryStatus deliveryStatus, Long clientId) throws OfficeNotFoundException {
        Optional<Office> office = officeRepository.findById(officeId);
        if(!office.isPresent()){
            throw new OfficeNotFoundException("Office not available");
        }
        OfficeDTOnoCompany officeDTOnoCompany = entityMapper.mapToOfficeDTOnoCompany(office.get());
        return officeDTOnoCompany.getOrders().stream().
                filter(orderDTOnoOffice -> orderDTOnoOffice
                        .getDeliveryStatus()
                        .equals(deliveryStatus))
                .collect(Collectors.toList()).stream()
                .filter(orderDTOnoOffice -> orderDTOnoOffice
                        .getReceiver()
                        .getId()
                        .equals(clientId))
                .collect(Collectors.toList());
    }


    @Override
    public List<OrderDTOnoOffice> getOrdersByDeliveryStatusAndSenderId(Long officeId, DeliveryStatus deliveryStatus, Long clientId) throws OfficeNotFoundException {
        Optional<Office> office = officeRepository.findById(officeId);
        if(!office.isPresent()){
            throw new OfficeNotFoundException("Office not available");
        }
        OfficeDTOnoCompany officeDTOnoCompany = entityMapper.mapToOfficeDTOnoCompany(office.get());
        return officeDTOnoCompany.getOrders().stream().
                filter(orderDTOnoOffice -> orderDTOnoOffice
                        .getDeliveryStatus()
                        .equals(deliveryStatus))
                .collect(Collectors.toList()).stream()
                .filter(orderDTOnoOffice -> orderDTOnoOffice
                        .getSender()
                        .getId()
                        .equals(clientId))
                .collect(Collectors.toList());
    }

    @Override
    public Office saveOffice(Office office) {
        return officeRepository.save(office);
    }

    @Override
    public List<Office> fetchOfficeList() {
        return officeRepository.findAll();
    }

    @Override
    public void deleteOfficeById(Long officeId) throws OfficeNotFoundException {
        Optional<Office> office = officeRepository.findById(officeId);
        if(!office.isPresent()){
            throw new OfficeNotFoundException("Office not available !");
        }
        officeRepository.deleteById(officeId);
    }

    @Override
    public Office updateOffice(Long officeId, Office office) throws OfficeNotFoundException {
        Optional<Office> currOffice = officeRepository.findById(officeId);
        if(!currOffice.isPresent()){
            throw new OfficeNotFoundException("Office not available !");
        }
        Office officeDb=currOffice.get();

        if(Objects.nonNull(office.getAddress()) && !"".equals(office.getAddress())){
            officeDb.setAddress(office.getAddress());
        }
        if(Objects.nonNull(office.getRevenue()) && !"".equals(office.getRevenue())){
            officeDb.setRevenue(office.getRevenue());
        }

        return officeRepository.save(officeDb);
    }

}
