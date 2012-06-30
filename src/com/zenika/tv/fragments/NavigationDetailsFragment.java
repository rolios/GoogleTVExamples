package com.zenika.tv.fragments;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import com.zenika.tv.R;
import com.zenika.tv.R.id;
import com.zenika.tv.R.layout;
import com.zenika.tv.R.xml;
import com.zenika.tv.models.Conference;
import com.zenika.tv.models.Expert;
import com.zenika.tv.models.Training;
import com.zenika.tv.utils.DetailsParser;

import android.app.Fragment;
import android.content.res.XmlResourceParser;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.TextView;

public class NavigationDetailsFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		switch (getCurrentDetailId()/10) {
		case NavigationPrimaryMenuFragment.CONFERENCES:
			return inflater.inflate(R.layout.navigation_details_conference, container, false);
		case NavigationPrimaryMenuFragment.TRAININGS:
			return inflater.inflate(R.layout.navigation_details_training, container, false);
		case NavigationPrimaryMenuFragment.EXPERTS:
			return inflater.inflate(R.layout.navigation_details_expert, container, false);
		default:
			FrameLayout layout = new FrameLayout(getActivity());
			layout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
			layout.setBackgroundColor(android.graphics.Color.DKGRAY);
			return layout;
		}
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		new ParserTask().execute(getResources().getXml(R.xml.navigation_details));
	}

	private void initializeValues(Object entity) {
		View root = getView();
		switch (getCurrentDetailId()/10) {
		case NavigationPrimaryMenuFragment.CONFERENCES:
			if(!(entity instanceof Conference))
				return;
			Conference conference = (Conference)entity;
			TextView title = (TextView)root.findViewById(R.id.conference_title);
			TextView location = (TextView)root.findViewById(R.id.conference_location);
			TextView date = (TextView)root.findViewById(R.id.conference_date);
			TextView author = (TextView)root.findViewById(R.id.conference_author);

			title.setText(conference.getTitle());
			author.setText(conference.getAuthor());
			DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			date.setText(formatter.format(conference.getDate()));
			location.setText(conference.getLocation());
			break;
		case NavigationPrimaryMenuFragment.TRAININGS:
			if(!(entity instanceof Training))
				return;
			Training training = (Training)entity;
			TextView trainingTitle = (TextView)root.findViewById(R.id.training_title);
			TextView trainingLocation = (TextView)root.findViewById(R.id.training_location);
			TextView duration = (TextView)root.findViewById(R.id.training_duration);
			
			trainingTitle.setText(training.getTitle());
			trainingLocation.setText(training.getLocation());
			duration.setText(Integer.toString(training.getDuration()));
			break;
		case NavigationPrimaryMenuFragment.EXPERTS:
			if(!(entity instanceof Expert))
				return;
			Expert expert= (Expert)entity;
			TextView firstName = (TextView)root.findViewById(R.id.expert_first_name);
			TextView lastName = (TextView)root.findViewById(R.id.expert_last_name);
			TextView company = (TextView)root.findViewById(R.id.expert_compay);
			
			firstName.setText(expert.getFirstName());
			lastName.setText(expert.getLastName());
			company.setText(expert.getCompany());
		default:
			break;
		}
	}
	
	public int getCurrentDetailId() {
		return getArguments().getInt("detailId",-1);
	}

	public static NavigationDetailsFragment newInstance(int index){
		NavigationDetailsFragment details = new NavigationDetailsFragment();

		Bundle args = new Bundle();
		args.putInt("detailId", index);
		details.setArguments(args);

		return details;
	}
	
	class ParserTask extends AsyncTask<XmlResourceParser, Integer, Object>{
		
		@Override
		protected Object doInBackground(XmlResourceParser... params) {
			Object entity = null;
			try {
				entity=DetailsParser.getEntityById(params[0],getCurrentDetailId());
			} catch (IOException e) {
				e.printStackTrace();
			} catch (XmlPullParserException e) {
				e.printStackTrace();
			}
			return entity;
		}
		
		@Override
		protected void onPostExecute(Object result) {
			if(result!=null)
				initializeValues(result);
		}
	}
}
