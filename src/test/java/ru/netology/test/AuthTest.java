package ru.netology.test;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.page.LoginPage;
import ru.netology.page.PersonalArea;
import ru.netology.page.VerificationPage;

import static com.codeborne.selenide.Selenide.open;
import static ru.netology.data.DataHelper.*;
import static ru.netology.data.SQLHelper.*;

public class AuthTest {

    @BeforeEach
    public void setUp() {
        open("http://localhost:9999");
    }

    @AfterAll
    public static void cleanDatabase() {
        cleanDB();
    }

    @Test
    public void shouldLoginWithValidCredentials() {
        LoginPage loginPage = new LoginPage();
        AuthInfo authInfo = getAuthInfo();
        VerificationPage verificationPage = loginPage.validLogin(authInfo);
        PersonalArea personalArea = verificationPage.validVerify(getVerificationCode(authInfo));
    }

    @Test
    public void shouldNotLoginWithInvalidLogin() {
        LoginPage loginPage = new LoginPage();
        AuthInfo authInfo = getInvalidAuthInfoWithInvalidLogin();
        loginPage.loginTry(authInfo);
        loginPage.authFailed();
    }

    @Test
    public void shouldNotLoginWithInvalidPassword() {
        LoginPage loginPage = new LoginPage();
        AuthInfo authInfo = getInvalidAuthInfoWithInvalidPassword();
        loginPage.loginTry(authInfo);
        loginPage.authFailed();
    }

    @Test
    public void shouldNotLoginWithInvalidVerificationCode() {
        LoginPage loginPage = new LoginPage();
        AuthInfo authInfo = getAuthInfo();
        VerificationPage verificationPage = loginPage.validLogin(authInfo);
        verificationPage.verifyTry(getInvalidVerificationCode());
        verificationPage.invalidVerify();
    }

    @Test
    /*Bug*/
    public void shouldBlockUserIfLoginThreeTimesWithInvalidPassword() {
        LoginPage loginPage = new LoginPage();
        AuthInfo validAuthInfo = getAuthInfo();
        AuthInfo invalidAuthInfo = getInvalidAuthInfoWithInvalidPassword();
        loginPage.loginTry(invalidAuthInfo);
        loginPage.clearInputFields();
        loginPage.authFailed();

        loginPage.loginTry(invalidAuthInfo);
        loginPage.clearInputFields();
        loginPage.authFailed();

        loginPage.loginTry(invalidAuthInfo);
        loginPage.clearInputFields();
        loginPage.authFailed();

        loginPage.loginTry(validAuthInfo);
        loginPage.authFailedUserIsBlocked();
    }
}