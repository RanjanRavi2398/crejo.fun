package movie_review;

import java.time.Year;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class MovieMethodsImpl implements  MovieMethods{
	
	int current_year = Year.now().getValue();
	
	Map<String, Set<String>> genre_map = new HashMap<String, Set<String>>();
	Map<String, Integer> year_map = new HashMap<String, Integer>();
	Set<String> movies_released = new HashSet<String>();
	Map<String, Map<String, Integer>> viewer_movie_map = new HashMap<String, Map<String,Integer>>();
	Map<String, Map<String, Integer>> critic_movie_map = new HashMap<String, Map<String,Integer>>();
	Map<String, Map<String, Integer>> expert_movie_map = new HashMap<String, Map<String,Integer>>();
	Map<String, Map<String, Integer>> admin_movie_map = new HashMap<String, Map<String,Integer>>();
	Map<String, Integer> movie_map = new HashMap<String, Integer>();

	@Override
	public void add_Movie(String movie_year_genres) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void add_Movie(String movie, int year, String genres) {
		
		
		if(!movies_released.contains(movie)) {
			movies_released.add(movie);
			movie_map.put(movie, 0);
			
			//adding movies into corresponding genres
			String[] genre_array = genres.split(",");
			for(int i=0;i<genre_array.length;i++) {
				if(genre_map.containsKey(genre_array[i])) {
					genre_map.get(genre_array[i]).add(movie);
				}else {
					Set<String> set = new HashSet<String>();
					set.add(movie);
					genre_map.put(genre_array[i], set);
				}
			}
			
			//adding movies and its corresponding year
			year_map.put(movie, year);
			
		}
		
	}

	@Override
	public void add_User(String user_name) {
		
		//if user already present the not adding him
		if(!viewer_movie_map.containsKey(user_name)) {
			Map<String, Integer> moviescoremap = new HashMap<String, Integer>();
			viewer_movie_map.put(user_name, moviescoremap);
		}
		
	}

	@Override
	public void add_review(String user_name, String movie_name, int score) {
		
		if(movies_released.contains(movie_name) && year_map.get(movie_name) <= current_year) {	
			if(admin_movie_map.containsKey(user_name)) {
				//add movie to admin map
				if(admin_movie_map.get(user_name).containsKey(movie_name) || expert_movie_map.get(user_name).containsKey(movie_name) || critic_movie_map.get(user_name).containsKey(movie_name) || viewer_movie_map.get(user_name).containsKey(movie_name)) {
					//exception movie already reviewed
					System.out.println("Exception movie already reviewed");
				}else {
					admin_movie_map.get(user_name).put(movie_name, score*4);
					movie_map.put(movie_name, movie_map.get(movie_name)+(4*score));
				}
			}else if(expert_movie_map.containsKey(user_name)) {
				//add movie to expert map
				if(expert_movie_map.get(user_name).containsKey(movie_name) || critic_movie_map.get(user_name).containsKey(movie_name) || viewer_movie_map.get(user_name).containsKey(movie_name)) {
					//exception movie already reviewed
					System.out.println("Exception movie already reviewed");
				}else {
					expert_movie_map.get(user_name).put(movie_name, score*3);
					movie_map.put(movie_name, movie_map.get(movie_name)+(3*score));
					if(expert_movie_map.get(user_name).size() == 10) {
						admin_movie_map.put(user_name, new HashMap<String,Integer>());
					}
				}
			}else if(critic_movie_map.containsKey(user_name)) {
				//add movie to critic map
				if(critic_movie_map.get(user_name).containsKey(movie_name) || viewer_movie_map.get(user_name).containsKey(movie_name)) {
					//exception movie already reviewed
					System.out.println("Exception movie already reviewed");
				}else {
					critic_movie_map.get(user_name).put(movie_name, score*2);
					movie_map.put(movie_name, movie_map.get(movie_name)+(2*score));
					if(critic_movie_map.get(user_name).size() == 5) {
						expert_movie_map.put(user_name, new HashMap<String,Integer>());
					}
				}
			}else {
				//add movie to viewer map
				if(viewer_movie_map.get(user_name).containsKey(movie_name)) {
					//exception movie already reviewed
					System.out.println("Exception movie already reviewed");
				}else {
					viewer_movie_map.get(user_name).put(movie_name, score);
					movie_map.put(movie_name, movie_map.get(movie_name)+(score));
					if(viewer_movie_map.get(user_name).size() == 3) {
						critic_movie_map.put(user_name, new HashMap<String,Integer>());
					}
				}
			}
		}else {
			System.out.println("movie yet to be released or added");
		}
		
	}

	@Override
	public void listTopnforYear(int n, int year) {
		
		Set<String> moviesInYear = getAllMovieFor(year);
		System.out.println("List of top "+n+" in year "+year+" ");
		movie_map.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).filter(entry -> moviesInYear.contains(entry.getKey())).limit(n).forEachOrdered(entry -> System.out.print(entry.getKey() +"-"+entry.getValue()+" "));
	
	}
	
	
	@Override
	public void listTopnforYearUser(int n, int year, String user) {
		
		Set<String> moviesInYear = getAllMovieFor(year);
		Set<String> moviesByUser = new HashSet<String>();
		
		if(user.equalsIgnoreCase("viewer")) {
			for(Map.Entry<String, Map<String, Integer>> useri : viewer_movie_map.entrySet()) {
				
				//if(useri.getValue().size() <=2) {
					moviesByUser.addAll(useri.getValue().keySet());
				//}
				
			}
		}else if(user.equalsIgnoreCase("critic") || user.contains("critic")) {
			for(Map.Entry<String, Map<String, Integer>> useri : critic_movie_map.entrySet()) {
				
				//if(useri.getValue().size() >=3 && useri.getValue().size() <=10) {
					moviesByUser.addAll(useri.getValue().keySet());
				//}
				
			}
		}else if(user.equalsIgnoreCase("expert") || user.contains("expert")) {
			for(Map.Entry<String, Map<String, Integer>> useri : expert_movie_map.entrySet()) {
				
				//if(useri.getValue().size() >=11 && useri.getValue().size() <=25) {
					moviesByUser.addAll(useri.getValue().keySet());
				//}
				
			}	
		}else if(user.equalsIgnoreCase("admin") || user.contains("admin")) {
			for(Map.Entry<String, Map<String, Integer>> useri : admin_movie_map.entrySet()) {
				
				//if(useri.getValue().size() > 25) {
					moviesByUser.addAll(useri.getValue().keySet());
				//}
				
			}
		}else {
			System.out.println("user role invalid");
		}
		
		moviesInYear.retainAll(moviesByUser);
		//System.out.println("movies in yearafter intersection"+moviesInYear);
		System.out.print("List of top "+n+" movies in year "+year+" by user "+user+": ");
		movie_map.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).filter(entry -> moviesInYear.contains(entry.getKey())).limit(n).forEachOrdered(entry -> System.out.print(entry.getKey() +"-"+entry.getValue()+" "));
		
	}

	private Set<String> getAllMovieFor(int year) {
		
		Set<String> moviesInYear = new HashSet<String>();
		for(Map.Entry<String, Integer> i: year_map.entrySet()) {
			if(i.getValue() == year) {
				moviesInYear.add(i.getKey());
			}
		}
		//System.out.println("Movies in this year : "+moviesInYear);
		return moviesInYear;
	}

	@Override
	public void listTopnforGenre(int n, String genre) {
		
		System.out.println("List of top "+n+" in genre "+genre+" is:");
		movie_map.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).filter(entry -> getAllMovieFor(genre).contains(entry.getKey())).limit(n).forEachOrdered(entry -> System.out.print(entry.getKey() +"-"+entry.getValue()+" "));
		
	}
	
	private Set<String> getAllMovieFor(String genre) {
		
		if(genre_map.containsKey(genre)) {
			return genre_map.get(genre);
		}else {
			System.out.println("No movies in this genre");
			return null;
		}
	
	}

	@Override
	public void getAvgScoreInYear(int year) {
		
		Set<String> moviesInYear = getAllMovieFor(year);
		/*
		 * if(moviesInYear.size() > 0) { int sum = 0; int count = 0;
		 * for(Map.Entry<String, Integer> i:movie_map.entrySet()) {
		 * if(moviesInYear.contains(i.getKey())) { sum+=i.getValue(); count++; } }
		 * System.out.println("Average movie score for year "+year+
		 * " is "+(double)(sum/count)); }
		 */
		int sum = 0;
		int count = 0;
		for(Map.Entry<String, Map<String, Integer>> entry:viewer_movie_map.entrySet()) {
			for(Map.Entry<String, Integer> moviesmap :entry.getValue().entrySet()) {
				if(moviesInYear.contains(moviesmap.getKey())) {
					sum+=moviesmap.getValue();
					count++;
				}
			}
		}
		for(Map.Entry<String, Map<String, Integer>> entry:critic_movie_map.entrySet()) {
			for(Map.Entry<String, Integer> moviesmap :entry.getValue().entrySet()) {
				if(moviesInYear.contains(moviesmap.getKey())) {
					sum+=moviesmap.getValue();
					count++;
				}
			}
		}
		for(Map.Entry<String, Map<String, Integer>> entry:expert_movie_map.entrySet()) {
			for(Map.Entry<String, Integer> moviesmap :entry.getValue().entrySet()) {
				if(moviesInYear.contains(moviesmap.getKey())) {
					sum+=moviesmap.getValue();
					count++;
				}
			}
		}
		for(Map.Entry<String, Map<String, Integer>> entry:admin_movie_map.entrySet()) {
			for(Map.Entry<String, Integer> moviesmap :entry.getValue().entrySet()) {
				if(moviesInYear.contains(moviesmap.getKey())) {
					sum+=moviesmap.getValue();
					count++;
				}
			}
		}
		double ans = (double)sum/(double)count;
		
		System.out.println("Average score of movies released in year "+year+" is "+Math.round(ans*100.0)/100.0);
		
	}

	@Override
	public void getAvgScoreFor(String movie) {
		
		int sum = 0;
		int count = 0;
		for(Map.Entry<String, Map<String, Integer>> entry:viewer_movie_map.entrySet()) {
			for(Map.Entry<String, Integer> moviesmap :entry.getValue().entrySet()) {
				if(movie.equals(moviesmap.getKey())) {
					sum+=moviesmap.getValue();
					count++;
				}
			}
		}
		for(Map.Entry<String, Map<String, Integer>> entry:critic_movie_map.entrySet()) {
			for(Map.Entry<String, Integer> moviesmap :entry.getValue().entrySet()) {
				if(movie.equals(moviesmap.getKey())) {
					sum+=moviesmap.getValue();
					count++;
				}
			}
		}
		for(Map.Entry<String, Map<String, Integer>> entry:expert_movie_map.entrySet()) {
			for(Map.Entry<String, Integer> moviesmap :entry.getValue().entrySet()) {
				if(movie.equals(moviesmap.getKey())) {
					sum+=moviesmap.getValue();
					count++;
				}
			}
		}
		for(Map.Entry<String, Map<String, Integer>> entry:admin_movie_map.entrySet()) {
			for(Map.Entry<String, Integer> moviesmap :entry.getValue().entrySet()) {
				if(movie.equals(moviesmap.getKey())) {
					sum+=moviesmap.getValue();
					count++;
				}
			}
		}
		
		double ans = (double)sum/(double)count;
		
		System.out.println("Average score of movie "+movie+" is "+Math.round(ans*100.0)/100.0);
		
	}

	@Override
	public void getAvgScoreForGenre(String genre) {
		

		
		Set<String> moviesInGenre = getAllMovieFor(genre);
		/*
		 * if(moviesInYear.size() > 0) { int sum = 0; int count = 0;
		 * for(Map.Entry<String, Integer> i:movie_map.entrySet()) {
		 * if(moviesInYear.contains(i.getKey())) { sum+=i.getValue(); count++; } }
		 * System.out.println("Average movie score for year "+year+
		 * " is "+(double)(sum/count)); }
		 */
		int sum = 0;
		int count = 0;
		for(Map.Entry<String, Map<String, Integer>> entry:viewer_movie_map.entrySet()) {
			for(Map.Entry<String, Integer> moviesmap :entry.getValue().entrySet()) {
				if(moviesInGenre.contains(moviesmap.getKey())) {
					sum+=moviesmap.getValue();
					count++;
				}
			}
		}
		for(Map.Entry<String, Map<String, Integer>> entry:critic_movie_map.entrySet()) {
			for(Map.Entry<String, Integer> moviesmap :entry.getValue().entrySet()) {
				if(moviesInGenre.contains(moviesmap.getKey())) {
					sum+=moviesmap.getValue();
					count++;
				}
			}
		}
		for(Map.Entry<String, Map<String, Integer>> entry:expert_movie_map.entrySet()) {
			for(Map.Entry<String, Integer> moviesmap :entry.getValue().entrySet()) {
				if(moviesInGenre.contains(moviesmap.getKey())) {
					sum+=moviesmap.getValue();
					count++;
				}
			}
		}
		for(Map.Entry<String, Map<String, Integer>> entry:admin_movie_map.entrySet()) {
			for(Map.Entry<String, Integer> moviesmap :entry.getValue().entrySet()) {
				if(moviesInGenre.contains(moviesmap.getKey())) {
					sum+=moviesmap.getValue();
					count++;
				}
			}
		}
		double ans = (double)sum/(double)count;
		
		System.out.println("Average score of particular genre "+genre+" is "+Math.round(ans*100.0)/100.0);
		
	
		
	}
	

}

