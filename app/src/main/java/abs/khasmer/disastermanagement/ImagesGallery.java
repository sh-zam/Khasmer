package abs.khasmer.disastermanagement;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.GridView;

import java.io.File;
import java.util.ArrayList;


/*
 * Created by sh-zam <codeconscious@pm.me> on 10/10/18
 */

public class ImagesGallery extends AppCompatActivity {

	private static final String TAG = "ImagesGallery";
	private ArrayList<File> mFiles = new ArrayList<>();

	private GridView mGridView;
	private CustomGridViewAdapter mAdapter;
	private RecyclerView mRecyclerView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.images_gallery_activity);
		setTitle("Images");

		// Add file names to ArrayList
		File parent = Environment.getExternalStorageDirectory();
		File imagesDir = new File(parent, "Pictures/images");
		for (File file: imagesDir.listFiles()) {
			if (file.isFile()) {
				mFiles.add(file);
			}
		}

		if (getSupportActionBar() == null) {
			Log.e(TAG, "Don't change  theme");
			return;
		}
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		mRecyclerView = findViewById(R.id.recyclerView);
		mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
		mRecyclerView.setAdapter(new ImagesAdapter(this, mFiles));
	}

	/**
	 * removes the deleted views
	 */
	private void deleteView() {
		for (int i = mGridView.getCount() - 1; i >= 0; --i) {
			if (mGridView.isItemChecked(i)) {
				deleteImage(i);
				mAdapter.notifyDataSetChanged();
			}
		}
	}

	/**
	 * Deletes the image from storage and dataset
	 * @param position of the item
	 */
	private void deleteImage(int position) {
		mFiles.get(position).delete();
		mFiles.remove(position);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		super.onBackPressed();
		return super.onOptionsItemSelected(item);
	}
}
