package org.eugenewyj.tacos.data;

import org.eugenewyj.tacos.Order;
import org.eugenewyj.tacos.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * OrderRepository
 *
 * @author eugene
 * @date 2018/12/18
 */
public interface OrderRepository extends CrudRepository<Order, Long> {
    List<Order> findByUserOrdersByPlacedAtDesc(User user, Pageable pageable);
}
