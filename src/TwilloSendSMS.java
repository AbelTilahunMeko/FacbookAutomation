
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class TwilloSendSMS {
    private static final String ACCOUNT_SID =
            "AC927c0c66a3e45df78c3cb3e420358e6d";
    private static final String AUTH_TOKEN= "8e80b816b54089da3c8c5e0b92ad8c4f";
    private String twilloPhoneNumber = "+16039452612";

    public void sendSMS(String numberOfTexts, String phoneNumber, String currenPage){
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        String sendMessage = "The number of notifications you have buddy is"+ numberOfTexts + "\nClick on on the following link\n" + currenPage;
        Message message = Message.creator(new PhoneNumber(phoneNumber), new PhoneNumber(twilloPhoneNumber), sendMessage).create();
        System.out.println("The Message id is:- " + message.getSid());
    }
    public void sendSMSGmail(String numberOfTexts, String phoneNumber, String currentEmail){
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        String sendMessage = "You have a " + numberOfTexts +"inbox from your Gmail account "+ currentEmail;
        Message message = Message.creator(new PhoneNumber(phoneNumber), new PhoneNumber(twilloPhoneNumber), sendMessage).create();

    }
}
