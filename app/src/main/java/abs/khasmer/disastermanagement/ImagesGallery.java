package abs.khasmer.disastermanagement;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;


/*
 * Created by sh-zam <codeconscious@pm.me> on 10/10/18
 */

public class ImagesGallery extends AppCompatActivity {

	private static final String TAG = "ImagesGallery";
	private ArrayList<File> mFiles = new ArrayList<File>();

	private GridView mGridView;
	private CustomGridViewAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.images_gallery_activity);
		setTitle("Images");
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

		mGridView = findViewById(R.id.gridView);
		mAdapter = new CustomGridViewAdapter(this, mFiles);
		mGridView.setAdapter(mAdapter);
		mGridView.setChoiceMode(GridView.CHOICE_MODE_MULTIPLE_MODAL);
		mGridView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
			@Override
			public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
				ImageView iv = mGridView.getChildAt(position).findViewById(R.id.checked);
				if (checked) {
					iv.setVisibility(View.VISIBLE);
				}
				else {
					iv.setVisibility(View.GONE);
				}
			}

			@Override
			public boolean onCreateActionMode(ActionMode mode, Menu menu) {
				MenuInflater menuInflater = mode.getMenuInflater();
				menuInflater.inflate(R.menu.long_press_menu, menu);

				return true;
			}

			@Override
			public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
				return false;
			}

			@Override
			public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
				switch (item.getItemId()) {
					case R.id.menu_delete:
						deleteView();
						mode.finish();
						break;
					case R.id.menu_next:
						if (mGridView.getCheckedItemCount() != 2) {
							Toast.makeText(ImagesGallery.this, "Select two items", Toast.LENGTH_LONG).show();
						}
						else {
							// send photos to next activity
							SparseBooleanArray array = mGridView.getCheckedItemPositions();
							int[] positions = new int[2];
							for (int i = 0, j = 0; i < array.size() && j != 2; ++i) {
								if (array.get(i)) {
									positions[j] = i;
									j++;
								}
							}

							Intent i = new Intent(ImagesGallery.this, DetailsActivity.class);
							i.putExtra("FILE_1", mFiles.get(positions[0]));
							i.putExtra("FILE_2", mFiles.get(positions[1]));
							startActivity(i);
						}
						break;

				}
				return true;
			}

			@Override
			public void onDestroyActionMode(ActionMode mode) {
				// when user presses back button all the check views are cleared
				for (int i = 0; i < mGridView.getCount(); ++i) {
					if (mGridView.isItemChecked(i)) {
						mGridView.getChildAt(i).findViewById(R.id.checked).setVisibility(View.GONE);
					}
				}
			}
		});
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
