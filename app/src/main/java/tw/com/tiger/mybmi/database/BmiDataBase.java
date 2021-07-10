package tw.com.tiger.mybmi.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import tw.com.tiger.mybmi.database.dao.BmiDao;
import tw.com.tiger.mybmi.database.model.BmiLog;
import tw.com.tiger.mybmi.util.Constants;
import tw.com.tiger.mybmi.util.DateConverter;

//資料綁定的Getter-Setter,資料庫版本,是否將資料導出至文件
@Database(entities = {BmiLog.class},version = 1,exportSchema = false)
@TypeConverters({DateConverter.class})
public abstract class BmiDataBase extends RoomDatabase {
    public static final String DB_NAME = Constants.DB_NAME;//資料庫名稱
    private static BmiDataBase instance;

    public abstract BmiDao getBmiDao();//設置對外接口

    public static synchronized BmiDataBase getInstance(Context context){
        if(instance == null){
            instance = create(context);//創立新的資料庫
        }
        return instance;
    }

    private static BmiDataBase create(final Context context){
        return Room.databaseBuilder(context,BmiDataBase.class,DB_NAME)
                .build();
    }

    /**
     * 關閉資料庫
     */
    public void cleanUp(){
        instance = null;
    }
}
