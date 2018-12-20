package org.eugenewyj.tacos;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Ingredient（配料）
 *
 * @author eugene
 * @date 2018/12/12
 */
@Entity
@Data
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class Ingredient {

    @Id
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
