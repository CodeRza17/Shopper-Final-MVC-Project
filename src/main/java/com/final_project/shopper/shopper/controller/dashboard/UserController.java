package com.final_project.shopper.shopper.controller.dashboard;

import com.final_project.shopper.shopper.dtos.user.UserDashboardDto;
import com.final_project.shopper.shopper.dtos.user.UserEditDto;
import com.final_project.shopper.shopper.models.Role;
import com.final_project.shopper.shopper.models.User;
import com.final_project.shopper.shopper.repositories.RoleRepository;
import com.final_project.shopper.shopper.repositories.UserRepository;
import com.final_project.shopper.shopper.sevices.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;

    public UserController(UserService userService, UserRepository userRepository, RoleRepository roleRepository, ModelMapper modelMapper) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/dashboard/users")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public String users(Principal principal,  Model model){
        List<UserDashboardDto> userDashboardDtosList = userService.getUsersByBrand(principal);
        model.addAttribute("users", userDashboardDtosList);
        return "dashboard/users/index";
    }

    @GetMapping("/dashboard/users/edit/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public String editUser(@PathVariable Long id, Model model, Principal principal) {
        User user = userRepository.findById(id).orElseThrow();
        UserEditDto userEditDto = modelMapper.map(user, UserEditDto.class);

        User currentUser = userRepository.findByEmail(principal.getName());

        if (!user.getBrand().getId().equals(currentUser.getBrand().getId())) {
            throw new AccessDeniedException("You are not authorized to edit this user");
        }


        List<Role> roles = userService.getEditableRoles();

        model.addAttribute("user", userEditDto);
        model.addAttribute("roles", roles);

        return "dashboard/users/edit";
    }

    @PostMapping("/dashboard/users/edit")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public String updateUser(@ModelAttribute UserEditDto userEditDto) {
        userService.updateUser(userEditDto);
        return "redirect:/dashboard/users";
    }

    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @GetMapping("/dashboard/users/delete/{id}")
    public String deleteUserConfirm(@PathVariable Long id, Model model, Principal principal) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        User currentUser = userRepository.findByEmail(principal.getName());

        if (!user.getBrand().getId().equals(currentUser.getBrand().getId())) {
            throw new AccessDeniedException("You are not authorized to delete this user");
        }

        model.addAttribute("user", user);
        return "dashboard/users/delete";
    }


    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @PostMapping("/dashboard/users/delete")
    public String deleteUser(@RequestParam Long id, Principal principal) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        User currentUser = userRepository.findByEmail(principal.getName());

        if (!user.getBrand().getId().equals(currentUser.getBrand().getId())) {
            throw new AccessDeniedException("You are not authorized to delete this user");
        }

        userService.deleteUserById(id);

        return "redirect:/dashboard/users?deleteSuccess";
    }


}
