package com.example.demo.product;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ** 상품명, 입력값 필수
    @Column(length = 100, nullable = false)
    private String productName;

    // ** 상품설명, 입력값 필수
    @Column(length = 500, nullable = false)
    private String description;

    // ** 이미지 정보
    @Column(length = 100)
    private String image;

    // ** 가격
    private int price;

    @Builder
    public Product(Long id, String productName, String description, String image, int price) {
        this.id = id;
        this.productName = productName;
        this.description = description;
        this.image = image;
        this.price = price;
    }

    //DTO -> Entity
    public void updateFromDTO(ProductResponse.FindByIdDTO productDTO){
        // 모든 변경 사항을 셋팅. =>  기존에 있는 데이터에 저장해야하기 때문에 new 객체 생성을 하는 toEntity 사용 불가
        this.productName = productDTO.getProductName();
        this.description = productDTO.getDescription();
        this.image = productDTO.getImage();
        this.price = productDTO.getPrice();
    }

    // 확인이 필요 없으면 연관관계 매핑이 필요없음
    // 양쪽에서 확인이 필요 하디면 매핑을 해줘야함


}
