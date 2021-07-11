package tw.com.tiger.mybmi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

import tw.com.tiger.mybmi.database.BmiDataBase;
import tw.com.tiger.mybmi.database.model.BmiLog;

public class ReportActivity extends AppCompatActivity {
    private Button button_back;
    private ImageView btn_return;
    private TextView show_result;
    private TextView show_suggest;
    private double BMI_NUM;
    private BmiDataBase bmiDatabase;
    private BmiLog bmi;

    public ReportActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        initViews();
        showResults();
        setListensers();
    }

    private void initViews()
    {
        //button_back = (Button)findViewById(R.id.button);
        btn_return = findViewById(R.id.btn_return);
        show_result = findViewById(R.id.result);
        show_suggest = findViewById(R.id.suggest);
        bmiDatabase = BmiDataBase.getInstance(ReportActivity.this);
    }

    private void showResults()
    {
        try
        {
            DecimalFormat nf = new DecimalFormat("0.00");

            Bundle bundle = this.getIntent().getExtras();
            //身高
            double height = Double.parseDouble(bundle.getString("KEY_HEIGHT"))/100;
            //體重
            double weight = Double.parseDouble(bundle.getString("KEY_WEIGHT"));
            //計算出BMI值
            BMI_NUM = weight / (height*height);

            //結果
            show_result.setText(getText(R.string.bmi_result) +" "+ nf.format(BMI_NUM));
            //Toast.makeText(ReportActivity.this, "BMI:"+BMI, Toast.LENGTH_SHORT).show();

            //建議
            if(BMI_NUM > 25) //太重了
                show_suggest.setText(R.string.advice_heavy);
            else if(BMI_NUM < 20) //太輕了
                show_suggest.setText(R.string.advice_light);
            else //剛剛好
                show_suggest.setText(R.string.advice_average);
        }
        catch(Exception e)
        {
            Toast.makeText(this, getText(R.string.exception), Toast.LENGTH_SHORT).show();
        }
    }

    private void setListensers()
    {
        //返回按鈕，包裝後產生LOG到MyBMI.db
        btn_return.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                DecimalFormat nf = new DecimalFormat("0.00");

                Intent intent = new Intent();
                //Bundle bundle = new Bundle();
                //bundle.putString("BMI", nf.format(BMI));
                intent.putExtra("BMI",BMI_NUM);

                new Thread(() -> {
                    bmi = new BmiLog(BMI_NUM);
                    bmiDatabase.getBmiDao().insertData(bmi);
                }).start();
                //Toast.makeText(ReportActivity.this, "BMI:"+BMI, Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK, intent);
                //結束報告Class
                ReportActivity.this.finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        //關閉資料庫
        bmiDatabase.cleanUp();
        //關主程式
        super.onDestroy();
    }
}