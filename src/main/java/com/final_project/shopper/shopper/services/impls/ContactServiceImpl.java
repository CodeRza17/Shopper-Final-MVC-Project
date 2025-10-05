package com.final_project.shopper.shopper.services.impls;

import com.final_project.shopper.shopper.dtos.contact.ContactDashboardDto;
import com.final_project.shopper.shopper.dtos.contact.ContactMessageDetailDto;
import com.final_project.shopper.shopper.dtos.contact.ContactSendMessageDto;
import com.final_project.shopper.shopper.models.Brand;
import com.final_project.shopper.shopper.models.Contact;
import com.final_project.shopper.shopper.models.User;
import com.final_project.shopper.shopper.repositories.BrandRepository;
import com.final_project.shopper.shopper.repositories.ContactRepository;
import com.final_project.shopper.shopper.services.ContactService;
import com.final_project.shopper.shopper.services.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ContactServiceImpl implements ContactService {
    public final ContactRepository contactRepository;
    public final UserService userService;
    private final BrandRepository brandRepository;
    private final ModelMapper modelMapper;

    @Override
    public boolean sendMessageToBrand(ContactSendMessageDto contactSendMessageDto, String email, Long brandId) {
        try {
            User findUser = userService.findUserByEmail(email);
            String fullName = findUser.getName() + " " + findUser.getSurname();
            Contact contact = new Contact();
            contact.setFullName(fullName);
            contact.setEmail(email);
            contact.setBrand(brandRepository.findById(brandId).orElseThrow());
            contact.setMessage(contactSendMessageDto.getMessage());
            contact.setRead(false);
            contactRepository.save(contact);
            return true;

        } catch (Exception e) {
            throw new RuntimeException(e);

        }
    }

    @Override
    public List<ContactDashboardDto> findByBrandId(Long id) {
        List<Contact> findContacts = contactRepository.findAllByBrandId(id);
        List<ContactDashboardDto> contactDashboardDtoList = findContacts.stream().map(contact -> modelMapper.map(contact, ContactDashboardDto.class)).toList();
        for (ContactDashboardDto contactDashboardDto:contactDashboardDtoList){
            String message = contactDashboardDto.getMessage().substring(0,10);
            message+="....";
            contactDashboardDto.setMessage(message);
        }

        return contactDashboardDtoList;
    }

    @Override
    public ContactMessageDetailDto findMessageById(Brand brand, Long id) {
        Contact findContact = contactRepository.findById(id).orElseThrow();
        if (brand.getId() == findContact.getBrand().getId()){
            ContactMessageDetailDto contactMessageDetailDto = modelMapper.map(findContact, ContactMessageDetailDto.class);
            return contactMessageDetailDto;
        }else {
            return null;
        }

    }

    @Override
    public boolean readMessage(Long id) {
        try {
            Contact contact = contactRepository.findById(id).orElseThrow();
            boolean result = contact.isRead();
            if(!result){
                contact.setRead(true);
                contactRepository.save(contact);
            }
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
