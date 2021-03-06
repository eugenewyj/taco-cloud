package org.eugenewyj.tacos;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * DesignAndOrderTacosBrowserTest
 *
 * @author eugene
 * @date 2018/12/15
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class DesignAndOrderTacosBrowserTest {

    private static HtmlUnitDriver browser;

    @LocalServerPort
    private int port;

    @Autowired
    TestRestTemplate rest;

    @BeforeClass
    public static void setUp() {
        browser = new HtmlUnitDriver();
        browser.manage().timeouts()
                .implicitlyWait(10, TimeUnit.SECONDS);
    }

    @AfterClass
    public static void tearDown() {
        browser.quit();
    }

    @Test
    public void testDesginATacoPage_HappyPath() {
        browser.get(homePageUrl());
        clickDesignATaco();
        assertLandedOnLoginPage();
        doRegistration("testuser", "testpassword");
        assertLandedOnLoginPage();
        doLogin("testuser", "testpassword");
        assertDesignPageElements();
        buildAndSubmitATaco("Basic Taco", "FLTO", "GRBF", "CHED", "TMTO", "SLSA");
        clickBuildAnotherTaco();
        buildAndSubmitATaco("Another Taco", "COTO", "CARN", "JACK", "LETC", "SRCR");
        fillInAndSubmitOrderForm();
        assertEquals(homePageUrl(), browser.getCurrentUrl());
        doLogout();
    }

    @Test
    public void testDesignATacoPage_EmptyOrderInfo() {
        browser.get(homePageUrl());
        clickDesignATaco();
        assertLandedOnLoginPage();
        doRegistration("testuser2", "testpassword");
        doLogin("testuser2", "testpassword");
        assertDesignPageElements();
        buildAndSubmitATaco("Basic Taco", "FLTO", "GRBF", "CHED", "TMTO", "SLSA");
        submitEmptyOrderForm();
        fillInAndSubmitOrderForm();
        assertEquals(homePageUrl(), browser.getCurrentUrl());
        doLogout();
    }

    @Test
    public void testDesignATacoPage_InvalidOrderInfo() {
        browser.get(homePageUrl());
        clickDesignATaco();
        assertLandedOnLoginPage();
        doRegistration("testuser3", "testpassword");
        doLogin("testuser3", "testpassword");
        assertDesignPageElements();
        buildAndSubmitATaco("Basic Taco", "FLTO", "GRBF", "CHED", "TMTO", "SLSA");
        submitInvalidOrderForm();
        fillInAndSubmitOrderForm();
        assertEquals(homePageUrl(), browser.getCurrentUrl());
        doLogout();
    }

    private void doLogout() {
        WebElement logoutForm = browser.findElementByCssSelector("form#logoutForm");
        if (logoutForm != null) {
            logoutForm.submit();
        }
    }

    private void doLogin(String username, String password) {
        browser.findElementByCssSelector("input#username").sendKeys(username);
        browser.findElementByCssSelector("input#password").sendKeys(password);
        browser.findElementByCssSelector("form#loginForm").submit();
    }

    private void doRegistration(String username, String password) {
        browser.findElementByLinkText("here").click();
        assertEquals(registrationPageUrl(), browser.getCurrentUrl());
        browser.findElementByName("username").sendKeys(username);
        browser.findElementByName("password").sendKeys(password);
        browser.findElementByName("confirm").sendKeys(password);
        browser.findElementByName("fullname").sendKeys("Test McTest");
        browser.findElementByName("street").sendKeys("1234 Test Street");
        browser.findElementByName("city").sendKeys("Testville");
        browser.findElementByName("state").sendKeys("TX");
        browser.findElementByName("zip").sendKeys("12345");
        browser.findElementByName("phone").sendKeys("123-123-1234");
        browser.findElementByCssSelector("form#registerForm").submit();
    }

    private void submitInvalidOrderForm() {
        assertTrue(browser.getCurrentUrl().startsWith(orderDetailsPageUrl()));
        fillField("input#deliveryName", "I");
        fillField("input#deliveryStreet", "1");
        fillField("input#deliveryCity", "F");
        fillField("input#deliveryState", "C");
        fillField("input#deliveryZip", "8");
        fillField("input#ccNumber", "1234432112344322");
        fillField("input#ccExpiration", "14/91");
        fillField("input#ccCVV", "1234");
        browser.findElementByCssSelector("form#orderForm").submit();

        assertEquals(orderDetailsPageUrl(), browser.getCurrentUrl());

        List<String> validationErrors = getValidationErrorTexts();
        assertEquals(4, validationErrors.size());
        assertTrue(validationErrors.contains("Please correct the problems below and resubmit."));
        assertTrue(validationErrors.contains("信用卡账号非法"));
        assertTrue(validationErrors.contains("格式必须是 MM/YY"));
        assertTrue(validationErrors.contains("CVV非法"));
    }

    private void submitEmptyOrderForm() {
        assertEquals(currentOrderDetailsPageUrl(), browser.getCurrentUrl());
        fillField("input#deliveryName", "");
        fillField("input#deliveryStreet", "");
        fillField("input#deliveryCity", "");
        fillField("input#deliveryState", "");
        fillField("input#deliveryZip", "");
        browser.findElementByCssSelector("form#orderForm").submit();

        assertEquals(orderDetailsPageUrl(), browser.getCurrentUrl());

        List<String> validationErrors = getValidationErrorTexts();
        assertEquals(9, validationErrors.size());
        assertTrue(validationErrors.contains("Please correct the problems below and resubmit."));
        assertTrue(validationErrors.contains("名字不能为空"));
        assertTrue(validationErrors.contains("街道不能为空"));
        assertTrue(validationErrors.contains("城市不能为空"));
        assertTrue(validationErrors.contains("省份不能为空"));
        assertTrue(validationErrors.contains("zip码不能为空"));
        assertTrue(validationErrors.contains("信用卡账号非法"));
        assertTrue(validationErrors.contains("格式必须是 MM/YY"));
        assertTrue(validationErrors.contains("CVV非法"));
    }

    private List<String> getValidationErrorTexts() {
        List<WebElement> validationErrorElments = browser.findElementsByClassName("validationError");
        List<String> validationErrors = validationErrorElments.stream()
                .map(e -> e.getText())
                .collect(Collectors.toList());
        return validationErrors;
    }

    private String currentOrderDetailsPageUrl() {
        return homePageUrl() + "orders/current";
    }

    private void clickDesignATaco() {
        assertEquals(homePageUrl(), browser.getCurrentUrl());
        browser.findElementByCssSelector("a[id='design']").click();
    }

    private void fillInAndSubmitOrderForm() {
        assertTrue(browser.getCurrentUrl().startsWith(orderDetailsPageUrl()));
        fillField("input#deliveryName", "Ima Hungry");
        fillField("input#deliveryStreet", "1234 Culinary Blvd.");
        fillField("input#deliveryCity", "Foodsville");
        fillField("input#deliveryState", "CO");
        fillField("input#deliveryZip", "81019");
        fillField("input#ccNumber", "4111111111111111");
        fillField("input#ccExpiration", "10/19");
        fillField("input#ccCVV", "123");
        browser.findElementByCssSelector("form").submit();
    }

    private void fillField(String filedName, String value) {
        WebElement field = browser.findElementByCssSelector(filedName);
        field.clear();
        field.sendKeys(value);
    }

    private void clickBuildAnotherTaco() {
        assertTrue(browser.getCurrentUrl().startsWith(orderDetailsPageUrl()));
        browser.findElementByCssSelector("a[id='another']").click();
    }

    private String orderDetailsPageUrl() {
        return homePageUrl() + "orders";
    }

    private void buildAndSubmitATaco(String name, String... ingredients) {
        assertDesignPageElements();

        for (String ingredient : ingredients) {
            browser.findElementByCssSelector("input[value='" + ingredient + "']").click();
        }

        browser.findElementByCssSelector("input#name").sendKeys(name);
        browser.findElementByCssSelector("form#tacoForm").submit();
    }


    private void assertDesignPageElements() {
        assertEquals(homePageUrl() + "design", browser.getCurrentUrl());
        List<WebElement> ingredientGroups = browser.findElementsByClassName("ingredient-group");
        assertEquals(5, ingredientGroups.size());

        WebElement wrapGroup = browser.findElementByCssSelector("div.ingredient-group#wraps");
        List<WebElement> wraps = wrapGroup.findElements(By.tagName("div"));
        assertEquals(2, wraps.size());
        assertIngredient(wrapGroup, 0, "FLTO", "Flour Tortilla");
        assertIngredient(wrapGroup, 1, "COTO", "Corn Tortilla");

        WebElement proteinGroup = browser.findElementByCssSelector("div.ingredient-group#proteins");
        List<WebElement> proteins = proteinGroup.findElements(By.tagName("div"));
        assertEquals(2, proteins.size());
        assertIngredient(proteinGroup, 0, "GRBF", "Ground Beef");
        assertIngredient(proteinGroup, 1, "CARN", "Carnitas");

        WebElement cheeseGroup = browser.findElementByCssSelector("div.ingredient-group#cheeses");
        List<WebElement> cheeses = cheeseGroup.findElements(By.tagName("div"));
        assertEquals(2, cheeses.size());
        assertIngredient(cheeseGroup, 0, "CHED", "Cheddar");
        assertIngredient(cheeseGroup, 1, "JACK", "Monterrey Jack");

        WebElement veggieGroup = browser.findElementByCssSelector("div.ingredient-group#veggies");
        List<WebElement> veggies = veggieGroup.findElements(By.tagName("div"));
        assertEquals(2, veggies.size());
        assertIngredient(veggieGroup, 0, "TMTO", "Diced Tomatoes");
        assertIngredient(veggieGroup, 1, "LETC", "Lettuce");

        WebElement sauceGroup = browser.findElementByCssSelector("div.ingredient-group#sauces");
        List<WebElement> sauces = sauceGroup.findElements(By.tagName("div"));
        assertEquals(2, sauces.size());
        assertIngredient(sauceGroup, 0, "SLSA", "Salsa");
        assertIngredient(sauceGroup, 1, "SRCR", "Sour Cream");
    }

    private void assertIngredient(WebElement ingredientGroup, int ingredientIndex, String id, String name) {
        List<WebElement> proteins = ingredientGroup.findElements(By.tagName("div"));
        WebElement ingredient = proteins.get(ingredientIndex);
        assertEquals(id, ingredient.findElement(By.tagName("input")).getAttribute("value"));
        assertEquals(name, ingredient.findElement(By.tagName("span")).getText());
    }

    private String registrationPageUrl() {
        return homePageUrl() + "register";
    }

    private void assertLandedOnLoginPage() {
        assertEquals(loginPageUrl(), browser.getCurrentUrl());
    }

    private String loginPageUrl() {
        return homePageUrl() + "login";
    }

    private String homePageUrl() {
        return "http://localhost:" + port + "/";
    }
}
