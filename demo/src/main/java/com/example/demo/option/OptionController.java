package com.example.demo.option;

import com.example.demo.core.utils.ApiUtils;
import com.example.demo.product.Product;
import com.example.demo.product.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class OptionController {

    private final OptionService optionService;

    @PostMapping("options/save")
    public ResponseEntity<?> save(@RequestBody OptionRequest.saveDTO optionDTO){
        Option saveProduct = optionService.save(optionDTO);

        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(saveProduct);
        return ResponseEntity.ok(apiResult);
    }


    /**
    * @param id
    * id의 관련된 설명 (ProductId)
    * @return
    * 반환값에 관련된 설명 (List&#60;OptionResponse.FindByProductIdDTO&#62;) - 리스트로반환
    **/

    // 옵션 개별 상품 검색
    @GetMapping("/products/{id}/options")
    public ResponseEntity<?> findById(@PathVariable Long id){
        List<OptionResponse.FindByProductIdDTO> optionResponses =
                optionService.findByProductId(id);

        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(optionResponses);
        return ResponseEntity.ok(apiResult);
    }

    // 상픔에 대한 전체 옵션 전체옵션 - 옵션 전체 상품 검색
    @GetMapping("/options")
    public ResponseEntity<?> findAll(){
        List<OptionResponse.FindAllDTO> optionResponses =
                optionService.findAll();

        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(optionResponses);
        return ResponseEntity.ok(apiResult);
    }

    // 상품 안에 옵션이 있으니까
    // producId로 검색해서 상품 정보를 불러올수 있음

    @PutMapping("/options/update")
    public ResponseEntity<?> update(@RequestBody OptionRequest.updateDTO optionDTO){
        Option updateProduct = optionService.update(optionDTO);

        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(updateProduct);
        return ResponseEntity.ok(apiResult);
    }


    @DeleteMapping("/delete/{id}/options/{optionId}")
    public ResponseEntity<?> delete(@PathVariable Long id, @PathVariable Long optionId){
        optionService.delete(id, optionId);

        ApiUtils.ApiResult<?> apiResult = ApiUtils.success("success");
        return ResponseEntity.ok(apiResult);
    }

}
