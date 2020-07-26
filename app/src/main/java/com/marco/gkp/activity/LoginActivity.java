package com.marco.gkp.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.marco.gkp.R;
import com.marco.gkp.util.api.ServerApi;
import com.marco.gkp.util.sharedpreferences.SharedPrefManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private SharedPrefManager sharedprefmanager;
    private ConnectivityManager connectivityManager;

    private EditText etNoRegis, etPassword;
    private CardView cvRegistrasi, cvLogin;
    private TextInputLayout tilNoRegis, tilPassword;
    private TextView btnLupaPassword;
    private ProgressBar loading;
    private LinearLayout llCard;

    int checkNoRegis = 0, checkPassword = 0;
    private String TAG = "Errornya Adalah";

    String url = "http://192.168.43.29/gkp_bekasi_api/login.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //...........................................................SET PREFERENCE
        sharedprefmanager = new SharedPrefManager(this);
        sharedprefmanager.checkLogin();       //CEK LOGIN

        //....................................................CHECK KONEKSI INTERNET DAN WIFI
//        initConnection();

        initialization();

//        ...................................................TEXT LISTENER CEK ERROR
        etNoRegis.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                tilNoRegis.setError(null);
                tilNoRegis.setHintTextAppearance(R.style.MyCorrectText);

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                checkNoRegis = s.length();

            }
        });

        etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                tilPassword.setError(null);
                tilPassword.setHintTextAppearance(R.style.MyCorrectText);

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                checkPassword = s.length();

            }
        });

        //................................................BUTTON LUPA PASSWORD

        //...........................TOMBOL LOGIN
        cvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String no_regis = etNoRegis.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                if (checkNoRegis > 0 && checkPassword > 0) {

                    if (!Patterns.PHONE.matcher(no_regis).matches()) {
                        tilNoRegis.setError("Masukan No Regis Yang Valid");
                        tilNoRegis.setHintTextAppearance(R.style.MyErrorText);

                    } else {
                        initLogin(no_regis, password);
                        tilNoRegis.setError(null);
                        tilPassword.setError(null);

                    }

                } else {

                    if (checkNoRegis == 0) {
                        tilNoRegis.setError("Email Tidak Boleh Kosong!");
                        tilNoRegis.setHintTextAppearance(R.style.MyErrorText);

                    }

                    if (checkPassword == 0) {
                        tilPassword.setError("Kata Sandi Tidak Boleh Kosong!");
                        tilPassword.setHintTextAppearance(R.style.MyErrorText);

                    }

                }

            }
        });

        cvRegistrasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toRegister = new Intent(getApplicationContext(), RegistrasiActivity.class);
                startActivity(toRegister);

            }
        });
    }

    private void initialization() {
        //...............................................DEKLARASI
        etNoRegis = findViewById(R.id.et_noregis_login);
        etPassword = findViewById(R.id.et_password_login);
        tilNoRegis = findViewById(R.id.txtin_noregis_login);
        tilPassword = findViewById(R.id.txtin_password_login);
        cvRegistrasi = findViewById(R.id.cvregis);
        cvLogin = findViewById(R.id.cvlogin);
        loading = findViewById(R.id.loading);
        btnLupaPassword = findViewById(R.id.tv_btn_lupa_password);
        llCard = findViewById(R.id.llcard);
    }

    //.....................................................CHECK INTERNET AND WIFI
//    private void initConnection() {
//
//        connectivityManager = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
//        NetworkInfo wifiNetwork = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
//        NetworkInfo mobileNetwork = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
//
//        if ((wifiNetwork != null && wifiNetwork.isConnected()) || (mobileNetwork != null && mobileNetwork.isConnected())) {
//
//        } else {
//
//            Toast.makeText(getApplicationContext(), "Koneksi Internet Bermasalah, Cek Kondisi Internet Anda", Toast.LENGTH_LONG).show();
//
//        }
//    }

    private void initLogin(final String no_regis, final String password) {
        loading.setVisibility(View.VISIBLE);
        llCard.setVisibility(View.GONE);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerApi.URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("login");

                            if (success.equals("1")) {

                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject object = jsonArray.getJSONObject(i);

                                    String name = object.getString("name").trim();
                                    String no_regis = object.getString("no_regis").trim();
                                    String alamat = object.getString("alamat").trim();

                                    sharedprefmanager.createSession(name, no_regis, alamat);

                                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                    intent.putExtra("name", name);
                                    intent.putExtra("no_regis", no_regis);
                                    startActivity(intent);
                                    finish();

                                    loading.setVisibility(View.GONE);


                                }

                            }else {
                                Toast.makeText(LoginActivity.this, "Password atau Email salah", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            loading.setVisibility(View.GONE);
                            llCard.setVisibility(View.VISIBLE);
                            Toast.makeText(LoginActivity.this, "Password atau Email salah", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loading.setVisibility(View.GONE);
                        llCard.setVisibility(View.VISIBLE);
                        Toast.makeText(LoginActivity.this, "Tidak ada jaringan", Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("no_regis", no_regis);
                params.put("password", password);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

}
