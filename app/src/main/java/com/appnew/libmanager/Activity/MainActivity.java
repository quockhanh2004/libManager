package com.appnew.libmanager.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.appnew.libmanager.Adapter.LoaiSachAdapter;
import com.appnew.libmanager.Adapter.PhieuMuonAdapter;
import com.appnew.libmanager.Adapter.SachAdapter;
import com.appnew.libmanager.Adapter.ThanhVienAdapter;
import com.appnew.libmanager.Adapter.Top10Adapter;
import com.appnew.libmanager.DAO.SachDAO;
import com.appnew.libmanager.Model.LoaiSach;
import com.appnew.libmanager.Model.PhieuMuon;
import com.appnew.libmanager.Model.Sach;
import com.appnew.libmanager.Model.ThanhVien;
import com.appnew.libmanager.Model.User;
import com.appnew.libmanager.R;
import com.appnew.libmanager.databinding.ActivityMainBinding;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding mainBinding;
    private PhieuMuonAdapter phieuMuonAdapter;
    private ThanhVienAdapter thanhVienAdapter;
    private ThanhVienAdapter userAdapter;
    private LoaiSachAdapter loaiSachAdapter;
    private SachAdapter sachAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());

        Intent getname = getIntent();
        String name = getname.getStringExtra("user");
