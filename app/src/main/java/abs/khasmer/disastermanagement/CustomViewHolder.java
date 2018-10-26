package abs.khasmer.disastermanagement;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import java.util.HashSet;


/*
 * Created by sh_zam on 26/10/18
 * Email: codeconscious@pm.me
 */

public class CustomViewHolder extends RecyclerView.ViewHolder
			implements View.OnLongClickListener, View.OnClickListener {
	ConstraintLayout mParentLayout;
	ImageView mImageView;
	ImageView mCheckedImageView;

	private Context mContext;
	private HashSet<Integer> mSelectedItems;

	public CustomViewHolder(Context context, @NonNull View itemView, HashSet<Integer> selectedItems) {
		super(itemView);
		mImageView = itemView.findViewById(R.id.imageView);
		mCheckedImageView = itemView.findViewById(R.id.checked);
		mParentLayout = itemView.findViewById(R.id.gallery_item_parent);
		mContext = context;
		mSelectedItems = selectedItems;

		mParentLayout.setOnLongClickListener(this);
		mParentLayout.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if (mSelectedItems.size() >= 1) {
			toggle();
		}
	}

	@Override
	public boolean onLongClick(View v) {
		toggle();
		return true;
	}

	private void toggle() {
		if (mCheckedImageView.getVisibility() == View.GONE) {
			mCheckedImageView.setVisibility(View.VISIBLE);
			mSelectedItems.add(getAdapterPosition());
			if (mSelectedItems.size() == 1)
				((ImagesGallery)mContext).toggleActionBarView();
		}
		else {
			mCheckedImageView.setVisibility(View.GONE);
			mSelectedItems.remove(getAdapterPosition());
			if (mSelectedItems.size() == 0)
				((ImagesGallery)mContext).toggleActionBarView();
		}
	}
}
