package com.appnew.libmanager.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
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
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.appnew.libmanager.DAO.PhieuMuonDAO;
import com.appnew.libmanager.Model.PhieuMuon;
import com.appnew.libmanager.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class PhieuMuonAdapter extends RecyclerView.Adapter<PhieuMuonAdapter.ViewHoler> {
    private Context context;
    private PhieuMuonDAO phieuMuonDAO;
    private List<PhieuMuon> list;

    public PhieuMuonAdapter(Context context) {
        this.context = context;
        phieuMuonDAO = new PhieuMuonDAO(context);
        list = phieuMuonDAO.getList();
    }

    @NonNull
    @Override
    public PhieuMuonAdapter.ViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_phieumuon, parent, false);
        return new ViewHoler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhieuMuonAdapter.ViewHoler holder, int position) {
        holder.txtMaPhieuMuon.setText(list.get(holder.getAdapterPosition()).getMaPhieuMuon());
        holder.txtNguoiMuon.setText(list.get(holder.getAdapterPosition()).getNguoiMuon());
        holder.txtNgayMuon.setText(list.get(holder.getAdapterPosition()).getNgayMuon());
    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        }
        return 0;

    }

    public void add(PhieuMuon phieuMuon) {
        if (phieuMuonDAO.add(phieuMuon)) {
            list.clear();
            list.addAll(phieuMuonDAO.getList());
            notifyDataSetChanged();
        } else {
            Toast.makeText(context, "Thêm thất bại", Toast.LENGTH_SHORT).show();
        }
    }

    public void edit(PhieuMuon phieuMuon) {
        if (phieuMuonDAO.update(phieuMuon)) {
            list.clear();
            list.addAll(phieuMuonDAO.getList());
            notifyDataSetChanged();
        } else {
            Toast.makeText(context, "Sửa thất bại", Toast.LENGTH_SHORT).show();
        }
    }

    public void remove(int position){
        PhieuMuon p = list.get(position);
        if (phieuMuonDAO.remove(p.getMaPhieuMuon())){
            list.remove(position);
            notifyItemRemoved(position);
        } else {
            Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
        }
    }

    private void showEditDialog(PhieuMuon phieuMuon) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add, null);
        builder.setView(view);

        TextInputLayout txtInput0, txtInput1, txtInput2;
        TextInputEditText edtAdd0, edtAdd1, edtAdd2;
        TextView txtNameAdd;
        LinearLayout layoutAddImage;
        Button btnAdd0, btnAdd1;


        txtInput0 = view.findViewById(R.id.txtInput0);
        txtInput1 = view.findViewById(R.id.txtInput1);
        txtInput2 = view.findViewById(R.id.txtInput2);
        edtAdd0 = view.findViewById(R.id.edtAdd0);
        edtAdd1 = view.findViewById(R.id.edtAdd1);
        edtAdd2 = view.findViewById(R.id.edtAdd2);
        txtNameAdd = view.findViewById(R.id.txtNameAdd);
        layoutAddImage = view.findViewById(R.id.layoutAddImage);
        btnAdd0 = view.findViewById(R.id.btnAdd0);
        btnAdd1 = view.findViewById(R.id.btnAdd1);

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        builder.setCancelable(true);


        txtNameAdd.setText("Sửa phiếu mượn");
        txtInput0.setHint("Mã phiếu mượn");
        txtInput1.setHint("Tên người mượn");
        txtInput2.setHint("Mã sách mượn");
        layoutAddImage.setVisibility(View.GONE);
        btnAdd0.setText("Hủy");
        btnAdd1.setText("Sửa");

        btnAdd1.setOnClickListener(view1 -> {
            String ma, ten, masach;
            Date today = new Date();

            // Định dạng ngày thành chuỗi "DD/MM/YYYY"
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            String formattedDate = sdf.format(today);


            ma = edtAdd0.getText().toString().trim();
            ten = edtAdd1.getText().toString().trim();
            masach = edtAdd2.getText().toString().trim();

            phieuMuon.setMaPhieuMuon(ma);
            phieuMuon.setNguoiMuon(ten);
            phieuMuon.setMaSach(masach);

            edit(phieuMuon);
            alertDialog.dismiss();
        });

        btnAdd0.setOnClickListener(view1 -> {
            alertDialog.dismiss();
        });
    }

    public class ViewHoler extends RecyclerView.ViewHolder {
        CardView itemPhieuMuon;
        TextView txtMaPhieuMuon, txtNguoiMuon, txtNgayMuon;

        public ViewHoler(@NonNull View itemView) {
            super(itemView);
            itemPhieuMuon = itemView.findViewById(R.id.itemPhieuMuon);
            txtMaPhieuMuon = itemView.findViewById(R.id.txtMaPhieuMuon);
            txtNguoiMuon = itemView.findViewById(R.id.txtNguoiMuon);
            txtNgayMuon = itemView.findViewById(R.id.txtNgayMuon);
        }
    }

}
