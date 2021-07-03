package tw.com.tiger.mybmi;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    private static final int ACTIVITY_REPORT = 1000;
    private Button button_calc;
    private EditText num_height;
    private EditText num_weight;
    private TextView show_result;
    private TextView show_suggest;

    ActivityResultLauncher launcher = registerForActivityResult(new ResultContract(), new ActivityResultCallback<String>() {
        @Override
        public void onActivityResult(String result) {
            //Toast.makeText(MainActivity.this, result, Toast.LENGTH_SHORT).show();
            num_weight.requestFocus();
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.w("Main", "開始事件");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //初始化控制項
        initViews();
        //初始化資料
        initData();
        //設定監聽事件
        setListeners();
    }

    private void initViews()
    {
        button_calc = (Button)findViewById(R.id.calcBmi);
        num_height = (EditText)findViewById(R.id.height);
        num_weight = (EditText)findViewById(R.id.weight);
        show_result = (TextView)findViewById(R.id.result);
        show_suggest = (TextView)findViewById(R.id.suggest);
    }

    private void initData()
    {
    }

    private void setListeners()
    {
        button_calc.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent();
                //intent.setClass(MainActivity.this, ReportActivity.class);
                //Bundle bundle = new Bundle();
                //bundle.putString("KEY_HEIGHT", num_height.getText().toString());
                //bundle.putString("KEY_WEIGHT", num_weight.getText().toString());
                //intent.putExtras(bundle);
                //startActivityForResult(intent, ACTIVITY_REPORT);
                String str_height = num_height.getText().toString();
                String str_weight = num_weight.getText().toString();
                if (str_height.length()==0){
                    num_height.setError(getString(R.string.hint_height)+"!");
                }else if(str_weight.length()==0){
                    num_weight.setError(getString(R.string.hint_weight)+"!");
                }else{
                    launcher.launch(true);
                }

            }

        });
    }

    @Deprecated
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
        super.onActivityResult(requestCode, resultCode, intent);
        if(resultCode == RESULT_OK)
        {
            // 處理第二個頁面REPORT帶回的資料
            if(requestCode == ACTIVITY_REPORT)
            {
                Bundle bundle = intent.getExtras();
                String bmi = bundle.getString("BMI");
                show_suggest.setText(getString(R.string.advice_history) + bmi);
                num_weight.setText(R.string.input_empty);
                num_weight.requestFocus();
            }
        }
    }

    public class ResultContract extends ActivityResultContract<Boolean, String> {
        @NonNull
        @Override
        public Intent createIntent(@NonNull Context context, Boolean input) {
            //Intent intent = new Intent();
            //intent.setClass(MainActivity.this, ReportActivity.class);
            Intent intent = new Intent(MainActivity.this, ReportActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("KEY_HEIGHT", num_height.getText().toString());
            bundle.putString("KEY_WEIGHT", num_weight.getText().toString());
            intent.putExtras(bundle);
            return intent;
        }

        @Override
        public String parseResult(int resultCode, @Nullable Intent intent) {
            DecimalFormat nf = new DecimalFormat("0.00");
            Double bmi = intent.getDoubleExtra("BMI", 0);
            String bmi_s = nf.format(bmi);
            show_suggest.setText(getString(R.string.advice_history) + nf.format(bmi));
            num_weight.setText(R.string.input_empty);
            //Log.d("MainActivity", "resultCode: $resultCode, data: $data")
            return bmi_s;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Toast.makeText(this, getString(R.string.press_setting) , Toast.LENGTH_SHORT).show();
                Toast.makeText(this, getString(R.string.thinking), Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_about:
                //Toast.makeText(this, "按下設定", Toast.LENGTH_SHORT).show();
                openOptionsDialog();
                break;
            default:

        }
        //return true;
        return super.onOptionsItemSelected(item);
    }

    /**
     * 關於說明
     */
    private void openOptionsDialog()
    {
        new AlertDialog.Builder(this)
            .setTitle(getString(R.string.about_bmi))
            .setMessage(getString(R.string.about_content))
            .setPositiveButton("OK",
                 new DialogInterface.OnClickListener() {
                     @Override
                     public void onClick(DialogInterface dialog, int which) {
                         Toast.makeText(MainActivity.this, getString(R.string.understood),Toast.LENGTH_SHORT).show();
                     }
                 })
            .show();
    }
}