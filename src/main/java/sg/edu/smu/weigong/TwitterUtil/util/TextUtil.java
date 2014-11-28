package sg.edu.smu.weigong.TwitterUtil.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TextUtil {
	
	//for loading user id list
	public static long[] getUserList(String userIdFile)
			throws FileNotFoundException, IOException {
		//get user list
		BufferedReader reader = new BufferedReader(new FileReader(userIdFile));
		String line = null;
		List<Long> userIds = new ArrayList<Long>();
		while((line = reader.readLine()) != null){
			String id = line;
			if(line.contains(",")){
				String columns[] = line.split(",");
				id = columns[0];
			}
			
			userIds.add(Long.valueOf(id));
		}
		long[] usersList = new long[userIds.size()];
		for(int i = 0; i < userIds.size(); i++){
			usersList[i] = userIds.get(i);
		}
		reader.close();
		return usersList;
	}
}
