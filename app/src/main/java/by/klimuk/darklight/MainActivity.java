package by.klimuk.darklight;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity
        implements BottomNavigationView.OnNavigationItemSelectedListener{

    FrameLayout fragmentsLayout;// Слой фрагментов в активити
    HouseFragment houseFrag; // фрагмент домового чата
    DistrictFragment districtFrag; // фрагмент районного чата
    CityFragment cityFrag; // фрагмент городского чата

    // Переменные темы
    int themeId;
    String THEME_ID = "themeId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Запрещаем применение ночной темы
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        //Считываем идентификатор текущей темы
        SharedPreferences sPref = getSharedPreferences(THEME_ID, MODE_PRIVATE);
        themeId = sPref.getInt(THEME_ID, getResources().getIdentifier("MyLightTheme", "style",
                this.getPackageName()));
        // Устанавливаем тему
        setTheme(themeId);

        setContentView(R.layout.activity_main);

        // Инициализируем поле для фрагментов
        fragmentsLayout = findViewById(R.id.fragmentsLayout);

        // Инициализируем фрагменты
        houseFrag = new HouseFragment();
        districtFrag = new DistrictFragment();
        cityFrag = new CityFragment();

        // Размещаем фрагмент домового чата в поле фрагментов активности
        replaseFragment(houseFrag);

        // Находим навигационную панель и присваиваем ей слушателя
        BottomNavigationView btnNavi = findViewById(R.id.btnNavi);
        btnNavi.setOnNavigationItemSelectedListener(this);
    }

    // Размещаем фрагмент в поле фрагментов активности
    private void replaseFragment(Fragment fragment) {
        FragmentTransaction fTrans = getSupportFragmentManager().beginTransaction();
        fTrans.replace(fragmentsLayout.getId(), fragment);
        fTrans.commit();
    }
    //Обработка нажатий BottomNavigation для смены фрагментов
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_house:
                replaseFragment(houseFrag);
                break;
            case R.id.navigation_district:
                replaseFragment(districtFrag);
                break;
            case R.id.navigation_city:
                replaseFragment(cityFrag);
                break;
        }
        return true;
    }
    // Создание меню
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    // Обработка нажатий элементов меню
    public boolean onOptionsItemSelected(MenuItem item) {
        SharedPreferences sPref = getSharedPreferences(THEME_ID, MODE_PRIVATE);
        SharedPreferences.Editor editor = sPref.edit();
        switch (item.getItemId()) {
            case R.id.light:
                editor.putInt(THEME_ID, getResources().getIdentifier("MyLightTheme", "style",
                        this.getPackageName()));
                break;
            case R.id.dark:
                editor.putInt(THEME_ID, getResources().getIdentifier("MyDarkTheme", "style",
                        this.getPackageName()));
                break;
        }
        editor.commit();
        recreate();
        return super.onOptionsItemSelected(item);
    }
}