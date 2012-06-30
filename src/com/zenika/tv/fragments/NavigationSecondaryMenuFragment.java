package com.zenika.tv.fragments;

import com.zenika.tv.R;
import com.zenika.tv.R.id;

import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class NavigationSecondaryMenuFragment extends ListFragment {

	private static final String[] CONFERENCES = new String[]{ "Spring in action",
		"How to debug Android Apps"
	};
	private static final String[] TRAININGS = new String[]{ "Android - 3 days",
		"Java - 2 days", "C++ - 40 days"
	};
	private static final String[] EXPERTS = new String[]{ 
		"Larry Page", "Linus Torvalds"
	};
	
	private String[][] listItems = new String[][]{CONFERENCES,TRAININGS,EXPERTS};
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		changeTitle(null);
	}
	
	public void changeTitle(String details){
		String title=null;
		switch (getCurrentPrimaryIndex()) {
		case NavigationPrimaryMenuFragment.CONFERENCES:
			title = "Conferences";
			break;
		case NavigationPrimaryMenuFragment.TRAININGS:
			title = "Trainings";
			break;
		case NavigationPrimaryMenuFragment.EXPERTS:
			title = "Experts";
			break;
		default:
			break;
		}
		if(details!=null)
			title=title.concat(" > "+details);
		getActivity().setTitle(title);
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		getListView().setItemChecked(position, true);
		replaceDetailsFragment(position);
		changeTitle(listItems[getCurrentPrimaryIndex()][position]);
	}
	
	private void replaceDetailsFragment(int position) {
		NavigationDetailsFragment details = (NavigationDetailsFragment) 
				getFragmentManager().findFragmentById(R.id.details);
		if(details == null || details.getCurrentDetailId() != position){
			details = NavigationDetailsFragment.newInstance(getCurrentPrimaryIndex()*10+position);
			FragmentTransaction ft = getFragmentManager().beginTransaction();
			ft.replace(R.id.details, details);
			ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
			ft.commit();
		}
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setListAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_activated_1, listItems[getCurrentPrimaryIndex()]));
		getListView().setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
	}

	
	public int getCurrentPrimaryIndex() {
		return getArguments().getInt("primaryIndex", 0);
	}

	public static NavigationSecondaryMenuFragment newInstance(int index){
		NavigationSecondaryMenuFragment secondaryMenu = new NavigationSecondaryMenuFragment();

		Bundle args = new Bundle();
		args.putInt("primaryIndex", index);
		secondaryMenu.setArguments(args);

		return secondaryMenu;
	}
}
