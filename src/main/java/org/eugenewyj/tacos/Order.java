package org.eugenewyj.tacos;

import lombok.Data;
import org.hibernate.validator.constraints.CreditCardNumber;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * Order
 *
 * @author eugene
 * @date 2018/12/15
 */
@Data
public class Order {
    @NotBlank(message = "名字不能为空")
    private String name;

    @NotBlank(message = "街道不能为空")
    private String street;

    @NotBlank(message = "城市不能为空")
    private String city;

    @NotBlank(message = "省份不能为空")
    private String state;

    @NotBlank(message = "zip码不能为空")
    private String zip;

    @CreditCardNumber(message = "信用卡账号非法")
    private String ccNumber;

    @Pattern(regexp = "^(0[1-9]|1[0-2])([\\/])([1-9][0-9])$",
    message = "格式必须是 MM/YY")
    private String ccExpiration;

    @Digits(integer = 3, fraction = 0, message = "CVV非法")
    private String ccCVV;
}
