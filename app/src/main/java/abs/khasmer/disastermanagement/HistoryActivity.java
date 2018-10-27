package abs.khasmer.disastermanagement;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

/*
 * Created by sh_zam on 27/10/18
 * Email: codeconscious@pm.me
 */

public class HistoryActivity extends AppCompatActivity {

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.history_activity);
		if (getSupportActionBar() == null) {
			// theme changed
			return;
		}
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		RecyclerView recyclerView = findViewById(R.id.recyclerView);
		recyclerView.setLayoutManager(new LinearLayoutManager(this,
				LinearLayoutManager.VERTICAL, false));
		recyclerView.setAdapter(new HistoryAdapter(this, new DataSaver(this).getResults()));
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		onBackPressed();
		return super.onOptionsItemSelected(item);
	}
}
