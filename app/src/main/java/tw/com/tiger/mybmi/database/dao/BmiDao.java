package tw.com.tiger.mybmi.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.Date;
import java.util.List;

import tw.com.tiger.mybmi.database.model.BmiLog;
import tw.com.tiger.mybmi.util.Constants;

@Dao
public interface BmiDao {

    String tableName = Constants.TABLE_NAME_BMI;

    /**=======================================================================================*/
    /**簡易新增所有資料的方法*/
    @Insert(onConflict = OnConflictStrategy.REPLACE)//預設萬一執行出錯怎麼辦，REPLACE為覆蓋
    void insertData(BmiLog log);

    /**複雜(?)新增所有資料的方法*/
    @Query("INSERT INTO "+tableName+"(bmi) VALUES(:bmi)")
    void insertData(double bmi);

    /**=======================================================================================*/
    /**撈取全部資料*/
    @Query("SELECT * FROM " + tableName)
    List<BmiLog> displayAll();

    /**=======================================================================================*/
    /**取得目前總筆數*/
    @Query("SELECT COUNT(id) FROM " + tableName)
    int getCount();

    /**=======================================================================================*/
    /**撈取最大筆紀錄*/
    @Query("SELECT Max(id) FROM " + tableName)
    int getMax();

    /**=======================================================================================*/
    /**撈取最大筆Bmi*/
    @Query("SELECT bmi FROM " + tableName + " where id= :id")
    double displayMaxBmi(int id);

    /**=======================================================================================*/
    /**簡易更新資料的方法*/
    @Update
    void updateData(BmiLog log);

    /**複雜(?)更新資料的方法*/
    @Query("UPDATE "+tableName+" SET bmi = :bmi,date = :date WHERE id = :id" )
    void updateData(int id, double bmi, Date date);

    /**=======================================================================================*/
    /**簡單刪除資料的方法*/
    @Delete
    void deleteData(BmiLog log);

    /**刪除所有資料*/
    @Query("DELETE FROM "+ tableName)
    void deleteDatas();

    /**複雜(?)刪除資料的方法*/
    @Query("DELETE FROM " + tableName + " WHERE id = :id")
    void deleteData(int id);

}
