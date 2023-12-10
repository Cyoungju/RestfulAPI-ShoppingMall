package com.example.demo.option;

import com.example.demo.ProductApplication;
import com.example.demo.core.error.exception.Exception400;
import com.example.demo.core.error.exception.Exception404;
import com.example.demo.product.Product;
import com.example.demo.product.ProductRepository;
import com.example.demo.product.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional(readOnly = true) // 읽기 전용
@RequiredArgsConstructor
@Service
public class OptionService {
    private final OptionRepository optionRepository;
    private final ProductRepository productRepository;

    @Transactional
    public Option save(OptionRequest.saveDTO optionDTO) {

        Optional<Product> optionalOption = productRepository.findById(optionDTO.getId());

        if (optionalOption.isPresent()) {
            Product product = optionalOption.get();

            Option entity = optionDTO.toEntity(product);

            // Option 엔터티를 저장하고 반환
            return optionRepository.save(entity);
        } else {
            throw new Exception404("해당 ID에 대한 Option을 찾을 수 없습니다. ID: " + optionDTO.getId());
        }
    }


    public List<OptionResponse.FindByProductIdDTO> findByProductId(Long id){
        //id를 이용해서 optionRepository에서 해당 제품의 옵션 리스트 조회
        List<Option> optionList = optionRepository.findByProductId(id);

        // 제품이 없을 경우 예외 처리
        if (optionList.isEmpty()) {
            throw new Exception404("해당 ID에 대한 제품이 없습니다. Product ID: " + id);
        }

        // 옵션 리스트를 OptionResponse.FindByProductIdDTO로 변환하는 스트림 작업 진행
        List<OptionResponse.FindByProductIdDTO> optionResponse =
                optionList.stream().map(OptionResponse.FindByProductIdDTO::new)
                        // OptionResponse.FindByProductIdDTO 클래스의 생성자를 이용하여 각각의 옵션을 OptionResponse.FindByProductIdDTO로 매핑한다.
                        .collect(Collectors.toList());
                        // 매핑된 OptionResponse.FindByProductIdDTO 객체들을 리스트로 수집
        return optionResponse;
        // 변환된 OptionResponse.FindByProductIdDTO 리스트를 반환
    }

    public List<OptionResponse.FindAllDTO> findAll() {
        List<Option> optionList = optionRepository.findAll();

        // 옵션이 하나도 없을 경우 예외 처리
        if (optionList.isEmpty()) {
            throw new Exception404("옵션이 없습니다.");
        }

        List<OptionResponse.FindAllDTO> findAllDTOS =
                optionList.stream().map(OptionResponse.FindAllDTO::new)
                        .collect(Collectors.toList());

        return findAllDTOS;
    }


    @Transactional
    public Option update(OptionRequest.updateDTO updateDTO, Long id) {
        // productRepository 사용해 productId에 해당하는 상품 찾기
        Product product = productRepository.findById(id)
                .orElseThrow(() ->  new Exception404("해당 ID에 대한 상품을 찾을 수 없습니다. Product ID: " + id));

        // optionRepository 사용하여 해당 상품에 속한 옵션 중 optionId와 일치하는 옵션 찾기
        Optional<Option> optionalOption = optionRepository.findByIdAndProduct(updateDTO.getId(), product);
        // 찾은 옵션이 있다면 수정 작업 진행
        if (optionalOption.isPresent()) {
            Option option = optionalOption.get();
            option.updateFromDTO(updateDTO);

            return optionRepository.save(option);

        } else {
            // 찾은 상품 없는 경우에 대한 처리
            throw new Exception404("해당 상품을 찾을 수 없습니다: " + updateDTO.getProductId());
        }
    }



    @Transactional
    public void delete(Long id, Long optionId) {
        // productTd에 해당하는 상품 찾기
        Product deleteProduct = productRepository.findById(id)
                .orElseThrow(() -> new Exception404("해당 ID에 대한 상품을 찾을 수 없습니다. Product ID: " + id));

        // optionRepository를 사용하여 해당하는 상품이 속한 옵션들중 optionId와 일치하는 옵션 찾기
        Optional<Option> optionalOption = optionRepository.findByIdAndProduct(optionId, deleteProduct);

        // 찾는 옵션이 있다면 삭제
        if(optionalOption.isPresent()){
            optionRepository.delete(optionalOption.get());
        } else {
            // 없다면 예외처리
            throw new Exception404("해당 옵션을 찾을 수 없습니다: " + optionId + " for Product ID: " + id);
        }
    }



}
