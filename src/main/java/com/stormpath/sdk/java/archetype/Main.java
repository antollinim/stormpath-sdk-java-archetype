package com.stormpath.sdk.java.archetype;

import com.stormpath.sdk.account.Account;
import com.stormpath.sdk.api.ApiKey;
import com.stormpath.sdk.api.ApiKeys;
import com.stormpath.sdk.application.Application;
import com.stormpath.sdk.application.Applications;
import com.stormpath.sdk.client.Client;
import com.stormpath.sdk.client.Clients;
import com.stormpath.sdk.directory.CustomData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @since 1.0
 */
public class Main {
	private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {

        String path = System.getProperty("user.home") + "/.stormpath/apiKey.properties";
        //Client
        ApiKey apiKey = ApiKeys.builder().setFileLocation(path).build();
        Client client = Clients.builder().setApiKey(apiKey).build();
        
        logger.debug("Stormpath client has successfully been initialized");

		//Create an application
        Application application = client.instantiate(Application.class);
        application.setName("My New Stormpath App!"); //must be unique among your other apps
        application = client.createApplication(Applications.newCreateRequestFor(application).createDirectory().build());
        
        logger.debug("The Application '" + application.getName() + "' has successfully been created in Stormpath with href: " + application.getHref());
        
        //Create an account
        Account account = client.instantiate(Account.class);
        account.setGivenName("John");
        account.setSurname("Doe");
        account.setUsername("johndoe"); //optional, defaults to email if unset
        account.setEmail("johndoe@emaildomainname.com");
        account.setPassword("1234$aBC");
		account = application.createAccount(account);
		
        logger.debug("The Account for '" + account.getUsername() + "' has successfully been created with href: " + account.getHref());

    }

}