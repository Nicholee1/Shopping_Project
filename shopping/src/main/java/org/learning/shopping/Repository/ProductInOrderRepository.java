package org.learning.shopping.Repository;

import org.learning.shopping.entity.ProductInOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductInOrderRepository extends JpaRepository<ProductInOrder,Long> {
}
