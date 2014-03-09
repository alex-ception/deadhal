package fr.upem.android.deadhal;

import android.app.Activity;
import android.graphics.Color;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.ZoomControls;

public class BuilderActivity extends Activity {
	private FrameLayout pCameraLayout = null; // this layout contains
												// surfaceview
	private ZoomControls zoomControls;
	Camera mCamera = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_builder);
		LinearLayout linearLayout = (LinearLayout)findViewById(R.id.linearLayout);
		Switch sw = new Switch(getApplicationContext());
		sw.setText("Rotation : ");
		sw.setTextColor(Color.BLACK);
		BuilderView mView = new BuilderView(this,sw);
		linearLayout.addView(sw);
		linearLayout.addView(mView);
		
	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.builder, menu);
		return true;
	}

}
