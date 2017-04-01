package com.example.wxx.apostil;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, SwipeRefreshLayout.OnRefreshListener {
    private RecyclerView mRecyclerView;
    private ArrayList<HashMap<String,String>> mapList;
    private SimpleAdapter myAdapter;
    private SwipeRefreshLayout refreshLayout;
    private boolean isRefresh;
    private Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



        /**
         * 初始化RecyclerView
         */
        initView();
        /**
         * 初始化mapList
         */
        initData();


    }

    private void initData() {
        mapList =new ArrayList<HashMap<String, String>>();
        jsonVolleyRequest();
    }

    private void jsonVolleyRequest() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, MyConstants.BASE_URL ,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONArray jsonArray;
                String res = response.optString("Post");
                try {
                    jsonArray=new JSONArray(res);
                } catch (JSONException e) {
                    jsonArray=new JSONArray();
                    e.printStackTrace();
                }
                if (!mapList.isEmpty()){
                    mapList.clear();
                }
                for(int i=0;i<jsonArray.length();i++){
                    JSONObject jsonObject=jsonArray.optJSONObject(i);
                    HashMap<String,String> map=new HashMap<String, String>();
                    map.put(MyConstants.TITLE,jsonObject.optString(MyConstants.TITLE));
                    map.put(MyConstants.DATE,jsonObject.optString(MyConstants.DATE));
                    map.put(MyConstants.CONTENT,jsonObject.optString(MyConstants.CONTENT));
                    map.put(MyConstants.ID,jsonObject.optString(MyConstants.ID));
                    mapList.add(map);
                }
                myAdapter=new SimpleAdapter(getApplicationContext(),mapList);
                mRecyclerView.setAdapter(myAdapter);
                myAdapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "No Response!", Toast.LENGTH_SHORT).show();
            }
        });
        MySingleton.getInstance(this.getApplicationContext()).addToRequsetQueue(jsonObjectRequest);
    }

    private void initView() {
        /**
         * 初始化下拉刷新控件
         */
        isRefresh=false;
        refreshLayout= (SwipeRefreshLayout) findViewById(R.id.swipLayout);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_red_light, android.R.color.holo_orange_light,
                android.R.color.holo_green_light);
        /**
         *初始化RecyclerView
         */
        mRecyclerView=(RecyclerView) findViewById(R.id.mRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setHasFixedSize(true);
        myAdapter=new SimpleAdapter(this,mapList);
        mRecyclerView.setAdapter(myAdapter);

        /**
         * 新增button
         */
        addButton= (Button) findViewById(R.id.addBtn);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(),EditActivity.class);
                i.putExtra(MyConstants.REQUSTFROM,MyConstants.MAIN);
                startActivity(i);

            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_login) {

        } else if (id == R.id.nav_settings) {

        } else if (id == R.id.nav_share) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public void onRefresh() {
        if(!isRefresh){
            isRefresh=true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    refreshLayout.setRefreshing(false);
                    jsonVolleyRequest();
                    isRefresh=false;
                }
            },3000);
        }
    }
}
