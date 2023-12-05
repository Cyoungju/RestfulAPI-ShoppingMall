package com.example.demo.option;

import com.example.demo.product.Product;
import com.example.demo.product.ProductResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "option_tb",
        indexes = {
            @Index(name = "option_product_id_index", columnList = "product_id") //빈번한 호출
})

public class Option {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    // 옵션 상품 이름, 필수 입력값
    @Column(length = 100, nullable = false)
    private String optionName;

    //옵션 상품 가격
    private Long price;

    //옵션 상품 남은 수량
    private Long quantity;


    @Builder
    public Option(Long id, Product product, String optionName, Long price, Long quantity) {
        this.id = id;
        this.product = product;
        this.optionName = optionName;
        this.price = price;
        this.quantity = quantity;
    }

//    public Option toupdate(Product product){
//        Option option = new Option();
//        this.product = product;
//        return option;
//
//    }

    //DTO -> Entity
    public void updateFromDTO(OptionResponse.FindByProductIdDTO optionDTO){
        this.optionName = optionDTO.getOptionName();
        this.price = optionDTO.getPrice();
        this.quantity = optionDTO.getQuantity();
    }

}
