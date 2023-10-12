package com.appnew.libmanager.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.appnew.libmanager.DataBase.DbHelper;
import com.appnew.libmanager.Model.LoaiSach;
import com.appnew.libmanager.Model.Sach;

import java.util.ArrayList;
import java.util.List;

public class SachDAO {
    DbHelper dbHelper;

    public SachDAO(Context context) {
        dbHelper = new DbHelper(context);
    }

    public String getFirstLetters(String input) {
        String[] words = input.split(" "); // Tách chuỗi thành các từ bằng khoảng trắng
        StringBuilder result = new StringBuilder();

        for (String word : words) {
            if (!word.isEmpty()) {
                result.append(word.charAt(0)); // Lấy ký tự đầu tiên của từ
            }
        }

        return result.toString().toLowerCase();
    }

    //    String firstLetters = getFirstLetters(input);
    public boolean add(Sach sach) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        String ma = getFirstLetters(sach.getName());
        values.put("MASACH", ma);
        values.put("TENSACH", sach.getName());
        values.put("TACGIA", sach.getTacgia());
        values.put("MALOAISACH", sach.getMaloaisach());

        long result = database.insert("SACH", null, values);
        return result != -1;
    }

    public boolean update(Sach sach) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
//        values.put("MATHUTHU", thanhVien.getMa());
        values.put("TENSACH", sach.getName());
        values.put("TENSACH", sach.getName());
        values.put("TACTGIA", sach.getMaloaisach());

        int result = database.update("SACH", values, "MASACH = ?", new String[]{String.valueOf(sach.getMa())});
        return result > 0;
    }

    public boolean remove(String maSach) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        int rowsAffected = database.delete("SACH", "MASACH=?", new String[]{String.valueOf(maSach)});
        return rowsAffected > 0;
    }

    public LoaiSach getSach(String maSach) {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = database.query(
                "LOAISACH", null, "MALOAISACH = ?", new String[]{maSach}, null,              // GROUP BY
                null, null, null);
        LoaiSach sach = null;
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                // Tạo đối tượng YourDataModel từ dữ liệu truy vấn
                sach = new LoaiSach();
                sach.setTenLoaiSach(cursor.getString(1));
                sach.setMaLoaiSach(maSach);
                // Thêm các trường dữ liệu khác ở đây
            }
            cursor.close();
        }

        database.close();

        return sach;
    }

    public List<Sach> getList() {
        List<Sach> list = new ArrayList<>();
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        database.beginTransaction();
        try {
            Cursor cursor = database.rawQuery("SELECT * FROM SACH", null);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    list.add(new Sach(cursor.getString(0),
                            cursor.getString(1),
                            cursor.getString(2),
                            cursor.getString(3)));
                } while (cursor.moveToNext());
                database.setTransactionSuccessful();
            }
        } catch (Exception e) {
            Log.e("SachDAO", "getSachList: " + e);
        } finally {
            database.endTransaction();
        }

        return list;
    }
}
