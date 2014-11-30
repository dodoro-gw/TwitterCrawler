package sg.edu.smu.twittercrawler.core.oauth;

import com.fasterxml.jackson.core.type.*;
import com.fasterxml.jackson.databind.*;

import java.io.*;
import java.util.*;

/**
 * Created by taosun on 28/11/14.
 */
public class OAuthAccountsLoader {


    //get the accounts from Twitter Rest API
    //account is in JSON format
    //public String accounts = "[{\"consumer_secret\":\"\",\"consumer_key\":\"\",\"token\":\"\",\"token_secret\":\"\"}, {\"consumer_secret\":\"\",\"consumer_key\":\"\",\"token\":\"\",\"token_secret\":\"\"}]";


    public static List<OAuthAccount> load(String accountsJsonFileName){
        ObjectMapper mapper = new ObjectMapper();
        mapper.setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);

        File accountsFile = new File(accountsJsonFileName);

        try {
            return mapper.readValue(accountsFile, new TypeReference<List<OAuthAccount>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
}
