package com.final_project.shopper.shopper.sevices.impls;

import com.final_project.shopper.shopper.dtos.contactInfo.ContactInfoDto;
import com.final_project.shopper.shopper.models.Brand;
import com.final_project.shopper.shopper.models.ContactInfo;
import com.final_project.shopper.shopper.models.User;
import com.final_project.shopper.shopper.repositories.ContactInfoRepository;
import com.final_project.shopper.shopper.repositories.UserRepository;
import com.final_project.shopper.shopper.sevices.ContactInfoService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class ContactInfoServiceImpl implements ContactInfoService {
    private final ContactInfoRepository contactInfoRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;


    public ContactInfoServiceImpl(UserRepository userRepository, ContactInfoRepository contactInfoRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.contactInfoRepository = contactInfoRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ContactInfoDto getUpdatedContactInfos() {
        String email = UserDetailService.getCurrentUserEmail();
        User user = userRepository.findByEmail(email);
        Brand findBrand = user.getBrand();
        ContactInfo findContactInfo = findBrand.getContactInfo();
        ContactInfoDto contactInfoDto = modelMapper.map(findContactInfo, ContactInfoDto.class);
        return contactInfoDto;
    }
}
