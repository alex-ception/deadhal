package fr.upem.android.deadhal;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

public class BuilderActivity extends Activity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        
        BuilderView mView = new BuilderView(this);
		setContentView(mView);
		
        //setContentView(R.layout.activity_builder);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.builder, menu);
        return true;
    }
    

}
