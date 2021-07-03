package tw.com.tiger.mybmi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class ReportActivity extends AppCompatActivity {
    private Button button_back;
    private TextView show_result;
    private TextView show_suggest;
    private double BMI;

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
        button_back = (Button)findViewById(R.id.button);
        show_result = (TextView)findViewById(R.id.result);
        show_suggest = (TextView)findViewById(R.id.suggest);
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
            BMI = weight / (height*height);

            //結果
            show_result.setText(getText(R.string.bmi_result) + nf.format(BMI));
            //Toast.makeText(ReportActivity.this, "BMI:"+BMI, Toast.LENGTH_SHORT).show();

            //建議
            if(BMI > 25) //太重了
                show_suggest.setText(R.string.advice_heavy);
            else if(BMI < 20) //太輕了
                show_suggest.setText(R.string.advice_light);
            else //剛剛好
                show_suggest.setText(R.string.advice_average);
        }
        catch(Exception e)
        {
            Toast.makeText(this, "要先輸入身高體重喔!", Toast.LENGTH_SHORT).show();
        }
    }

    private void setListensers()
    {
        button_back.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                DecimalFormat nf = new DecimalFormat("0.00");

                Intent intent = new Intent();
                //Bundle bundle = new Bundle();
                //bundle.putString("BMI", nf.format(BMI));
                intent.putExtra("BMI",BMI);
                //Toast.makeText(ReportActivity.this, "BMI:"+BMI, Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK, intent);
                //結束報告Class
                ReportActivity.this.finish();
            }
        });
    }
}