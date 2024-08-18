package ru.zed.app.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.zed.app.model.MappingUtils;
import ru.zed.app.model.entity.ProductDTO;
import ru.zed.app.model.entity.ProductEntity;
import ru.zed.app.model.entity.ProductImage;
import ru.zed.app.service.ImageService;
import ru.zed.app.service.ProductService;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URI;

@Controller
@RequiredArgsConstructor
@RequestMapping("/catalog/products")
public class ProductController {

    private final ProductService productService;
    private final ImageService imageService;
    private final MappingUtils mappingUtils;

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

    @GetMapping("/images/{id}")
    public ResponseEntity<?> getImageById(@PathVariable("id") Long id) {
        ProductImage image = imageService.getImageById(id).orElse(null);
        return ResponseEntity.ok()
                .header("fileName", image.getOriginalFileName())
                .contentType(MediaType.valueOf(image.getContentType()))
                .contentLength(image.getSize())
                .body(new InputStreamResource(new ByteArrayInputStream(image.getBytes())));
    }

    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> saveProduct(@ModelAttribute @Valid ProductEntity entity,
                                            @RequestParam("file") MultipartFile file) throws IOException {
        this.productService.saveProduct(entity, file);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteProductFromDatabase(@PathVariable("id") Long id) {
        this.productService.deleteProductById(id);
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<String> handleUnsupportedMediaType(HttpMediaTypeNotSupportedException e) {
        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                .body("Content-type должен быть 'application/json'");
    }
}
