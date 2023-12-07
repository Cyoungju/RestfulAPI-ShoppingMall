package com.example.demo.product;

import com.example.demo.core.error.exception.Exception404;
import com.example.demo.option.Option;
import com.example.demo.option.OptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Transactional(readOnly = true) // 읽기 전용
@RequiredArgsConstructor
@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final OptionRepository optionRepository;

    public List<ProductResponse.FindAllDTO> findAll(int page) {
        Pageable pageable = PageRequest.of(page,3);
        Page<Product> productPage = productRepository.findAll(pageable);

        List<ProductResponse.FindAllDTO> productDTOS =
                productPage.getContent().stream().map(ProductResponse.FindAllDTO::new)//람다식 - > 기본생성자 호출
                .collect(Collectors.toList());


        return productDTOS;
    }

    //개별상품 검색
    public ProductResponse.FindByIdDTO findById(Long id) { //상품 하나 안에 재품들이 여러개 일수 있음(+옵션)
    // DTO안에 LIST 가지고 있기 때문에 바로 DTO반환 (상품이랑 옵션 함께 반환 해야하기 때문에 ProductResponse.FindByIdDTO에서 작업)
        Product product = productRepository.findById(id).orElseThrow( //예외 처리 바로하기
                () -> new Exception404("해당 상품을 찾을 수 없습니다. : " + id) // 상품이 없을 경우
        );
        // product.getId()로 Option 상품 검색
         List<Option> optionList = optionRepository.findByProductId(product.getId());

        // ** 검색이 완료된 제품 - 리스트로 반환
        return new ProductResponse.FindByIdDTO(product, optionList); // 생성자 반환
    }

    @Transactional
    public void delete(Long id) {
        Product product = productRepository.findById(id).orElseThrow( //예외 처리 바로하기
                () -> new Exception404("해당 상품을 찾을 수 없습니다. : " + id) // 상품이 없을 경우
        );
        productRepository.delete(product);
    }

    @Transactional
    public Product save(ProductResponse.FindAllDTO product) {
        Product saveProduct = productRepository.save(product.toEntity());
        return saveProduct;
    }

    @Transactional
    public Product update(Long id, ProductResponse.FindByIdDTO productDTO) {
        Product productUpdate = productRepository.findById(id).orElseThrow( //예외 처리 바로하기
                () -> new Exception404("해당 상품을 찾을 수 없습니다. : " + id) // 상품이 없을 경우
        );

        productUpdate.updateFromDTO(productDTO);

        return productRepository.save(productUpdate);
    }

}
