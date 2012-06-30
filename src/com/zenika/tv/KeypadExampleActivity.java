package com.zenika.tv;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.ImageView;

public class KeypadExampleActivity extends Activity {
	
	private ImageView logo;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.keypad_example);
		logo = (ImageView)findViewById(R.id.logo);
		logo.setImageResource(R.drawable.icon);
		logo.setAdjustViewBounds(true);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		Log.d("ZenTV","event: "+event.getKeyCode());
		ObjectAnimator animation;
		switch (event.getKeyCode()) {
		case KeyEvent.KEYCODE_DPAD_DOWN:
			animation = ObjectAnimator.ofFloat(logo, "rotation", logo.getRotation()-90f);
			animation.setDuration(500);
			animation.start();
			break;
		case KeyEvent.KEYCODE_DPAD_LEFT:
			animation = ObjectAnimator.ofFloat(logo, "X", logo.getX()-180f);
			animation.setDuration(500);
			animation.start();
			break;
		case KeyEvent.KEYCODE_DPAD_RIGHT:
			animation = ObjectAnimator.ofFloat(logo, "X", logo.getX()+180f);
			animation.setDuration(500);
			animation.start();
			break;
		case KeyEvent.KEYCODE_DPAD_UP:
			animation = ObjectAnimator.ofFloat(logo, "rotation", logo.getRotation()+90f);
			animation.setDuration(500);
			animation.start();
			break;
		case KeyEvent.KEYCODE_SPACE:
			animation = ObjectAnimator.ofFloat(logo, "rotation", 0);
			animation.setDuration(500);
			animation.start();
			animation = ObjectAnimator.ofFloat(logo, "X", 0);
			animation.setDuration(500);
			animation.start();
		default:
			break;
		}
		Log.d("ZenTV","x: "+logo.getX());
		Log.d("ZenTV","rotation: "+logo.getRotation());
		return super.onKeyDown(keyCode, event);
	}
}