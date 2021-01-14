package vn.edu.poly.duanquanlysach.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import vn.edu.poly.duanquanlysach.R;
import vn.edu.poly.duanquanlysach.activity.MenuActivity;
import vn.edu.poly.duanquanlysach.activity.TheloaiActivity;
import vn.edu.poly.duanquanlysach.model.Hoadonclass;
import vn.edu.poly.duanquanlysach.model.Nguoidungclass;
import vn.edu.poly.duanquanlysach.model.Theloaiclass;
import vn.edu.poly.duanquanlysach.sqlite.Theloaisql;

public class TheLoaiAdapter extends RecyclerView.Adapter<TheLoaiAdapter.ViewHolder> implements Filterable {
    List<Theloaiclass> list;
    List<Theloaiclass> listFull;
    Context context;
    private AlertDialog alertDialog;

    public TheLoaiAdapter(List<Theloaiclass> list, Context context) {
        this.list = list;
        this.context = context;
        listFull=new ArrayList<>(list);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_theloai, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.tvMatheloai.setText(list.get(position).getMatheloai());
        holder.tvTentheloai.setText(list.get(position).getTentheloai());
        holder.tvVitri.setText(list.get(position).getVitri());
        holder.imgDeleteTheloai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Bạn có muốn xóa?");
                final Theloaiclass theloaiclass = list.get(position);
                final Theloaisql theloaisql = new Theloaisql(view.getContext());
                builder.setPositiveButton("xóa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(view.getContext(), "bạn đã xóa" + list.get(position).getTentheloai(), Toast.LENGTH_SHORT).show();
                        theloaisql.xoatheloai(theloaiclass);
                        list.remove(position);
                        notifyDataSetChanged();


                    }
                });
                builder.setNegativeButton("hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(view.getContext(), "bạn đã hủy xóa", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.create();
                builder.show();
            }
        });
        holder.imgUpdateTheloai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                View alert = LayoutInflater.from(view.getContext()).inflate(R.layout.dialog_add_theloai, null);
                builder.setView(alert);
                final Theloaisql theloaisql = new Theloaisql(view.getContext());
                //khai báo && tham chiếu
                TextView tvTitleTheloai;
                final TextInputEditText tvUpdateMatheloai;
                final TextInputEditText tvUpdateTentheloai;
                final TextInputEditText tvUpdateMota;
                final TextInputEditText tvUpdateViTri;
                final Button btn_update_theloai = alert.findViewById(R.id.btn_add_theloai);
                final Button btn_huyupdate_theloai = alert.findViewById(R.id.btn_huyadd_theloai);
                tvTitleTheloai = (TextView) alert.findViewById(R.id.tv_title_theloai);
                tvUpdateMatheloai = (TextInputEditText) alert.findViewById(R.id.tv_update_matheloai);
                tvUpdateTentheloai = (TextInputEditText) alert.findViewById(R.id.tv_update_tentheloai);
                tvUpdateMota = (TextInputEditText) alert.findViewById(R.id.tv_update_mota);
                tvUpdateViTri = (TextInputEditText) alert.findViewById(R.id.tv_update_vitri);

                //lấy lại dữ liệu
                tvUpdateMatheloai.setEnabled(false);
                tvUpdateViTri.setEnabled(false);
                tvUpdateTentheloai.setEnabled(false);
                tvUpdateMota.setEnabled(false);
                final Theloaiclass theloaiclass = list.get(position);
                tvUpdateMatheloai.setText(theloaiclass.getMatheloai());
                tvUpdateTentheloai.setText(theloaiclass.getTentheloai());
                tvUpdateMota.setText(theloaiclass.getMota());
                tvUpdateViTri.setText(theloaiclass.getVitri());
                btn_update_theloai.setText("Sửa");


                btn_update_theloai.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        tvUpdateViTri.setEnabled(true);
                        tvUpdateTentheloai.setEnabled(true);
                        tvUpdateMota.setEnabled(true);

                        btn_update_theloai.setText("Sửa xong");
                        btn_update_theloai.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (tvUpdateMatheloai.getText().toString().equals("") || tvUpdateTentheloai.getText().toString().equals("") || tvUpdateMota.getText().toString().equals("") || tvUpdateViTri.getText().toString().equals("")) {
                                    Toast.makeText(v.getContext(), "không để rỗng", Toast.LENGTH_SHORT).show();
                                } else if (tvUpdateViTri.getText().toString().equals("A") || tvUpdateViTri.getText().toString().equals("B") || tvUpdateViTri.getText().toString().equals("C")) {
                                    String a = tvUpdateMatheloai.getText().toString();
                                    String b = tvUpdateTentheloai.getText().toString();
                                    String c = tvUpdateMota.getText().toString();
                                    String d = tvUpdateViTri.getText().toString();
                                    Theloaiclass theloaiclass1 = new Theloaiclass();
                                    theloaiclass1.matheloai = a;
                                    theloaiclass1.tentheloai = b;
                                    theloaiclass1.mota = c;
                                    theloaiclass1.vitri = d;
                                    long result = theloaisql.updatetheloai(theloaiclass1);
                                    notifyDataSetChanged();
                                    if (result > 0) {

                                        Toast.makeText(v.getContext(), "bạn đã sửa thành công", Toast.LENGTH_SHORT).show();

                                    } else {
                                        Toast.makeText(v.getContext(), "không thành công", Toast.LENGTH_SHORT).show();
                                    }
                                    list = theloaisql.getalltheloai();
                                    alertDialog.dismiss();
                                } else {
                                    Toast.makeText(v.getContext(), "Vui lòng nhập vị trí A, B, C (Viết hoa)", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });

                    }
                });
                btn_huyupdate_theloai.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
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

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgTheloaisach;
        public TextView tvMatheloai;
        public TextView tvTentheloai;
        public TextView tvVitri;
        public ImageView imgUpdateTheloai;
        public ImageView imgDeleteTheloai;
        Theloaisql theloaisql;

        public ViewHolder(View view) {
            super(view);
            imgTheloaisach = (ImageView) view.findViewById(R.id.img_theloaisach);
            tvMatheloai = (TextView) view.findViewById(R.id.tv_matheloai);
            tvTentheloai = (TextView) view.findViewById(R.id.tv_tentheloai);
            tvVitri = (TextView) view.findViewById(R.id.tv_vitri);
            imgUpdateTheloai = (ImageView) view.findViewById(R.id.img_update_theloai);
            imgDeleteTheloai = (ImageView) view.findViewById(R.id.img_delete_theloai);


        }
    }
    @Override
    public Filter getFilter() {
        return listFilter;
    }

    private Filter listFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            List<Theloaiclass> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(listFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Theloaiclass item : listFull) {
                    if (item.getTentheloai().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            list.clear();
            list.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
}
