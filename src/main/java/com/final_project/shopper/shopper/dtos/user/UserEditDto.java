package com.final_project.shopper.shopper.dtos.user;


import com.final_project.shopper.shopper.models.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEditDto {
    private Long id;
    private String name;
    private String surname;
    private String email;
    private String roleName;
}
