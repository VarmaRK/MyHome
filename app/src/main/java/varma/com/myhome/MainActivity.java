package varma.com.myhome;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;



import varma.com.helper.SQLiteHandler;
import varma.com.helper.SessionManager;

public class MainActivity extends AppCompatActivity {
    private Button btnNotification;
    private Button btnPhotos;
    private Button btnFCM;
    GridView gridView;
    String letterList[] = {"", "", "", "", "", ""};
    int lettersIcon[] = {R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.d, R.drawable.e, R.drawable.f};
    Toolbar toolbar;

    private SessionManager session;
    private SQLiteHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Home");

        gridView = (GridView) findViewById(R.id.gridView);
        GridAdapter adapter = new GridAdapter(MainActivity.this, lettersIcon, letterList);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, "ClickedLetter:" + letterList[position], Toast.LENGTH_LONG).show();

                Intent intent = new Intent(MainActivity.this, CGPPActivity.class);
                intent.putExtra("DisGRPID", String.valueOf(position));
                intent.putExtra("subcatposition", "0");
                // intent.putExtra("category","AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");

                startActivity(intent);

            }

        });
       /******************************************************************************************************************/

        btnNotification = (Button) findViewById(R.id.btnNotifications);
        btnNotification.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Clicked Photos", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), MyHomeImageLoadActivity.class);
                intent.putExtra("Category", "Notifications");
                startActivity(intent);
            }

        });

        btnPhotos = (Button) findViewById(R.id.btnPhotos);
        btnPhotos.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Clicked Photos", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), MyHomeImageLoadActivity.class);
                intent.putExtra("Category", "Photos");
                startActivity(intent);
            }

        });

        btnFCM = (Button) findViewById(R.id.btnFCM);
        btnFCM.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Clicked Photos", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), DisplayFCMTokenActivity.class);
              //  intent.putExtra("Category", "Photos");
                startActivity(intent);
            }

        });
       /*******************************************************************************************************************/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main,menu);

        MenuItem SearchVisible = menu.findItem(R.id.action_search);
        SearchVisible.setVisible(false);

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
            Toast.makeText(getApplicationContext(), "You selected account Main", Toast.LENGTH_SHORT).show();
            session = new SessionManager(getApplicationContext());
            db = new SQLiteHandler(getApplicationContext());
            session.setLogin(false);
            db.deleteUsers();
            // Launching the login activity
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
        return true;
    }

    public void notificationClick(View v){
        Toast.makeText(getApplicationContext(), "Clicked Photos", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MainActivity.this, MyHomeImageLoadActivity.class);
        startActivity(intent);
       // finish(); If enable Hardware back button closes the app and will not navigate to the back screen
    }

    public void photosClick(View v){
        String Temp = "Notifications";
        Toast.makeText(getApplicationContext(), "Clicked Photos", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplicationContext(), MyHomeImageLoadActivity.class);
        intent.putExtra("Category", Temp);
        startActivity(intent);
        // finish(); If enable Hardware back button closes the app and will not navigate to the back screen
    }
}
