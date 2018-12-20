package org.eugenewyj.tacos.web;

import lombok.extern.slf4j.Slf4j;
import org.eugenewyj.tacos.Order;
import org.eugenewyj.tacos.data.OrderRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.support.SessionStatus;

import javax.inject.Inject;
import javax.validation.Valid;

/**
 * OrderController
 *
 * @author eugene
 * @date 2018/12/15
 */
@Slf4j
@Controller
@RequestMapping("/orders")
public class OrderController {

    private OrderRepository orderRepository;

    @Inject
    public OrderController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @GetMapping("/current")
    public String orderForm(Model model) {
        model.addAttribute("order", new Order());
        return "orderForm";
    }

    @PostMapping
    public String processOrder(@Valid Order order, Errors errors, SessionStatus sessionStatus) {
        if (errors.hasErrors()) {
            return "orderForm";
        }

        orderRepository.save(order);
        sessionStatus.isComplete();

        log.info("Order submitted:" + order);
        return "redirect:/";
    }
}
