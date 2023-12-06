package com.example.demo.cart;


import com.example.demo.core.security.CustomUserDetails;
import com.example.demo.core.utils.ApiUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class CartController {
//jwt가 HEADER에 등록이 되어있어야 내용 확인이 가능함


    private final CartService cartService;

    // ** 카트에 상품 추가
    @PostMapping("/carts/add")
    public ResponseEntity<?> addCartList(
            @RequestBody @Valid List<CartRequest.SaveDTO> requestDTO,
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            Error error) { // 인증받은 애들만 메소드에 접근할 수 있음
        cartService.addCartList(requestDTO, customUserDetails.getUser());

        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(null);
        return ResponseEntity.ok(apiResult);
    }

    // ** 카트 전체 상품 확인
    @GetMapping("/carts/update")
    public ResponseEntity<?> update(
            @RequestBody @Valid List<CartRequest.UpdateDTO> requestDTO,
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            Error error){ // 인증받은 애들만 메소드에 접근할 수 있음
        CartResponse.UpdateDTO updateDTO = cartService.update(requestDTO, customUserDetails.getUser());

        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(updateDTO);
        return ResponseEntity.ok(apiResult);
    }
    
    // ** 카트 전체 상품 확인
    @GetMapping("/carts")
    public ResponseEntity<?> carts(@AuthenticationPrincipal CustomUserDetails customUserDetails){ // 인증받은 애들만 메소드에 접근할 수 있음
        CartResponse.FindAllDTO findAllDTO = cartService.findAll();

        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(findAllDTO);
        return ResponseEntity.ok(apiResult);
    }



}
