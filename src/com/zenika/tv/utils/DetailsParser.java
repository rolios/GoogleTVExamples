package com.zenika.tv.utils;

import java.io.IOException;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.content.res.XmlResourceParser;

import com.zenika.tv.models.Conference;
import com.zenika.tv.models.Expert;
import com.zenika.tv.models.Training;

public class DetailsParser {
	
	public static Object getEntityById(XmlResourceParser parser, int id) throws IOException, XmlPullParserException{
		int eventType = parser.next();
		while(eventType != XmlPullParser.END_DOCUMENT){
			if(eventType == XmlPullParser.START_TAG 
					&& "item".equals(parser.getName())
					&& parser.getAttributeIntValue(1, -1) == id){
				String type = parser.getAttributeValue(0);
				if("conference".equals(type))
					return buildConference(parser);
				else if("expert".equals(type))
					return buildExpert(parser);
				else if("training".equals(type))
					return buildTraining(parser);
				else
					return null;
			}
			eventType = parser.next();
		}
		return null;
	}

	public static Conference buildConference(XmlPullParser parser)throws IOException, XmlPullParserException{
		int eventType = parser.next();
		Conference conference = new Conference();
		while(eventType != XmlPullParser.END_TAG && !"item".equals(parser.getName())){
			if(eventType == XmlPullParser.START_TAG && "title".equals(parser.getName()))
				conference.setTitle(parser.nextText());
			if(eventType == XmlPullParser.START_TAG && "location".equals(parser.getName()))
				conference.setLocation(parser.nextText());
			if(eventType == XmlPullParser.START_TAG && "date".equals(parser.getName()))
				conference.setDate(parser.nextText());
			if(eventType == XmlPullParser.START_TAG && "author".equals(parser.getName()))
				conference.setAuthor(parser.nextText());
			eventType = parser.next();
		}
		return conference;
	}

	public static Expert buildExpert(XmlPullParser parser)throws IOException, XmlPullParserException{
		int eventType = parser.next();
		Expert expert = new Expert();
		while(eventType != XmlPullParser.END_TAG && !"item".equals(parser.getName())){
			if(eventType == XmlPullParser.START_TAG && "firstName".equals(parser.getName()))
				expert.setFirstName(parser.nextText());
			if(eventType == XmlPullParser.START_TAG && "lastName".equals(parser.getName()))
				expert.setLastName(parser.nextText());
			if(eventType == XmlPullParser.START_TAG && "company".equals(parser.getName()))
				expert.setCompany(parser.nextText());
			eventType = parser.next();
		}
		return expert;
	}

	public static Training buildTraining(XmlPullParser parser)throws IOException, XmlPullParserException{
		int eventType = parser.next();
		Training training = new Training();
		while(eventType != XmlPullParser.END_TAG && !"item".equals(parser.getName())){
			if(eventType == XmlPullParser.START_TAG && "title".equals(parser.getName()))
				training.setTitle(parser.nextText());
			if(eventType == XmlPullParser.START_TAG && "location".equals(parser.getName()))
				training.setLocation(parser.nextText());
			if(eventType == XmlPullParser.START_TAG && "duration".equals(parser.getName()))
				training.setDuration(Integer.parseInt(parser.nextText()));
			eventType = parser.next();
		}
		return training;	
	}
}
