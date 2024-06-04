package edu.birzeit.www;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class userActivty extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.user_activtiy);
        String name=getIntent().getStringExtra("name");
        int image=getIntent().getIntExtra("image",0);
       TextView carname=findViewById(R.id.nametxt);
        ImageView img=findViewById(R.id.carimg);


        carname.setText(name);
        img.setImageResource(image);

    }
    public void backbtn(View view){
        Intent intent = new Intent(userActivty.this, MainActivity2.class );
        startActivity(intent);
    }
}