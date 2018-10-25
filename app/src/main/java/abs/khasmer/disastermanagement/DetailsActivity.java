package abs.khasmer.disastermanagement;

/*
 * Created by sh-zam <codeconscious@pm.me> on 11/10/18
 */

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;


public class DetailsActivity extends Activity {
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
		File file1 = (File) getIntent().getSerializableExtra("FILE_1");
		File file2 = (File) getIntent().getSerializableExtra("FILE_2");
		ImageView iv1 = (ImageView) findViewById(R.id.imageView1);
		ImageView iv2 = (ImageView) findViewById(R.id.imageView2);
		TextView tv = (TextView) findViewById(R.id.textViewAnalysis);

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
      CharSequence text = "Disaster: " + Double.toString(round(pct, 2)) + "%";
      int duration = Toast.LENGTH_LONG;
      Toast toast = Toast.makeText(context, text, duration);
      toast.setGravity(Gravity.BOTTOM|Gravity.CENTER, 0, 0);
      toast.show();
      tv.setText(text);
		
	}
	
	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}
}
