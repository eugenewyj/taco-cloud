package org.eugenewyj.tacos;

import org.eugenewyj.tacos.data.IngredientRepository;
import org.eugenewyj.tacos.data.OrderRepository;
import org.eugenewyj.tacos.data.TacoRepository;
import org.eugenewyj.tacos.data.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * HomeControllerTest
 *
 * @author eugene
 * @date 2018/12/14
 */
@RunWith(SpringRunner.class)
@WebMvcTest
public class HomeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderRepository orderRepository;

    @MockBean
    private TacoRepository tacoRepository;

    @MockBean
    private IngredientRepository ingredientRepository;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @Test
    public void testHome() throws Exception {
        mockMvc.perform(get("/abc"))
                .andExpect(status().isOk())
                .andExpect(view().name("home"))
                .andExpect(content().string(containsString("Welcome to...")));
    }
}
