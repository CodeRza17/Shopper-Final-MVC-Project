package com.final_project.shopper.shopper.sevices;

import com.final_project.shopper.shopper.dtos.basket.AddToCart;
import com.final_project.shopper.shopper.dtos.basket.BasketDto;
import com.final_project.shopper.shopper.dtos.basket.BasketUserDto;

import java.util.List;

public interface BasketService {
    boolean addToCart(AddToCart addToCart, String email);

    List<BasketUserDto> getUserBasket(String email);

    boolean removeCartItem(Long cartId, String email);

    Double getSubTotalPrice(String email);

    List<BasketDto> getUserBaskets(String userEmail);

    void removeUserBasket(Long id);
}
