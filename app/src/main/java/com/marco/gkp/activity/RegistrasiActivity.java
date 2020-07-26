package com.marco.gkp.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.marco.gkp.R;

public class RegistrasiActivity extends AppCompatActivity {

    private Button Btn_kebali;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrasi);

        Btn_kebali = findViewById(R.id.btn_kembali);

        Btn_kebali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent a = new Intent(RegistrasiActivity.this, LoginActivity.class);
                startActivity(a);

            }
        });
    }
}
