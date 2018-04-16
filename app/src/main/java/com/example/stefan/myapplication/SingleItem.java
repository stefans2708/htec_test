package com.example.stefan.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class SingleItem extends AppCompatActivity {

    TextView textViewTitle, textViewDescription;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_item);

        textViewTitle = (TextView)findViewById(R.id.txtTitle);
        textViewDescription = (TextView)findViewById(R.id.txtDesc);
        imageView = (ImageView)findViewById(R.id.imageView);

        getIncomintIntent();
    }

    private void getIncomintIntent() {
        if(getIntent().hasExtra("title") &&
                getIntent().hasExtra("description") &&
                getIntent().hasExtra("image"))
        {
            textViewTitle.setText(getIntent().getStringExtra("title"));
            textViewDescription.setText(getIntent().getStringExtra("description"));
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Picasso.get()
                .load(getIntent().getStringExtra("image"))
                .into(imageView);
    }
}
