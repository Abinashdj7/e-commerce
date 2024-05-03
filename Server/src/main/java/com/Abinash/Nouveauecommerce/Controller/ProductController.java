package com.Abinash.Nouveauecommerce.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.service.annotation.GetExchange;

import com.Abinash.Nouveauecommerce.Exception.ProductException;
import com.Abinash.Nouveauecommerce.Model.Product;
import com.Abinash.Nouveauecommerce.Service.ProductService;

@CrossOrigin(origins = "*",allowedHeaders = "*", exposedHeaders = "*")
@RestController
@RequestMapping("/api")
public class ProductController {
	
	@Autowired
	ProductService productService;
	
	@GetMapping("/products")
	public ResponseEntity<Page<Product>> findProductByCategoryHandler(@RequestParam String category,
			@RequestParam String color,List<String> sizes,@RequestParam Integer minPrice,
			@RequestParam Integer maxPrice,@RequestParam Integer minDiscount,@RequestParam String sort,
			@RequestParam String stock,@RequestParam Integer pageNumber,@RequestParam Integer pageSize){
		Page<Product> res=productService.getAllProduct(category, color, sizes, minPrice, maxPrice, minDiscount, sort, stock, pageNumber, pageSize);
		System.out.println("Every product is here");
		
		return new ResponseEntity<Page<Product>>(res,HttpStatus.ACCEPTED);
		
	}
 	
	@GetMapping("/products/id/{productId}")
	public ResponseEntity<Product> findProductByIdHandler(@PathVariable Long productId) throws ProductException{
		Product product=productService.findProductById(productId);
		return new ResponseEntity<Product>(product,HttpStatus.ACCEPTED);
	}
	@GetMapping("/product/search")
	public ResponseEntity<List<Product>> searchProductHandler(@RequestParam String q){
		
		List<Product> products=productService.searchProduct(q);
		
		return new ResponseEntity<List<Product>>(products,HttpStatus.OK);
	}
	
	@GetMapping("/allProducts")
	public ResponseEntity<List<Product>> everyProduct(){
		List<Product> products=productService.getAllProducts();
		return new ResponseEntity<List<Product>>(products,HttpStatus.OK);
	}
	
	@GetMapping("/category/{levelOneCategory}/{levelTwoCategory}/{levelThreeCategory}")
	public ResponseEntity<List<Product>> getProductTrue(@PathVariable String levelOneCategory,@PathVariable String levelTwoCategory,
			@PathVariable String levelThreeCategory){
		List<Product> products=productService.getProductTrue(levelOneCategory, levelTwoCategory, levelThreeCategory);
		
		return new ResponseEntity<List<Product>>(products,HttpStatus.OK);
	}
}
