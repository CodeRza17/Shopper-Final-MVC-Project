package com.final_project.shopper.shopper.controller;

import com.final_project.shopper.shopper.dtos.basket.AddToCart;
import com.final_project.shopper.shopper.dtos.basket.BasketDto;
import com.final_project.shopper.shopper.dtos.basket.BasketUserDto;
import com.final_project.shopper.shopper.dtos.order.OrderUserDto;
import com.final_project.shopper.shopper.dtos.product.*;
import com.final_project.shopper.shopper.dtos.sizes.SizeProductDetailDto;
import com.final_project.shopper.shopper.dtos.user.UserDto;
import com.final_project.shopper.shopper.models.Order;
import com.final_project.shopper.shopper.payloads.PaginationPayload;
import com.final_project.shopper.shopper.repositories.BrandRepository;
import com.final_project.shopper.shopper.repositories.ProductCategoryRepository;
import com.final_project.shopper.shopper.sendEmail.OrderEmailService;
import com.final_project.shopper.shopper.services.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ShopController {
    private final ProductService productService;
    private final ProductCategoryRepository productCategoryRepository;
    private final BrandRepository brandRepository;
    private final SizesService sizesService;
    private final BasketService basketService;
    private final UserService userService;
    private final OrderService orderService;

    @Autowired
    private OrderEmailService orderEmailService;


    public ShopController(ProductService productService, ProductCategoryService productCategoryService, ProductCategoryRepository productCategoryRepository, BrandRepository brandRepository, SizesService sizesService, BasketService basketService, UserService userService, OrderService orderService) {
        this.productService = productService;
        this.productCategoryRepository = productCategoryRepository;
        this.brandRepository = brandRepository;
        this.sizesService = sizesService;
        this.basketService = basketService;
        this.userService = userService;
        this.orderService = orderService;
    }


    @PostMapping("/cart/use-reward")
    @PreAuthorize("isAuthenticated()")
    public String useRewardPoint(@RequestParam("rewardPoint") int rewardPoint,
                                 HttpSession session,
                                 Principal principal,
                                 Model model) {
        String email = principal.getName();

        Double availablePoint = userService.getRewardPoint(email);
        if (availablePoint == null) availablePoint = 0.0;

        Integer previousUsed = (Integer) session.getAttribute("usedRewardPoint");
        if (previousUsed == null) previousUsed = 0;

        int totalUsed = previousUsed + rewardPoint;

        if (totalUsed > availablePoint) {
            session.setAttribute("usedRewardPoint", previousUsed);
            model.addAttribute("error", "You cannot use more reward points than available.");
            return "redirect:/cart";
        }

        session.setAttribute("usedRewardPoint", totalUsed);
        return "redirect:/cart";
    }


    @GetMapping("/cart")
    @PreAuthorize("isAuthenticated()")
    public String cart(Principal principal, Model model, HttpSession session){
        String email = principal.getName();
        List<BasketUserDto> basketUserDtoList = basketService.getUserBasket(email);
        model.addAttribute("baskets", basketUserDtoList);
        Double subTotalPrice = basketService.getSubTotalPrice(email);
        model.addAttribute("subTotal", subTotalPrice);

        Double rewardPoint = userService.getRewardPoint(email);
        if (rewardPoint == null) rewardPoint = 0.0;

        Integer usedRewardPoint = (Integer) session.getAttribute("usedRewardPoint");
        if (usedRewardPoint == null) usedRewardPoint = 0;

        rewardPoint = Math.max(0.0, rewardPoint - usedRewardPoint);

        model.addAttribute("rewardPoint", rewardPoint);
        model.addAttribute("usedRewardPoint", usedRewardPoint);

        Double resultSubTotal = subTotalPrice - usedRewardPoint;
        if(resultSubTotal < 0) {
            resultSubTotal = 0.0;
        }

        Double total = resultSubTotal + 5;
        if (total < 0) {
            total = 0.0;
        }
        model.addAttribute("resultSubTotal", resultSubTotal);
        model.addAttribute("total", total);

        return "cart.html";
    }

    @PostMapping("/addToCart")
    @PreAuthorize("isAuthenticated()")
    public String addToCart(AddToCart addToCart, Principal principal){
        String email = principal.getName();

        boolean result = basketService.addToCart(addToCart, email);

        return "redirect:/cart";
    }


    @GetMapping("/removeFromCart/{id}")
    @PreAuthorize("isAuthenticated()")
    public String deleteCart(@PathVariable Long id, Principal principal){
        String email = principal.getName();
        boolean result = basketService.removeCartItem(id, email);
        return "redirect:/cart";
    }


    @GetMapping("/checkout")
    @PreAuthorize("isAuthenticated()")
    public String checkout(Model model, Principal principal, HttpSession session){

        String userEmail = principal.getName();
        List<BasketDto> basketDtoList = basketService.getUserBaskets(userEmail);
        UserDto userDto = userService.findUserDtoByEmail(userEmail);

        model.addAttribute("user", userDto);
        model.addAttribute("baskets", basketDtoList);

        Double subTotalPrice = basketService.getSubTotalPrice(userEmail);

        Integer usedRewardPoint = (Integer) session.getAttribute("usedRewardPoint");
        if (usedRewardPoint == null) usedRewardPoint = 0;

        Double result = subTotalPrice-usedRewardPoint+5;
        model.addAttribute("subTotal", result);
        session.setAttribute("subTotal", result);

        return "checkout.html";
    }


    @PostMapping("/checkout")
    @PreAuthorize("isAuthenticated()")
    public String checkout(OrderUserDto orderUserDto, Principal principal, HttpSession session){

        String userEmail = principal.getName();
        Integer usedRewardPoint = (Integer) session.getAttribute("usedRewardPoint");
        if (usedRewardPoint == null) usedRewardPoint = 0;
        boolean result2 = userService.updateRewardPoint(userEmail, usedRewardPoint);
        Order order = orderService.orderProduct(userEmail, orderUserDto);
        if (result2){
            session.setAttribute("usedRewardPoint", 0);
        }

        Double totalPrice = (Double) session.getAttribute("subTotal");
        orderEmailService.sendOrderConfirmationEmail(userEmail, order, totalPrice);
        session.setAttribute("subTotal", 0);
        return "redirect:/";
    }

    @GetMapping("/product/product-detail/{id}")
    public String productDetail(Model model, @PathVariable Long id){

        ProductDetailDto findProductDetailDto = productService.findProductDetailsById(id);
        model.addAttribute("product", findProductDetailDto);


        List<SizeProductDetailDto> sizeProductDetailDtos = sizesService.findByProductId(id);

        model.addAttribute("sizes", sizeProductDetailDtos);

        List<ProductRandomDto> productRandomDtoList = productService.getRandomProducts();
        model.addAttribute("randomProducts", productRandomDtoList);

        List<ProductBsDto> productBsDtoList = productService.getTopFourProducts();
        model.addAttribute("bsProduct", productBsDtoList);

        List<RelatedProductsDto> relatedProductsDtosList = productService.getRelatedProducts(id);


        List<List<RelatedProductsDto>> partitionedList = new ArrayList<>();
        for (int i = 0; i < relatedProductsDtosList.size(); i += 3) {
            int end = Math.min(i + 3, relatedProductsDtosList.size());
            partitionedList.add(relatedProductsDtosList.subList(i, end));
        }

        model.addAttribute("rlProductGroups", partitionedList);


        return "product_detail";
    }

    @GetMapping("/products/{audience}")
    public String getProductsByAudience(@PathVariable("audience") String audience, @RequestParam(required = false) List<Long> categoryIds, @RequestParam(required = false) List<Long> brandIds, @RequestParam(required = false) Integer currentPage, Model model) {

        currentPage = currentPage == null ? 1 : currentPage;

        PaginationPayload<ProductAudienceDto> products = productService
                .getProductsFiltered(audience, categoryIds, brandIds, currentPage);

        model.addAttribute("audience", audience);
        model.addAttribute("products", products);


        model.addAttribute("categories", productCategoryRepository.findAll());
        model.addAttribute("brands", brandRepository.findAll());
        model.addAttribute("selectedCategoryIds", categoryIds != null ? categoryIds : new ArrayList<>());
        model.addAttribute("selectedBrandIds", brandIds != null ? brandIds : new ArrayList<>());

        List<ProductRandomDto> productRandomDtoList = productService.getRandomProducts();
        model.addAttribute("randomProducts", productRandomDtoList);

        List<ProductBsDto> productBsDtoList = productService.getTopFourProducts();
        model.addAttribute("bsProduct", productBsDtoList);

        return "products";
    }
    @GetMapping("/products/top-seller")
    public String topSeller(Model model){

        return "top-seller";
    }

    @GetMapping("/products/best-seller")
    public String bestSeller(Model model){
        List<ProductAudienceDto> productBsDtoList = productService.getBestSeller();

        model.addAttribute("bsProduct", productBsDtoList);
        return "best-seller";
    }

    @GetMapping("/order-history")
    @PreAuthorize("isAuthenticated()")
    public String orderHistory(Principal principal, Model model) {
        model.addAttribute("orders", orderService.getOrdersByUser(principal.getName()));
        return "order-history";
    }


}
