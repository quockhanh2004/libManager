package com.appnew.libmanager.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {


    public DbHelper(@Nullable Context context) {
        super(context, "libManager", null, 3);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlUser = "CREATE TABLE USER(MATHUTHU INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "TENTHUTHU TEXT, SDT INTEGER, NAMSINH INTEGER, ADMIN INTEGER, USERNAME TEXT, PASSWORD TEXT)";
        db.execSQL(sqlUser);

        String sqlSach = "CREATE TABLE SACH(MASACH TEXT PRIMARY KEY, TENSACH TEXT, TACGIA TEXT, MALOAISACH TEXT)";
        db.execSQL(sqlSach);

        String sqlLoaiSach = "CREATE TABLE LOAISACH(MALOAISACH TEXT PRIMARY KEY, TENLOAISACH TEXT)";
        db.execSQL(sqlLoaiSach);

        String sqlThanhVien = "CREATE TABLE THANHVIEN(MATHANHVIEN TEXT PRIMARY KEY, TENTHANHVIEN TEXT, NAMSINH INTEGER, SDT INTEGER)";
        db.execSQL(sqlThanhVien);

        String sqlPhieuMuon = "CREATE TABLE PHIEUMUON(MAPHIEUMUON TEXT PRIMARY KEY, NGAYMUON TEXT, TENNGUOIMUON TEXT, TENSACH TEXT, MASACH TEXT)";
        db.execSQL(sqlPhieuMuon);

        String dataUser = "INSERT INTO USER VALUES" +
                "('34197','Phạm Ngọc Quốc Khánh', '382914192', '2004', '1', 'khanhpnq', '123');";
        db.execSQL(dataUser);

        String dataSach = "INSERT INTO SACH VALUES " +
                "('mtncd', 'Một Trăm Năm Cô Đơn', 'Gabriel Garcia Marquez', 'tieuThuyet')," +
                "('i&rob', 'Tôi, Robot', 'Isaac Asimov', 'tieuThuyet')," +
                "('btsct', 'Bạn thật sự có tài', 'Tina Seelig', 'knSong')," +
                "('nlkk', 'Người Lớn Không Khóc', 'Erich Segal', 'tieuThuyet')," +
                "('7tqcnhq', '7 Thói Quen Của Người Hiệu Quả', 'Stephen Covey', 'ptcn')," +
                "('tdn&c', 'Tư Duy Nhanh và Chậm', 'Daniel Kahneman', 'ptcn')," +
                "('nttt', 'Nguyên Tắc Thống Trị', 'Bertrand Russell', 'khoa&triet')," +
                "('dkb:ntt', 'Điều Khác Biệt: Những Thách Thức Cho Giới Trí Thức', 'Steven Jay Gould', 'khoa&triet')," +
                "('cgck', 'Cha giàu, Con khôn', 'Robert Kiyosaki', 'kt&tc')," +
                "('ndttm', 'Nhà Đầu Tư Thông Minh', 'Benjamin Graham', 'kt&tc')," +
                "('qlcd', 'Quyền lực chế độ', 'Noam Chomsky', 'ct&xh')," +
                "('1984', '1984', 'George Orwell', 'ct&xh');";
        db.execSQL(dataSach);

        String dataLoaiSach = "INSERT INTO LOAISACH VALUES " +
                "('tieuThuyet', 'Tiểu thuyết')," +
                "('knSong', 'Kỹ năng sống')," +
                "('ptcn', 'Phát triển cá nhân')," +
                "('khoa&triet', 'Khoa học và Triết học')," +
                "('kt&tc', 'Kinh tế và tài chính')," +
                "('ct&xh', 'Chính trị và Xã hội');";
        db.execSQL(dataLoaiSach);

        String dataPhieuMuon = "INSERT INTO PHIEUMUON VALUES " +
                "('PM0001', '24/09/2023', 'Phạm Ngọc Quốc Khánh', 'Bạn thật sự có tài', 'btsct')," +
                "('PM0002', '24/09/2023', 'Nguyễn Văn Khánh', 'Tôi, Robot', 'i&rob')," +
                "('PM0003', '25/09/2023', 'Nguyễn Minh Nhựt', 'Tôi, Robot', 'i&rob');";
        db.execSQL(dataPhieuMuon);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL("DROP TABLE IF EXISTS USER");
            db.execSQL("DROP TABLE IF EXISTS SACH");
            db.execSQL("DROP TABLE IF EXISTS LOAISACH");
            db.execSQL("DROP TABLE IF EXISTS THANHVIEN");
            db.execSQL("DROP TABLE IF EXISTS PHIEUMUON");
            onCreate(db);
        }
    }
}
