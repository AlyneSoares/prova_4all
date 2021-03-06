package org.alyne.soares.steps;

import org.alyne.soares.infrastructure.SetUp;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import static org.junit.Assert.*;
import java.io.File;

import org.alyne.soares.pageObjects.HomePage;
import org.apache.commons.io.FileUtils;
import org.alyne.soares.pageObjects.CartContentPage;

public class StepDefinitions {
	private final static String SCREENSHOT_PATH = "target/reports/screenshot/";
    private final WebDriver driver;
    private final HomePage homePage;
    private final CartContentPage cartPage;
    private final SetUp setup;

    public StepDefinitions() throws Exception {
        setup = new SetUp();
        setup.setWebDriver();
        driver = SetUp.driver;
        homePage = new HomePage(driver);
        cartPage = new CartContentPage(driver);
    }

    @Given("I navigate to ShopCart-4all")
    public void i_navigate_to_shopCart4all() {
        homePage.openHomePage();
    }

    @When("I add Brigadeiro to my cart")
    public void i_add_brigadeiro_to_my_cart() {
        homePage.selectCategoryDoces();
        homePage.addBrigadeiro();
    }

    @When("I add Alfajor to my cart")
    public void i_add_alfajor_to_my_cart() {
        homePage.addAlfajor();
    }

    @When("I add drinks to my cart")
    public void i_add_drinks_to_my_cart() {
        homePage.selectCategoryBebidas();
        homePage.addCoke();
        homePage.addFanta();
        homePage.addAgua();
    }

    @When("I increase the brigadeiro's quantity in {int}")
    public void i_increase_the_brigadeiro_quantity(final int qnt) {
        homePage.openCartPage();
        cartPage.increaseBrigadeiro(qnt);
    }

    @When("I add Rissole to my cart")
    public void i_add_rissole_to_my_cart() {
        homePage.selectCategoryTodos();
        homePage.addRissole();
    }

    @When("I increase the rissole's quantity in {int}")
    public void i_increase_the_rissole_quantity(final int qnt) {
        homePage.openCartPage();
        cartPage.increaseRissole(qnt);
    }

    @When("I decrease the rissole's quantity in {int}")
    public void i_decrease_the_rissole_quantity(final int qnt) {
        cartPage.decreaseRissole(qnt);
    }

    @Then("I purchase the candies")
    public void i_purchase_the_candies() throws Exception {
        TakesScreenshot candyCart =((TakesScreenshot)driver);
        File CandyCart = candyCart.getScreenshotAs(OutputType.FILE);   
        File DestFile = new File(SCREENSHOT_PATH + "candyCart.jpg"); 
        FileUtils.copyFile(CandyCart, DestFile);
        cartPage.clickCheckOut();
        assertEquals("Pedido realizado com sucesso!", cartPage.modalMessage());
        cartPage.closeConfirmationModal();
        homePage.closeBrowser();
    }

    @Then("I confirm the total value of my cart")
    public void i_confirm_total_value_of_my_cart() throws Exception {
        TakesScreenshot fullCart =((TakesScreenshot)driver);
        File FullCart = fullCart.getScreenshotAs(OutputType.FILE);
        File DestFile = new File(SCREENSHOT_PATH + "fullCart.jpg");
        FileUtils.copyFile(FullCart, DestFile);
        final Double productsSum = cartPage.cokeTotalPrice() + cartPage.fantaTotalPrice() + cartPage.aguaTotalPrice()
                + cartPage.rissoleTotalPrice();
        assertEquals(new Double (cartPage.totalPrice()), new Double (productsSum));
        cartPage.clickCheckOut();
        assertEquals("Pedido realizado com sucesso!", cartPage.modalMessage());        
        cartPage.closeConfirmationModal();
        homePage.closeBrowser();
    }

}
