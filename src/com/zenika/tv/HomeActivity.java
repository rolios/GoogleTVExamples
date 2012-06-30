package com.zenika.tv;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class HomeActivity extends ListActivity {

	private static final ActivityClassItem[] ACTIVITIES=new ActivityClassItem[]{
		new ActivityClassItem(KeypadExampleActivity.class) ,
		new ActivityClassItem(NavigationFirstExampleActivity.class),
		new ActivityClassItem(ChannelExampleActivity.class)
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setListAdapter(new ArrayAdapter<ActivityClassItem>(this, android.R.layout.simple_list_item_1, ACTIVITIES));
		ListView lv=getListView();
		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				startActivity(new Intent(HomeActivity.this,ACTIVITIES[position].getActivityClass()));
			}
		});
	}
}

class ActivityClassItem {
	
	private Class activityClass;
	
	public Class<Activity> getActivityClass() {
		return activityClass;
	}

	public ActivityClassItem(Class clazz) {
		activityClass=clazz;
	}
	
	@Override
	public String toString() {
		return activityClass.getSimpleName();
	}
	
}
