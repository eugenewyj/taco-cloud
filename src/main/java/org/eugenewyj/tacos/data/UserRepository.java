package org.eugenewyj.tacos.data;

import org.eugenewyj.tacos.User;
import org.springframework.data.repository.CrudRepository;

/**
 * UserRepository
 *
 * @author eugene
 * @date 2018/12/20
 */
public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
}
