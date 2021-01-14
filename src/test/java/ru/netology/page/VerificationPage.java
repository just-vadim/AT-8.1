package ru.netology.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class VerificationPage {
    private SelenideElement codeField = $("[data-test-id=code] input");
    private SelenideElement verifyButton = $("[data-test-id=action-verify]");
    private SelenideElement errorMsg = $("[data-test-id='error-notification']");

    public VerificationPage() {
        codeField.shouldBe(visible);
    }

    public void verifyTry(String code) {
        codeField.setValue(code);
        verifyButton.click();
    }

    public PersonalArea validVerify(String code) {
        verifyTry(code);
        return new PersonalArea();
    }

    public void invalidVerify() {
        errorMsg.shouldBe(visible);
    }
}