package com.appnew.libmanager.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.appnew.libmanager.DAO.ThanhVienDAO;
import com.appnew.libmanager.DAO.UserDAO;
import com.appnew.libmanager.Model.ThanhVien;
import com.appnew.libmanager.Model.User;
import com.appnew.libmanager.R;

import java.util.ArrayList;
import java.util.List;

public class ThanhVienAdapter extends RecyclerView.Adapter<ThanhVienAdapter.ViewHoler> {
    private Context context;
    private ThanhVienDAO thanhVienDAO;
    private UserDAO userDAO;
    private boolean thanhVien;
    private List<ThanhVien> thanhVienList;
    private List<User> userList;

    public ThanhVienAdapter(Context context, boolean thanhVien) {
        this.context = context;
        this.thanhVien = thanhVien;
        if (thanhVien) {
            thanhVienDAO = new ThanhVienDAO(context);
            thanhVienList = thanhVienDAO.getList();
        } else {
            userDAO = new UserDAO(context);
            userList = userDAO.getListUser();
        }
    }

    @NonNull
    @Override
    public ThanhVienAdapter.ViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_thanhvien, parent, false);
        return new ViewHoler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ThanhVienAdapter.ViewHoler holder, int position) {
        if (thanhVien) {
            holder.txtName.setText(thanhVienList.get(holder.getAdapterPosition()).getName());
            holder.txtMa.setText(thanhVienList.get(holder.getAdapterPosition()).getMa());
            holder.txtNamSinh.setText(String.valueOf(thanhVienList.get(holder.getAdapterPosition()).getNamsinh()));
            holder.txtSDT.setText("0" + thanhVienList.get(holder.getAdapterPosition()).getSdt());
        } else {
            holder.txtName.setText(userList.get(holder.getAdapterPosition()).getName());
            holder.txtMa.setText(String.valueOf(userList.get(holder.getAdapterPosition()).getMa()));
            holder.txtNamSinh.setText(String.valueOf(userList.get(holder.getAdapterPosition()).getNamsinh()));
            holder.txtSDT.setText("0" + userList.get(holder.getAdapterPosition()).getSdt());
        }

    }

    @Override
    public int getItemCount() {
        if (thanhVien) {
            if (thanhVienList != null) {
                return thanhVienList.size();
            }
        } else {
            if (userList != null) {
                return userList.size();
            }
        }

        return 0;
    }

    public void addUser(User user) {
        if (userDAO.add(user)) {
            userList.clear();
            userList.addAll(userDAO.getListUser());
            notifyDataSetChanged();
        } else {
            Toast.makeText(context, "Thêm thất bại", Toast.LENGTH_SHORT).show();
        }
    }

    public void add(ThanhVien thanhVien) {
        if (thanhVienDAO.add(thanhVien)) {
            thanhVienList.clear();
            thanhVienList.addAll(thanhVienDAO.getList());
            notifyDataSetChanged();
        } else {
            Toast.makeText(context, "Thêm thất bại", Toast.LENGTH_SHORT).show();
        }
    }

    public void edit(ThanhVien thanhVien) {
        if (thanhVienDAO.update(thanhVien)) {
            thanhVienList.clear();
            thanhVienList.addAll(thanhVienDAO.getList());
            notifyDataSetChanged();
        } else {
            Toast.makeText(context, "Sửa thất bại", Toast.LENGTH_SHORT).show();
        }
    }

    public void remove(int position){
        ThanhVien p = thanhVienList.get(position);
        if (thanhVienDAO.remove(p.getMa())){
            thanhVienList.remove(position);
            notifyItemRemoved(position);
        } else {
            Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
        }
    }

    public void editUser(User user) {
        if (userDAO.update(user)) {
            userList.clear();
            userList.addAll(userDAO.getListUser());
            notifyDataSetChanged();
        } else {
            Toast.makeText(context, "Sửa thất bại", Toast.LENGTH_SHORT).show();
        }
    }

    public void removeUser(int position){
        User p = userList.get(position);
        if (userDAO.remove(p.getMa())){
            userList.remove(position);
            notifyItemRemoved(position);
        } else {
            Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
        }
    }

    public class ViewHoler extends RecyclerView.ViewHolder {
        TextView txtName, txtSDT, txtNamSinh, txtMa;
        CardView itemThanhVien;

        public ViewHoler(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            txtMa = itemView.findViewById(R.id.txtMa);
            txtNamSinh = itemView.findViewById(R.id.txtNamSinh);
            txtSDT = itemView.findViewById(R.id.txtSDT);
            itemThanhVien = itemView.findViewById(R.id.itemThanhVien);
        }
    }
}
