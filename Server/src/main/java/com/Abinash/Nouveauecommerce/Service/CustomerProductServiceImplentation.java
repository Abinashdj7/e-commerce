package com.Abinash.Nouveauecommerce.Service;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.Abinash.Nouveauecommerce.Exception.ProductException;
import com.Abinash.Nouveauecommerce.Model.Category;
import com.Abinash.Nouveauecommerce.Model.Product;
import com.Abinash.Nouveauecommerce.Repo.CategoryRepo;
import com.Abinash.Nouveauecommerce.Repo.ProductRepo;
import com.Abinash.Nouveauecommerce.Request.CreateProductRequest;

@Service
public class CustomerProductServiceImplentation implements ProductService{

	@Autowired
	ProductRepo productRepo;
	
	@Autowired
	UserService userService;
	
	@Autowired
	CategoryRepo categoryRepo;
	
	@Override
	public Product createProduct(CreateProductRequest req) {
		
		try {
			Category topLevel=categoryRepo.findByName(req.getTopLevelCategory());
			
			if(topLevel==null) {
				Category topLevelCategory=new Category();
				topLevelCategory.setName(req.getTopLevelCategory());
				topLevelCategory.setLevel(1);
				
				topLevel=categoryRepo.save(topLevelCategory);
			}
			System.out.println(topLevel.getName());
			System.out.println(topLevel);
			System.out.println(req.getSecondLevelCategory());
			Category secondLevel=categoryRepo.findByNameAndParent(req.getSecondLevelCategory(),topLevel.getName());
			System.out.println("Hey at the end");
			if(secondLevel==null) {
				Category secondLevelCategory=new Category();
				secondLevelCategory.setName(req.getSecondLevelCategory());
				secondLevelCategory.setParentCategory(topLevel);
				secondLevelCategory.setLevel(2);
				secondLevel=categoryRepo.save(secondLevelCategory);
			}
			Category thirdLevel=categoryRepo.findByNameAndParent(req.getThirdLevelCategory(),secondLevel.getName());
			
			if(thirdLevel==null) {
				Category thirdLevelCategory=new Category();
				thirdLevelCategory.setName(req.getThirdLevelCategory());
				thirdLevelCategory.setParentCategory(secondLevel);
				thirdLevelCategory.setLevel(3);
				
				secondLevel=categoryRepo.save(thirdLevelCategory);
			}
			
			Product product=new Product();
			product.setTitle(req.getTitle());
			product.setColour(req.getColour());
			product.setDescription(req.getDescription());
			product.setDiscountedPrice(req.getDiscountedPrice());
			product.setDiscountPercent(req.getDiscountPercent());
			product.setImageUrl(req.getImageUrl());
			product.setBrand(req.getBrand());
			product.setPrice(req.getPrice());
			product.setSizes(req.getSize());
			product.setQuantity(req.getQuantity());
			product.setCategory(thirdLevel);
			product.setCreatedAt(LocalDateTime.now());
			
			Product savedProduct=productRepo.save(product);
			return savedProduct;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String deleteProduct(Long productId) throws ProductException {
		Product product=findProductById(productId);
		product.getSizes().clear();
		productRepo.delete(product);
		return "Product deleted successfully";
	}

	@Override
	public Product updateProduct(Long productId, Product req) throws ProductException {
		Product product=findProductById(productId);
		if(req.getQuantity()!=0) {
			product.setQuantity(req.getQuantity());
		}
		return productRepo.save(product);
	}

	@Override
	public Product findProductById(Long id) throws ProductException {
		Optional<Product> opt=productRepo.findById(id);
		if(opt.isPresent()) {
			return opt.get();
		}
		throw new ProductException("Product not found with id-"+id);
	}

	@Override
	public List<Product> findByProductByCategory(String category) {
		List<Product> products = productRepo.findByCategory(category);
		
		return products;
	}

	@Override
	public List<Product> getAllProducts() {
		return productRepo.findAll();
	}


	@Override
	public List<Product> searchProduct(String query) {
		List<Product> products=productRepo.searchProduct(query);
		return products;
	}



	
	
	@Override
	public Page<Product> getAllProduct(String category,String color, 
			List<String> sizes, Integer minPrice, Integer maxPrice, 
			Integer minDiscount,String sort, String stock, Integer pageNumber, Integer pageSize ) {

		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		
		List<Product> products = productRepo.filterProducts(category, minPrice, maxPrice, minDiscount, sort,color);

		if(stock!=null) {

			if(stock.equals("in_stock")) {
				products=products.stream().filter(p->p.getQuantity()>0).collect(Collectors.toList());
			}
			else if (stock.equals("out_of_stock")) {
				products=products.stream().filter(p->p.getQuantity()<1).collect(Collectors.toList());				
			}
				
					
		}
		int startIndex = (int) pageable.getOffset();
		int endIndex = Math.min(startIndex + pageable.getPageSize(), products.size());

		List<Product> pageContent = products.subList(startIndex, endIndex);
		Page<Product> filteredProducts = new PageImpl<>(pageContent, pageable, products.size());
		System.out.println("Filtered products"+filteredProducts.getContent());
	    return filteredProducts; // If color list is empty, do nothing and return all products
		
		
	}


	@Override
	public List<Product> recentlyAddedProduct() {
		
		return productRepo.findTop10ByOrderByCreatedAtDesc();
	}

	@Override
	public List<Product> getProductTrue(String levelOneCategory, String levelTwoCategory, String levelThreeCategory) {
		
		List<Product> products=productRepo.getProductsByCategory(levelOneCategory, levelTwoCategory, levelThreeCategory);
		return products;
	}
}
