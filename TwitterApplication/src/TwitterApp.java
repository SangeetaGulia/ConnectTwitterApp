import twitter4j.Status;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

import java.util.*;

/**
 * Created by knodus on 24/4/16.
 */
public class TwitterApp {
    static int i = 0;
    public static void main(String args[]) {


        ConfigurationBuilder confBuild = new ConfigurationBuilder();
        confBuild.setDebugEnabled(true).setOAuthConsumerKey("KAf3lEOe0Q5MQyasCsjnbVZRv")
                .setOAuthConsumerSecret("LrtMIR480j6A6R8zpgjzFcgdDaI9hdWKZRCVxKl16lVfE7MxVk")
                .setOAuthAccessToken("4840081465-P8eJKYcmbYJmm4pZE4M8rfvqMOL59CPBkfwLmZl")
                .setOAuthAccessTokenSecret("eHjn1g5YKog7S0AJDWhQDuXmtG0FUE4HCtN09qWYjARWw");


        TwitterFactory tf = new TwitterFactory(confBuild.build());
        List<Post> userstatus = new ArrayList<>();
        twitter4j.Twitter tw = tf.getInstance();

        try {
            List<Status> statuses = tw.getHomeTimeline();
            System.out.println("Showing home timeline.");
            for (Status status : statuses) {
                System.out.println(" First Form Output :"+status.getUser().getName() + ":" +
                        status.getText());
            }
        }
        catch(Exception ex){
            System.out.println("Using First Form"+ex);
        }

        try {
            List<Status> listOfStatus = tw.getHomeTimeline();
            for (Status status : listOfStatus) {
                String statusText = status.getText();
                int retweet = status.getRetweetCount();
                Date date = status.getCreatedAt();
                int likes = status.getFavoriteCount();
                String user = status.getUser().toString();
                userstatus.add(i, new Post(statusText, retweet, date, likes, user));
                i++;
            }

            System.out.println("~~~~~~~~~~~~~~~~ sorted by date and then retweets and then likes ~~~~~~~~~~~~~~~~");

            Collections.sort(userstatus, Comparator.comparing(Post::getDateOfPosting).reversed()
                    .thenComparing(Post::getNoOfRetweets).reversed()
                    .thenComparing(Post::getNoOfLikes).reversed());

            System.out.println("~~~~~~~ sorted on the basis of most recent Post ~~~~~~~~~~");
            Collections.sort(userstatus, Comparator.comparing(Post::getDateOfPosting).reversed());
            userstatus.forEach(System.out::println);

            System.out.println("~~~~~~~~~ sorted on the basis of retweets ~~~~~~~~~~~~");
            Collections.sort(userstatus, Comparator.comparing(Post::getNoOfRetweets).reversed());
            userstatus.forEach(System.out::println);

            System.out.println("~~~~~~~~~~sorted on the basis of number of likes ~~~~~~~~~~~~~~~~");
            Collections.sort(userstatus, Comparator.comparing(Post::getNoOfLikes).reversed());
            userstatus.forEach(System.out::println);

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}

class Post {
    String Status;
    Date postingDate;
    int retweet;
    int noOfLikes;
    String Name;

    Post(String Status,  int retweet, Date postingDate, int noOfLikes, String Name) {

        this.Status = Status;
        this.retweet = retweet;
        this.postingDate = postingDate;
        this.noOfLikes = noOfLikes;
        this.Name = Name;
    }

    public int getNoOfRetweets() {
        return retweet;
    }

    public Date getDateOfPosting() {
        return postingDate;
    }

    public int getNoOfLikes() {
        return noOfLikes;
    }

   @Override
    public String toString() {

        return "tweets{" +
                "\n\tStatus : " + Status + ",\n" +
                "\tName : "  + Name + ",\n" +
                "\tretweet : " + retweet + ",\n" +
                "\tdate of posting : " + postingDate + ",\n" +
                "\tlikes : " + noOfLikes + "\n }";
    }

}
