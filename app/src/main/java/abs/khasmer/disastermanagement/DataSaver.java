package abs.khasmer.disastermanagement;

/*
 * Created by sh_zam on 27/10/18
 * Email: codeconscious@pm.me
 */

import android.content.Context;

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
import java.util.ArrayList;

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
			JSONArray jsonArray = getJsonArray();
			if (jsonArray == null) {
				jsonArray = new JSONArray();
			}
			JSONObject object = new JSONObject();
			object.put(IMAGE_ONE_LOCATION, image1Location);
			object.put(IMAGE_TWO_LOCATION, image2Location);
			object.put(DISASTER_RATING, rating);
			jsonArray.put(object);
			// JSON mapping ends

			//Log.d("DataSaver", "json data: " + object.toString());

			FileOutputStream out = mContext.openFileOutput(FILENAME,
					Context.MODE_PRIVATE);

			Writer writer = new OutputStreamWriter(out);
			writer.write(jsonArray.toString());
			writer.close();
		}
		catch (JSONException | IOException e) {
			// ( ͡° ͜ʖ ͡°)
			e.printStackTrace();
		}
	}

	private JSONArray getJsonArray() {
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
			return new JSONArray(builder.toString());
		}
		catch (IOException | JSONException e) {
			// ( ͡° ͜ʖ ͡°)
			e.printStackTrace();
		}
		return null;
	}

	public ArrayList<HistoryItem> getResults() {
		JSONArray jsonArray = getJsonArray();
		if (jsonArray == null) {
			return new ArrayList<>();
		}
		return parseJson(jsonArray);
	}

	private ArrayList<HistoryItem> parseJson(JSONArray array) {
		ArrayList<HistoryItem> historyItems = new ArrayList<>();
		try {
			for (int i = 0; i < array.length(); ++i) {
				JSONObject object = array.getJSONObject(i);
				historyItems.add(new HistoryItem(
						object.getString(IMAGE_ONE_LOCATION),
						object.getString(IMAGE_TWO_LOCATION),
						object.getDouble(DISASTER_RATING)));
			}
			//Log.d("DataSaver", "historyItems: " + array.toString());
		}
		catch (JSONException e) {
			// ( ͡° ͜ʖ ͡°)
			e.printStackTrace();
		}
		return historyItems;
	}

}
