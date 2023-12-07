package com.example.demo.cart;

import com.example.demo.option.Option;
import com.example.demo.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "cart_tb",
        indexes = {
            @Index(name = "cart_user_id_idx", columnList = "user_id"),
            @Index(name = "cart_option_id_idx", columnList = "option_id"),
        },
        // 고유값
        uniqueConstraints = {
            @UniqueConstraint(name = "uk_cart_option_user", columnNames = {"user_id", "option_id"})
        }
)
// 최적화
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ** User별로 카트에 묶임 - cart입장 에서 생각(많은 user에 cart가 하나씩) - 회원은 많은데 내 장바구니는 하나
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;


    @OneToOne(fetch = FetchType.LAZY) // 지연로딩
    private Option option;
    // 상품이랑 연결할꺼면 ManyToOne이 맞지만
    // 옵션은 OneToOne임


    @Column(nullable = false)
    private Long quantity; // 장바구니의 총수량


    @Column(nullable = false)
    private Long price; //장바구니 총 가격


    @Builder
    public Cart(Long id, User user, Option option, Long quantity, Long price) {
        this.id = id;
        this.user = user;
        this.option = option;
        this.quantity = quantity;
        this.price = price;
    }

    public void update(Long quantity, Long price){
        this.quantity = quantity;
        this.price = price;
    }
}
