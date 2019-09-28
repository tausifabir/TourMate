package com.example.tourmatebase03.Weather;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tourmatebase03.EventListActivity;
import com.example.tourmatebase03.R;

import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherActivity extends AppCompatActivity {


    protected LocationManager locationManager;
    TextView txtLat, cityTextView;
    double lati;
    double longi;
    String cityName;
    String api_city ;

    CurrentWeather currentWeather;

    SectionPageAdapter sectionPageAdapter;
    ViewPager viewPager;
    TabLayout tabLayout;

    private EditText etCityName;
    private ImageView ivSearch;
    private Toolbar toolbar;

    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabs);

//        etCityName = findViewById(R.id.et_city_name);
//        ivSearch = findViewById(R.id.iv_search);
/*

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
*/

//        ivSearch.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String cityName = etCityName.getText().toString().trim();
//
//                if(TextUtils.isEmpty(cityName)){
//                    Toast.makeText(MainActivity.this, "Enter city name", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//                cityChange(cityName);
//            }
//        });

        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);

//        txtLat = (TextView) findViewById(R.id.textView);
//        cityTextView = findViewById(R.id.cityTextView);

/*        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        //locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        onLocationChanged(location);
        Log.i("city Name", cityName);
        Log.i("api city Name", api_city+"");*/


    }

/*    @Override
    public void onLocationChanged(Location location) {
        longi = location.getLongitude();
        lati = location.getLatitude();
        cityName = hereLocation();
        getWeather(cityName);

        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);


    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d("Latitude", "disable");
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d("Latitude", "enable");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d("Latitude", "status");
    }*/

    public String hereLocation() {
        String curCity= "";
        Geocoder geocoder = new Geocoder(WeatherActivity.this, Locale.getDefault());
        List<Address> addressList;
        try {
            addressList = geocoder.getFromLocation(lati, longi, 1);
            if (addressList.size() > 0) {
                curCity = addressList.get(0).getLocality();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return curCity;
    }


    public void getWeather(String cityName) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);

        String url = String.format("weather?q=%s&appid=%s",cityName, getString(R.string.weather_api_key));

        Call<CurrentWeather> call = api.getCurrentWeather(url);

        call.enqueue(new Callback<CurrentWeather>() {
            @Override
            public void onResponse(Call<CurrentWeather> call, Response<CurrentWeather> response) {
                currentWeather = response.body();
                api_city = currentWeather.getName();
                Bundle bundle = new Bundle();
                bundle.putString("city", api_city);
                Tab1Fragment tab1Fragment = new Tab1Fragment();
                tab1Fragment.setArguments(bundle);
                Toast.makeText(WeatherActivity.this, api_city+"",
                        Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<CurrentWeather> call, Throwable t) {

            }
        });
    }



    public void setupViewPager(ViewPager viewPager) {
        sectionPageAdapter = new SectionPageAdapter(getSupportFragmentManager());
        sectionPageAdapter.addFragment(new Tab1Fragment(), "Current");
        sectionPageAdapter.addFragment(new Tab2Fargment(), "7 Days Forecast");
        viewPager.setAdapter(sectionPageAdapter);


    }


    private void cityChange(String cityName){
        ((Tab1Fragment) sectionPageAdapter.getItem(0)).dataFetch(cityName);
        ((Tab2Fargment) sectionPageAdapter.getItem(1)).dataFetch(cityName);
    }

    public void unitChange(String units) {
        ((Tab1Fragment) sectionPageAdapter.getItem(0)).unitFetch(units);
        ((Tab2Fargment) sectionPageAdapter.getItem(1)).unitFetch(units);
    }

    public void backToHome() {
        ((Tab1Fragment) sectionPageAdapter.getItem(0)).getWeatherByHome();
        ((Tab2Fargment) sectionPageAdapter.getItem(1)).getWeatherByHome();
    }

    public String getMyData() {
        return api_city;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main2, menu);

        MenuItem item = menu.findItem(R.id.searchMenu);
        SearchView searchView = (SearchView) item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String city) {
                if (city.length()>=3) {
                    cityChange(city);
                    return true;
                }
                Toast.makeText(WeatherActivity.this, "Please enter city Name",
                        Toast.LENGTH_LONG).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.celciousMenu :
                String celsius = "metric";
                if (item.isChecked()) {
                    item.setChecked(false);
                } else {
                    item.setChecked(true);
                }

                unitChange(celsius);

                //Toast.makeText(this, "celsius", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.fahrenhiteMenu:
                String fahrenheit = "imperial";
                if (item.isChecked()) {
                    item.setChecked(false);
                } else {
                    item.setChecked(true);
                }

                unitChange(fahrenheit);

                //Toast.makeText(this, "Fahrenheit", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.homeMenu:

                //Toast.makeText(this, "clicked", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(WeatherActivity.this, EventListActivity.class));
                return true;

            default:
                return false;
        }
        //return super.onOptionsItemSelected(item);
    }
}
