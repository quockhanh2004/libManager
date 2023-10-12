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

import com.appnew.libmanager.DAO.SachDAO;
import com.appnew.libmanager.Model.Sach;
import com.appnew.libmanager.R;

import java.util.List;

public class SachAdapter extends RecyclerView.Adapter<SachAdapter.ViewHoler> {
    private Context context;
    private SachDAO sachDAO;
    private List<Sach> list;

    public SachAdapter(Context context) {
        this.context = context;
        sachDAO = new SachDAO(context);
        list = sachDAO.getList();
    }

    @NonNull
    @Override
    public SachAdapter.ViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_sach, parent, false);
        return new ViewHoler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SachAdapter.ViewHoler holder, int position) {
        holder.txtTenSach.setText(list.get(holder.getAdapterPosition()).getName());
        holder.txtMaSach.setText(list.get(holder.getAdapterPosition()).getMa());
        holder.txtLoaiSach.setText(list.get(holder.getAdapterPosition()).getMaloaisach());
        holder.txtTacGia.setText(list.get(holder.getAdapterPosition()).getTacgia());
    }

    @Override
    public int getItemCount() {
        if (list != null){
            return list.size();
        }
        return 0;
    }

    public void add(Sach sach) {
        if (sachDAO.add(sach)) {
            list.clear();
            list.addAll(sachDAO.getList());
            notifyDataSetChanged();
        } else {
            Toast.makeText(context, "Thêm thất bại", Toast.LENGTH_SHORT).show();
        }
    }

    public void edit(Sach sach) {
        if (sachDAO.update(sach)) {
            list.clear();
            list.addAll(sachDAO.getList());
            notifyDataSetChanged();
        } else {
            Toast.makeText(context, "Sửa thất bại", Toast.LENGTH_SHORT).show();
        }
    }

    public void remove(int position){
        Sach p = list.get(position);
        if (sachDAO.remove(p.getMa())){
            list.remove(position);
            notifyItemRemoved(position);
        } else {
            Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
        }
    }

    public class ViewHoler extends RecyclerView.ViewHolder {
        TextView txtTenSach, txtTacGia, txtLoaiSach, txtMaSach;
        CardView itemSach;
        public ViewHoler(@NonNull View itemView) {
            super(itemView);
            txtTenSach = itemView.findViewById(R.id.txtTenSach);
            txtMaSach = itemView.findViewById(R.id.txtMaSach);
            txtLoaiSach = itemView.findViewById(R.id.txtLoaiSach);
            txtTacGia = itemView.findViewById(R.id.txtTacGia);
            itemSach = itemView.findViewById(R.id.itemSach);
        }
    }
}
