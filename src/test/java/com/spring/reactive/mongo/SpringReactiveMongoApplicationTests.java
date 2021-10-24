package com.spring.reactive.mongo;

import com.spring.reactive.mongo.dto.ProductDTO;
import com.spring.reactive.mongo.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

/**
 * mocking services
 */
@WebFluxTest
@RunWith(SpringRunner.class)
class SpringReactiveMongoApplicationTests {

	@Autowired
	private WebTestClient webTestClient;

	@MockBean
	ProductService productService;



	@Test
	public void addProductTest() {
		Mono<ProductDTO> productDTOMono = Mono.just(new ProductDTO("101", "mobile", 1, 10000));
		when(productService.saveProduct(productDTOMono)).thenReturn(productDTOMono);

		webTestClient.post().uri("/products")
				.body(Mono.just(productDTOMono), ProductDTO.class)
				.exchange().expectStatus().isOk();
	}

	@Test
	public void getProductsTest() {
		Flux<ProductDTO> productDTOFlux = Flux.just(new ProductDTO("102", "pc", 2, 20000));
		when(productService.getProducts()).thenReturn(productDTOFlux);

		Flux<ProductDTO> responseBody = webTestClient.get().uri("/products")
			.exchange()
				.expectStatus().isOk()
				.returnResult(ProductDTO.class).getResponseBody();
		StepVerifier.create(responseBody)
					.expectSubscription()
					.expectNext(new ProductDTO("102", "pc", 2, 20000))
					.verifyComplete();
	}

	@Test
	public  void getProductTest() {
		Mono<ProductDTO> productDTOMono = Mono.just(new ProductDTO("103", "pc", 2, 20000));
		when(productService.getProduct(any())).thenReturn(productDTOMono);

		Flux<ProductDTO> responseBody  = webTestClient.get().uri("/products/999")
				.exchange().expectStatus().isOk()
				.returnResult(ProductDTO.class)
				.getResponseBody();

		StepVerifier.create(responseBody)
				.expectSubscription()
				.expectNextMatches(p -> p.getName().equals("pc"))
				.verifyComplete();
	}

	@Test
	public void updateProductTest() {
		Mono<ProductDTO> productDTOMono = Mono.just(new ProductDTO("106", "mouse", 2, 20000));
		when(productService.updateProduct(productDTOMono, "106")).thenReturn(productDTOMono);

		webTestClient.put().uri("/products/109")
					.body(Mono.just(productDTOMono), ProductDTO.class)
					.exchange()
					.expectStatus().isOk();
	}

	@Test
	public void deleteProductTest() {
		given(productService.deleteProduct(any())).willReturn(Mono.empty());

		webTestClient.delete().uri("/products/delete/106")
				.exchange()
				.expectStatus().isOk();
	}
}
