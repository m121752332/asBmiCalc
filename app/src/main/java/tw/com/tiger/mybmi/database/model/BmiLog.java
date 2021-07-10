package tw.com.tiger.mybmi.database.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

import lombok.Data;

@Entity(tableName = "bmi_log")
@Data
public class BmiLog {
    @PrimaryKey(autoGenerate = true)    //  主鍵是否自動增加，預設為false
    @ColumnInfo(name = "id")
    private int id;
    private double bmi;
    private Date date;

    public BmiLog(){}

    public BmiLog(double bmi) {
        this.bmi = bmi;
        this.date = new Date(System.currentTimeMillis());
    }

    public BmiLog(double bmi,Date date) {
        this.bmi = bmi;
        this.date = new Date(System.currentTimeMillis());
    }

    @Ignore//如果要使用多形的建構子，必須加入@Ignore
    public BmiLog(int id,double bmi,Date date) {
        this.id = id;
        this.bmi = bmi;
        this.date = new Date(System.currentTimeMillis());
    }
}