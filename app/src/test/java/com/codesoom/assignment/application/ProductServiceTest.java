package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
import com.codesoom.assignment.dto.ProductData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@DisplayName("ProductService 인터페이스의")
public class ProductServiceTest {
    public static final String NAME = "먼지털이";
    public static final String MAKER = "코드숨";
    public static final int PRICE = 2200000;
    public static final String URL = "picture.com";
    public static final Long BASIC_ID = 1L;

    private ProductService productService;
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productRepository = mock(ProductRepository.class);
        productService = new ToyService(productRepository);
    }

    Product createdProduct() {
        return new Product(BASIC_ID, NAME, MAKER, PRICE, URL);
    }

    @Nested
    @DisplayName("create 메서드는")
    class Describe_create {
        @Nested
        @DisplayName("상품 정보가 주어지면")
        class Context_with_product {
            ProductData productData = new ProductData(NAME, MAKER, PRICE, URL);
            Product expect = createdProduct();

            @BeforeEach
            void prepare() {
                given(productRepository.save(productData.toProduct()))
                        .willReturn(expect);
            }

            @Test
            @DisplayName("상품을 리턴한다")
            void It_returns_product() {
                assertThat(productService.create(productData)).isEqualTo(expect);

                verify(productRepository).save(any(Product.class));
            }
        }
    }

    @Nested
    @DisplayName("findById 메서드는")
    class Describe_findById {
        @Nested
        @DisplayName("상품이 있다면")
        class Context_with_product {
            @BeforeEach
            void prepare() {
                given(productRepository.findById(BASIC_ID))
                        .willReturn(Optional.of(createdProduct()));
            }

            @Test
            @DisplayName("상품을 리턴한다")
            void It_returns_product() {
                assertThat(productService.findById(BASIC_ID))
                        .isEqualTo(createdProduct());

                verify(productRepository).findById(BASIC_ID);
            }
        }
    }
}
