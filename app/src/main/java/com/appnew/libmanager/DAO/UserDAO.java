package com.appnew.libmanager.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.appnew.libmanager.DataBase.DbHelper;
import com.appnew.libmanager.Model.User;

import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private DbHelper dbHelper;
    private Context context;

    public UserDAO(Context context) {
        this.context = context;
        this.dbHelper = new DbHelper(context);
    }

    public boolean add(User user) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        User last = getListUser().get(getListUser().size() -1);
        int maNew = last.getMa() + 1;
        values.put("MATHUTHU", maNew);
        values.put("TENTHUTHU", user.getName());
        values.put("NAMSINH", user.getNamsinh());
        values.put("SDT", user.getSdt());
        values.put("ADMIN", user.getAdmin());
        values.put("USERNAME", maNew);
        values.put("PASSWORD", maNew);
        long result = database.insert("USER", null, values);
        return result != -1;
    }

    public boolean update(User user) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
//        values.put("MATHUTHU", user.getMa());
        values.put("TENTHUTHU", user.getName());
        values.put("NAMSINH", user.getNamsinh());
        values.put("SDT", user.getSdt());
        values.put("ADMIN", user.getAdmin());
        values.put("USERNAME", user.getUserName());
        values.put("PASSWORD", user.getPass());
        int result = database.update("USER", values, "MATHUTHU = ?", new String[]{String.valueOf(user.getMa())});
        return result > 0;
    }

    public boolean remove(int maThuThu){
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        int rowsAffected = database.delete("USER", "MATHUTHU=?", new String[]{String.valueOf(maThuThu)});
        return rowsAffected > 0;
    }

    public List<User> getListUser() {
        List<User> list = new ArrayList<>();
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        database.beginTransaction();
        try {
            Cursor cursor = database.rawQuery("SELECT * FROM USER", null);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    list.add(new User(cursor.getInt(0),
                            cursor.getString(1),
                            cursor.getInt(2),
                            cursor.getInt(3),
                            cursor.getInt(4),
                            cursor.getString(5),
                            cursor.getString(6)));
                } while (cursor.moveToNext());
                database.setTransactionSuccessful();
            }
        } catch (Exception e) {
            Log.e("UserDao", "getUserList: " + e);
        } finally {
            database.endTransaction();
        }

        return list;
    }
}
