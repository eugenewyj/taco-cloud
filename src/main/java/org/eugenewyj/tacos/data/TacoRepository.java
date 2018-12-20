package org.eugenewyj.tacos.data;

import org.eugenewyj.tacos.Taco;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * TacoRepository
 *
 * @author eugene
 * @date 2018/12/18
 */
public interface TacoRepository extends CrudRepository<Taco, Long> {
}
