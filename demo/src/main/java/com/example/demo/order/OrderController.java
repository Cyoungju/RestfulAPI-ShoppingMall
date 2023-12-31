package com.example.demo.order;

import com.example.demo.core.security.CustomUserDetails;
import com.example.demo.core.utils.ApiUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/orders")
@RestController
public class OrderController {

    private final OrderService orderservice;

    @PostMapping  // 저장 Post - /orders
    public ResponseEntity<?> save(@AuthenticationPrincipal CustomUserDetails customUserDetails){
        OrderResponse.FindByDTO findByDTO = orderservice.save(customUserDetails.getUser());
        return ResponseEntity.ok(findByDTO); //인증 되는지 확인해야함,,
    }


    @GetMapping("/{id}") // 하나의 목록 조회 Get - /orders/{id}
    public ResponseEntity<?> findById(@PathVariable Long id)//주문 결과 확인
    {
        OrderResponse.FindByDTO findByDTO = orderservice.findById(id);
        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(findByDTO);
        return ResponseEntity.ok(apiResult);
    }

    @DeleteMapping // 삭제 Delete - /orders
    public ResponseEntity<?> clear(){
        orderservice.clear();

        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(null);
        return ResponseEntity.ok(apiResult);
    }
}