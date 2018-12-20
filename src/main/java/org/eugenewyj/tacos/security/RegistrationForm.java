package org.eugenewyj.tacos.security;

import lombok.Data;
import org.eugenewyj.tacos.User;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * RegistrationForm
 *
 * @author eugene
 * @date 2018/12/20
 */
@Data
public class RegistrationForm {

    private String username;
    private String password;
    private String fullname;
    private String street;
    private String city;
    private String state;
    private String zip;
    private String phone;

    public User toUser(PasswordEncoder passwordEncoder) {
        return new User(
                username, passwordEncoder.encode(password),
                fullname, street, city, state, zip, phone);
    }

}
