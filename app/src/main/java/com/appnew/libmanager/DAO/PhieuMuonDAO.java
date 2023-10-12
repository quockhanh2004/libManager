package com.appnew.libmanager.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.appnew.libmanager.DataBase.DbHelper;
import com.appnew.libmanager.Model.PhieuMuon;
import com.appnew.libmanager.Model.Sach;

import java.util.ArrayList;
import java.util.List;

public class PhieuMuonDAO {
    private DbHelper dbHelper;

    public PhieuMuonDAO(Context context) {
        dbHelper = new DbHelper(context);
    }

    public boolean add(PhieuMuon phieuMuon) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("MAPHIEUMUON", phieuMuon.getMaPhieuMuon());
        values.put("TENNGUOIMUON", phieuMuon.getNguoiMuon());
        values.put("NGAYMUON", phieuMuon.getNgayMuon());
        values.put("MASACH", phieuMuon.getMaSach());

        long result = database.insert("PHIEUMUON", null, values);
        return result != -1;
    }

    public boolean update(PhieuMuon phieuMuon) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
//        values.put("MATHUTHU", thanhVien.getMa());
        values.put("TENNGUOIMUON", phieuMuon.getNguoiMuon());
        values.put("NGAYMUON", phieuMuon.getNgayMuon());
        values.put("MASACH", phieuMuon.getMaSach());

        int result = database.update("PHIEUMUON", values, "MAPHIEUMUON = ?", new String[]{String.valueOf(phieuMuon.getMaPhieuMuon())});
        return result > 0;
    }

    public boolean remove(String maPhieuMuon) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        int rowsAffected = database.delete("PHIEUMUON", "MAPHIEUMUON=?", new String[]{String.valueOf(maPhieuMuon)});
        return rowsAffected > 0;
    }

    public List<PhieuMuon> getList() {
        List<PhieuMuon> list = new ArrayList<>();
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        database.beginTransaction();
        try {
            Cursor cursor = database.rawQuery("SELECT * FROM PHIEUMUON", null);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    list.add(new PhieuMuon(cursor.getString(0),
                            cursor.getString(1),
                            cursor.getString(2),
                            cursor.getString(3),
                            cursor.getString(4)
                    ));
                } while (cursor.moveToNext());
                database.setTransactionSuccessful();
            }
        } catch (Exception e) {
            Log.e("PhieuMuonhDAO", "getPhieuMuonList: " + e);
        } finally {
            database.endTransaction();
        }

        return list;
    }
}
