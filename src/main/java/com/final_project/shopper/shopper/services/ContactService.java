package com.final_project.shopper.shopper.services;

import com.final_project.shopper.shopper.dtos.contact.ContactDashboardDto;
import com.final_project.shopper.shopper.dtos.contact.ContactMessageDetailDto;
import com.final_project.shopper.shopper.dtos.contact.ContactSendMessageDto;
import com.final_project.shopper.shopper.models.Brand;

import java.util.List;

public interface ContactService {
    boolean sendMessageToBrand(ContactSendMessageDto contactSendMessageDto, String email, Long brandId);

    List<ContactDashboardDto> findByBrandId(Long id);

    ContactMessageDetailDto findMessageById(Brand brand, Long id);

    boolean readMessage(Long id);
}
