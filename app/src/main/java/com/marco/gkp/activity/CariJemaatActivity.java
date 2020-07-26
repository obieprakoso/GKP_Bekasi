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
import com.marco.gkp.adapter.JemaatAdapter;
import com.marco.gkp.model.CariJemaatModel;
import com.marco.gkp.util.api.ServerApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CariJemaatActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    private static ProgressDialog mProgressDialog;
    ArrayList<CariJemaatModel> dataModelArrayList= new ArrayList<>();
    private JemaatAdapter rvAdapter;
    private ImageView ivBack;
    private TextView etCariJemaat;
    private RecyclerView recyclerView;
    private Button btnRefreshJemaatList;
    SwipeRefreshLayout swipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cari_jamaat);

        recyclerView = findViewById(R.id.rvjemaat);
        ivBack = findViewById(R.id.img_back_cari_jemaat);
        etCariJemaat = findViewById(R.id.et_search_jemaat);
        btnRefreshJemaatList = findViewById(R.id.btn_provider_refresh);
        swipe = findViewById(R.id.swipe_refresh_layout_cari_jemaat);

        swipe.setOnRefreshListener(this);

        swipe.post(new Runnable() {
                       @Override
                       public void run() {
                           dataModelArrayList.clear();
                           fetchingJSON();
                       }
                   }
        );

        etCariJemaat.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //providerAdapter.getFilter().filter(s);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //providerAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {
                rvAdapter.getFilter().filter(s);

            }
        });

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent tohome = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(tohome);
                finish();

            }
        });

        btnRefreshJemaatList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etCariJemaat.setText("");
            }
        });

    }

    private void fetchingJSON() {

        //showSimpleProgressDialog(this, "Loading...","Memuat Data Jemaat",false);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, ServerApi.URL_VIEW_ALL_JAMAAT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("data jemaat", ">>" + response);

                        try {

                            //removeSimpleProgressDialog();

                            JSONObject obj = new JSONObject(response);
                            if(obj.optString("status").equals("true")){

                                dataModelArrayList = new ArrayList<>();
                                JSONArray dataArray  = obj.getJSONArray("jemaat");

                                for (int i = 0; i < dataArray.length(); i++) {

                                    CariJemaatModel playerModel = new CariJemaatModel();
                                    JSONObject dataobj = dataArray.getJSONObject(i);

                                    playerModel.setName(dataobj.getString("nama"));
                                    playerModel.setNo_regis(dataobj.getString("no_regis"));
                                    playerModel.setJenis_kelamin(dataobj.getString("jenis_kelamin"));
                                    playerModel.setAlamat(dataobj.getString("alamat"));
                                    playerModel.setStatus(dataobj.getString("status"));

                                    dataModelArrayList.add(playerModel);

                                }
                                swipe.setRefreshing(false);
                                setupRecycler();

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
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

        rvAdapter = new JemaatAdapter(dataModelArrayList);
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

    @Override
    public void onRefresh() {
        dataModelArrayList.clear();
        rvAdapter.notifyDataSetChanged();
//        mShimmerViewContainer.showShimmerAdapter();
        fetchingJSON();
    }
}
