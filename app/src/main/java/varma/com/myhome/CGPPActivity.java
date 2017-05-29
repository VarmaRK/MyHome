package varma.com.myhome;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import varma.com.helper.SQLiteHandler;
import varma.com.helper.SessionManager;

public class CGPPActivity extends AppCompatActivity {
    ListView listview;
    String myJSON;
    JSONArray records = null;
    private static final String TAG_RESULTS="result";
    ArrayList<HashMap<String, String>> recordAL;
    Toolbar toolbar;
    private SessionManager session;
    private SQLiteHandler db;
    String DisGRPID = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cgpp);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Conversations");

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        Intent intent = getIntent();
        Bundle intentBundle = getIntent().getExtras();
        if(intentBundle != null) {
            DisGRPID = intentBundle.getString("DisGRPID", "");
        }

        recordAL = new ArrayList<HashMap<String,String>>();
        getData();
        listview = (ListView) findViewById(R.id.listview);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l){
                HashMap<String, String> clickedItem = recordAL.get(i);
                Toast.makeText(CGPPActivity.this, "ClickedLetter:" +  i, Toast.LENGTH_LONG).show();
                Intent in = new Intent(CGPPActivity.this.getApplicationContext(),ConversationForum.class);
                in.putExtra("map", clickedItem);
                in.putExtra("DisGRPID", DisGRPID);
                startActivity(in);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main,menu);

        MenuItem NotificationVisible = menu.findItem(R.id.action_notification);
        NotificationVisible.setVisible(false);

        MenuItem AccountVisible = menu.findItem(R.id.action_account);
        AccountVisible.setVisible(false);

        MenuItem SubmitVisible = menu.findItem(R.id.action_submit);
        SubmitVisible.setVisible(false);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == android.R.id.home){
            finish();
            return super.onOptionsItemSelected(item);
        }

        int res_id=item.getItemId();
        if(res_id == R.id.action_notification){
            Toast.makeText(getApplicationContext(), "You selected notification", Toast.LENGTH_SHORT).show();
        }
        if(res_id == R.id.action_account){
            Toast.makeText(getApplicationContext(), "You selected account !!!", Toast.LENGTH_SHORT).show();
            session = new SessionManager(getApplicationContext());
            db = new SQLiteHandler(getApplicationContext());
            session.setLogin(false);
            db.deleteUsers();
            // Launching the login activity
            Intent intent = new Intent(CGPPActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
        return true;
    }

    public void getData(){
        class GetDataJSON extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {
                DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());

         //       String myURL = "http://192.168.3.2/mhConversations.php?ID="+"'+DisGRPID+'";
                String myURL = "http://192.168.3.2/mhConversations.php?DisGRPID="+DisGRPID;

                HttpPost httppost = new HttpPost(myURL);
                httppost.setHeader("Content-type", "application/json");
                InputStream inputStream = null;
                String result = null;
                try {
                    HttpResponse response = httpclient.execute(httppost);
                    HttpEntity entity = response.getEntity();

                    inputStream = entity.getContent();
                    // json is UTF-8 by default
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
                    StringBuilder sb = new StringBuilder();

                    String line = null;
                    while ((line = reader.readLine()) != null)
                    {
                        sb.append(line + "\n");
                    }
                    result = sb.toString();
                } catch (Exception e) {
                    // Oops
                }
                finally {
                    try{if(inputStream != null)inputStream.close();}catch(Exception squish){}
                }
                return result;
            }
            @Override
            protected void onPostExecute(String result){
                myJSON=result;
                showList();
            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute();
    }
    protected void showList(){
        try {
            JSONObject jsonObj = new JSONObject(myJSON);
            records = jsonObj.getJSONArray(TAG_RESULTS);

            for(int i=0;i<records.length();i++){
                JSONObject c = records.getJSONObject(i);
              /*  String Id = c.getString("Id");
                String Name = c.getString("Name");
                String Block = c.getString("Block");
                // String id = c.getString("id");
                */
                String Id = c.getString("Id");
                String Name = c.getString("Name");
                String Block = c.getString("Block");
                String Flat = c.getString("Flat");
                String Date = c.getString("Date");
                String Time = c.getString("Time");
                String Title = c.getString("Title");
                String Description = c.getString("Description");
                String Likes = c.getString("Likes");
                String Replies = c.getString("Replies");
                String Photo = c.getString("Photo");

                HashMap<String,String> recordHM = new HashMap<String,String>();
                /*recordHM.put("Id",Id);
                recordHM.put("Name",Name);
                recordHM.put("Block",Block);*/

                recordHM.put("Id",Id);
                recordHM.put("Name",Name);
                recordHM.put("Block",Block);
                recordHM.put("Flat",Flat);
                recordHM.put("Date",Date);
                recordHM.put("Time",Time);
                recordHM.put("Title",Title);
                recordHM.put("Description",Description);
                recordHM.put("Likes",Likes);
                recordHM.put("Replies",Replies);
                recordHM.put("Photo",Photo);

                //  recordHM.put("images",images);

                recordAL.add(recordHM);
            }

            // ArrayAdapter<String> listViewAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,menuItems);


            ListAdapter listViewAdapter = new SimpleAdapter(
                    CGPPActivity.this, recordAL, R.layout.list_item,
            //        new String[]{"Id","Name","Block"},
            //        new int[]{R.id.id, R.id.name, R.id.address}

   new String[]{"Name","Block","Date","Title","Description","Likes","Replies","Photo"},
   new int[]{ R.id.Name, R.id.Block,R.id.Date, R.id.Title, R.id.Description, R.id.Likes,R.id.Replies, R.id.Photo}


            );

            listview.setAdapter(listViewAdapter);

        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void floatClick(View v) {
        Context context = getApplicationContext();
        CharSequence text = "Hello toast!";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
        Intent in = new Intent(CGPPActivity.this.getApplicationContext(),AddDiscussionActivity.class);
        in.putExtra("DisGRPID", DisGRPID);
        startActivity(in);

    }
}
