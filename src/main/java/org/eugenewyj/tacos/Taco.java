package org.eugenewyj.tacos;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * Taco (墨西哥玉米薄饼卷)
 *
 * @author eugene
 * @date 2018/12/12
 */
@Data
public class Taco {

    @NotNull
    @Size(min=5, message = "名称必须大于5个字符")
    private String name;

    @Size(min = 1, message = "至少选择一种配料")
    private List<String> ingredients;
}
