package com.example.demo.cart;

import com.example.demo.option.Option;
import com.example.demo.product.Product;
import com.example.demo.user.User;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.util.List;
import java.util.stream.Collectors;

public class CartResponse { //응답

    @Data
    public static class UpdateDTO {
        private List<CartDTO> dtoList;

        private Long totalPrice;

        public UpdateDTO(List<Cart> dtoList) {
            this.dtoList = dtoList.stream().map(CartDTO::new).collect(Collectors.toList()); //객체 생성
            this.totalPrice = totalPrice;
        }

        @Data
        public class CartDTO{
            private Long cartId;

            private Long optionId;

            public String optionName;

            private Long quantity;

            private Long price;

            public CartDTO(Cart cart) {
                this.cartId = cart.getId();
                this.optionId = cart.getOption().getId();
                this.optionName = cart.getOption().getOptionName();
                this.quantity = cart.getQuantity();
                this.price = cart.getPrice();
            }
        }
    }

    @Data
    public static class FindAllDTO {

        List<ProductDTO> products;

        private Long totalPrice;

        //생성자
        public FindAllDTO(List<Cart> cartList){ // Cart로 받음 - Cart에 Option이 있음

            this.products = cartList.stream()
                    .map( cart -> cart.getOption().getProduct()).distinct() //Option에 Product형태를 반환하는
                    .map( product -> new ProductDTO(cartList, product)) // List<ProductDTO>  -Product의 정보를 카트에 담아서 쓰려고
                    .collect(Collectors.toList());
                    // 카트 리스트 안에 원소는 카트
                    // 람다식은 전체 내용물에 접근한다 - for문의 생략화 - 간소화
                    // 전체 원소중에 하나가 어떤형태로 작동할것인가?

            this.totalPrice = cartList.stream()
                    .mapToLong(cart -> cart.getOption().getPrice() *cart.getQuantity())
                    .sum(); // 전체 합을 반환해라
        }



        /*
        * 주어진 코드는 FindAllDTO 클래스의 생성자입니다. 이 생성자는 cartList라는 Cart 객체의 리스트를 인자로 받습니다.

        해당 코드는 다음과 같은 과정을 수행합니다:

        cartList를 스트림으로 변환합니다. 스트림은 자바 8부터 추가된 기능으로, 데이터를 처리하고 연속적으로 연산을 수행할 수 있도록 합니다.
        map 함수를 사용하여 각각의 Cart 객체에서 getOption().getProduct()를 호출합니다. 이는 Cart 객체에서 Option을 가져와 그에 해당하는 Product를 추출하는 과정입니다.
        distinct 함수를 사용하여 중복된 Product를 제거합니다. 따라서 결과로 나오는 리스트에는 중복되지 않은 Product만 포함됩니다.
        다시 map 함수를 사용하여 각각의 Product 객체를 ProductDTO로 변환합니다. ProductDTO는 Product 객체의 필요한 정보만을 담은 데이터 전송 객체입니다.
        최종적으로 변환된 ProductDTO 객체들을 리스트로 수집하여 반환합니다.
        따라서, 이 코드는 cartList의 각 Cart 객체에서 Product를 추출하고, 중복을 제거한 후, 해당 Product들을 ProductDTO로 변환하여 리스트로 반환하는 것을 의미합니다.
        * */

        // 디자인 패턴,,,
        // 브릿지 패턴의 형태와 비슷하게 작성됨


        @Data
        public class ProductDTO{
    
            //Product가 cart정보를 가지고있지 않기 때문에 이곳에서 ProductDTO를 생성한것
            private Long id;
    
            private String productName;
    
            List<CartDTO> carDTOS;
    
            //생성자 - 위에 map new ProductDTO(product)와 맞추기 위해? product를 받아서 생성자 작성
            public ProductDTO(List<Cart> cartList, Product product) {
                this.id = product.getId();
                this.productName = product.getProductName();
                this.carDTOS = cartList.stream()
                        .filter(cart -> cart.getOption().getProduct().getId() == product.getId())
                        .map(CartDTO::new).collect(Collectors.toList());
                        //단일객체
                //리스트에 리스트 담을때 - 람다식 사용
            }



            @Data
            public class CartDTO{ // 외부 호출용이 아님
                private Long id;

                private OptionDTO optionDTO;

                private Long quantity;

                private Long price;

                public CartDTO(Cart cart) {
                    this.id = cart.getId();
                    this.optionDTO = new OptionDTO(cart.getOption());//단일객체 - 리스트가 아님 람다식 사용x
                    this.quantity = cart.getQuantity();
                    this.price = cart.getPrice();
                }


                @Data
                public class OptionDTO{

                    private Long id;

                    private String optionName;

                    private Long price;

                    public OptionDTO(Option option) {
                        this.id = option.getId();
                        this.optionName = option.getOptionName();
                        this.price = option.getPrice();
                    }
                }
            }

            // cart- option - product - cart정보를 가지고 있어야함
            // cart가 모든 정보를 가지고 있게됨

            // 테이블이 4개 - 3개의 테이블을 사용해서 데이터를 서로 사용할수 있게                                                          
        }
    }


    @Data
    public static class DeleteDTO {
        private Long cartId;
        private Long quantity;
    }

}
