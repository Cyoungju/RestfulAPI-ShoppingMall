package com.example.demo.product;

import com.example.demo.core.utils.ApiUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/products")
@RestController
public class ProductController {
    private final ProductService productService;

    @PostMapping // 저장 Post - "/products"
    public ResponseEntity<?> save(@RequestBody ProductResponse.FindAllDTO product){
        Product save = productService.save(product);

        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(save);
        return ResponseEntity.ok(apiResult);
    }

    // ** 전체 상품 확인
    @GetMapping //조회 - Get - "/products"
    public ResponseEntity<?> findAll(@RequestParam(defaultValue = "0") int page){
        List<ProductResponse.FindAllDTO> productResponses = productService.findAll(page);
        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(productResponses);
        return ResponseEntity.ok(apiResult);
    }

    // ** 개별 상품 확인
    @GetMapping("/{id}")  // 조회 (하나만) Get - "/products/{id}"
    public ResponseEntity<?> findById(@PathVariable Long id){
        ProductResponse.FindByIdDTO productDTOS = productService.findById(id);
        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(productDTOS);
        return ResponseEntity.ok(apiResult);
    }

    @PutMapping("/{id}") // 수정 Put - "/products/{id}"
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody ProductResponse.FindByIdDTO productDTO) {
        Product update = productService.update(id, productDTO);
        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(update);
        return ResponseEntity.ok(apiResult);
    }


    @DeleteMapping("/{id}") // 삭제 Delete  - "/products/{id}"
    public ResponseEntity<?> delete(@PathVariable Long id){
        productService.delete(id);
        ApiUtils.ApiResult<?> apiResult = ApiUtils.success("success");
        return ResponseEntity.ok(apiResult);
    }


}
