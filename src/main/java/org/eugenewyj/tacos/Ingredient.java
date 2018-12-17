package org.eugenewyj.tacos;

import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * Ingredient（配料）
 *
 * @author eugene
 * @date 2018/12/12
 */
@Data
@RequiredArgsConstructor
public class Ingredient {

    private final String id;
    private final String name;
    private final Type type;

    public enum Type {
        /**
         * 包装
         */
        WRAP,
        /**
         * 蛋白质
         */
        PROTEIN,
        /**
         * 蔬菜
         */
        VEGGIES,
        /**
         * 奶酪
         */
        CHEESE,
        /**
         * 果酱
         */
        SAUCE
    }
}
