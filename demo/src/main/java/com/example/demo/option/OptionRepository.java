package com.example.demo.option;

import com.example.demo.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OptionRepository extends JpaRepository<Option, Long> {
    List<Option> findByProductId(Long id);

    Optional<Option> findByIdAndProduct(Long optionId, Product deleteProduct);
}
