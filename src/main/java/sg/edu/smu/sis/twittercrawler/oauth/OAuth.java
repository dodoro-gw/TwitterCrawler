package sg.edu.smu.sis.twittercrawler.oauth;

import java.util.Iterator;

import org.json.JSONObject;

public class OAuth {
	public Integer crawlerId;
	
	public String consumer_key;
	public String consumer_key_secret;
	public String token;
	public String token_secret;
	
	public OAuth(String jsonToken, int cid){
		resetAll(jsonToken);
		crawlerId = cid;
	}
	
	public void resetAll(String jsonToken){
		JSONObject obj;
		try {
			obj = new JSONObject(jsonToken);

			Iterator<?> itr = obj.keys();
			while (itr.hasNext()) {
				String key = (String) itr.next();
				if (key.compareTo("consumer_secret") == 0) {
					consumer_key_secret = obj.getString(key);
				}else if(key.compareTo("consumer_key") == 0){
					consumer_key = obj.getString(key);
				}else if(key.compareTo("token") == 0){
					token = obj.getString(key);
				}else if(key.compareTo("token_secret") == 0){
					token_secret = obj.getString(key);
				}
			}
		}catch(Exception e){
				e.printStackTrace();
			}
	}
	
	
}
