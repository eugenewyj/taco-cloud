package org.eugenewyj.tacos.data;

import org.eugenewyj.tacos.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * OrderRepository
 *
 * @author eugene
 * @date 2018/12/18
 */
public interface OrderRepository extends CrudRepository<Order, Long> {
}
