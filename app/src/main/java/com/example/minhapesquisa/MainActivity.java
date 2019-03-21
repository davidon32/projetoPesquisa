package com.example.minhapesquisa;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private Button buttonPesquisar;
    private EditText editView;
    private ImageView ivPoster;
    private TextView tvYear,tvActors,tvDirector,tvCountry,tvLenguage,tvPlot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editView = findViewById(R.id.editText);
        tvYear = findViewById(R.id.tvYear);
        tvActors = findViewById(R.id.tvActors);
        tvDirector = findViewById(R.id.tvDirectors);
        tvCountry = findViewById(R.id.tvCountry);
        tvLenguage = findViewById(R.id.tvLenguage);
        tvPlot = findViewById(R.id.tvPlot);
        ivPoster = findViewById(R.id.ivPoster);
    }

    public void search(View view){

        String mNmae = editView.getText().toString();
        if(mNmae.isEmpty()){

            editView.setError("por favor coloque um nome");
            return;

        }

        String url = "http://www.omdbapi.com/?t=" + mNmae + "&plot=full&apikey=87ee7766";

        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest request = new StringRequest(Request.Method.GET, url,


                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //tvYear.setText(response);
                        //Log.d("MOVIE", response);
                        try {
                            JSONObject movie = new JSONObject(response);

                            String result = movie.getString("Response");

                            if( result.equals("True")){
                                Toast.makeText(MainActivity.this, "Encontrado", Toast.LENGTH_SHORT).show();
                                String year = movie.getString("Year");
                                tvYear.setText("Year: " + year);

                                String director = movie.getString("Director");
                                tvDirector.setText("Director" + director);

                                String actors = movie.getString("Actors");
                                tvActors.setText("Actors: " + actors);

                                String country = movie.getString("Country");
                                tvCountry.setText("Country: " + country);

                                String language = movie.getString("Language");
                                tvLenguage.setText("Language: " + language);

                                String plot = movie.getString("Plot");
                                tvPlot.setText("Plot: " + plot);

                                String posterUrl = movie.getString("Poster");

                                if(posterUrl.equals("N/A")){

                                }else{
                                    Picasso.get().load(posterUrl).into(ivPoster);
                                }
                            }
                            else {
                                Toast.makeText(MainActivity.this, "Filme nao encontrado", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );

        queue.add(request);

    }

}
