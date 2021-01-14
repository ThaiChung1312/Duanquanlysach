package vn.edu.poly.duanquanlysach.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.behavior.HideBottomViewOnScrollBehavior;

import java.util.List;
import java.util.zip.Inflater;

import vn.edu.poly.duanquanlysach.R;
import vn.edu.poly.duanquanlysach.model.HoaDonChiTietClass;
import vn.edu.poly.duanquanlysach.sqlite.Hdctsqlite;

public class HoaDonChiTietAdapter extends RecyclerView.Adapter<HoaDonChiTietAdapter.ViewHolder> {
    List<HoaDonChiTietClass> list;
    Context context;
    private AlertDialog alertDialog;

    public HoaDonChiTietAdapter(List<HoaDonChiTietClass> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hdct, parent, false);
        return new HoaDonChiTietAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.tvMahdct2.setText(list.get(position).getMahdct());
        holder.tvGiabia2.setText(list.get(position).getSachclass().getGiabia());
        holder.tvSlsach2.setText(String.valueOf(list.get(position).getSoluong()));
        holder.tvmasach2.setText(list.get(position).getSachclass().getMasach());
        holder.tvThanhtien2.setText(String.valueOf(list.get(position).getSoluong() * Integer.parseInt(list.get(position).getSachclass().getGiabia())));
        holder.imgdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setPositiveButton("xóa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        HoaDonChiTietClass hoaDonChiTietClass = list.get(position);
                        Hdctsqlite hdctsqlite = new Hdctsqlite(v.getContext());
                        hdctsqlite.deleteHoaDonChiTietByID(String.valueOf(hoaDonChiTietClass.getMahdct()));
                        list.remove(position);
                        notifyDataSetChanged();
                        Toast.makeText(v.getContext(), "đã xóa", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog.dismiss();
                    }
                });
                builder.create();
                alertDialog = builder.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        public TextView tvMahdct2;
        public TextView tvGiabia2;
        public TextView tvSlsach2, tvmasach2;
        public ImageView imgdelete, imgupdate;
        public TextView tvThanhtien2;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMahdct2 = (TextView) itemView.findViewById(R.id.tv_mahdct2);
            tvGiabia2 = (TextView) itemView.findViewById(R.id.tv_giabia2);
            tvSlsach2 = (TextView) itemView.findViewById(R.id.tv_slsach2);
            tvThanhtien2 = (TextView) itemView.findViewById(R.id.tv_thanhtien2);
            imgdelete = itemView.findViewById(R.id.img_deletehdct2);
            imgupdate = itemView.findViewById(R.id.img_updatehdct2);
            tvmasach2 = itemView.findViewById(R.id.tv_masach2);


        }
    }
}
