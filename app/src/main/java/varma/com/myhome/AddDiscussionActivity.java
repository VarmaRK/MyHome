package varma.com.myhome;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import varma.com.helper.SQLiteHandler;

public class AddDiscussionActivity extends AppCompatActivity {
    Toolbar toolbar;
    public EditText DisTitle;
    public EditText DisDesc;
    String DisTitle_str="";
    String DisDesc_str="";
    public String ShortName;
    public String Block;
    public String Flat;
    public String uid;
    public SQLiteHandler db;
    String DisGRPID="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_discussion);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("NewDiscussion");

        // Back Navigation
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        Intent intent = getIntent();
        Bundle intentBundle = getIntent().getExtras();
        if(intentBundle != null) {
            DisGRPID = intentBundle.getString("DisGRPID", "");
        }

        DisTitle = (EditText) findViewById(R.id.DisTitle);
        DisDesc = (EditText) findViewById(R.id.DisDesc);


        db = new SQLiteHandler(getApplicationContext());
        HashMap<String, String> user = db.getUserDetails();
        ShortName = user.get("ShortName");
        Block = user.get("Block");
        Flat = user.get("Flat");
        uid = user.get("uid");

        /*
        ShortName = "ShortName";
        Block = "Block";
        Flat = "Flat";
        uid = "uid";
        */

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main,menu);

        MenuItem NotificationVisible = menu.findItem(R.id.action_notification);
        NotificationVisible.setVisible(false);

        MenuItem AccountVisible = menu.findItem(R.id.action_account);
        AccountVisible.setVisible(false);

        MenuItem SearchVisible = menu.findItem(R.id.action_search);
        SearchVisible.setVisible(false);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
     // Back Navigation
        if(item.getItemId() == android.R.id.home){
            finish();
            return super.onOptionsItemSelected(item);
        }

        int res_id=item.getItemId();
        if(res_id == R.id.action_submit){
            Toast.makeText(getApplicationContext(), "You selected Submit", Toast.LENGTH_SHORT).show();
            insert();
        }
        return true;
    }
/************************************************************ INSERT Start************************************************/
    public void insert(){
   //     String name = editTextName.getText().toString();
        String name = "AAA";

        DisTitle_str = DisTitle.getText().toString();
        DisDesc_str = DisDesc.getText().toString();
        insertToDatabase();
    }

    private void insertToDatabase(){
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
                nameValuePairs.add(new BasicNameValuePair("Field2", name));
                nameValuePairs.add(new BasicNameValuePair("Field3", DisGRPID));
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
             //   Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
                Toast.makeText(getApplicationContext(), "AAAAAAAAAAAAAAAAAA", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(AddDiscussionActivity.this, CGPPActivity.class);
                intent.putExtra("DisGRPID", DisGRPID);
                startActivity(intent);
                //  TextView textViewResult = (TextView) findViewById(R.id.textViewResult);
                // textViewResult.setText("Inserted");
            }
        }
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute("AAA");
    }
/************************************************************ INSERT End************************************************/

}
