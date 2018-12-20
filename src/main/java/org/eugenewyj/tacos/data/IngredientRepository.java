package org.eugenewyj.tacos.data;

import org.eugenewyj.tacos.Ingredient;
import org.springframework.data.repository.CrudRepository;

/**
 * IngredientRepository
 *
 * @author eugene
 * @date 2018/12/18
 */
public interface IngredientRepository extends CrudRepository<Ingredient, String> {
}
