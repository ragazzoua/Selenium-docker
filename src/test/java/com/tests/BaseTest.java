package com.tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import java.net.MalformedURLException;
import java.net.URL;

public class BaseTest {

    protected WebDriver driver;

    @BeforeTest
    public void setupDriver(ITestContext ctx) throws MalformedURLException {
        // BROWSER => chrome / firefox
        // HUB_HOST => localhost / 10.0.1.3 / hostname

        String host = "localhost";
        DesiredCapabilities dc;

        if (System.getProperty("BROWSER") != null &&
                System.getProperty("BROWSER").equalsIgnoreCase("firefox")) {
            dc = DesiredCapabilities.firefox();
        } else {
            dc = DesiredCapabilities.chrome();
        }

        if (System.getProperty("HUB_HOST") != null) {
            host = System.getProperty("HUB_HOST");
        }

        String testName = ctx.getCurrentXmlTest().getName();

        String completeUrl = "http://" + host + ":4445/wd/hub";
        dc.setCapability("name", testName);
        this.driver = new RemoteWebDriver(new URL(completeUrl), dc);
    }

    @AfterTest
    public void quitDriver() {
        this.driver.quit();
    }

    /*Have the same problem as mbround18 when trying to connect to the selenoid server using ruby/capybara and java/selenide.
     cannot start process [/root/.aerokube/selenoid/chromedriver --whitelisted-ips='' --verbose --port=41316]: fork/exec /root/.aerokube/selenoid/chromedriver: no such file or directory
     Can anybody help me please?

RESOLVED
    * I had the same problem on Ubuntu 18.04 using docker .aerokube/selenoid/chromedriver: no such file or directory.
      I was able to fix it with ./cm selenoid update which install the latest 2 versions of chrome, firefox... drivers and change browsers.json from "default": "latest" to "default": "72.0".
      Just leave this here if someone have the same issue.
    *
    *
    *
    * */


}
