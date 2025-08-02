package com.final_project.shopper.shopper.sevices;

import com.final_project.shopper.shopper.dtos.auth.UserRegisterDto;
import com.final_project.shopper.shopper.dtos.user.UserDashboardDto;
import com.final_project.shopper.shopper.dtos.user.UserEditDto;
import com.final_project.shopper.shopper.models.Role;
import com.final_project.shopper.shopper.models.User;

import java.security.Principal;
import java.util.List;

public interface UserService {

    boolean register(UserRegisterDto userRegisterDto);

    User findUserByEmail(String username);

    Double getRewardPoint(String email);


    List<UserDashboardDto> getUsersByBrand(Principal principal);

    List<Role> getEditableRoles();

    void updateUser(UserEditDto userEditDto);

    void deleteUserById(Long id);
}
