package movie_review;

public interface MovieMethods {
	
	//method to add movies in the movies list (considering the input as whole string and then splitting)
	void add_Movie(String movie_year_genres);
	
	//method to add movies in the movies list (considering seperate parameters for movie, year, genres)
	void add_Movie(String movie, int year, String genres);
	
	//method to add users in the list
	void add_User(String user_name);
	
	//method to add reviews by user
	void add_review(String user_name, String movie_name, int score);
	
	//list top n by score in given year
	void listTopnforYear(int n, int year);

	//list top n by score in given year by user prefered
	void listTopnforYearUser(int n, int year, String user);

	//list of top n movies by review score in "drama"
	void listTopnforGenre(int n, String genre);
	
	//Average review score for movie in particular year
	void getAvgScoreInYear(int year);
	
	//Average review score for movie
	void getAvgScoreFor(String movie);

	//Average review score for genre
	void getAvgScoreForGenre(String genre);

}

