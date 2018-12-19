package org.eugenewyj.tacos.data;

import org.eugenewyj.tacos.Order;

/**
 * OrderRepository
 *
 * @author eugene
 * @date 2018/12/18
 */
public interface OrderRepository {
    Order save(Order order);
}
