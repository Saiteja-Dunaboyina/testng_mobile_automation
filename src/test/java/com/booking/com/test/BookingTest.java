package com.booking.com.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.NoSuchElementException;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.booking.com.base.Base;
import com.booking.com.pages.LoginPage;
import com.booking.com.pages.StayPage;

public class BookingTest extends Base{
	
	private static final Logger logger = LogManager.getLogger(BookingTest.class);
	public LoginPage loginPage;
	public StayPage stayPage;
	
	@BeforeTest
	public void initializePages() {
		loginPage = new LoginPage(driver);
		stayPage = new StayPage(driver);
	}
	
	@Test(priority = 1)
	public void login() {
		logger.info("Login in Booking.com");
		test = extent.createTest("Login with valid credentials").assignAuthor("sai teja");
		String mail = jsonData.get("mail").getAsString();
		String password = jsonData.get("password").getAsString();
		test.log(Status.INFO, "Allowing the notification");
		loginPage.notification();
		test.log(Status.INFO, "Login with "+mail+" and password "+password);
		loginPage.login(mail,password);
		boolean isStayButtonDisplayed = loginPage.isStayDisplayed();
		test.log(isStayButtonDisplayed ? Status.PASS : Status.FAIL,
				  isStayButtonDisplayed ? "Stay button is displayed":"stay button is not displayed");
		Assert.assertTrue(isStayButtonDisplayed,"Stay button should be displayed");
		test.log(Status.INFO, "Logout from the app");
		loginPage.logout();
	}
	
	@Test(priority = 2)
	public void hotelBooking() {
		logger.info("Hotel Booking for Stay");
		test = extent.createTest("User Booking a hotel for Stay").assignAuthor("sai teja");
		String mail = jsonData.get("mail").getAsString();
		String password = jsonData.get("password").getAsString();
		test.log(Status.INFO, "Login with "+mail+" and password "+password);
		loginPage.login(mail,password);
		loginPage.clickOnSearchBtn();
		boolean isStayButtonDisplayed = loginPage.isStayDisplayed();
		test.log(isStayButtonDisplayed ? Status.PASS : Status.FAIL,
				  isStayButtonDisplayed ? "Stay button is displayed":"stay button is not displayed");
		Assert.assertTrue(isStayButtonDisplayed,"Stay button should be displayed");
		test.log(Status.INFO, "User booking the hotel");
		String city = jsonData.get("city").getAsString();
		stayPage.bookingHotel(city);
		try {
			stayPage.backNavigation();
			boolean isStayButtonDisplayed1 = loginPage.isStayDisplayed();
			test.log(isStayButtonDisplayed1 ? Status.PASS : Status.FAIL,
					  isStayButtonDisplayed1 ? "Stay button is displayed":"stay button is not displayed");
			Assert.assertTrue(isStayButtonDisplayed1,"Stay button should be displayed");
			test.log(Status.INFO, "Logout from the app");
			loginPage.logout();
		}catch(NoSuchElementException ex) {
			boolean isStayButtonDisplayed2 = loginPage.isStayDisplayed();
			test.log(isStayButtonDisplayed2 ? Status.PASS : Status.FAIL,
					  isStayButtonDisplayed2 ? "Stay button is displayed":"stay button is not displayed");
			Assert.assertTrue(isStayButtonDisplayed2,"Stay button should be displayed");
			test.log(Status.INFO, "Logout from the app");
			loginPage.logout();
		}
	}

}
