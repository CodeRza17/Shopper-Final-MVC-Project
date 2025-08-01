package com.final_project.shopper.shopper.sevices.impls;

import com.final_project.shopper.shopper.dtos.brand.BrandCreateDto;
import com.final_project.shopper.shopper.dtos.brand.BrandDashboardDto;
import com.final_project.shopper.shopper.dtos.brand.BrandDeleteDto;
import com.final_project.shopper.shopper.dtos.brand.BrandUpdateDto;
import com.final_project.shopper.shopper.dtos.contactInfo.ContactInfoDto;
import com.final_project.shopper.shopper.models.Brand;
import com.final_project.shopper.shopper.models.ContactInfo;
import com.final_project.shopper.shopper.models.Product;
import com.final_project.shopper.shopper.models.User;
import com.final_project.shopper.shopper.repositories.BrandRepository;
import com.final_project.shopper.shopper.repositories.ContactInfoRepository;
import com.final_project.shopper.shopper.repositories.ProductRepository;
import com.final_project.shopper.shopper.repositories.UserRepository;
import com.final_project.shopper.shopper.sevices.BrandService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandServiceImpl implements BrandService {
    private final BrandRepository brandRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final ContactInfoRepository contactInfoRepository;
    private final ProductRepository productRepository;

    public BrandServiceImpl(BrandRepository brandRepository, UserRepository userRepository, ModelMapper modelMapper, ContactInfoRepository contactInfoRepository, ProductRepository productRepository) {
        this.brandRepository = brandRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.contactInfoRepository = contactInfoRepository;
        this.productRepository = productRepository;
    }

    @Override
    public boolean create(BrandCreateDto brandCreateDto) {
        Brand findBrand = brandRepository.findByName(brandCreateDto.getName());
        if (findBrand != null){
            return false;
        }

        ContactInfo contactInfo = new ContactInfo();
        contactInfo.setAddressMap(brandCreateDto.getContactInfo().getAddressMap());
        contactInfo.setAddress(brandCreateDto.getContactInfo().getAddress());
        contactInfo.setEmail(brandCreateDto.getContactInfo().getEmail());
        contactInfo.setPhoneNumber(brandCreateDto.getContactInfo().getPhoneNumber());

        Brand brand = new Brand();
        brand.setName(brandCreateDto.getName());
        brand.setBradOwnerCode(brandCreateDto.getBradOwnerCode());
        brand.setLogoUrl(brandCreateDto.getLogoUrl());
        brand.setContactInfo(contactInfo);
        brand.setSalesCount(0L);

        brandRepository.save(brand);

        return true;
    }

    @Override
    public BrandDashboardDto getBrand() {
        String email = UserDetailService.getCurrentUserEmail();
        User user = userRepository.findByEmail(email);
        Brand brand = user.getBrand();
        BrandDashboardDto brandDashboardDto = modelMapper.map(brand, BrandDashboardDto.class);
        return brandDashboardDto;
    }

    @Override
    public boolean UpdateBrand(BrandUpdateDto brandUpdateDto, ContactInfoDto contactInfoDto) {
        try {
            String email = UserDetailService.getCurrentUserEmail();
            User user = userRepository.findByEmail(email);
            Brand findBrand = user.getBrand();

            ContactInfo findContactInfo = contactInfoRepository.findById(findBrand.getId()).orElseThrow();

            findContactInfo.setAddress(contactInfoDto.getAddress());
            findContactInfo.setPhoneNumber(contactInfoDto.getPhoneNumber());
            findContactInfo.setAddressMap(contactInfoDto.getAddressMap());
            findContactInfo.setEmail(contactInfoDto.getEmail());

            findBrand.setName(brandUpdateDto.getName());
            findBrand.setLogoUrl(brandUpdateDto.getLogoUrl());
            findBrand.setContactInfo(findContactInfo);

            brandRepository.save(findBrand);
            return true;

        }catch (Exception e) {
            return false;
        }
    }

    @Override
    public BrandUpdateDto getUpdatedBrand() {
        String email = UserDetailService.getCurrentUserEmail();
        User user = userRepository.findByEmail(email);
        Brand findBrand = user.getBrand();
        BrandUpdateDto brandUpdateDto = modelMapper.map(findBrand, BrandUpdateDto.class);
        return brandUpdateDto;
    }

    @Override
    public boolean deleteBrand() {
        try {

            String email = UserDetailService.getCurrentUserEmail();
            User currentUser = userRepository.findByEmail(email);


            Brand brand = currentUser.getBrand();

            if (brand == null) {
                return false;
            }

            Long brandId = brand.getId();


            List<Product> products = productRepository.findAllByBrandId(brandId);
            productRepository.deleteAll(products);


            List<User> users = userRepository.findAllByBrandId(brandId);
            userRepository.deleteAll(users);


            ContactInfo contactInfo = brand.getContactInfo();
            if (contactInfo != null) {
                contactInfoRepository.delete(contactInfo);
            }


            brandRepository.delete(brand);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public BrandDeleteDto getDeletedBrand() {
        String email = UserDetailService.getCurrentUserEmail();
        User user = userRepository.findByEmail(email);
        Brand findBrand = user.getBrand();
        return modelMapper.map(findBrand, BrandDeleteDto.class);
    }

}
