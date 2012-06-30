package com.zenika.tv.fragments;

import com.zenika.tv.R;
import com.zenika.tv.R.id;
import com.zenika.tv.R.layout;

import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

public class NavigationPrimaryMenuFragment extends ListFragment {

	private final Integer[] entries= new Integer[]{ android.R.drawable.ic_menu_recent_history,
			android.R.drawable.ic_menu_share, android.R.drawable.ic_menu_myplaces};

	public static final int CONFERENCES = 0;
	public static final int TRAININGS = 1;
	public static final int EXPERTS = 2;
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		setListAdapter(new ImageArrayAdapter(getActivity(), R.layout.drawable_item_list, R.id.image_list_item, entries));
		getListView().setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
		getListView().setItemChecked(0, true);
		replaceSecondaryMenu(0);
	}

	private void replaceSecondaryMenu(int position) {
		NavigationSecondaryMenuFragment secondaryMenu = (NavigationSecondaryMenuFragment) 
				getFragmentManager().findFragmentById(R.id.secondaryMenu);
		if(secondaryMenu == null || secondaryMenu.getCurrentPrimaryIndex() != position){
			secondaryMenu = NavigationSecondaryMenuFragment.newInstance(position);
			FragmentTransaction ft = getFragmentManager().beginTransaction();
			ft.replace(R.id.secondaryMenu, secondaryMenu);
			NavigationDetailsFragment details = (NavigationDetailsFragment)
					getFragmentManager().findFragmentById(R.id.details);
			if(details!=null)
				ft.remove(details);
			ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
			ft.commit();
		}
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		replaceSecondaryMenu(position);
	}

	class ImageArrayAdapter extends ArrayAdapter<Integer>{

		private Context mContext;
		private int mLayoutResource;
		private int mImageViewResource;

		public ImageArrayAdapter(Context context, int layoutResource, int imageViewResource, Integer[] objects) {
			super(context, layoutResource, objects);
			this.mContext = context;
			this.mLayoutResource = layoutResource;
			this.mImageViewResource = imageViewResource;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view= convertView;
			ImageView image;
			LayoutInflater mInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			view = mInflater.inflate(mLayoutResource, parent, false);
			try{
				image = (ImageView) view.findViewById(mImageViewResource);
			} catch (ClassCastException e) {
				Log.e("ArrayAdapter", "You must supply a resource ID for a TextView");
				throw new IllegalStateException(
						"ArrayAdapter requires the resource ID to be a TextView", e);
			}

			image.setImageResource(getItem(position));
			return view;
		}



	}
}
