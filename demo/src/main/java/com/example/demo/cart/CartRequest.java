package com.example.demo.cart;

import com.example.demo.option.Option;
import com.example.demo.user.User;
import lombok.Data;


public class CartRequest {
    @Data
    public static class SaveDTO { // 요청
        private Long optionId;
        private Long quantity;

        public Cart toEntity(Option option, User user){
            return Cart.builder()
                    .option(option)
                    .user(user)
                    .quantity(quantity)
                    .price(option.getPrice() * quantity)
                    .build();
        }
    }


    @Data
    public static class UpdateDTO {
        private Long cartId;
        private Long quantity;
    }
}
