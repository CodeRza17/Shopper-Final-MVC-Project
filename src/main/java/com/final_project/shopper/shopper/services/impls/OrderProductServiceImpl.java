package com.final_project.shopper.shopper.services.impls;

import com.final_project.shopper.shopper.models.*;
import com.final_project.shopper.shopper.repositories.BasketRepository;
import com.final_project.shopper.shopper.repositories.OrderProductRepository;
import com.final_project.shopper.shopper.repositories.ProductRepository;
import com.final_project.shopper.shopper.repositories.UserRepository;
import com.final_project.shopper.shopper.services.BasketService;
import com.final_project.shopper.shopper.services.OrderProductService;
import com.final_project.shopper.shopper.services.ProductService;
import org.hibernate.engine.jdbc.Size;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderProductServiceImpl implements OrderProductService {
    private final OrderProductRepository orderProductRepository;
    private final BasketService basketService;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public OrderProductServiceImpl(ProductService productService, OrderProductRepository orderProductRepository, BasketService basketService, BasketRepository basketRepository, BasketService basketService1, ProductRepository productRepository, UserRepository userRepository) {
        this.orderProductRepository = orderProductRepository;
        this.basketService = basketService1;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @Override
    public boolean createOrderProduct(Order order) {
        User findUser = order.getUser();
        List<Basket> findBasket = findUser.getBaskets();
        Double rewardPoint = 0.0;
        for (Basket basket : findBasket) {
            OrderProduct orderProduct = new OrderProduct();
            orderProduct.setQuantity(basket.getQuantity());
            orderProduct.setPrice(basket.getProduct().getPrice());
            orderProduct.setProduct(basket.getProduct());
            orderProduct.setOrder(order);
            orderProduct.setSize(basket.getSize());
            orderProduct.setTotalPrice(basket.getProduct().getPrice() * basket.getQuantity());


            Product product = basket.getProduct();

            product.setCountStock(product.getCountStock() - basket.getQuantity());

            product.setSalesCount(product.getSalesCount() + basket.getQuantity());


            Sizes sizeObj = product.getSizeQuantities().stream()
                    .filter(s -> s.getSize().equalsIgnoreCase(basket.getSize()))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Size can't find: " + basket.getSize()));

            sizeObj.setQuantity(sizeObj.getQuantity() - basket.getQuantity());

            rewardPoint+=product.getRewardPoints()*basket.getQuantity();

            productRepository.save(product);
            orderProductRepository.save(orderProduct);
        }

        findUser.setRewardPoint(findUser.getRewardPoint()+rewardPoint);
        basketService.removeUserBasket(findUser.getId());

        return true;
    }

    @Override
    public List<OrderProduct> findOrdersByBrandId(String email) {
        List<OrderProduct> orderProductList = orderProductRepository.findAll();
        List<OrderProduct> brandOrderProduct = new ArrayList<>();
        Long id = userRepository.findByEmail(email).getBrand().getId();
        for (OrderProduct orderProduct : orderProductList) {
            Product product = orderProduct.getProduct();
            if (product.getBrand().getId() == id){
                brandOrderProduct.add(orderProduct);
            }
        }
        return brandOrderProduct;
    }


}
