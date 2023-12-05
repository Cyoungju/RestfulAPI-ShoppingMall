package com.example.demo.option;

import com.example.demo.product.Product;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
public class OptionResponse {
    private Long id;

    private Long productId;

    private Product product;

    private String optionName;

    private Long price;

    private Long quantity;

    @NoArgsConstructor
    @Data
    public static class FindByProductIdDTO{
        private Long id;

        private Long productId;

        private String optionName;

        private Long price;

        private Long quantity;




        public FindByProductIdDTO(Option option) {
            this.id = option.getId();
            this.productId = option.getProduct().getId();
            this.optionName = option.getOptionName();
            this.price = option.getPrice();
            this.quantity = option.getQuantity();
        }

        public Option toEntity(Product product) {
            return Option.builder()
                    .optionName(optionName)
                    .price(price)
                    .quantity(quantity)
                    .product(product)
                    .build();
        }



    }


    @NoArgsConstructor
    @Data
    public static class FindAllDTO{
        private Long id;

        private Long productId;

        private String optionName;

        private Long price;

        private Long quantity;

        public FindAllDTO(Option option) {
            this.id = option.getId();
            this.productId = option.getProduct().getId();
            this.optionName = option.getOptionName();
            this.price = option.getPrice();
            this.quantity = option.getQuantity();
        }
    }
}
