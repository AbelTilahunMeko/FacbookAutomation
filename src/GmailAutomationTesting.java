import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.Scanner;

public class GmailAutomationTesting {
    public static WebElement emailInputElement;
    public static WebElement passwordInput;
    public static WebDriver webDriver;
    public static String numberOfUserInbox;
    public static String currentUserEmail;
    public static TwilloSendSMS twilloSendSMS;

    //This is the user info
    private static String userNameEnterd;
    private static String passwordEnterd;
    private static String phoneNumber;


    public static void main(String[] args) {
//        System.out.println("Say gmail automation ");
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Abel Tilahun\\IdeaProjects\\AutoMateTest\\chromedriver.exe");


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
            gmailAutomation();
        }

    }

    private static void gmailAutomation(){
        webDriver = new ChromeDriver();
        twilloSendSMS = new TwilloSendSMS();
        try {
            webDriver.get("https://www.gmail.com");

        } catch (WebDriverException e) {
            System.out.println(e);
        }

        //The title will be Gmail if a person successfully logs in
        if (webDriver.getTitle().equals("Gmail")) {
            if (passingInputs()) {
                if(gettingInput()){
                    twilloSendSMS.sendSMSGmail(numberOfUserInbox, phoneNumber, currentUserEmail );
                }else{
                    main(null); //Looping back when ever there is an error
                }
            } else {
                System.out.println("Something Went Wrong");
            }
        } else {
            try {
                //This else statment is here becasuse when ever my internet crush the tread will make it wait
                //That way it won't stop it's process
                Thread.sleep(2000);
                webDriver.navigate().to("https://www.gmail.com");
                passingInputs(); //If if crush here too.. then ur internet connection sucks
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
    private static boolean passingInputs() {
        emailInputElement = webDriver.findElement(By.name("identifier"));
        if (emailInputElement == null) {
            System.out.println("The element wasn't found");
            return false;
        } else {
            //This is where we enter the user email address
            //aveltown
            //0912772879abelA
            emailInputElement.sendKeys(userNameEnterd);
            emailInputElement.sendKeys(Keys.ENTER);

            try {
                Thread.sleep(4000);
                //Now we find the element of the pasword
            } catch (InterruptedException e) {
                e.printStackTrace();
                return false;
            }

            passwordInput = webDriver.findElement(By.name("password"));
            passwordInput.sendKeys(passwordEnterd);
            passwordInput.sendKeys(Keys.ENTER);

        }
        return true;
    }

    private static boolean gettingInput() {
        //Inorder to get the title of the webpage we must first wait to load
        try {
            Thread.sleep(9000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //Gmail provides the email that the person logedin with and the number of inbox that email have in the title portion of the html
        String title = webDriver.getTitle();
        if (title == "Gmail") {
            return false;
        }
        String[] tilteSp = title.split(" - ");
        String firstHalf = tilteSp[0];
        currentUserEmail = tilteSp[1];
        String[] seconHalf = firstHalf.split(" ");

        numberOfUserInbox = seconHalf[1];

        return true;

    }


}
