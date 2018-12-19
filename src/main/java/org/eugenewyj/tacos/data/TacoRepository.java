package org.eugenewyj.tacos.data;

import org.eugenewyj.tacos.Taco;

/**
 * TacoRepository
 *
 * @author eugene
 * @date 2018/12/18
 */
public interface TacoRepository {
    Taco save(Taco design);
}
