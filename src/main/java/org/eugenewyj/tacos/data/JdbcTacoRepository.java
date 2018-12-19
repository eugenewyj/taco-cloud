package org.eugenewyj.tacos.data;

import org.eugenewyj.tacos.Taco;
import org.springframework.stereotype.Repository;

/**
 * JdbcTacoRepository
 *
 * @author eugene
 * @date 2018/12/19
 */
@Repository
public class JdbcTacoRepository implements TacoRepository {
    @Override
    public Taco save(Taco design) {
        return null;
    }
}
