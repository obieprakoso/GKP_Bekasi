package com.marco.gkp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.marco.gkp.R;
import com.marco.gkp.adapter.JemaatAdapter;
//import com.marco.gkp.adapter.JemaatAdapterListView;
import com.marco.gkp.adapter.KategorialAdapter;
import com.marco.gkp.app.AppController;
import com.marco.gkp.model.CariJemaatModel;
import com.marco.gkp.model.KategorialModel;
import com.marco.gkp.util.api.ServerApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
public class CariJemaatActivityListView extends AppCompatActivity {
//public class CariJemaatActivityListView extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

//    ListView list;
//    SwipeRefreshLayout swipe;
//    public static List<CariJemaatModel> jemaatList = new ArrayList<CariJemaatModel>();
//    ImageView Back;
//    SearchView editsearch;
//
//
//    private static final String TAG = CariJemaatActivityListView.class.getSimpleName();
//
//    private static String url_list = ServerApi.URL_VIEW_ALL_JAMAAT;
//
//    private int offSet = 0;
//
//    int no;
//
//    JemaatAdapterListView adapter;
//
//    public static final String TAG_NO = "no";
//    public static final String TAG_ID = "id";
//    public static final String TAG_NOREGIS = "no_regis";
//    public static final String TAG_NAMA = "nama";
//    public static final String TAG_ALAMAT = "alamat";
//    public static final String TAG_STATUS = "status";
//    public static final String TAG_KELAMIN = "jenis_kelamin";
//
//    Handler handler;
//    Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cari_jamaat);

//        swipe = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout_cari_jemaat);
//        list = (ListView) findViewById(R.id.listjemaat);
//        Back = (ImageView) findViewById(R.id.img_back_cari_jemaat);
//
//        editsearch =findViewById(R.id.search);
//
////        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
////
////            @Override
////            public void onItemClick(AdapterView<?> parent, View view,
////                                    int position, long id) {
////                // TODO Auto-generated method stub
////                Intent intent = new Intent(CariJemaatActivityListView.this, DetailKategorialActivity.class);
////                intent.putExtra(TAG_ID, jemaatList.get(position).getId());
////                startActivity(intent);
////            }
////        });
//
//        editsearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String text) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//
////                adapter.getFilter().filter(newText);
//
//                return false;
//            }
//        });
//
//        adapter = new JemaatAdapterListView(CariJemaatActivityListView.this, jemaatList);
//        list.setAdapter(adapter);
//
//        swipe.setOnRefreshListener(this);
//
//        swipe.post(new Runnable() {
//                       @Override
//                       public void run() {
//                           swipe.setRefreshing(true);
//                           jemaatList.clear();
//                           adapter.notifyDataSetChanged();
//                           callNews(0);
//                       }
//                   }
//        );
//
//        Back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent intent = new Intent(CariJemaatActivityListView.this, HomeActivity.class);
//                startActivity(intent);
//                finish();
//
//            }
//        });
//
//
//        list.setOnScrollListener(new AbsListView.OnScrollListener() {
//
//            private int currentVisibleItemCount;
//            private int currentScrollState;
//            private int currentFirstVisibleItem;
//            private int totalItem;
//
//            @Override
//            public void onScrollStateChanged(AbsListView view, int scrollState) {
//                this.currentScrollState = scrollState;
//                this.isScrollCompleted();
//            }
//
//            @Override
//            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//                this.currentFirstVisibleItem = firstVisibleItem;
//                this.currentVisibleItemCount = visibleItemCount;
//                this.totalItem = totalItemCount;
//            }
//
//            private void isScrollCompleted() {
//                if (totalItem - currentFirstVisibleItem == currentVisibleItemCount
//                        && this.currentScrollState == SCROLL_STATE_IDLE) {
//
//                    swipe.setRefreshing(true);
//                    handler = new Handler();
//
//                    runnable = new Runnable() {
//                        public void run() {
//                            callNews(offSet);
//                        }
//                    };
//
//                    handler.postDelayed(runnable, 3000);
//                }
//            }
//
//        });
//
//    }
//
//    @Override
//    public void onRefresh() {
//        jemaatList.clear();
//        adapter.notifyDataSetChanged();
//        callNews(0);
//    }
//
//    private void callNews(int page) {
//
//        swipe.setRefreshing(true);
//
//        // Creating volley request obj
//        JsonArrayRequest arrReq = new JsonArrayRequest(url_list + page,
//                new Response.Listener<JSONArray>() {
//                    @Override
//                    public void onResponse(JSONArray response) {
//                        Log.e(TAG, response.toString());
//
//                        if (response.length() > 0) {
//                            // Parsing json
//                            for (int i = 0; i < response.length(); i++) {
//                                try {
//
//                                    JSONObject obj = response.getJSONObject(i);
//                                    CariJemaatModel news = new CariJemaatModel();
//
//                                    no = obj.getInt(TAG_NO);
//
//                                    news.setId(obj.getString(TAG_ID));
//                                    news.setName(obj.getString(TAG_NAMA));
//                                    news.setNo_regis(obj.getString(TAG_NOREGIS));
//                                    news.setAlamat(obj.getString(TAG_ALAMAT));
//                                    news.setStatus(obj.getString(TAG_STATUS));
//                                    news.setJenis_kelamin(obj.getString(TAG_KELAMIN));
//
//                                    // adding news to news array
//                                    jemaatList.add(news);
//
//                                    if (no > offSet)
//                                        offSet = no;
//
//                                    Log.e(TAG, "offSet " + offSet);
//
//                                } catch (JSONException e) {
//                                    Log.e(TAG, "JSON Parsing error: " + e.getMessage());
//                                }
//
//                                // notifying list adapter about data changes
//                                // so that it renders the list view with updated data
//                                adapter.notifyDataSetChanged();
//                            }
//                        }
//                        swipe.setRefreshing(false);
//                    }
//
//                }, new Response.ErrorListener() {
//
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                VolleyLog.e(TAG, "Error: " + error.getMessage());
//                swipe.setRefreshing(false);
//            }
//        });
//
//        // Adding request to request queue
//        AppController.getInstance().addToRequestQueue(arrReq);
//    }
//
//    @Override
//    public void onBackPressed() {
//        Intent intent = new Intent(CariJemaatActivityListView.this, HomeActivity.class);
//        startActivity(intent);
//        finish();
//        super.onBackPressed();
//    }
    }
}
