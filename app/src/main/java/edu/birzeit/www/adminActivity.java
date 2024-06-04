package edu.birzeit.www;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class adminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.admin_activity);


        String name=getIntent().getStringExtra("name");
        int image=getIntent().getIntExtra("image",0);
    TextView namecar=findViewById(R.id.nametxt);
    ImageView img=findViewById(R.id.carimg);
    namecar.setText(name);
    img.setImageResource(image);

    }
    public void backbtn(View view){
        Intent intent = new Intent(adminActivity.this, MainActivity2.class );
        startActivity(intent);
    }
    public void updatebtn(View view){
        Intent intent = new Intent(adminActivity.this, ApdateActivity.class );
        startActivity(intent);
    }

}