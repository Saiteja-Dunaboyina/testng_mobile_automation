package com.booking.com.pages;

import org.openqa.selenium.By;
import org.testng.Assert;

import io.appium.java_client.AppiumDriver;

public class StayPage {
	
	private AppiumDriver appiumDriver;
	public final By stayBtn = By.xpath("//android.widget.TextView[@text='Stays']");
	public final By destination = By.xpath("//android.widget.Button[@content-desc=\"Enter your destination\"]");
	public final By destinationInput = By.xpath("//android.widget.EditText[@text='Enter destination']");
	public final By selectDestination = By.xpath("(//android.view.ViewGroup[@index='0'])[2]");
	public final By selectDates = By.xpath("//android.widget.Button[@resource-id='com.booking:id/facet_date_picker_confirm']");
	public final By searchBtn = By.xpath("//android.view.View[@content-desc=\"Accommodation search box\"]/android.view.View/android.widget.Button");
	public final By selectHotel = By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout/android.widget.LinearLayout/androidx.compose.ui.platform.ComposeView/android.view.View/android.view.View/android.view.ViewGroup/android.widget.FrameLayout/androidx.compose.ui.platform.ComposeView/android.view.View/android.view.ViewGroup/android.widget.FrameLayout/androidx.compose.ui.platform.ComposeView/android.view.View/android.view.View/android.view.View[1]");
	public final By selectRooms = By.xpath("//android.widget.Button[@resource-id='com.booking:id/select_room_cta']");
	public final By selectAndCustomizeBtn = By.xpath("//android.widget.TextView[@text='Select and customize']/following-sibling::android.widget.Button");
	public final By nextStepBtn = By.xpath("//android.widget.TextView[@text='Next step']/following-sibling::android.widget.Button");
	public final By nextStepBtn2 = By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/androidx.compose.ui.platform.ComposeView/android.view.View/android.view.View[2]/android.view.View/android.view.View[3]/android.widget.TextView");
	public final By confirmBtn = By.xpath("//android.widget.TextView[@text='Confirm']/following-sibling::android.widget.Button");
	public final By reserveBtn = By.xpath("//android.widget.Button[@text='Reserve']");
	public final By addMissingBtn = By.xpath("//android.widget.TextView[@text='Add missing details']/following-sibling::android.widget.Button");
	public final By backNavigateBtn = By.xpath("//android.widget.ImageButton[@content-desc=\"Navigate up\"]");//5
	
	public StayPage(AppiumDriver appiumDriver) {
		this.appiumDriver = appiumDriver;
	}
	
	public void bookingHotel(String cityName) {
		appiumDriver.findElement(stayBtn).click();
		appiumDriver.findElement(destination).click();
		appiumDriver.findElement(destinationInput).sendKeys(cityName);
		appiumDriver.findElement(selectDestination).click();
		appiumDriver.findElement(selectDates).click();
		appiumDriver.findElement(searchBtn).click();
		appiumDriver.findElement(selectHotel).click();
		appiumDriver.findElement(selectRooms).click();
		appiumDriver.findElement(selectAndCustomizeBtn).click();
		try {
			if(appiumDriver.findElement(nextStepBtn).isDisplayed()) {
				appiumDriver.findElement(nextStepBtn).click();
				if(appiumDriver.findElement(nextStepBtn2).isDisplayed()) {
					appiumDriver.findElement(nextStepBtn2).click();
				}
				appiumDriver.findElement(confirmBtn).click();
			}
			appiumDriver.findElement(reserveBtn).click();
			appiumDriver.findElement(addMissingBtn).click();
		}catch (Exception e) {
			appiumDriver.findElement(reserveBtn).click();
			appiumDriver.findElement(addMissingBtn).click();
		}

	}
	
	
	public void backNavigation() {
		
		 while(appiumDriver.findElement(backNavigateBtn).isDisplayed()) {
			 appiumDriver.findElement(backNavigateBtn).click();
		 }
	}
	
}
