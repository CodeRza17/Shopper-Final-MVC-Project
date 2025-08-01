package com.final_project.shopper.shopper.sevices.impls;


import com.final_project.shopper.shopper.dtos.auth.UserRegisterDto;
import com.final_project.shopper.shopper.dtos.user.UserDashboardDto;
import com.final_project.shopper.shopper.dtos.user.UserEditDto;
import com.final_project.shopper.shopper.models.Brand;
import com.final_project.shopper.shopper.models.Role;
import com.final_project.shopper.shopper.models.User;
import com.final_project.shopper.shopper.repositories.BrandRepository;
import com.final_project.shopper.shopper.repositories.RoleRepository;
import com.final_project.shopper.shopper.repositories.UserRepository;
import com.final_project.shopper.shopper.sevices.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final BrandRepository brandRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;

    public UserServiceImpl(UserRepository userRepository, BrandRepository brandRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.brandRepository = brandRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean register(UserRegisterDto userRegisterDto) {
        User findUser = userRepository.findByEmail(userRegisterDto.getEmail());
        if (findUser != null) {
            return false;
        }

        User user = new User();


        if (userRegisterDto.getBrandId() != null) {
            Brand brand = brandRepository.findById(userRegisterDto.getBrandId())
                    .orElseThrow(() -> new RuntimeException("Brand not found"));


            if ("ROLE_SUPER_ADMIN".equalsIgnoreCase(userRegisterDto.getRoleName())) {
                if (userRegisterDto.getBrandOwnerCode() == null ||
                        !brand.getBradOwnerCode().equals(userRegisterDto.getBrandOwnerCode())) {
                    throw new RuntimeException("Invalid brand owner code");
                }
            }

            user.setBrand(brand);
        }


        Role role = roleRepository.findByName(userRegisterDto.getRoleName())
                .orElseThrow(() -> new RuntimeException("Role not found"));

        user.setName(userRegisterDto.getName());
        user.setSurname(userRegisterDto.getSurname());
        user.setEmail(userRegisterDto.getEmail());
        user.setPassword(passwordEncoder.encode(userRegisterDto.getPassword()));
        user.setRole(role);

        userRepository.save(user);
        return true;
    }


    @Override
    public User findUserByEmail(String username) {
        return userRepository.findByEmail(username);
    }

    @Override
    public Double getRewardPoint(String email) {
        User findUser = userRepository.findByEmail(email);
        return findUser.getRewardPoint();
    }

    @Override
    public List<UserDashboardDto> getUsersByBrand(Principal principal) {
        String email = principal.getName();
        User user = userRepository.findByEmail(email);
        Brand brand = user.getBrand();
        List<User> findUsers = userRepository.findAllByBrandId(brand.getId());
        List<UserDashboardDto> userDashboardDtoList = findUsers.stream()
                .map(user1 -> modelMapper.map(user1, UserDashboardDto.class))
                .toList();
        return userDashboardDtoList;
    }

    public List<Role> getEditableRoles() {
        return roleRepository.findAll().stream()
                .filter(role -> {
                    String name = role.getName();
                    return "ROLE_ADMIN".equals(name) || "ROLE_MANAGER".equals(name) || "ROLE_SUPER_ADMIN".equals(name);
                })
                .collect(Collectors.toList());
    }

    @Override
    public void updateUser(UserEditDto userEditDto) {

        User user = userRepository.findById(userEditDto.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));


        user.setName(userEditDto.getName());
        user.setSurname(userEditDto.getSurname());


        Role role = roleRepository.findByName(userEditDto.getRoleName())
                .orElseThrow(() -> new RuntimeException("Role not found"));
        user.setRole(role);

        user.setRole(role);


        userRepository.save(user);
    }



}

