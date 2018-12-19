package org.eugenewyj.tacos;

import lombok.Data;
import org.hibernate.validator.constraints.CreditCardNumber;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Order
 *
 * @author eugene
 * @date 2018/12/15
 */
@Data
public class Order {

    private Long id;

    private LocalDateTime placedAt;

    @NotBlank(message = "名字不能为空")
    private String deliveryName;

    @NotBlank(message = "街道不能为空")
    private String deliveryStreet;

    @NotBlank(message = "城市不能为空")
    private String deliveryCity;

    @NotBlank(message = "省份不能为空")
    private String deliveryState;

    @NotBlank(message = "zip码不能为空")
    private String deliveryZip;

    @CreditCardNumber(message = "信用卡账号非法")
    private String ccNumber;

    @Pattern(regexp = "^(0[1-9]|1[0-2])([\\/])([1-9][0-9])$",
    message = "格式必须是 MM/YY")
    private String ccExpiration;

    @Digits(integer = 3, fraction = 0, message = "CVV非法")
    private String ccCVV;

    private List<Taco> tacos = new ArrayList<>();

    public void addDesign(Taco saved) {
        this.tacos.add(saved);
    }
}
