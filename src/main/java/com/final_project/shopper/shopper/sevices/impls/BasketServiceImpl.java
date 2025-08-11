package com.final_project.shopper.shopper.sevices.impls;

import com.final_project.shopper.shopper.dtos.basket.AddToCart;
import com.final_project.shopper.shopper.dtos.basket.BasketDto;
import com.final_project.shopper.shopper.dtos.basket.BasketUserDto;
import com.final_project.shopper.shopper.models.Basket;
import com.final_project.shopper.shopper.models.Product;
import com.final_project.shopper.shopper.models.User;
import com.final_project.shopper.shopper.repositories.BasketRepository;
import com.final_project.shopper.shopper.sevices.BasketService;
import com.final_project.shopper.shopper.sevices.ProductService;
import com.final_project.shopper.shopper.sevices.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BasketServiceImpl implements BasketService {

    private final BasketRepository basketRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final ProductService productService;

    public BasketServiceImpl(BasketRepository basketRepository, UserService userService, ModelMapper modelMapper, ProductService productService) {
        this.basketRepository = basketRepository;
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.productService = productService;
    }


    @Override
    public boolean addToCart(AddToCart addToCart, String email) {
        try {

            User findUser = userService.findUserByEmail(email);
            Product findProduct = productService.findProductById(addToCart.getProductId());
            Basket findBasketProduct = basketRepository.findByProductIdAndUserId(addToCart.getProductId(), findUser.getId());
            Integer quantity = addToCart.getQuantity() == null ? 1 : addToCart.getQuantity();

            if (findBasketProduct == null){

                Basket basket = new Basket();
                basket.setQuantity(quantity);
                basket.setUser(findUser);
                basket.setProduct(findProduct);
                basketRepository.save(basket);
            }else {
                Integer totalQuantity = findBasketProduct.getQuantity() + quantity;
                findBasketProduct.setQuantity(totalQuantity);
                basketRepository.save(findBasketProduct);
            }

            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public List<BasketUserDto> getUserBasket(String email) {
        User findUser = userService.findUserByEmail(email);
        List<Basket> basketList = basketRepository.findByUserId(findUser.getId());
        List<BasketUserDto> basketUserDtoList = basketList.stream().map(basket -> modelMapper.map(basket, BasketUserDto.class)).collect(Collectors.toList());
        return basketUserDtoList;
    }

    @Override
    public boolean removeCartItem(Long cartId, String email) {
        try {
            User findUser = userService.findUserByEmail(email);
            Basket findBasketProduct = basketRepository.findById(cartId).orElseThrow();

            basketRepository.delete(findBasketProduct);

            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public Double getSubTotalPrice(String email) {
        User findUser = userService.findUserByEmail(email);
        List<Basket> basketList = basketRepository.findByUserId(findUser.getId());
        List<BasketUserDto> basketUserDtoList = basketList.stream().map(basket -> modelMapper.map(basket, BasketUserDto.class)).collect(Collectors.toList());
        Double result = 0.0;
        for (int i = 0; i < basketUserDtoList.size(); i++) {
            BasketUserDto basket = basketUserDtoList.get(i);
            result += basket.getTotalPrice();
        }
        return result;
    }

    @Override
    public List<BasketDto> getUserBaskets(String userEmail) {
        User user = userService.findUserByEmail(userEmail);
        List<Basket> basketList = user.getBaskets();
        List<BasketDto> basketDtoList = basketList.stream().map(product -> modelMapper.map(product, BasketDto.class)).collect(Collectors.toList());
        return basketDtoList;
    }

    @Override
    public void removeUserBasket(Long id) {
        List<Basket> basketList = basketRepository.findByUserId(id);
        basketRepository.deleteAll(basketList);
    }
}
