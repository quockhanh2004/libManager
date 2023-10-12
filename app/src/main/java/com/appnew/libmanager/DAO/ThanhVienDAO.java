package com.appnew.libmanager.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.appnew.libmanager.DataBase.DbHelper;
import com.appnew.libmanager.Model.ThanhVien;

import java.util.ArrayList;
import java.util.List;

public class ThanhVienDAO {
    private DbHelper dbHelper;
    private Context context;

    public ThanhVienDAO(Context context) {
        this.context = context;
        this.dbHelper = new DbHelper(context);
    }

    public boolean add(ThanhVien thanhVien) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        String newId = null;
        try {
            List<ThanhVien> list = getList();
            ThanhVien last = list.get(list.size() - 1);
            String id = last.getMa();
            int number = Integer.parseInt(String.valueOf(id.charAt(id.length() - 1)));
            number++;
            newId = id.substring(0, id.length() - 1) + number;
            values.put("MATHANHVIEN", newId);
        } catch (Exception e) {
            newId = "TV001";
            values.put("MATHANHVIEN", newId);
        }



        values.put("TENTHANHVIEN", thanhVien.getName());
        values.put("NAMSINH", thanhVien.getNamsinh());
        values.put("SDT", thanhVien.getSdt());

        long result = database.insert("THANHVIEN", null, values);
        return result != -1;
    }

    public boolean update(ThanhVien thanhVien) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
//        values.put("MATHUTHU", thanhVien.getMa());
        values.put("TENTHANHVIEN", thanhVien.getName());
        values.put("NAMSINH", thanhVien.getNamsinh());
        values.put("SDT", thanhVien.getSdt());

        int result = database.update("THANHVIEN", values, "MATHANHVIEN = ?", new String[]{String.valueOf(thanhVien.getMa())});
        return result > 0;
    }

    public boolean remove(String maThanhVien) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        int rowsAffected = database.delete("THANHVIEN", "MATHANHVIEN=?", new String[]{String.valueOf(maThanhVien)});
        return rowsAffected > 0;
    }

    public List<ThanhVien> getList() {
        List<ThanhVien> list = new ArrayList<>();
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        database.beginTransaction();
        try {
            Cursor cursor = database.rawQuery("SELECT * FROM THANHVIEN", null);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    list.add(new ThanhVien(cursor.getString(0),
                            cursor.getString(1),
                            cursor.getInt(2),
                            cursor.getInt(3)));
                } while (cursor.moveToNext());
                database.setTransactionSuccessful();
            }
        } catch (Exception e) {
            Log.e("ThanhVienDAO", "getThanhVienList: " + e);
        } finally {
            database.endTransaction();
        }

        return list;
    }
}
