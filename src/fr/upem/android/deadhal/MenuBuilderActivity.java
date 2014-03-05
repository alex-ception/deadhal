package fr.upem.android.deadhal;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class MenuBuilderActivity extends Activity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_builder);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_builder, menu);
        return true;
    }

    public void buttonsOnClick(View view)
    {
        switch (view.getId()) {
            case R.id.menu_builder_button_new:
                Intent builder = new Intent(view.getContext(), BuilderActivity.class);
                this.startActivityForResult(builder, 0);
                break;
            case R.id.menu_builder_button_load:
                break;
            case R.id.menu_builder_button_main_menu:
                Intent menuMain = new Intent(view.getContext(), MenuMainActivity.class);
                this.startActivityForResult(menuMain, 0);
                break;
            default:
                break;
        }
    }
}
