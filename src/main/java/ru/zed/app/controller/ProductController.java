package ru.zed.app.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.zed.app.model.entity.ProductDTO;
import ru.zed.app.model.entity.ProductEntity;
import ru.zed.app.service.ProductService;

import java.net.URI;

@Controller
@RequiredArgsConstructor
@RequestMapping("/catalog/products")
public class ProductController {

    private final ProductService productService;

    @RequestMapping(value = "list", method = RequestMethod.GET)
    public String getProductsList(Model model) {
        model.addAttribute("products", this.productService.findAllProducts());
        return "/catalog/products/list";
    }

    @GetMapping("/{id:\\d+}")
    public String getProductPage(@PathVariable("id") Long id, Model model) {
        ProductDTO productDTO = this.productService.findProduct(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        model.addAttribute("product", productDTO);
        return "/catalog/products/productPage";
    }

    @GetMapping("/create")
    public String getNewProductPage() {
        return "/catalog/products/new_product";
    }

    @PostMapping(value = "/create", consumes = "application/json")
    public ResponseEntity<Void> saveProduct(@Valid @RequestBody ProductEntity productEntity) {
        this.productService.saveProduct(productEntity);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(productEntity.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<String> handleUnsupportedMediaType(HttpMediaTypeNotSupportedException e) {
        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                .body("Content-type должен быть 'application/json'");
    }
}
