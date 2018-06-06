import com.twilio.Twilio;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import javax.management.Notification;
import java.awt.*;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
public class FacebookAutomation {
    static WebElement currenURI , passwordInput, notification;
    private static String userNameEnterd, passwordEnterd, phoneNumber;
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);

        System.out.print("Please Enter your username:-- ");
        userNameEnterd = scanner.nextLine();

        System.out.print("Please Enter your password:-- ");
        passwordEnterd = scanner.nextLine();

        System.out.print("Please add +251 before the number\nPlease Enter your phoneNumber:-- ");
        phoneNumber = scanner.nextLine();

        if(userNameEnterd.isEmpty() || passwordEnterd.isEmpty()|| phoneNumber.isEmpty()){
            System.out.println("Please enter all of the fields");
            main(null);
        }else{
            facebookAutomation();
        }
    }

    public static void facebookAutomation(){



        TwilloSendSMS twilloSendSMS = new TwilloSendSMS();

        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Abel Tilahun\\IdeaProjects\\AutoMateTest\\chromedriver.exe");


        ChromeOptions options = new ChromeOptions();
        //This will what makes the allow notification...
        //So that we wont stop
        options.addArguments("--disable-notifications");
        WebDriver webDriver = new ChromeDriver(options);

        webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        //This will let us run the Javascript from Java code
        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) webDriver;


        try {
            webDriver.get("https://www.facebook.com");
        }catch (WebDriverException e){
            throw e;
        }

        webDriver.findElement(By.name("email")).sendKeys(userNameEnterd);
        passwordInput = webDriver.findElement(By.name("pass"));
        passwordInput.sendKeys(passwordEnterd);
        passwordInput.sendKeys(Keys.ENTER);


        try{
            notification = webDriver.findElement(By.id("notificationsCountValue"));
            System.out.println("The notification number is:- "+notification.getText());
            String currenPage = webDriver.getCurrentUrl();
            int numberOfNotifcation = Integer.valueOf(notification.getText());
            if(numberOfNotifcation>0){
                twilloSendSMS.sendSMS(notification.getText(), phoneNumber, currenPage);
            }

        }catch (org.openqa.selenium.NoSuchElementException e){
            javascriptExecutor.executeScript("alert('Hey Change the ID')");
        }

        try {
            Thread.sleep(4000);

        }catch (Exception e){
            try{
                throw e;
            }catch (InterruptedException S){
                System.exit(0);
            }
        }

        webDriver.close();
    }
}
