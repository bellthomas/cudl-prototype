package com.test;

import java.util.Map;
import java.util.HashMap;
 
import com.twilio.sdk.resource.instance.Account;
import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.MessageFactory;
import com.twilio.sdk.resource.instance.Message;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
 
public class SmsSender {
 
    /* Find your sid and token at twilio.com/user/account */
    public static final String ACCOUNT_SID = "AC931868207edb5bf3ab2d76816f376364";
    public static final String AUTH_TOKEN = "99bc9e3bec39aebda4290d3bb5617081";
 
    public static void main(String[] args) throws TwilioRestException {
 
        TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);
 
        Account account = client.getAccount();
 
        MessageFactory messageFactory = account.getMessageFactory();
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("To", "+447715975524")); // Replace with a valid phone number for your account.
        params.add(new BasicNameValuePair("From", "+441673372018")); // Replace with a valid phone number for your account.
        params.add(new BasicNameValuePair("Body", "It works from phone"));
        Message sms = messageFactory.create(params);
    }
}
