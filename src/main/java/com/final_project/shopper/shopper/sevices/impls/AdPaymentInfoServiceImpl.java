package com.final_project.shopper.shopper.sevices.impls;

import com.final_project.shopper.shopper.dtos.adPaymentInfo.AdPaymentInfoDashboardDto;
import com.final_project.shopper.shopper.models.AdPaymentInfo;
import com.final_project.shopper.shopper.repositories.AdPaymentInfoRepository;
import com.final_project.shopper.shopper.sevices.AdPaymentInfoService;
import org.springframework.stereotype.Service;

@Service
public class AdPaymentInfoServiceImpl implements AdPaymentInfoService {
    private final AdPaymentInfoRepository adPaymentInfoRepository;

    public AdPaymentInfoServiceImpl(AdPaymentInfoRepository adPaymentInfoRepository) {
        this.adPaymentInfoRepository = adPaymentInfoRepository;
    }

    @Override
    public boolean addNewPaymentInfo(AdPaymentInfoDashboardDto adPaymentInfoDashboardDto) {
        try{
            AdPaymentInfo adPaymentInfo = new AdPaymentInfo();
            adPaymentInfo.setAmount(adPaymentInfoDashboardDto.getAmount());
            adPaymentInfo.setTimeType(adPaymentInfoDashboardDto.getTimeType());
            adPaymentInfo.setTimeCount(adPaymentInfoDashboardDto.getTimeCount());
            adPaymentInfoRepository.save(adPaymentInfo);
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
