package abs.khasmer.disastermanagement;

import android.content.Context;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;


/*
 * Created by sh_zam on 27/10/18
 * Email: codeconscious@pm.me
 */

public class HistoryAdapter extends
		RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

	private Context mContext;
	private ArrayList<HistoryItem> mHistoryItems;

	public HistoryAdapter(Context context, ArrayList<HistoryItem> items) {
		mContext = context;
		mHistoryItems = items;
	}

	@NonNull
	@Override
	public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

		View v = LayoutInflater.from(viewGroup.getContext())
				.inflate(R.layout.history_recyclerview_item, null);

		return new ViewHolder(v);
	}

	@Override
	public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
		HistoryItem item = mHistoryItems.get(position);
		Glide.with(mContext).load(item.getImage1Location()).into(holder.mImageView1);
		Glide.with(mContext).load(item.getImage2Location()).into(holder.mImageView2);
		holder.mTextView.setText(String.valueOf(item.getRating()));
	}

	@Override
	public int getItemCount() {
		return mHistoryItems.size();
	}

	static class ViewHolder extends RecyclerView.ViewHolder {

		ImageView mImageView1;
		ImageView mImageView2;
		TextView mTextView;

		public ViewHolder(@NonNull View itemView) {
			super(itemView);
			mImageView1 = itemView.findViewById(R.id.imageView1);
			mImageView2 = itemView.findViewById(R.id.imageView2);
			mTextView = itemView.findViewById(R.id.disasterTV);
		}
	}
}
