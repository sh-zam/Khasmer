package abs.khasmer.disastermanagement;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;

/*
 * Created by sh_zam on 26/10/18
 * Email: codeconscious@pm.me
 */


public class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.ViewHolder> {

	private ArrayList<File> mFiles;
	private Context mContext;

	public ImagesAdapter(Context context, ArrayList<File> files) {
		mContext = context;
		mFiles = files;
	}

	static class ViewHolder extends RecyclerView.ViewHolder
			implements View.OnLongClickListener, View.OnClickListener {
		ConstraintLayout mParentLayout;
		ImageView mImageView;
		ImageView mCheckedImageView;

		public ViewHolder(@NonNull View itemView) {
			super(itemView);
			mImageView = itemView.findViewById(R.id.imageView);
			mCheckedImageView = itemView.findViewById(R.id.checked);
			mParentLayout = itemView.findViewById(R.id.gallery_item_parent);

			mParentLayout.setOnLongClickListener(this);
			mParentLayout.setOnClickListener(this);
		}

		@Override
		public void onClick(View v) {

		}

		@Override
		public boolean onLongClick(View v) {
			return false;
		}
	}

	@NonNull
	@Override
	public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

		View v = LayoutInflater.from(viewGroup.getContext())
				.inflate(R.layout.recycler_view_item, viewGroup, false);

		return new ViewHolder(v);
	}

	@Override
	public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
		ImageView iv = viewHolder.mImageView;
		Glide.with(mContext).load(mFiles.get(position)).into(iv);
	}

	@Override
	public int getItemCount() {
		return mFiles.size();
	}
}
