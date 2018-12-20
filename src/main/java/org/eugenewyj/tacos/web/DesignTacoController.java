package org.eugenewyj.tacos.web;

import lombok.extern.slf4j.Slf4j;
import org.eugenewyj.tacos.Ingredient;
import org.eugenewyj.tacos.Ingredient.Type;
import org.eugenewyj.tacos.Order;
import org.eugenewyj.tacos.Taco;
import org.eugenewyj.tacos.User;
import org.eugenewyj.tacos.data.IngredientRepository;
import org.eugenewyj.tacos.data.TacoRepository;
import org.eugenewyj.tacos.data.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * DesignTacoController
 *
 * @author eugene
 * @date 2018/12/12
 */
@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("order")
public class DesignTacoController {

    private final IngredientRepository ingredientRepository;
    private TacoRepository tacoRepository;
    private UserRepository userRepository;

    @Autowired
    public DesignTacoController(IngredientRepository ingredientRepository, TacoRepository tacoRepository, UserRepository userRepository) {
        this.ingredientRepository = ingredientRepository;
        this.tacoRepository = tacoRepository;
        this.userRepository = userRepository;
    }

    @ModelAttribute(name = "order")
    public Order order() {
        return new Order();
    }

    @ModelAttribute(name = "design")
    public Taco taco() {
        return new Taco();
    }

    @GetMapping
    public String showDesignForm(Model model, Principal principal) {
        List<Ingredient> ingredients = new ArrayList<>();
        ingredientRepository.findAll().forEach(i -> ingredients.add(i));

        Type[] types = Type.values();
        for (Type type : types) {
            model.addAttribute(type.toString().toLowerCase(), filterByType(ingredients, type));
        }

        String username = principal.getName();
        User user = userRepository.findByUsername(username);
        model.addAttribute("user", user);

        return "design";
    }

    private Object filterByType(List<Ingredient> ingredients, Type type) {
        return ingredients.stream()
                .filter(x -> x.getType().equals(type))
                .collect(Collectors.toList());
    }

    @PostMapping
    public String processDesign(@Valid Taco design, Errors errors, @ModelAttribute Order order) {
        if (errors.hasErrors()) {
            return "design";
        }

        log.info("处理design:" + design);

        Taco saved = tacoRepository.save(design);
        order.addDesign(saved);

        return "redirect:/orders/current";
    }
}
