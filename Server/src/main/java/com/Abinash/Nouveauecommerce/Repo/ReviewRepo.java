package com.Abinash.Nouveauecommerce.Repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.Abinash.Nouveauecommerce.Model.Review;

public interface ReviewRepo extends JpaRepository<Review, Long>{
	
	@Query("SELECT r FROM Review r WHERE r.product.id=:productId")
	public List<Review> getAllProductsReview(@Param("productId") Long productId);
}
