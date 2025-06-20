package com.ecommerce.product_service.repository;

import com.ecommerce.product_service.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Integer> {
}
