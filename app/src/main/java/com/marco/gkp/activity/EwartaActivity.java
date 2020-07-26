package com.marco.gkp.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.marco.gkp.R;
import com.marco.gkp.adapter.EwartaAdapter;
import com.marco.gkp.adapter.JemaatAdapter;
import com.marco.gkp.adapter.KategorialAdapter;
import com.marco.gkp.model.CariJemaatModel;
import com.marco.gkp.model.EwartaModel;
import com.marco.gkp.model.KategorialModel;
import com.marco.gkp.util.api.ServerApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class EwartaActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    private static ProgressDialog mProgressDialog;
    ArrayList<EwartaModel> dataModelArrayList = new ArrayList<EwartaModel>();
    private EwartaAdapter rvAdapter;
    SwipeRefreshLayout swipe;
    private ImageView ivBack;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ewarta);

        recyclerView = findViewById(R.id.list_ewarta);
        ivBack = findViewById(R.id.img_back_ewarta);
        swipe = findViewById(R.id.swipe_refresh_layout);


        swipe.setOnRefreshListener(this);

        swipe.post(new Runnable() {
                       @Override
                       public void run() {
                           swipe.setRefreshing(true);
                           dataModelArrayList.clear();
                           fetchingJSON();
                       }
                   }
        );

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent tohome = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(tohome);
                finish();

            }
        });

    }

    private void fetchingJSON() {

        swipe.setRefreshing(true);

        showSimpleProgressDialog(this, "Loading...","Memuat Data Ewarta",false);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, ServerApi.URL_VIEW_ALL_EWARTA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("data ewarta", ">>" + response);

                        try {

                            removeSimpleProgressDialog();

                            JSONObject obj = new JSONObject(response);
                            if(obj.optString("status").equals("true")){

                                dataModelArrayList = new ArrayList<>();
                                JSONArray dataArray  = obj.getJSONArray("ewarta");

                                for (int i = 0; i < dataArray.length(); i++) {

                                    EwartaModel ewarta_model = new EwartaModel();
                                    JSONObject dataobj = dataArray.getJSONObject(i);

                                    ewarta_model.setFile(dataobj.getString("file"));
                                    ewarta_model.setKetfile(dataobj.getString("ket_file"));
                                    ewarta_model.setDatetime(dataobj.getString("tgl"));

                                    dataModelArrayList.add(ewarta_model);

                                }

                                setupRecycler();

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        swipe.setRefreshing(false);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("volley", "error : " + error.getMessage());
                        removeSimpleProgressDialog();
                        Toast.makeText(getApplicationContext(), "Koneksi Internet Bermasalah, Cek Kondisi Internet Anda ", Toast.LENGTH_LONG).show();
                    }
                });

        // request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(stringRequest);


    }

    private void setupRecycler(){

        rvAdapter = new EwartaAdapter(dataModelArrayList);
        recyclerView.setAdapter(rvAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));

    }

    public static void removeSimpleProgressDialog() {
        try {
            if (mProgressDialog != null) {
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                    mProgressDialog = null;
                }
            }
        } catch (IllegalArgumentException ie) {
            ie.printStackTrace();

        } catch (RuntimeException re) {
            re.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onRefresh() {
        dataModelArrayList.clear();
        rvAdapter.notifyDataSetChanged();
        fetchingJSON();
    }

    public static void showSimpleProgressDialog(Context context, String title,
                                                String msg, boolean isCancelable) {
        try {
            if (mProgressDialog == null) {
                mProgressDialog = ProgressDialog.show(context, title, msg);
                mProgressDialog.setCancelable(isCancelable);
            }

            if (!mProgressDialog.isShowing()) {
                mProgressDialog.show();
            }

        } catch (IllegalArgumentException ie) {
            ie.printStackTrace();
        } catch (RuntimeException re) {
            re.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {

        Intent tohome = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(tohome);
        finish();

        super.onBackPressed();
    }
}
