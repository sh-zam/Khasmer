package abs.khasmer.disastermanagement;

/*
 * Created by sh-zam <codeconscious@pm.me> on 10/10/18
 */

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;

public class CustomGridViewAdapter extends ArrayAdapter<File> {

	private Context mContext;
	private ArrayList<File> mFiles;

	public CustomGridViewAdapter(Context context, ArrayList<File> files) {
		super(context, 0);
		mContext = context;
		mFiles = files;
	}

	@Override
	public int getCount() {
		return mFiles.size();
	}

	@NonNull
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext)
					.inflate(R.layout.gridview_item, parent, false);
		}

		ImageView iv = convertView.findViewById(R.id.imageView);
		Glide.with(mContext).load(mFiles.get(position)).into(iv);

		return convertView;
	}
}
