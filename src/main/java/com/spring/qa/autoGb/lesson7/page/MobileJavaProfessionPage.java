package com.spring.qa.autoGb.lesson7.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;


@Component
public class MobileJavaProfessionPage {

    private final WebDriver driver;

    @FindBy(xpath = "//a[@id='start_learn']")
    private WebElement buttonOffer;

    public MobileJavaProfessionPage(@Lazy WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public WebElement getOffer() {
        return buttonOffer;
    }
}
