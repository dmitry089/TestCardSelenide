package ru.netology;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.codeborne.selenide.Configuration;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryTest {
    @BeforeEach
            public void openPage() {
        open("http://localhost:9999/");
    }
    static void setUp() {
        Configuration.headless = true;
    }
    @Test
    public void shouldCheckCorrectForm() {
        $x("//input [@placeholder='Город']").val("Астрахань");
        $x("//input[@type='tel']").doubleClick().sendKeys(Keys.BACK_SPACE);
        String meetingDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $x("//input[@placeholder='Дата встречи']").val(meetingDate);
        $("[data-test-id='name'] input").setValue("Караулов Дмитрий");
        $("[data-test-id='phone'] input").setValue("+79033210166");
        $("[data-test-id='agreement']").click();
        $(".button__content").click();
        $("[data-test-id='notification']").waitUntil(visible,15000);
    }
    @Test
    void shouldCheckIncorrectCity() {
        $x("//input [@placeholder='Город']").val("Лондон");
        $x("//input[@type='tel']").doubleClick().sendKeys(Keys.BACK_SPACE);
        String meetingDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $x("//input[@placeholder='Дата встречи']").val(meetingDate);
        $("[data-test-id='name'] input").val("Петров Иван");
        $("[data-test-id='phone'] input").val("+79253562244");
        $("[data-test-id='agreement']").click();
        $(".button__content").click();
        $("[data-test-id=city] .input__sub").should(exactText("Доставка в выбранный город недоступна"));

    }

    @Test
    void shouldCheckNameEmpty() {
        $x("//input [@placeholder='Город']").val("Краснодар");
        $x("//input[@type='tel']").doubleClick().sendKeys(Keys.BACK_SPACE);
        String meetingDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $x("//input[@placeholder='Дата встречи']").val(meetingDate);
        $("[data-test-id='name'] input").val(" ");
        $("[data-test-id='phone'] input").val("+79213332244");
        $("[data-test-id='agreement']").click();
        $(".button__content").click();
        $("[data-test-id=name] .input__sub").should(exactText("Поле обязательно для заполнения"));

    }
}



