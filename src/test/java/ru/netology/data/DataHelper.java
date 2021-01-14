package ru.netology.data;

import com.github.javafaker.Faker;
import lombok.Value;

public class DataHelper {
    private DataHelper(){}

    @Value
    public static class AuthInfo {
        private String login;
        private String password;
    }

    public static AuthInfo getAuthInfo() {
        return new AuthInfo("vasya", "qwerty123");
    }

    public static AuthInfo getInvalidAuthInfoWithInvalidLogin(){
        Faker faker = new Faker();
        return new AuthInfo(faker.name().firstName(),"password");
    }

    public static AuthInfo getInvalidAuthInfoWithInvalidPassword(){
        Faker faker = new Faker();
        return new AuthInfo("vasya", faker.internet().password(8,12, true, false, true));
    }
    public static String getInvalidVerificationCode() {
        Faker faker = new Faker();
        return faker.numerify("###");
    }
}