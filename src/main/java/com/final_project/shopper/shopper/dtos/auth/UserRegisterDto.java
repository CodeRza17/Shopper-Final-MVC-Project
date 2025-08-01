package com.final_project.shopper.shopper.dtos.auth;

import com.final_project.shopper.shopper.models.Role;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterDto {
        private String name;
        private String surname;
        private String email;
        private String password;
        private String roleName;
        private Long brandId;
        private Boolean adminRequest;
        private String brandOwnerCode;
}
