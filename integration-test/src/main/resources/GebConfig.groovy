import org.openqa.selenium.Dimension
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.remote.DesiredCapabilities

driver = {
    def driver = new ChromeDriver()
    driver.manage().window().size = new Dimension(1280, 860)
    
    driver
}

environments {
    headless {
        driver = {
            ChromeOptions options = new ChromeOptions()
            options.setHeadless(true)
            
            def driver = new ChromeDriver(options)
            driver.manage().window().size = new Dimension(1280, 860)
            
            driver
        }
    }
}

baseUrl = "http://localhost:8000/"

waiting {
	timeout = 5
	retryInterval = 0.1
}