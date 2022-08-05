package guru.qa;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class TestRegistrationForm {

    TestData testPerson = new TestData("John", "Smith", "any@gmail.com",
            "Any Street", "8800800099");

    @BeforeAll
    static void config(){
        Configuration.baseUrl = "https://demoqa.com";
        Configuration.timeout = 5000;
        Configuration.browser = "chrome";
        Configuration.browserSize = "760x840";
    }

    @AfterAll
    static void configuration(){
        Configuration.holdBrowserOpen = true;
    }

    @Test
    void registrationFormTest() {
        open("/automation-practice-form");

        $("#firstName").setValue(testPerson.firstName);
        $("#lastName").setValue(testPerson.lastName);
        $("#userEmail").setValue(testPerson.email);
        $("[for='gender-radio-1']").click();
        $("#userNumber").setValue(testPerson.phoneNumb);
        $("#dateOfBirthInput").click();

        $(".react-datepicker__year-select").click();
        $(".react-datepicker__year-select").selectOptionByValue("1992");
        $(".react-datepicker__month-select").click();
        $(".react-datepicker__month-select").selectOptionByValue("2");
        $(".react-datepicker__day--019").click();

        $("#subjectsInput").setValue("Computer Science").pressEnter();
        $(byText("Reading")).click();
        $(byText("Music")).click();
        $("#uploadPicture").uploadFile(new File("src/test/resources/ava.jpg"));
        $("#currentAddress").setValue(testPerson.currentAddress);
        $("#state").click();
        $(byText("Haryana")).click();
        $("#city").click();
        $(byText("Karnal")).click();
        $("#submit").click();
        //Assertions
        $(".modal-header").shouldHave(text("Thanks for submitting the form"));
        $(".table-responsive").shouldHave(
                text(testPerson.firstName),
                text(testPerson.lastName),
                text("Male"),
                text(testPerson.phoneNumb),
                text("19 March,1992"),
                text("Computer Science"),
                text("Reading, Music"),
                text("ava.jpg"),
                text(testPerson.currentAddress),
                text("Haryana Karnal"));
    }
}
