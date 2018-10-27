package abs.khasmer.disastermanagement;

/*
 * Created by sh-zam <codeconscious@pm.me> on 11/10/18
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;


public class DetailsActivity extends AppCompatActivity {
	// NDK STUFF
	static {
		System.loadLibrary("dip");
	}
	public native double calcDisasterPCT(
			Bitmap bitmap_src,
			Bitmap bitmap_dst
			);
	// END NDK STUFF

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.details_activity);
		setTitle("Analyze");

		if (getSupportActionBar() == null) {
			Log.e("DetailsActivity", "Don't Change the theme!");
			return;
		}

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		File file1 = (File) getIntent().getSerializableExtra("FILE_1");
		File file2 = (File) getIntent().getSerializableExtra("FILE_2");
		ImageView iv1 = findViewById(R.id.imageView1);
		ImageView iv2 = findViewById(R.id.imageView2);
		TextView tv = findViewById(R.id.textViewAnalysis);

		Glide.with(this).load(file1).into(iv1);
		Glide.with(this).load(file2).into(iv2);
		
		// Convert Images to Bitmaps
		Bitmap left = BitmapFactory.decodeFile(file1.getAbsolutePath());
		Bitmap right = BitmapFactory.decodeFile(file2.getAbsolutePath());
		
		// Trigger C++
//      Bitmap left=(Bitmap)data.getExtras().get("data");
//      Bitmap right=(Bitmap)data.getExtras().get("data");
//      iv1.setImageBitmap(right);
//      
		 double pct = calcDisasterPCT(left, right);

		 // Toast display
		 Context context = getApplicationContext();
		 CharSequence text = "Calculated Percentages Damages " + Double.toString(round(pct, 2)) + "%";
		 int duration = Toast.LENGTH_SHORT;
		 Toast toast = Toast.makeText(context, text, duration);
		 toast.setGravity(Gravity.BOTTOM|Gravity.CENTER, 0, 0);
		 toast.show();
		 tv.setText(text);
		 if (pct >= 75) {
		 	tv.setBackgroundColor(Color.RED);
		 }
		 else if (pct >= 50) {
			tv.setBackgroundColor(Color.YELLOW);
		 }
		 else {
		 	tv.setBackgroundColor(Color.GREEN);
		 }
	}
	
	public static double round(double value, int places) {
		if (places < 0) throw new IllegalArgumentException();

		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		onBackPressed();
		return super.onOptionsItemSelected(item);
	}
}
