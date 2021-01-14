package vn.edu.poly.duanquanlysach.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.behavior.HideBottomViewOnScrollBehavior;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import vn.edu.poly.duanquanlysach.R;
import vn.edu.poly.duanquanlysach.activity.HDCTActivity;
import vn.edu.poly.duanquanlysach.activity.HoadonActivity;
import vn.edu.poly.duanquanlysach.model.HoaDonChiTietClass;
import vn.edu.poly.duanquanlysach.model.Hoadonclass;
import vn.edu.poly.duanquanlysach.model.Sachclass;
import vn.edu.poly.duanquanlysach.sqlite.Hdctsqlite;
import vn.edu.poly.duanquanlysach.sqlite.Sachsql;

public class HoaDonChiTietAdapter extends RecyclerView.Adapter<HoaDonChiTietAdapter.ViewHolder> {
    List<HoaDonChiTietClass> list;
    List<Sachclass> list_masach_adapter=new ArrayList<>();
    Context context;
    public AlertDialog alertDialog;

    public androidx.appcompat.app.AlertDialog alertDialog1;

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
                final AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Bạn có muốn xóa không?");


//                final TextView tongtien=v.findViewById(R.id.tv_tongtien);
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
                        notifyDataSetChanged();
                        alertDialog.dismiss();

                    }
                });
                builder.create();
                alertDialog = builder.show();
            }
        });
        holder.imgupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(v.getContext());
                View alert = LayoutInflater.from(v.getContext()).inflate(R.layout.dialog_add_hdct, null);
                builder.setView(alert);
                TextView tvNameDialog;
                final TextInputEditText tvAddMahdct;

                final TextInputEditText tvAddSlhdct;
                Button btnadd, btnhuy;
                 final Spinner spinner3;
                 spinner3 = alert.findViewById(R.id.spiner_masach_hdct);
                tvNameDialog = (TextView) alert.findViewById(R.id.tv_name_dialog);
                tvAddMahdct = (TextInputEditText) alert.findViewById(R.id.tv_add_mahdct);
                tvAddMahdct.setEnabled(false);
                tvAddSlhdct = (TextInputEditText) alert.findViewById(R.id.tv_add_slhdct);
                btnadd = alert.findViewById(R.id.btn_add_hdct);
                btnhuy = alert.findViewById(R.id.btn_huyadd_hdct);
                gett3(spinner3);
                spinner3.setSelection(getIndexSpinner(spinner3,list.get(position).getSachclass().getMasach()));
                tvNameDialog.setText("Sửa hóa đơn chi tiết");
                final Sachsql sachsql = new Sachsql(v.getContext());
                final Hdctsqlite hdctsqlite = new Hdctsqlite(v.getContext());
                tvAddMahdct.setText(list.get(position).getMahdct());
                tvAddSlhdct.setText(String.valueOf(list.get(position).getSoluong()));
                btnadd.setText("Sửa");
                btnadd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        try {

                            Sachclass sach = sachsql.getSachByID(spinner3.getSelectedItem().toString());

                            if (sach != null) {

                                Hoadonclass hoaDon = new Hoadonclass(list.get(position).getHoadonclass().getMahoadon(), list.get(position).getHoadonclass().getNgaymuahang());
                                HoaDonChiTietClass hdct = new HoaDonChiTietClass(tvAddMahdct.getText().toString(), hoaDon, sach, Integer.parseInt(tvAddSlhdct.getText().toString()));


                                long result = hdctsqlite.updateHoaDonChiTiet(hdct);
                                ((HDCTActivity) context).recreate();
                                alertDialog1.dismiss();
                                int tong = 0;
                                for (int i = 0; i < list.size(); i++) {
                                    tong += list.get(i).getSoluong() * Integer.parseInt(list.get(i).getSachclass().getGiabia());
                                }


                                if (result > 0) {
                                    Toast.makeText(v.getContext(), "Đã sửa thành công", Toast.LENGTH_SHORT).show();


                                } else {
                                    Toast.makeText(v.getContext(), "thất bại", Toast.LENGTH_SHORT).show();

                                }
                            } else {

                                tvAddMahdct.setError("Mã sách không tồn tại");

                            }


                        } catch (Exception ex) {
                            Log.e("Error", ex.toString());
                        }
                    }


                });
                btnhuy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog1.dismiss();
                    }
                });
                builder.create();
                alertDialog1 = builder.show();
            }
        });
    }
    public void gett3(Spinner spinner) {
        Sachsql sachsql=new Sachsql(context);
        list_masach_adapter = sachsql.getallsach();

        ArrayAdapter<Sachclass> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, list_masach_adapter);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

    }

    private int getIndexSpinner(Spinner spinner, String s) {
        for (int j = 0; j < spinner.getCount(); j++) {
            if (spinner.getItemAtPosition(j).toString().equalsIgnoreCase(s)) {
                return j;

            }
        }
        return 0;
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
