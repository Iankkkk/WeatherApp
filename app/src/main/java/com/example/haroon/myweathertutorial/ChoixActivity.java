package com.example.haroon.myweathertutorial;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ChoixActivity extends AppCompatActivity {

    TextView t1_temp,t2_city,t3_description,t4_date;
    Button retour = null;
    ImageView icone;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choix);

        t1_temp = (TextView)findViewById(R.id.textView);
        t2_city = (TextView)findViewById(R.id.textView3);
        t3_description = (TextView)findViewById(R.id.textView4);
        t4_date = (TextView)findViewById(R.id.textView2);
        retour = (Button)findViewById(R.id.retour);
        icone = (ImageView)findViewById(R.id.imageView);

        retour.setOnClickListener(retourListener);


        setTitle("Meteo");

        find_weather();
    }

    public void find_weather()
    {
        String url ="http://api.openweathermap.org/data/2.5/weather?q=algiers,algeria&appid=b7d1a5da596d0f045ad99a42e9d40d57&units=Imperial";

        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try
                {
                    JSONObject main_object = response.getJSONObject("main");
                    JSONArray array = response.getJSONArray("weather");
                    JSONObject object = array.getJSONObject(0);
                    String temp = String.valueOf(main_object.getDouble("temp"));
                    String description = object.getString("description");
                    String city = response.getString("name");

                    //  t1_temp.setText(temp);
                    t2_city.setText(city);
                    t3_description.setText(description);

                    if(description.equals("scattered clouds"))
                    {
                        icone.setImageResource(R.drawable.icon_scatteredclouds);
                    }
                    if(description.equals("clear sky"))
                    {
                        icone.setImageResource(R.drawable.icon_clearsky);
                    }

                    if(description.equals("broken clouds"))
                    {
                        icone.setImageResource(R.drawable.icon_brokenclouds);
                    }

                    if(description.equals("overcast clouds"))
                    {
                        icone.setImageResource(R.drawable.icon_brokenclouds);
                    }

                    if(description.equals("rain"))
                    {
                        icone.setImageResource(R.drawable.icon_rain);
                    }

                    if(description.equals("snow"))
                    {
                        icone.setImageResource(R.drawable.icon_snow);
                    }

                    if(description.equals("mist"))
                    {
                        icone.setImageResource(R.drawable.icon_mist);
                    }

                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat sdf = new SimpleDateFormat("EEEE dd/MM", Locale.FRANCE);
                    String formatted_date = sdf.format(calendar.getTime());

                    t4_date.setText(formatted_date);

                    double temp_int = Double.parseDouble(temp);
                    double centi = (temp_int - 32) /1.8000;
                    centi = Math.round(centi);
                    int i = (int)centi;
                    t1_temp.setText(String.valueOf(i));



                }catch(JSONException e)
                {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }
        );
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jor);


    }

    private View.OnClickListener retourListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent mainActivity = new Intent(ChoixActivity.this, MainActivity.class);
            startActivity(mainActivity);

        }
    };




}