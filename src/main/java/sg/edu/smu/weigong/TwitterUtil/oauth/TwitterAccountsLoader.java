package sg.edu.smu.weigong.TwitterUtil.oauth;

import com.fasterxml.jackson.core.type.*;
import com.fasterxml.jackson.databind.*;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 * Created by taosun on 28/11/14.
 */
public class TwitterAccountsLoader {

    private static final String ACCOUNTS_FILENAME = "/accounts.json";

    private List<OAuthAccount> accounts;

    public void load() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);
        File accountsFile = null;
        try {
            accountsFile = new File(this.getClass().getResource(ACCOUNTS_FILENAME).toURI());
        } catch (URISyntaxException e) {
            throw new IOException("Failed to find accounts.json file");
        }
        this.accounts = mapper.readValue(accountsFile, new TypeReference<List<OAuthAccount>>() {
        });
    }

    public List<OAuthAccount> getAccounts() {
        return this.accounts;
    }
}
