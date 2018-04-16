package com.example.stefan.myapplication;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DisplayList extends AppCompatActivity {

    Button btn;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    ArrayList<Item> items;
    String url = "https://raw.githubusercontent.com/danieloskarsson/mobile-coding-exercise/master/items.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_list);

        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        items = new ArrayList<>();

        new FetchData().execute();
    }


    private class FetchData extends AsyncTask<Void,Void,Void>{

        ProgressDialog progressDialog;

        protected void onPreExecute() {
            progressDialog = new ProgressDialog(DisplayList.this);
            progressDialog.setMessage("Loading data...");
            progressDialog.show();
        }

        protected Void doInBackground(Void... voids) {

            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            try {
                                int count = 0;
                                while(count < response.length()) {

                                    JSONObject jsonObject = response.getJSONObject(count);
                                    Item item = new Item(jsonObject.getString("title"),
                                            jsonObject.getString("description"),
                                            jsonObject.getString("image"));
                                    items.add(item);
                                    count++;
                                }

                                adapter = new ItemAdapter(DisplayList.this,items);
                                recyclerView.setAdapter(adapter);
                            } catch (JSONException e) {
                                Toast.makeText(DisplayList.this,"Problems while fetching data...",Toast.LENGTH_LONG).show();
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener(){
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(DisplayList.this,"Error while fetching data...",Toast.LENGTH_LONG).show();
                            error.printStackTrace();
                        }
                    });
            RequestQueue requestQueue = Volley.newRequestQueue(DisplayList.this);
            requestQueue.add(jsonArrayRequest);

            return null;
        }

        protected void onPostExecute(Void unused) {
            progressDialog.dismiss();
        }
    }
}
