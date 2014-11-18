import org.openqa.selenium.Dimension
import org.openqa.selenium.chrome.ChromeDriver

driver = { 
	def driver = new ChromeDriver() 
	driver.manage().window().size = new Dimension(1280,860)
	driver	
}

baseUrl = "http://localhost:8080/"

waiting {
	timeout = 5
	retryInterval = 0.1
}