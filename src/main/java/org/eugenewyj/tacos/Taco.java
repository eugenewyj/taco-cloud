package org.eugenewyj.tacos;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Taco (墨西哥玉米薄饼卷)
 *
 * @author eugene
 * @date 2018/12/12
 */
@Data
@Entity
public class Taco {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min=5, message = "名称必须大于5个字符")
    private String name;

    @ManyToMany(targetEntity = Ingredient.class)
    @Size(min = 1, message = "至少选择一种配料")
    private List<Ingredient> ingredients;

    private LocalDateTime createdAt;

    @PrePersist
    void createdAt() {
        this.createdAt = LocalDateTime.now();
    }
}
