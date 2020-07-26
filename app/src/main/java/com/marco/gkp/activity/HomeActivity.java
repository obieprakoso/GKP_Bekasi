package com.marco.gkp.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.marco.gkp.R;
import com.marco.gkp.util.sharedpreferences.SharedPrefManager;

import java.util.HashMap;

import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity {

    SharedPrefManager sharedPrefManager;
    String getname, getno_regis;
    TextView tvName, tvNoRegis;
    CardView cvCari, cvKatagorial, cvNonKatagorial, cvWarta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        initialization();

        sharedPrefManager = new SharedPrefManager(this);
        HashMap<String, String> user = sharedPrefManager.getUserDetail();
        getname = user.get(sharedPrefManager.NAME);
        getno_regis = user.get(sharedPrefManager.NO_REGIS);
//        sharedPrefManager.checkLogin();

        tvName.setText(getname);
        tvNoRegis.setText(getno_regis);

        cvCari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent tocarijamaat = new Intent(getApplicationContext(), CariJemaatActivity.class);
                startActivity(tocarijamaat);
                finish();

            }
        });

        cvKatagorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent tokategorial = new Intent(getApplicationContext(), KategorialActivity.class);
                startActivity(tokategorial);
                finish();

            }
        });

        cvNonKatagorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent tononkategorial = new Intent(getApplicationContext(), NonKategorialActivity.class);
                startActivity(tononkategorial);
                finish();

            }
        });

        cvWarta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent toewarta = new Intent(getApplicationContext(), EwartaActivity.class);
                startActivity(toewarta);
                finish();

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.tentang) {

            Intent totentang = new Intent(getApplicationContext(), TentangActivity.class);
            startActivity(totentang);
            finish();

        } else if (item.getItemId() == R.id.keluar) {

            AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
            builder.setMessage("Apa Anda Yakin Ingin Keluar Aplikasi ?")
                    .setCancelable(false)
                    .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            sharedPrefManager.logout();
                            startActivity(new Intent(HomeActivity.this, LoginActivity.class)
                                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                            finish();
                        }
                    })
                    .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();

                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();

        }

        return true;
    }
    private void initialization() {
        //...............................................DEKLARASI
        tvName = findViewById(R.id.nama);
        tvNoRegis = findViewById(R.id.noregis);
        cvCari = findViewById(R.id.cv_cari_card);
        cvKatagorial = findViewById(R.id.cv_katagorial_card);
        cvNonKatagorial = findViewById(R.id.cv_nonkatagorial_card);
        cvWarta = findViewById(R.id.cv_warta_card);
    }

}
