package udel.edu.wangyc.minesweeper;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.app.AlertDialog;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button toPlay;
    public String level="9";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView yourVariableName = findViewById(R.id.instructions);
        yourVariableName.setMovementMethod(new ScrollingMovementMethod());
//        布置ToPlayGame按钮监听事件
        toPlay=(Button)findViewById(R.id.startButton);
        toPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Activity跳转
                Intent intent=new Intent();
                intent.putExtra("level",level);
                intent.setClass(MainActivity.this,GameActivity.class);
                startActivity(intent);
            }
        });


}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
}
