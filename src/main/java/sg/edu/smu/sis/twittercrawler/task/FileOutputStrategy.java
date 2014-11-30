package sg.edu.smu.sis.twittercrawler.task;

import twitter4j.User;

import java.io.*;

/**
 * TwitterCrawler
 *
 * @author taosun
 * @since 30/11/14
 */
public class FileOutputStrategy implements OutputStrategy{

    private final File file;

    private PrintWriter writer;

    public FileOutputStrategy(File file) throws IOException {
        this.file = file;

        if(file.isFile()) {
            this.writer = new PrintWriter(new BufferedWriter(new FileWriter(file)));
        }
    }

    @Override
    public void output(User user, String content) throws IOException {
        if(file.isDirectory()) {
            String directoryPath = file.getPath();
            String filePath = directoryPath + "_" + user.getId();
            this.writer = new PrintWriter(new BufferedWriter(new FileWriter(filePath)));
        }

        this.writer.println(content);
        this.writer.flush();

        if(file.isDirectory()) {
            this.writer.close();
        }
    }

    public void close() {
        if(file.isFile()) {
            this.writer.close();
        }
    }
}
