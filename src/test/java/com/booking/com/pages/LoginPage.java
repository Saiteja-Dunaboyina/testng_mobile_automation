package com.booking.com.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;


public class LoginPage {
	
	private AppiumDriver appiumDriver;
	public final By allowNotification = By.xpath("//android.widget.TextView[@text='OK']/following-sibling::android.widget.Button");	
	public final By allowNotificationBtn = By.xpath("//android.widget.Button[@text='Allow']");
	public final By continueWithMailBtn = By.xpath("//android.widget.TextView[@text='Continue with email']/following-sibling::android.widget.Button");
	public final By emialInput = By.xpath("//android.widget.EditText[@index=1]");
	public final By continueBtn = By.xpath("//android.widget.TextView[@text='Continue']/following-sibling::android.widget.Button");
	public final By passwordInput = By.xpath("//android.widget.EditText[@resource-id='com.booking:id/identity_text_input_edit_text']");
	public final By signInBtn = By.xpath("//android.widget.TextView[@text='Sign in']");
	public final By stayBtn = By.xpath("//android.widget.TextView[@text='Stays']");
	public final By element = By.xpath("//android.widget.Button");
	public final By profileBtn = By.xpath("//android.widget.FrameLayout[@content-desc=\"Profile\"]/android.widget.ImageView");
	public final By signoutBtn = By.xpath("//android.widget.TextView[@text='Sign out']");
	public final By proceedBtn = By.xpath("//android.widget.Button[@text='PROCEED']");
	public final By signIn = By.xpath("//android.widget.Button[@resource-id='com.booking:id/facet_profile_header_sign_in_cta']");
	public final By searchBtn = By.xpath("//android.widget.FrameLayout[@content-desc=\"Search\"]/android.widget.ImageView");

	
	public LoginPage(AppiumDriver appiumDriver) {
		this.appiumDriver = appiumDriver;
	}
	
	public void notification() {
		appiumDriver.findElement(allowNotification).click();
		appiumDriver.findElement(allowNotificationBtn).click();
		List<WebElement> webElements = appiumDriver.findElements(element);
		System.out.println(webElements.size() + " ====================================");
	}
	
	public void login(String mail,String password) {
		appiumDriver.findElement(continueWithMailBtn).click();
		appiumDriver.findElement(emialInput).sendKeys(mail);
		appiumDriver.findElement(continueBtn).click();
		appiumDriver.findElement(passwordInput).sendKeys(password);
		appiumDriver.findElement(signInBtn).click();
	}
	
	public boolean isStayDisplayed() {
		return appiumDriver.findElement(stayBtn).isDisplayed();
	}
	
	public void logout() {
		appiumDriver.findElement(profileBtn).click();
		WebElement optionLogOut = appiumDriver.findElement(AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(new UiSelector().text(\"Sign out\"));"));
		optionLogOut.click();
		appiumDriver.findElement(proceedBtn).click();
		appiumDriver.findElement(signIn).click();
	}
	
	public void clickOnSearchBtn() {
		appiumDriver.findElement(searchBtn).click();
	}
	

}
