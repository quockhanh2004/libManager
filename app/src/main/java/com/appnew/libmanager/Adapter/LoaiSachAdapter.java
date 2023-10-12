package com.appnew.libmanager.Adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.appnew.libmanager.DAO.LoaiSachDAO;
import com.appnew.libmanager.DAO.SachDAO;
import com.appnew.libmanager.Model.LoaiSach;
import com.appnew.libmanager.Model.Sach;
import com.appnew.libmanager.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

public class LoaiSachAdapter extends RecyclerView.Adapter<LoaiSachAdapter.ViewHoler> {
    private Context context;
    private LoaiSachDAO loaiSachDAO;
    private SachDAO sachDAO;
    private List<LoaiSach> loaiSachList;
    private List<Sach> sachList;

    public LoaiSachAdapter(Context context) {
        this.context = context;
        loaiSachDAO = new LoaiSachDAO(context);
        sachDAO = new SachDAO(context);
        loaiSachList = loaiSachDAO.getList();
        sachList = sachDAO.getList();
    }

    @NonNull
    @Override
    public LoaiSachAdapter.ViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_loaisach, parent, false);
        return new ViewHoler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LoaiSachAdapter.ViewHoler holder, int position) {
        int soLuong = 0;
        for (Sach s : sachList) {
            if (s.getMaloaisach().equals(loaiSachList.get(holder.getAdapterPosition()).getMaLoaiSach())){
                soLuong++;
            }
        }

        holder.txtLoaiSach.setText(loaiSachList.get(holder.getAdapterPosition()).getTenLoaiSach());
        holder.txtMaLoaiSach.setText(loaiSachList.get(holder.getAdapterPosition()).getMaLoaiSach());
        holder.txtSoLuong.setText(String.valueOf(soLuong));

        holder.itemLoaiSach.setOnClickListener(view -> {
            showEditDialog(loaiSachList.get(holder.getAdapterPosition()));
        });
    }

    @Override
    public int getItemCount() {
        if (loaiSachList != null) {
            return loaiSachList.size();
        }
        return 0;
    }

    public void add(LoaiSach loaiSach) {
        if (loaiSachDAO.add(loaiSach)) {
            loaiSachList.clear();
            loaiSachList.addAll(loaiSachDAO.getList());
            notifyDataSetChanged();
        } else {
            Toast.makeText(context, "Thêm thất bại", Toast.LENGTH_SHORT).show();
        }
    }

    public void edit(LoaiSach loaiSach) {
        if (loaiSachDAO.update(loaiSach)) {
            loaiSachList.clear();
            loaiSachList.addAll(loaiSachDAO.getList());
            notifyDataSetChanged();
        } else {
            Toast.makeText(context, "Sửa thất bại", Toast.LENGTH_SHORT).show();
        }
    }

    public void remove(int position){
        LoaiSach p = loaiSachList.get(position);
        if (loaiSachDAO.remove(p.getMaLoaiSach())){
            loaiSachList.remove(position);
            notifyItemRemoved(position);
        } else {
            Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
        }
    }

    public void reload(){
        loaiSachList.clear();
        loaiSachList.addAll(loaiSachDAO.getList());
        sachList.clear();
        sachList.addAll(sachDAO.getList());
        notifyDataSetChanged();
    }
    private void showEditDialog(LoaiSach loaiSach) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add, null);
        builder.setView(view);

        TextInputLayout txtInput0, txtInput1, txtInput2;
        TextInputEditText edtAdd0, edtAdd1;
        TextView txtNameAdd;
        LinearLayout layoutAddImage;
        Button btnAdd0, btnAdd1;

        txtInput0 = view.findViewById(R.id.txtInput0);
        txtInput1 = view.findViewById(R.id.txtInput1);
        txtInput2 = view.findViewById(R.id.txtInput2);
        edtAdd0 = view.findViewById(R.id.edtAdd0);
        edtAdd1 = view.findViewById(R.id.edtAdd1);
        txtNameAdd = view.findViewById(R.id.txtNameAdd);
        layoutAddImage = view.findViewById(R.id.layoutAddImage);
        btnAdd0 = view.findViewById(R.id.btnAdd0);
        btnAdd1 = view.findViewById(R.id.btnAdd1);

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        builder.setCancelable(true);

        txtNameAdd.setText("Sửa loại sách");
        txtInput0.setHint("Tên loại sách");
        txtInput1.setHint("Mã loại sách");

        txtInput2.setVisibility(View.GONE);
        layoutAddImage.setVisibility(View.GONE);

        btnAdd0.setText("Hủy");
        btnAdd1.setText("Sửa");

        edtAdd0.setText(loaiSach.getMaLoaiSach());
        edtAdd1.setText(loaiSach.getTenLoaiSach());

        btnAdd1.setOnClickListener(view1 -> {
            String ten, ma;
            ten = edtAdd0.getText().toString().trim();
            ma = edtAdd1.getText().toString().trim();

            edit(new LoaiSach(ma, ten));
            alertDialog.dismiss();
        });

        btnAdd0.setOnClickListener(view1 -> {
            alertDialog.dismiss();
        });
    }
    public class ViewHoler extends RecyclerView.ViewHolder {
        TextView txtLoaiSach, txtMaLoaiSach, txtSoLuong;
        CardView itemLoaiSach;

        public ViewHoler(@NonNull View itemView) {
            super(itemView);
            txtLoaiSach = itemView.findViewById(R.id.txtLoaiSach);
            txtMaLoaiSach = itemView.findViewById(R.id.txtMaLoaiSach);
            txtSoLuong = itemView.findViewById(R.id.txtSoLuong);
            itemLoaiSach = itemView.findViewById(R.id.itemLoaiSach);
        }
    }
}
