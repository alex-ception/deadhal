package fr.upem.android.deadhal;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class MenuMainActivity extends Activity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	public void buttonsOnClick(View view)
	{
	    switch (view.getId()) {
	        case R.id.menu_main_button_builder:
	            Intent menuBuilder = new Intent(view.getContext(), MenuBuilderActivity.class);
	            this.startActivityForResult(menuBuilder, 0);
	            break;
	        case R.id.menu_main_button_quit:
	            this.finish();
	            System.exit(0);
            default:
                break;
	    }
	}
}
