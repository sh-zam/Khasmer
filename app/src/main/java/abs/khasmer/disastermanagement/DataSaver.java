package abs.khasmer.disastermanagement;

/*
 * Created by sh_zam on 27/10/18
 * Email: codeconscious@pm.me
 */

/*

JSON array start
[

JSON object start
"$date" : {
	"image1" : "<location to that image>",
	"image2" : "<location to another image>",
	"rating" : "<result in percentage>"
}
JSON object end

...
...
...

]
JSON array end
 */

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;

public class DataSaver {

	///////////////////////////////////////////////////////////////////////////
	// JSON keys start
	///////////////////////////////////////////////////////////////////////////

	private static final String IMAGE_ONE_LOCATION = "image1";
	private static final String IMAGE_TWO_LOCATION = "image2";
	private static final String DISASTER_RATING = "rating";

	///////////////////////////////////////////////////////////////////////////
	// JSON keys end
	///////////////////////////////////////////////////////////////////////////

	private static final String FILENAME = "history.json";
	private final Context mContext;

	public DataSaver(Context context) {
		mContext = context;
	}

	public void saveResult(String image1Location,
								  String image2Location,
								  double rating) {
		try {
			// JSON mapping starts
			JSONObject object = new JSONObject();
			object.put(IMAGE_ONE_LOCATION, image1Location);
			object.put(IMAGE_TWO_LOCATION, image2Location);
			object.put(DISASTER_RATING, rating);
			// JSON mapping ends

			Log.d("DataSaver", "json data: " + object.toString());

			FileOutputStream out = mContext.openFileOutput(FILENAME,
					Context.MODE_PRIVATE);

			Writer writer = new OutputStreamWriter(out);
			writer.write(object.toString());
			writer.close();
		}
		catch (JSONException | IOException e) {
			// ( ͡° ͜ʖ ͡°)
			e.printStackTrace();
		}
	}

	public JSONArray getResults() {
		StringBuilder builder = new StringBuilder();
		try {
			FileInputStream fis = mContext.openFileInput(FILENAME);
			InputStreamReader in = new InputStreamReader(fis);
			 BufferedReader reader = new BufferedReader(in);

			String tmp;
			while ((tmp = reader.readLine()) != null) {
				builder.append(tmp);
			}
			reader.close();
		}
		catch (IOException e) {
			// ( ͡° ͜ʖ ͡°)
			e.printStackTrace();
		}

		return parseString(builder.toString());
	}

	private JSONArray parseString(String s) {
		try {
			return new JSONArray(s);
		}
		catch (JSONException e) {
			// ( ͡° ͜ʖ ͡°)
			e.printStackTrace();
		}
		return null;
	}

}
