package varma.com.myhome;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import varma.com.helper.SQLiteHandler;


public class ConversationForum extends AppCompatActivity {

    ListView listview;
    String myJSON;
    JSONArray records = null;
    private static final String TAG_RESULTS="result";
    ArrayList<HashMap<String, String>> recordAL;
    Toolbar toolbar;
    public String Id_Query="";
    public EditText DisTitle;
    public EditText DisDesc;
    String DisTitle_str="";
    String DisDesc_str="";
    public String ShortName;
    public String Block;
    public String Flat;
    public String uid;
    public SQLiteHandler db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation_forum);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("ViewReply Discussion");

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }


        db = new SQLiteHandler(getApplicationContext());
        HashMap<String, String> user = db.getUserDetails();
        ShortName = user.get("ShortName");
        Block = user.get("Block");
        Flat = user.get("Flat");
        uid = user.get("uid");




        Intent intent = getIntent();
        HashMap<String, String> hashMap = (HashMap<String, String>)intent.getSerializableExtra("map");
        Toast.makeText(ConversationForum.this, "ClickedLetter:" +  hashMap.get("Id"), Toast.LENGTH_LONG).show();

        Id_Query = hashMap.get("Id");
        DisTitle_str =  hashMap.get("Title");

       //  Log.v("HashMapTest", hashMap.get("key"));
        DisDesc = (EditText) findViewById(R.id.editText);
        recordAL = new ArrayList<HashMap<String,String>>();
        getData();


        listview = (ListView) findViewById(R.id.listview);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l){
                HashMap<String, String> clickedItem = recordAL.get(i);
                Toast.makeText(ConversationForum.this, "ClickedLetter:" +  "YAHOO", Toast.LENGTH_LONG).show();
            }
        });
    } // onCreate End

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home)
            finish();
            return super.onOptionsItemSelected(item);
    }

    public void getData(){
        class GetDataJSON extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {
                DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
           //     String myURL = "http://192.168.3.2/mhConversations.php";
                String myURL = "http://192.168.3.2/mhDiscussionViewReply.php?ID="+Id_Query;
                HttpPost httppost = new HttpPost(myURL);
                httppost.setHeader("Content-type", "application/json");
                InputStream inputStream = null;
                String Id_Query_Te = Id_Query;
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

                // String id = c.getString("id");

                HashMap<String,String> recordHM = new HashMap<String,String>();
          /*      recordHM.put("Id",Id);
                recordHM.put("Name",Name);
                recordHM.put("Block",Block);  */
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
                    ConversationForum.this, recordAL, R.layout.list_item,
                    new String[]{"Name","Block","Date","Title","Description","Likes","Replies","Photo"},
                    new int[]{ R.id.Name, R.id.Block,R.id.Date, R.id.Title, R.id.Description, R.id.Likes,R.id.Replies, R.id.Photo}
            );

            listview.setAdapter(listViewAdapter);

        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void insert(View view){
        String name = "AAA";
      //  DisTitle_str = DisTitle.getText().toString();
        DisDesc_str = DisDesc.getText().toString();
        insertToDatabase(name);
    }

    private void insertToDatabase(String name){
        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            // String name = editTextName.getText().toString();
            String name = "TEMP";
            @Override
            protected String doInBackground(String... params) {
                //    String paramUsername = params[0];
                //    String paramAddress = params[1];
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                //    nameValuePairs.add(new BasicNameValuePair("name", name));
                //   nameValuePairs.add(new BasicNameValuePair("Id", uid));
                nameValuePairs.add(new BasicNameValuePair("Name", ShortName)); // Completed
                nameValuePairs.add(new BasicNameValuePair("Block", Block));    // Completed
                nameValuePairs.add(new BasicNameValuePair("Flat", Flat));      // Completed
                nameValuePairs.add(new BasicNameValuePair("Type", "Master"));
                nameValuePairs.add(new BasicNameValuePair("Date", name));
                nameValuePairs.add(new BasicNameValuePair("Time", name));
                nameValuePairs.add(new BasicNameValuePair("Title", DisTitle_str)); // Completed
                nameValuePairs.add(new BasicNameValuePair("Description", DisDesc_str)); // Completed
                nameValuePairs.add(new BasicNameValuePair("Field1", uid));      // Completed
                nameValuePairs.add(new BasicNameValuePair("Field2", Id_Query));
                nameValuePairs.add(new BasicNameValuePair("Field3", name));
                nameValuePairs.add(new BasicNameValuePair("Field4", name));
                nameValuePairs.add(new BasicNameValuePair("Field5", name));
                nameValuePairs.add(new BasicNameValuePair("Field6", name));
                nameValuePairs.add(new BasicNameValuePair("Field7", name));
            try {
                    HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost("http://192.168.3.2/mhConversationsUpdate.php");
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    HttpResponse response = httpClient.execute(httpPost);
                    HttpEntity entity = response.getEntity();
                 }
            catch (ClientProtocolException e) {
                    }
            catch (IOException e) {
                    }
              return "success";
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                recordAL = new ArrayList<HashMap<String,String>>();
                getData();
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
                //  TextView textViewResult = (TextView) findViewById(R.id.textViewResult);
                // textViewResult.setText("Inserted");
            }
        }
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute(name);
    }

} // ConversationForum End
