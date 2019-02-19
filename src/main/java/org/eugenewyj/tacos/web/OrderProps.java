package org.eugenewyj.tacos.web;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * OrderProps
 *
 * @author eugene
 * @date 2019-02-19
 */
@Component
@ConfigurationProperties(prefix = "taco.orders")
@Data
@Validated
public class OrderProps {
    @Min(value = 5, message = "必须介于5和25之间")
    @Max(value = 25, message = "必须介于5和25之间")
    private int pageSize = 20;
}
