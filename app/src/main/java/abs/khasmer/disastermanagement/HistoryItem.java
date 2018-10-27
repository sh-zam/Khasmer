package abs.khasmer.disastermanagement;

/*
 * Created by sh_zam on 27/10/18
 * Email: codeconscious@pm.me
 */

public class HistoryItem {

	private final String mImage1Location;
	private final String mImage2Location;
	private final double mRating;

	public HistoryItem(String image1Location,
					   String image2Location,
					   double rating) {
		mImage1Location = image1Location;
		mImage2Location = image2Location;
		mRating = rating;
	}


	public String getImage1Location() {
		return mImage1Location;
	}

	public String getImage2Location() {
		return mImage2Location;
	}

	public double getRating() {
		return mRating;
	}
}
