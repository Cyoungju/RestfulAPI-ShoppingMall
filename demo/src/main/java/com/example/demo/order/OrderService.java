package com.example.demo.order;


import com.example.demo.cart.Cart;
import com.example.demo.cart.CartRepository;
import com.example.demo.core.error.exception.Exception404;
import com.example.demo.core.error.exception.Exception500;
import com.example.demo.order.item.Item;
import com.example.demo.order.item.ItemRepository;
import com.example.demo.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final ItemRepository itemRepository;


    @Transactional
    public OrderResponse.FindByDTO save(User user) {
        // ** 장바구니 조회
        List<Cart> cartList = cartRepository.findAllByUserId(user.getId());
        // 유저의 아이디를 가지고 있는 동일한 카트 모두 검색

        if(cartList.size() == 0) {//상품 없음
            throw new Exception404("장바구니에 상품내용이 존재 하지 않습니다.");
        }

        // ** 주문 생성
        Order order = orderRepository.save(
                Order.builder().user(user).build()
        );


        // ** 아이템 저장
        List<Item> itemList = new ArrayList<>();

        for(Cart cart : cartList){
            Item item = Item.builder()
                    .option(cart.getOption())
                    .order(order)
                    .quantity(cart.getQuantity())
                    .price(cart.getOption().getPrice() * cart.getQuantity())
                    .build();

            itemList.add(item);
        }

        try {
            //리스트에 저장된 애들 전부 저장되게
            itemRepository.saveAll(itemList);
        }catch (Exception e){
            throw new Exception500("결제 실패");
        }

        return new OrderResponse.FindByDTO(order, itemList);
    }

    public OrderResponse.FindByDTO findById(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(
            () -> new Exception404("해당 주문을 찾을 수 없습니다." + id));

        List<Item> itemList = itemRepository.findAllByOrderId(id);

        return new OrderResponse.FindByDTO(order, itemList);

    }

    @Transactional
    public void clear() {
        try {
            itemRepository.deleteAll();
        }catch (Exception e) {
             throw new Exception500("아이템 삭제 오류");
        }
    }

    // 주문은 업데이트 없음

    // 아이템 주문 내역 삭제

}
