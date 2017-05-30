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
import android.widget.GridView;
import android.widget.Toast;

public class DiscussionHomeActivity extends AppCompatActivity {
    GridView gridView;
    String letterList[] = {"", "", "", "", "", ""};
    int lettersIcon[] = {R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.d, R.drawable.e, R.drawable.f};
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discussion_home);


        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Discussions");

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }


        gridView = (GridView) findViewById(R.id.gridView);
        GridAdapter adapter = new GridAdapter(DiscussionHomeActivity.this, lettersIcon, letterList);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(DiscussionHomeActivity.this, "ClickedLetter:" + letterList[position], Toast.LENGTH_LONG).show();
                Intent intent = new Intent(DiscussionHomeActivity.this, CGPPActivity.class);
                intent.putExtra("DisGRPID", String.valueOf(position));
                intent.putExtra("subcatposition", "0");
                // intent.putExtra("category","AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
                startActivity(intent);
            }

        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
}
