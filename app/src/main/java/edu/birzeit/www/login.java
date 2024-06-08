package edu.birzeit.www;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class login extends AppCompatActivity {
//EditText editTextUsername = findViewById(R.id.editTextUsername);
    EditText editTextEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        editTextEmail = findViewById(R.id.editTextEmail);



        findViewById(R.id.subt).setOnClickListener(v -> {
            Intent intent = new Intent(this, signup.class);
            startActivity(intent);
        });
        findViewById(R.id.libt).setOnClickListener(v -> { //login btn

            //Shahd Update starts
            SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("email", editTextEmail.getText().toString());
            editor.apply();
            //Shahd Update ends


                Intent intent = new Intent(this, MainActivity2.class);
                startActivity(intent);
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    ////////////////////////////////////////////////////////////////////////


}