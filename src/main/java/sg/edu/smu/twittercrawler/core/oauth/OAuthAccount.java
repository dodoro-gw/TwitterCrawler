package sg.edu.smu.twittercrawler.core.oauth;

/**
 * Created by taosun on 28/11/14.
 */
public class OAuthAccount {
    private Integer crawlerId;

    private String consumerKey;

    private String consumerSecret;

    private String token;

    private String tokenSecret;

    public Integer getCrawlerId() {
        return crawlerId;
    }

    public void setCrawlerId(Integer crawlerId) {
        this.crawlerId = crawlerId;
    }

    public String getConsumerKey() {
        return consumerKey;
    }

    public void setConsumerKey(String consumerKey) {
        this.consumerKey = consumerKey;
    }

    public String getConsumerSecret() {
        return consumerSecret;
    }


    public void setConsumerSecret(String consumerSecret) {
        this.consumerSecret = consumerSecret;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTokenSecret() {
        return tokenSecret;
    }

    public void setTokenSecret(String tokenSecret) {
        this.tokenSecret = tokenSecret;
    }

    @Override
    public String toString() {
        return "OAuthAccount{" +
                "crawlerId=" + crawlerId +
                ", consumerKey='" + consumerKey + '\'' +
                ", consumerSecret='" + consumerSecret + '\'' +
                ", token='" + token + '\'' +
                ", tokenSecret='" + tokenSecret + '\'' +
                '}';
    }

}
