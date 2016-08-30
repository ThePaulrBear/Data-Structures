package rockcountdown;

public class Song {

	// These are "properties" because they have getters and setters. Individually they are "fields."
	private int rank;


	public Song(int rank, String title, String artist) {
		super();
		this.rank = rank;
		this.title = title;
		this.artist = artist;
	}


	public Song(String s) {
		super();

		String[] params = s.split("\t");
		rank = Integer.valueOf(params[0].trim());
		title = params[1].trim();
		artist = params[2].trim();
	}


	private String title;

	private String artist;


	public int getRank() {
		return rank;
	}


	public void setRank(int rank) {
		this.rank = rank;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getArtist() {
		return artist;
	}


	public void setArtist(String artist) {
		this.artist = artist;
	}
}
