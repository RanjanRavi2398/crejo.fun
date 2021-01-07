package movie_review;

import java.util.Scanner;

public class MovieReview {

	public static void main(String[] args) {
		
		MovieMethods movie_interface = new MovieMethodsImpl3();
		
		movie_interface.add_Movie("Don", 2006, "Action,Comedy");
		movie_interface.add_Movie("Tiger", 2008, "Drama");
		movie_interface.add_Movie("Padmaavat", 2006, "Comedy");
		movie_interface.add_Movie("Lunchbox", 2022, "Drama");
		movie_interface.add_Movie("Guru", 2006, "Drama");
		movie_interface.add_Movie("Metro", 2006, "Romance");
		
		
		movie_interface.add_User("SRK");
		movie_interface.add_User("Salman");
		movie_interface.add_User("Deepika");
		
		movie_interface.add_review("SRK", "Don", 2);
		movie_interface.add_review("SRK", "Padmaavat", 8);
		movie_interface.add_review("Salman", "Don", 5);
		movie_interface.add_review("Deepika", "Don", 9);
		movie_interface.add_review("Deepika", "Guru", 6);
		movie_interface.add_review("SRK", "Don", 10);
		movie_interface.add_review("Deepika", "Lunchbox", 5);
		movie_interface.add_review("SRK", "Tiger", 5);
		movie_interface.add_review("SRK", "Metro", 7);
		
		//List top n movies by review score in "2006"
		movie_interface.listTopnforYear(1, 2006);
		
		//list top n movies by review score in "2006" by user prefered
		movie_interface.listTopnforYearUser(1,2006,"critic");
		
		//list of top n movies by review score in "drama"
		movie_interface.listTopnforGenre(1,"Drama");
		
		//Average review score for particular year of release
		movie_interface.getAvgScoreInYear(2006);
		
		//Average review score for particular movie
		movie_interface.getAvgScoreFor("Don");
		
		//Average review score for particular genre
		movie_interface.getAvgScoreForGenre("Drama");
		
	}

}
