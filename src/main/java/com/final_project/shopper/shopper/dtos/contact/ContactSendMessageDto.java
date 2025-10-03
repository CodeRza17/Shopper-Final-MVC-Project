package com.final_project.shopper.shopper.dtos.contact;

import com.final_project.shopper.shopper.models.Brand;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContactSendMessageDto {
    private String message;
}
