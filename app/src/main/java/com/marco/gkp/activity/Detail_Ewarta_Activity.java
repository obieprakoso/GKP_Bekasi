package com.marco.gkp.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.marco.gkp.R;
import com.marco.gkp.app.AppController;
import com.marco.gkp.model.KategorialModel;
import com.marco.gkp.util.api.ServerApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Detail_Ewarta_Activity extends AppCompatActivity {

    TextView tv_file;
    String id_ewarta;

    private static final String TAG = KategorialModel.class.getSimpleName();

    public static final String TAG_ID = "id";
    public static final String TAG_FILE = "file";

    private static final String url_detail = ServerApi.URL_VIEW_DETAIL_EWARTA;
    String tag_json_obj = "json_obj_req";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail__ewarta_);

        tv_file = findViewById(R.id.tv_file);

        id_ewarta = getIntent().getStringExtra(TAG_ID);

        callDetailNews(id_ewarta);

    }

    private void callDetailNews(final String id) {

        StringRequest strReq = new StringRequest(Request.Method.POST, url_detail, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e(TAG, "Response " + response.toString());

                try {
                    JSONObject obj = new JSONObject(response);

                    String file = obj.getString(TAG_FILE);


                    tv_file.setText(file);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Detail News Error: " + error.getMessage());
                Toast.makeText(Detail_Ewarta_Activity.this,
                        error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to post url
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", id);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
    }

    @Override
    public void onBackPressed() {
        Detail_Ewarta_Activity.super.onBackPressed();
        finish();
    }

    @Override
    protected void onResume() {

        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(tv_file.getText().toString())));
        } catch (Exception e) {
            e.getStackTrace();
        }

        super.onResume();
    }
}
