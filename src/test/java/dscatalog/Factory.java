package dscatalog;

import dscatalog.dto.ProductDTO;
import dscatalog.entities.Category;
import dscatalog.entities.Product;

import java.time.Instant;

public class Factory {

    public static Product createProduct(){
        Product product = new Product(null,"cd", "cd", 20.0, "img", Instant.now());
        product.getCategories().add(new Category(1L, "Informatics"));
        return product;
    }

    public static ProductDTO createProductDTO(){
        Product product = createProduct();
        return new ProductDTO(product, product.getCategories());
    }


}
