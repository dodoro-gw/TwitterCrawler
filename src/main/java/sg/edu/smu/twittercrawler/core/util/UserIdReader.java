package sg.edu.smu.twittercrawler.core.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserIdReader {

    public static List<Long> getUsers(String userIdFile) throws IOException {
        //get user list
        BufferedReader reader = new BufferedReader(new FileReader(userIdFile));
        String line;
        List<Long> userIds = new ArrayList<>();
        while ((line = reader.readLine()) != null) {
            String id = line;
            if (id.length() < 1) {
                continue;
            }
            if (line.contains(",")) {
                String columns[] = line.split(",");
                id = columns[0];
            }

            userIds.add(Long.valueOf(id));
        }
        reader.close();

        return userIds;
    }

    //for loading user id list
    public static long[] getUserList(String userIdFile)
            throws IOException {
        List<Long> userIds = getUsers(userIdFile);

        long[] usersList = new long[userIds.size()];
        for (int i = 0; i < userIds.size(); i++) {
            usersList[i] = userIds.get(i);
        }
        return usersList;
    }
}
