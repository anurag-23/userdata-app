package com.anurag23.userdata;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<UserModel> userList = new ArrayList <UserModel>();
    CustomAdapter adapter;
    Context context;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar)findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.main_toolbar_title);

        context = this;

        RecyclerView usersRecyclerView = (RecyclerView)findViewById(R.id.users_recycler_view);
        adapter = new CustomAdapter(context, userList);
        usersRecyclerView.setAdapter(adapter);
        usersRecyclerView.setLayoutManager(new LinearLayoutManager(context));

        dialog = new ProgressDialog(MainActivity.this);
        dialog.setMessage("Please Wait...");
        dialog.show();

        new JSONTask().execute("http://jsonplaceholder.typicode.com/users");
    }


    public class JSONTask extends AsyncTask<String, Void, Boolean>{

        @Override
        protected Boolean doInBackground(String... params) {

            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL (params[0]);
                connection = (HttpURLConnection)url.openConnection();
                connection.connect();

                InputStream inputStream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(inputStream));

                StringBuffer buffer = new StringBuffer();
                String line;

                while ((line = reader.readLine())!=null)
                    buffer.append(line);

                String json = buffer.toString();

                JSONArray jsonArray = new JSONArray(json);

                for (int i=0; i<jsonArray.length(); i++){
                    UserModel user = new UserModel();
                    JSONObject userObject = jsonArray.getJSONObject(i);

                    user.setName(userObject.getString("name"));
                    user.setUserName(userObject.getString("username"));
                    user.setEmail(userObject.getString("email"));
                    user.setPhone(userObject.getString("phone"));
                    user.setWebsite(userObject.getString("website"));

                    JSONObject addressObject = userObject.getJSONObject("address");
                    user.setaStreet(addressObject.getString("street"));
                    user.setaSuite(addressObject.getString("suite"));
                    user.setaCity(addressObject.getString("city"));
                    user.setaZip(addressObject.getString("zipcode"));

                    userList.add(user);
                }

                return true;


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (connection!=null)
                    connection.disconnect();
                try {
                    if (reader!=null)
                        reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


            return false;
        }

        @Override
        protected void onPostExecute(Boolean jsonSuccess) {
            super.onPostExecute(jsonSuccess);

            dialog.dismiss();

            if (jsonSuccess == true) {
                adapter.notifyDataSetChanged();
            }
            else
                Toast.makeText(context, "Unable to fetch data!", Toast.LENGTH_LONG).show();

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.menu_refresh:{
                dialog.show();
                userList.clear();
                new JSONTask().execute("http://jsonplaceholder.typicode.com/users");
                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }
}
