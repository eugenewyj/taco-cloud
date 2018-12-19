package org.eugenewyj.tacos.data;

import org.eugenewyj.tacos.Ingredient;

/**
 * IngredientRepository
 *
 * @author eugene
 * @date 2018/12/18
 */
public interface IngredientRepository {
    Iterable<Ingredient> findAll();
}
