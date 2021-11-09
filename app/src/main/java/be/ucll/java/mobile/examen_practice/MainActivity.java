package be.ucll.java.mobile.examen_practice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.nav_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_chrono) {
            Intent intent = new Intent(this, ChronoActivity.class);
            startActivity(intent);
        }
        if (id == R.id.menu_fragments) {
            Intent intent = new Intent(this, FragmentActivity.class);
            startActivity(intent);
        }
        if (id == R.id.menu_storage) {
            Intent intent = new Intent(this, StorageActivity.class);
            startActivity(intent);
        }
        if (id == R.id.menu_weather) {
            Intent intent = new Intent(this, WeatherActivity.class);
            startActivity(intent);
        }
        if (id == R.id.menu_website) {

            //Deze specifieke intent opent een website.
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/kiwi.nl/"));
            startActivity(intent);
        }
        if (id == R.id.menu_settings){
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}