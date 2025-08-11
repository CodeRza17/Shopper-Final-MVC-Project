package com.final_project.shopper.shopper.sevices.impls;

import com.final_project.shopper.shopper.models.Basket;
import com.final_project.shopper.shopper.models.Order;
import com.final_project.shopper.shopper.models.OrderProduct;
import com.final_project.shopper.shopper.models.User;
import com.final_project.shopper.shopper.repositories.BasketRepository;
import com.final_project.shopper.shopper.repositories.OrderProductRepository;
import com.final_project.shopper.shopper.sevices.BasketService;
import com.final_project.shopper.shopper.sevices.OrderProductService;
import com.final_project.shopper.shopper.sevices.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderProductServiceImpl implements OrderProductService {
    private final ProductService productService;
    private final OrderProductRepository orderProductRepository;
    private final BasketService basketService;

    public OrderProductServiceImpl(ProductService productService, OrderProductRepository orderProductRepository, BasketService basketService, BasketRepository basketRepository, BasketService basketService1) {
        this.productService = productService;
        this.orderProductRepository = orderProductRepository;
        this.basketService = basketService1;
    }

    @Override
    public boolean createOrderProduct(Order order) {
        User findUser = order.getUser();
        List<Basket> findBasket = findUser.getBaskets();

        for (Basket basket : findBasket) {
            OrderProduct orderProduct = new OrderProduct();
            orderProduct.setQuantity(basket.getQuantity());
            orderProduct.setPrice(basket.getProduct().getPrice());
            orderProduct.setProduct(basket.getProduct());
            productService.removeQuantityById(basket.getProduct().getId(), basket.getQuantity());
            orderProduct.setOrder(order);
            orderProductRepository.save(orderProduct);
        }

        basketService.removeUserBasket(findUser.getId());


        return true;

    }
}
