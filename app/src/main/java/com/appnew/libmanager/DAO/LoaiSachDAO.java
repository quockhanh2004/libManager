package com.appnew.libmanager.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.appnew.libmanager.DataBase.DbHelper;
import com.appnew.libmanager.Model.LoaiSach;
import com.appnew.libmanager.Model.ThanhVien;

import java.util.ArrayList;
import java.util.List;

public class LoaiSachDAO {
    DbHelper dbHelper;
    public LoaiSachDAO(Context context) {
        dbHelper = new DbHelper(context);
    }

    public boolean add(LoaiSach loaiSach) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("MALOAISACH", loaiSach.getMaLoaiSach());
        values.put("TENLOAISACH", loaiSach.getTenLoaiSach());

        long result = database.insert("LOAISACH", null, values);
        return result != -1;
    }

    public boolean update(LoaiSach loaiSach) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
//        values.put("MATHUTHU", thanhVien.getMa());
        values.put("TENLOAISACH", loaiSach.getTenLoaiSach());

        int result = database.update("LOAISACH", values, "MALOAISACH = ?", new String[]{String.valueOf(loaiSach.getMaLoaiSach())});
        return result > 0;
    }

    public boolean remove(String maLoaiSach){
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        int rowsAffected = database.delete("LOAISACH", "MALOAISACH=?", new String[]{String.valueOf(maLoaiSach)});
        return rowsAffected > 0;
    }

    public List<LoaiSach> getList() {
        List<LoaiSach> list = new ArrayList<>();
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        database.beginTransaction();
        try {
            Cursor cursor = database.rawQuery("SELECT * FROM LOAISACH", null);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    list.add(new LoaiSach(cursor.getString(0),
                            cursor.getString(1)));
                } while (cursor.moveToNext());
                database.setTransactionSuccessful();
            }
        } catch (Exception e) {
            Log.e("LoaiSachDAO", "getLoaiSachList: " + e);
        } finally {
            database.endTransaction();
        }

        return list;
    }
}
