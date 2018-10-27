package abs.khasmer.disastermanagement;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

/*
 * Created by sh_zam on 27/10/18
 * Email: codeconscious@pm.me
 */

public class HistoryActivity extends AppCompatActivity {

	private DataSaver mDataSaver;
	private HistoryAdapter mAdapter;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.history_activity);
		if (getSupportActionBar() == null) {
			// theme changed
			return;
		}
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		mDataSaver = new DataSaver(this);
		mAdapter = new HistoryAdapter(this, mDataSaver.getResults());

		RecyclerView recyclerView = findViewById(R.id.recyclerView);
		recyclerView.setLayoutManager(new LinearLayoutManager(this,
				LinearLayoutManager.VERTICAL, false));
		recyclerView.setAdapter(mAdapter);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		onBackPressed();
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.delete_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	public void deleteHistory(MenuItem item) {
		mDataSaver.deleteHistory();

		// tells the adapter that data is deleted, so list can be cleared
		mAdapter.notifyDataDeleted();
	}
}
