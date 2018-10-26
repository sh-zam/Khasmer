package abs.khasmer.disastermanagement;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;


/*
 * Created by sh-zam <codeconscious@pm.me> on 10/10/18
 */

public class ImagesGalleryActivity extends AppCompatActivity {

	private static final String TAG = "ImagesGalleryActivity";
	private ArrayList<File> mFiles = new ArrayList<>();

	private ImagesAdapter mAdapter;
	private RecyclerView mRecyclerView;

	private boolean displayMenu = false;

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

		mAdapter = new ImagesAdapter(this, mFiles);
		mRecyclerView = findViewById(R.id.recyclerView);
		mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
		mRecyclerView.setAdapter(mAdapter);
	}

	void toggleActionBarView() {
		//getSupportActionBar().setCustomView(R.layout.toolbar_layout);
		displayMenu = !displayMenu;
		invalidateOptionsMenu();
	}

	/**
	 * removes the deleted views
	 */
	private void deleteView(Integer[] array) {
		deleteImages(array);
		mAdapter.notifyDataSetChanged();
	}

	/**
	 * Deletes the image from storage and the dataset
	 * @param positions of the selected images
	 */
	private void deleteImages(Integer[] positions) {
		for (int position: positions) {
			mFiles.get(position).delete();
			mFiles.remove(position);
		}
		mAdapter.clearSelectedItems();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (displayMenu)
			getMenuInflater().inflate(R.menu.long_press_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.menu_delete:
				deleteView(mAdapter.getSelectedItems());
				break;
			case R.id.menu_next:
				if (mAdapter.getSelectedItems().length != 2) {
					Toast.makeText(this, "Please select only 2 images", Toast.LENGTH_LONG)
							.show();
				}
				else {
					Intent i = new Intent(ImagesGalleryActivity.this, DetailsActivity.class);
					i.putExtra("FILE_1", mFiles.get(mAdapter.getSelectedItems()[0]));
					i.putExtra("FILE_2", mFiles.get(mAdapter.getSelectedItems()[1]));
					startActivity(i);
				}
				break;
			default:
				onBackPressed();
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onBackPressed() {
		if (displayMenu) {
			mAdapter.clearSelectedItems();
		}
		else {
			super.onBackPressed();
		}
	}
}
