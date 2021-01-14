package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static ru.netology.data.DataHelper.AuthInfo;

public class LoginPage {
    private SelenideElement loginField = $("[data-test-id='login'] input");
    private SelenideElement passwordField = $("[data-test-id='password'] input");
    private SelenideElement loginButton = $("[data-test-id='action-login']");
    private SelenideElement errorMsg = $("[data-test-id='error-notification']");

    public void loginTry(AuthInfo info) {
        loginField.setValue(info.getLogin());
        passwordField.setValue(info.getPassword());
        loginButton.click();
    }

    public VerificationPage validLogin(AuthInfo info) {
        loginTry(info);
        return new VerificationPage();
    }

    public void authFailed() {
        errorMsg.shouldBe(Condition.visible);
    }

    public void authFailedUserIsBlocked() {
        SelenideElement errorMsgUserIsBlocked = $("[data-test-id='error-notification']").shouldHave(text("Ошибка! Пользователь заблокирован"));
        errorMsgUserIsBlocked.shouldBe(visible);
    }

    public void clearInputFields() {
        loginField.sendKeys(Keys.chord(Keys.CONTROL, "a") + Keys.DELETE);
        passwordField.sendKeys(Keys.chord(Keys.CONTROL, "a") + Keys.DELETE);
    }
}