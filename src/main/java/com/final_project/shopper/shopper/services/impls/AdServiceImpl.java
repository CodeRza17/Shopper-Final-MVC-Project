package com.final_project.shopper.shopper.services.impls;

import com.final_project.shopper.shopper.dtos.ad.AdCreateDto;
import com.final_project.shopper.shopper.dtos.ad.AdDashboardDto;
import com.final_project.shopper.shopper.dtos.ad.AdHomeDto;
import com.final_project.shopper.shopper.enums.TimeType;
import com.final_project.shopper.shopper.models.Ad;
import com.final_project.shopper.shopper.models.AdPaymentInfo;
import com.final_project.shopper.shopper.models.User;
import com.final_project.shopper.shopper.repositories.AdPaymentInfoRepository;
import com.final_project.shopper.shopper.repositories.AdRepository;
import com.final_project.shopper.shopper.repositories.BrandRepository;
import com.final_project.shopper.shopper.repositories.UserRepository;
import com.final_project.shopper.shopper.services.AdService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AdServiceImpl implements AdService {
    private final AdRepository adRepository;
    private final BrandRepository brandRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final AdPaymentInfoRepository adPaymentInfoRepository;

    public AdServiceImpl(AdRepository adRepository, BrandRepository brandRepository, UserRepository userRepository, ModelMapper modelMapper, AdPaymentInfoRepository adPaymentInfoRepository) {
        this.adRepository = adRepository;
        this.brandRepository = brandRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.adPaymentInfoRepository = adPaymentInfoRepository;
    }


    @Override
    public List<AdDashboardDto> findAllByBrand(String email) {
        User user = userRepository.findByEmail(email);
        List<Ad> findAds = adRepository.findAllByBrand(user.getBrand());

        LocalDateTime now = LocalDateTime.now();


        findAds.forEach(ad -> {
            if (ad.getActivationDate() != null && ad.getActivationDate().isBefore(now) && ad.isActive()) {
                ad.setActive(false);
                adRepository.save(ad);
            }
        });


        return findAds.stream().map(ad -> modelMapper.map(ad, AdDashboardDto.class)).toList();
    }


    @Override
    public boolean createAd(String email, AdCreateDto adCreateDto) {
        try {
            User user = userRepository.findByEmail(email);
            Ad ad = new Ad();
            ad.setBrand(user.getBrand());
            ad.setPhotoUrl(adCreateDto.getPhotoUrl());


            AdPaymentInfo adPaymentInfo = adPaymentInfoRepository.findById(adCreateDto.getAdPaymentInfoId())
                    .orElseThrow(() -> new RuntimeException("Payment info not found"));

            LocalDateTime localDateTime = LocalDateTime.now();
            TimeType findTimeType = adPaymentInfo.getTimeType();
            Integer timeCount = adPaymentInfo.getTimeCount();

            switch (findTimeType) {
                case SECOND -> localDateTime = localDateTime.plusSeconds(timeCount);
                case MINUTE -> localDateTime = localDateTime.plusMinutes(timeCount);
                case HOUR -> localDateTime = localDateTime.plusHours(timeCount);
                case DAY -> localDateTime = localDateTime.plusDays(timeCount);
            }

            ad.setActivationDate(localDateTime);
            ad.setActive(true);

            adRepository.save(ad);
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteAd(Long id) {
        try{
            adRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<AdHomeDto> findAllActiveAds() {
        LocalDateTime now = LocalDateTime.now();


        List<Ad> adList = adRepository.findAll();


        for (Ad ad : adList) {
            if (ad.getActivationDate() != null && ad.getActivationDate().isBefore(now)) {

                if (ad.isActive()) {
                    ad.setActive(false);
                    adRepository.save(ad);
                }
            }
        }


        return adList.stream().filter(Ad::isActive).map(ad -> modelMapper.map(ad, AdHomeDto.class)).toList();
    }


}
