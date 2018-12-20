package org.eugenewyj.tacos.web;

import org.eugenewyj.tacos.Ingredient;
import org.eugenewyj.tacos.data.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * IngredientByIdConverter
 *
 * @author eugene
 * @date 2018/12/20
 */
@Component
public class IngredientByIdConverter implements Converter<String, Ingredient> {

    private IngredientRepository ingredientRepository;

    @Autowired
    public IngredientByIdConverter(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public Ingredient convert(String id) {
        Optional<Ingredient> optinalIngredient = ingredientRepository.findById(id);
        return optinalIngredient.orElse(null);
    }
}
