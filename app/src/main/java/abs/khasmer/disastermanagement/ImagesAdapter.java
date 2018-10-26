package abs.khasmer.disastermanagement;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;

/*
 * Created by sh_zam on 26/10/18
 * Email: codeconscious@pm.me
 */


public class ImagesAdapter extends RecyclerView.Adapter<CustomViewHolder> {

	private ArrayList<File> mFiles;
	private Context mContext;
	private CustomViewHolder mCustomViewHolder;

	private HashSet<Integer> mSelectedItems = new HashSet<>();

	public ImagesAdapter(Context activity, ArrayList<File> files) {
		mContext = activity;
		mFiles = files;
	}

	@NonNull
	@Override
	public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

		View v = LayoutInflater.from(viewGroup.getContext())
				.inflate(R.layout.recycler_view_item, viewGroup, false);

		mCustomViewHolder =  new CustomViewHolder(mContext, v, mSelectedItems);

		return mCustomViewHolder;
	}

	@Override
	public void onBindViewHolder(@NonNull CustomViewHolder viewHolder, int position) {
		ImageView iv = viewHolder.mImageView;
		viewHolder.mCheckedImageView.setVisibility(View.GONE);
		Glide.with(mContext).load(mFiles.get(position)).into(iv);
	}

	@Override
	public int getItemCount() {
		return mFiles.size();
	}

	public Integer[] getSelectedItems(){
		return mSelectedItems.toArray(new Integer[mSelectedItems.size()]);
	}

	public void clearSelectedItems() {
		mSelectedItems.clear();
		((ImagesGallery) mContext).toggleActionBarView();
		notifyDataSetChanged();
	}

}
