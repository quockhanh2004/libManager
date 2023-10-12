package com.appnew.libmanager.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.appnew.libmanager.DAO.PhieuMuonDAO;
import com.appnew.libmanager.DAO.SachDAO;
import com.appnew.libmanager.Model.PhieuMuon;
import com.appnew.libmanager.Model.Sach;
import com.appnew.libmanager.Model.Top10Sach;
import com.appnew.libmanager.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class Top10Adapter extends RecyclerView.Adapter<Top10Adapter.ViewHoler> {
    private Context context;
    private SachDAO sachDAO;
    private PhieuMuonDAO phieuMuonDAO;
    private List<Top10Sach> top10SachList = new ArrayList<>();
    private List<Sach> sachList;
    private List<PhieuMuon> phieuMuonList;

    public Top10Adapter(Context context) {
        this.context = context;
        sachDAO = new SachDAO(context);
        phieuMuonDAO = new PhieuMuonDAO(context);
        sachList = sachDAO.getList();
        phieuMuonList = phieuMuonDAO.getList();

        top10SachList = getTop10SachList(sachList, phieuMuonList);
    }

    @NonNull
    @Override
    public Top10Adapter.ViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_top10, parent, false);
        return new ViewHoler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Top10Adapter.ViewHoler holder, int position) {
        holder.txtTenSach.setText(top10SachList.get(holder.getAdapterPosition()).getTenSach());
        holder.txtMaSach.setText(top10SachList.get(holder.getAdapterPosition()).getMaSach());
        holder.txtLoaiSach.setText(sachDAO.getSach(top10SachList.get(holder.getAdapterPosition()).getLoaiSach()).getTenLoaiSach());
        holder.txtSoLuong.setText(String.valueOf(top10SachList.get(holder.getAdapterPosition()).getSoLuong()));

    }

    @Override
    public int getItemCount() {
        return top10SachList.size();
    }

    public class ViewHoler extends RecyclerView.ViewHolder {
        TextView txtTenSach, txtMaSach, txtLoaiSach, txtSoLuong;
        CardView itemTop10;

        public ViewHoler(@NonNull View itemView) {
            super(itemView);
            txtTenSach = itemView.findViewById(R.id.txtTenSach);
            txtMaSach = itemView.findViewById(R.id.txtMaSach);
            txtLoaiSach = itemView.findViewById(R.id.txtLoaiSach);
            txtSoLuong = itemView.findViewById(R.id.txtSoLuong);
            itemTop10 = itemView.findViewById(R.id.itemTop10);
        }
    }

    public void reload(){
        sachList.clear();
        phieuMuonList.clear();
        phieuMuonList.addAll(phieuMuonDAO.getList());
        sachList.addAll(sachDAO.getList());

        top10SachList = getTop10SachList(sachList, phieuMuonList);
        notifyDataSetChanged();
    }

    public void processSach(List<Sach> sachList, List<PhieuMuon> phieuMuonList, int index, List<Top10Sach> top10SachList) {
        if (index < sachList.size()) {
            Sach s = sachList.get(index);
            int soLuong = countSoLuong(phieuMuonList, s.getMa(), 0);
            top10SachList.add(new Top10Sach(s.getName(), s.getMa(), s.getMaloaisach(), soLuong));
            processSach(sachList, phieuMuonList, index + 1, top10SachList);
        }
    }

    public int countSoLuong(List<PhieuMuon> phieuMuonList, String maSach, int index) {
        if (index < phieuMuonList.size()) {
            PhieuMuon p = phieuMuonList.get(index);
            if (p.getMaSach().equals(maSach)) {
                return 1 + countSoLuong(phieuMuonList, maSach, index + 1);
            }
            return countSoLuong(phieuMuonList, maSach, index + 1);
        }
        return 0;
    }

    public List<Top10Sach> getTop10SachList(List<Sach> sachList, List<PhieuMuon> phieuMuonList) {
        List<Top10Sach> top10SachList = new ArrayList<>();
        processSach(sachList, phieuMuonList, 0, top10SachList);

        Collections.sort(top10SachList, new Comparator<Top10Sach>() {
            @Override
            public int compare(Top10Sach t1, Top10Sach t2) {
                return t2.getSoLuong() - t1.getSoLuong();
            }
        });
        List<Top10Sach> top10 = new ArrayList<>();
        if (top10SachList.size() > 10) {
            top10 = top10SachList.subList(0, 10);
        }

        Iterator<Top10Sach> bang0 = top10.iterator();
        while (bang0.hasNext()){
            Top10Sach sach = bang0.next();
            if (sach.getSoLuong() == 0){
                bang0.remove();
            }
        }
//        top10 = bang0;
        return top10;
    }

}
