package com.example.demo.product;



import com.example.demo.option.Option;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;


public class ProductResponse {

    @NoArgsConstructor
    @Data
    public static class FindAllDTO {
        // ** PK
        private Long id;

        // ** 상품명
        private String productName;

        // ** 상품 설명
        private String description;

        // ** 이미지 정보
        private String image;

        // ** 가격
        private int price;

        public FindAllDTO(Product product) {
            this.id = product.getId();
            this.productName = product.getProductName();
            this.description = product.getDescription();
            this.image = product.getImage();
            this.price = product.getPrice();
        }
    }

    @NoArgsConstructor
    @Data
    public static class FindByIdDTO {
        // ** PK
        private Long id;

        // ** 상품명
        private String productName;

        // ** 상품 설명
        private String description;

        // ** 이미지 정보
        private String image;

        // ** 가격
        private int price;

        private List<OptionDTO> optionList;


        //생성자
        public FindByIdDTO(Product product, List<Option> optionList) {
            this.id = product.getId();
            this.productName = product.getProductName();
            this.description = product.getDescription();
            this.image = product.getImage();
            this.price = product.getPrice();
            this.optionList = optionList.stream().map(OptionDTO::new)
                    .collect(Collectors.toList());
            // Option 객체의 리스트를 OptionDTO 객체의 리스트로 변환한 후, 이 결과를 this.optionList에 저장

            // optionList를 스트림으로 변환한 후, 각 Option 객체를 OptionDTO 객체로 변환
            // 이 변환은 OptionDTO::new를 통해 이루어짐.
            // 이 람다 표현식은 OptionDTO의 생성자를 가리키며, 이 생성자는 Option 객체를 인자로 받아 OptionDTO 객체를 생성하는 것
        }
    }


    @NoArgsConstructor
    @Data
    public static class OptionDTO{
        private Long id;
        private String optionName;
        private Long price;
        private Long quantity;

        public OptionDTO(Option option) {
            this.id = option.getId();
            this.optionName = option.getOptionName();
            this.price = option.getPrice();
            quantity = option.getQuantity();
        }
    }

}
