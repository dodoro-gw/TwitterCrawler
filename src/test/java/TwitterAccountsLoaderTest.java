import org.testng.*;
import org.testng.annotations.*;
import sg.edu.smu.weigong.TwitterUtil.oauth.*;

import java.io.*;

/**
 * Created by taosun on 28/11/14.
 */
public class TwitterAccountsLoaderTest {

    @Test
    public void testLoad() throws IOException {
        TwitterAccountsLoader loader = new TwitterAccountsLoader();
        loader.load();
        Assert.assertEquals(2, loader.getAccounts().size());
        for(OAuthAccount account : loader.getAccounts()) {
            System.out.println(account.toString());
        }
    }

}
