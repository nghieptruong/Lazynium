package com.malski.google.glue;

import com.malski.core.cucumber.TestContext;
import com.malski.google.validator.GoogleValidator;
import com.malski.google.web.pages.MainPage;
import com.malski.google.web.pages.ResultsPage;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

//import cucumber.api.java8.En;

//public class GoogleSteps implements En {
public class GoogleSteps {
    private GoogleValidator validator;

    @Before
    public void beforeScenario() {
        validator = new GoogleValidator();
    }

    @Given("^I open google main page$")
    public void open_google_main_page() {
        MainPage mainPage = new MainPage();
        TestContext.setPage(mainPage.open());
    }

    @When("^I search for \"(.+)\"$")
    public void search_for_page(String phrase) {
        MainPage mainPage = TestContext.getPage().as(MainPage.class);
        TestContext.setPage(mainPage.searchFor(phrase));
    }

    @Then("^verify that page \"(.+)\" is in results$")
    public void verify_that_page_is_in_results(String pageName) {
        ResultsPage resultsPage = TestContext.getPage().as(ResultsPage.class);
        validator.checkResultsText(resultsPage.getResultTitles(), pageName);
    }

    @After
    public void afterScenario() {
        TestContext.getBrowser().quit();
    }

//    public GoogleSteps() {
//        super();
//
//        Before(() -> {
//            validator = new GoogleValidator();
//        });
//
//        Given("^I open google main page$", () -> {
//            MainPage mainPage = new MainPage(this.browser);
//            page = mainPage.open();
//        });
//
//        When("^I search for \"(.+)\"$", (String phrase) -> {
//            MainPage mainPage = page.as(MainPage.class);
//            page = mainPage.searchFor(phrase);
//        });
//
//        Then("^verify that page \"(.+)\" is in results$", (String pageName) -> {
//            ResultsPage resultsPage = page.as(ResultsPage.class);
//            validator.checkResultsText(resultsPage.getResultTitles(), pageName);
//        });
//
//        After(() -> {
//            browser.quit();
//        });
//    }
}