//        Button btnLogOut = mainBinding.navigationView.findViewById(R.id.btnLogOut);
        TextView txtName = mainBinding.navigationView.getHeaderView(0).findViewById(R.id.txtNameUser);
        if (name != null) {
            txtName.setText(name);
        }


        setSupportActionBar(mainBinding.toolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);
        ActionBar actionBar = getSupportActionBar();

        phieuMuonAdapter = new PhieuMuonAdapter(this);
        thanhVienAdapter = new ThanhVienAdapter(this, true);
        userAdapter = new ThanhVienAdapter(this, false);
        loaiSachAdapter = new LoaiSachAdapter(this);
        sachAdapter = new SachAdapter(this);
        Top10Adapter top10Adapter = new Top10Adapter(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mainBinding.rcl.setLayoutManager(linearLayoutManager);
        mainBinding.rcl.setAdapter(phieuMuonAdapter);

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                String title = (String) actionBar.getTitle();
                assert title != null;
                if (!title.equalsIgnoreCase("10 sách được mượn nhiều nhất")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Cảnh báo");
                    builder.setMessage("Bạn có chắc muốn xoá ");
                    builder.setPositiveButton("Có", (dialog, id) -> {
                        if (title.equalsIgnoreCase("quản lí phiếu mượn")) {
                            phieuMuonAdapter.remove(position);
                        } else if (title.equalsIgnoreCase("quản lí thành viên")) {
                            thanhVienAdapter.remove(position);
                        } else if (title.equalsIgnoreCase("quản lí sách")) {
                            sachAdapter.remove(position);
                        } else if (title.equalsIgnoreCase("quản lí thể loại sách")) {
                            loaiSachAdapter.remove(position);
                        } else if (title.equalsIgnoreCase("quản lí tài khoản khác")) {
                            userAdapter.removeUser(position);
                        }
                        dialog.dismiss();
                    });
                    builder.setNegativeButton("Không", (dialog, which) -> {
                        if (title.equalsIgnoreCase("quản lí phiếu mượn")) {
                            phieuMuonAdapter.notifyItemChanged(position);
                        } else if (title.equalsIgnoreCase("quản lí thành viên")) {
                            thanhVienAdapter.notifyItemChanged(position);
                        } else if (title.equalsIgnoreCase("quản lí sách")) {
                            sachAdapter.notifyItemChanged(position);
                        } else if (title.equalsIgnoreCase("quản lí thể loại sách")) {
                            loaiSachAdapter.notifyItemChanged(position);
                        } else if (title.equalsIgnoreCase("quản lí tài khoản khác")) {
                            userAdapter.notifyItemChanged(position);
                        }
                        dialog.dismiss();
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                } else {
                    top10Adapter.notifyItemChanged(position);
                }
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(mainBinding.rcl);
        actionBar.setTitle("Quản lí phiếu mượn");

        mainBinding.navigationView.setNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.qlpm) {

                mainBinding.rcl.setAdapter(phieuMuonAdapter);
                mainBinding.floating.setVisibility(View.VISIBLE);
                phieuMuonAdapter.notifyDataSetChanged();
            } else if (item.getItemId() == R.id.qltv) {

                mainBinding.rcl.setAdapter(thanhVienAdapter);
                mainBinding.floating.setVisibility(View.VISIBLE);
                thanhVienAdapter.notifyDataSetChanged();
            } else if (item.getItemId() == R.id.qls) {

                mainBinding.rcl.setAdapter(sachAdapter);
                mainBinding.floating.setVisibility(View.VISIBLE);
                sachAdapter.notifyDataSetChanged();
            } else if (item.getItemId() == R.id.qlls) {

                mainBinding.rcl.setAdapter(loaiSachAdapter);
                mainBinding.floating.setVisibility(View.VISIBLE);
                loaiSachAdapter.reload();
            } else if (item.getItemId() == R.id.top10) {
                mainBinding.rcl.setAdapter(top10Adapter);

                mainBinding.floating.setVisibility(View.GONE);
                top10Adapter.reload();
            } else if (item.getItemId() == R.id.doanhthu) {
                Toast.makeText(this, "Tính năng đang phát triển", Toast.LENGTH_SHORT).show();
            } else if (item.getItemId() == R.id.changePass) {
                Toast.makeText(this, "Tính năng đang phát triển", Toast.LENGTH_SHORT).show();
            } else if (item.getItemId() == R.id.changePassOther) {
                itemTouchHelper.attachToRecyclerView(mainBinding.rcl);
                mainBinding.rcl.setAdapter(userAdapter);
                userAdapter.notifyDataSetChanged();
            } else if (item.getItemId() == R.id.btnLogOut) {
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
            }

            mainBinding.drawerLayout.closeDrawer(GravityCompat.START);
            actionBar.setTitle(item.getTitle());
            return false;
        });
        mainBinding.rcl.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0){
                    mainBinding.floating.setVisibility(View.GONE);
                }
                if (dy < 0) {
                    if (!actionBar.getTitle().equals("10 sách được mượn nhiều nhất"))
                    mainBinding.floating.setVisibility(View.VISIBLE);
                }
            }
        });
        mainBinding.floating.setOnClickListener(view -> {
            showAddDialog((Context) this, (String) actionBar.getTitle());
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            mainBinding.drawerLayout.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }

    private void showAddDialog(Context context, String key) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add, null);
        builder.setView(view);

        TextInputLayout txtInput0, txtInput1, txtInput2, txtInput3;
        TextInputEditText edtAdd0, edtAdd1, edtAdd2, edtAdd3;
        TextView txtNameAdd;
        LinearLayout layoutAddImage;
        Button btnAdd0, btnAdd1;


        txtInput0 = view.findViewById(R.id.txtInput0);
        txtInput1 = view.findViewById(R.id.txtInput1);
        txtInput2 = view.findViewById(R.id.txtInput2);
        txtInput3 = view.findViewById(R.id.txtInput3);
        edtAdd0 = view.findViewById(R.id.edtAdd0);
        edtAdd1 = view.findViewById(R.id.edtAdd1);
        edtAdd2 = view.findViewById(R.id.edtAdd2);
        edtAdd3 = view.findViewById(R.id.edtAdd3);
        txtNameAdd = view.findViewById(R.id.txtNameAdd);
        layoutAddImage = view.findViewById(R.id.layoutAddImage);
        btnAdd0 = view.findViewById(R.id.btnAdd0);
        btnAdd1 = view.findViewById(R.id.btnAdd1);

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        builder.setCancelable(true);


        if (key.equalsIgnoreCase("quản lí phiếu mượn")) {
            txtNameAdd.setText("Thêm phiếu mượn");
            txtInput0.setHint("Mã phiếu mượn");
            txtInput1.setHint("Tên người mượn");
            txtInput2.setHint("Tên sách mượn");
            edtAdd2.setCursorVisible(false);
            edtAdd2.setClickable(false);
            edtAdd2.setFocusable(false);
            edtAdd2.setFocusableInTouchMode(false);
            txtInput3.setVisibility(View.GONE);
            layoutAddImage.setVisibility(View.GONE);
            btnAdd0.setText("Hủy");
            btnAdd1.setText("Thêm");

            Sach c = new Sach();
            List<Sach> list = new SachDAO(this).getList();
            edtAdd2.setOnClickListener(v -> {

                String[] sach = new String[list.size()] ;

                int count = 0;
                for (Sach s :
                        list) {
                    sach[count] = s.getName();
                    count++;
                }

                AlertDialog.Builder tenSach = new AlertDialog.Builder(context);
                tenSach.setTitle("Chọn sách")
                        .setItems(sach, (dialog, which) -> {
                            // Hiển thị loại đã chọn trong EditText
                            edtAdd2.setText(sach[which]);
                            c.setMa(list.get(which).getMa());
                        });

                // Hiển thị AlertDialog
                tenSach.create().show();
            });

            btnAdd1.setOnClickListener(view1 -> {
                String ma, ten, masach;
                Date today = new Date();

                // Định dạng ngày thành chuỗi "DD/MM/YYYY"
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                String formattedDate = sdf.format(today);


                ma = edtAdd0.getText().toString().trim();
                ten = edtAdd1.getText().toString().trim();
                masach = c.getMa();
                phieuMuonAdapter.add(new PhieuMuon(ma, formattedDate, ten, "", masach));
                alertDialog.dismiss();
            });

            btnAdd0.setOnClickListener(view1 -> {
                alertDialog.dismiss();
            });
        } else if (key.equalsIgnoreCase("quản lí thành viên")) {
            txtNameAdd.setText("Thêm thành viên");
            txtInput0.setHint("Tên thành viên");
            txtInput1.setHint("Năm sinh");
            txtInput2.setHint("SĐT");
            txtInput3.setVisibility(View.GONE);
            edtAdd1.setInputType(InputType.TYPE_CLASS_NUMBER);
            edtAdd2.setInputType(InputType.TYPE_CLASS_NUMBER);
            layoutAddImage.setVisibility(View.GONE);
            btnAdd0.setText("Hủy");
            btnAdd1.setText("Thêm");

            btnAdd1.setOnClickListener(view1 -> {
                String ten, namsinh, sdt;
                ten = edtAdd0.getText().toString().trim();
                namsinh = edtAdd1.getText().toString().trim();
                sdt = edtAdd2.getText().toString().trim();
                thanhVienAdapter.add(new ThanhVien("", ten, Integer.parseInt(namsinh), Integer.parseInt(sdt)));
                alertDialog.dismiss();
            });

            btnAdd0.setOnClickListener(view1 -> {
                alertDialog.dismiss();
            });
        } else if (key.equalsIgnoreCase("quản lí thể loại sách")) {
            txtNameAdd.setText("Thêm loại sách");
            txtInput0.setHint("Tên loại sách");
            txtInput1.setHint("Mã loại sách");
            txtInput2.setVisibility(View.GONE);
            txtInput3.setVisibility(View.GONE);
            layoutAddImage.setVisibility(View.GONE);
            btnAdd0.setText("Hủy");
            btnAdd1.setText("Thêm");

            btnAdd1.setOnClickListener(view1 -> {
                String ten, ma;
                ten = edtAdd0.getText().toString().trim();
                ma = edtAdd1.getText().toString().trim();
                loaiSachAdapter.add(new LoaiSach(ma, ten));
                alertDialog.dismiss();
            });

            btnAdd0.setOnClickListener(view1 -> {
                alertDialog.dismiss();
            });
        } else if (key.equalsIgnoreCase("quản lí sách")) {
            txtNameAdd.setText("Thêm sách");
            txtInput0.setHint("Tên sách");
            txtInput1.setHint("Tác giả");
            txtInput2.setHint("Mã loại sách");
            txtInput3.setVisibility(View.GONE);
            btnAdd0.setText("Hủy");
            btnAdd1.setText("Thêm");

            btnAdd1.setOnClickListener(view1 -> {
                String ten, tacgia, maloai;
                ten = edtAdd0.getText().toString().trim();
                tacgia = edtAdd1.getText().toString().trim();
                maloai = edtAdd2.getText().toString().trim();
                sachAdapter.add(new Sach("", ten, tacgia, maloai));
                alertDialog.dismiss();
            });

            btnAdd0.setOnClickListener(view1 -> {
                alertDialog.dismiss();
            });
        } else if (key.equalsIgnoreCase("quản lí tài khoản khác")) {
            txtNameAdd.setText("Thêm Thủ thư");
            txtInput0.setHint("Tên Thủ thư");
            txtInput1.setHint("Số điện thoại");
            txtInput2.setHint("Năm sinh");
            txtInput3.setHint("Có phải là Admin?");
            edtAdd1.setInputType(InputType.TYPE_CLASS_NUMBER);
            edtAdd2.setInputType(InputType.TYPE_CLASS_NUMBER);
            layoutAddImage.setVisibility(View.GONE);
            btnAdd0.setText("Hủy");
            btnAdd1.setText("Thêm");

            edtAdd3.setOnClickListener(v -> {
                AlertDialog.Builder admin = new AlertDialog.Builder(MainActivity.this);
                String[] item = {"Có", "Không"};
                admin.setTitle("Đây có phải là Admin?")
                        .setItems(item, (dialog, id) -> {
                            edtAdd3.setText(item[id]);
//                    dialog.dismiss();
                        });
                admin.create().show();
            });

            btnAdd1.setOnClickListener(view1 -> {
                String ten, sdt, namsinh;
                int admin;
                ten = edtAdd0.getText().toString().trim();
                sdt = edtAdd1.getText().toString().trim();
                namsinh = edtAdd2.getText().toString().trim();
                admin = edtAdd3.getText().toString().trim().equals("Có") ? 1 : 0;
                userAdapter.addUser(new User(0, ten, Integer.parseInt(namsinh), Integer.parseInt(sdt), admin));
                alertDialog.dismiss();
            });

            btnAdd0.setOnClickListener(view1 -> {
                alertDialog.dismiss();
            });
        }
    }

}