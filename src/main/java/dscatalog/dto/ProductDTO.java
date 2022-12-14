package dscatalog.dto;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import dscatalog.Autor;
import dscatalog.entities.Category;
import dscatalog.entities.Product;

import javax.validation.constraints.*;

@Autor(name = "Vanderlei")
public class ProductDTO implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;
	
	private Long id;
	@NotBlank(message = "Required field")
	@Size(min = 3, max = 100)
	private String name;
	private String description;
	@Positive(message = "Field must contain positive value")
	private Double price;
	private String imgUrl;
	@PastOrPresent(message = "Date must be in present or past, not future")
	private Instant date;
	private final List<CategoryDTO> categories = new ArrayList<>();
	
	public ProductDTO() {}

	public ProductDTO(Long id, String name, String description, Double price, String imgUrl, Instant date) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;
		this.imgUrl = imgUrl;
		this.date = date;
	}
	
	public ProductDTO(Product product) {
		this.id = product.getId();
		this.name = product.getName();
		this.description = product.getDescription();
		this.price = product.getPrice();
		this.imgUrl = product.getImgUrl();
		this.date = product.getDate();
	}
	
	public ProductDTO(Product product, Set<Category> categories) {
		this(product);
		categories.forEach(cat -> this.categories.add(new CategoryDTO(cat)));
	}
	
	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public Instant getDate() {
		return date;
	}

	public void setDate(Instant date) {
		this.date = date;
	}

	public List<CategoryDTO> getCategories() {
		return categories;
	}
}
