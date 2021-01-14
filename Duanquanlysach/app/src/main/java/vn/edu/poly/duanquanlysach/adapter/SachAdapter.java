package vn.edu.poly.duanquanlysach.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import vn.edu.poly.duanquanlysach.R;
import vn.edu.poly.duanquanlysach.model.Sachclass;
import vn.edu.poly.duanquanlysach.sqlite.Sachsql;

public class SachAdapter extends RecyclerView.Adapter<SachAdapter.ViewHolder> {
    List<Sachclass> list;
    Context context;
    Sachclass sachclass;

    public SachAdapter(List<Sachclass> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sach1, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.tvTensach1.setText(list.get(position).getTensach());
        holder.tvMasach1.setText(list.get(position).getMasach());
        holder.tvSoluong1.setText(list.get(position).getSoluong());
        holder.tvGiabia1.setText(list.get(position).getGiabia());
        holder.imgdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(view.getContext());
              builder.setTitle("Thông Báo");
                builder.setMessage("bạn có muốn xóa?");
                builder.setPositiveButton("đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Sachsql sachsql=new Sachsql(view.getContext());
                        Sachclass sachclass=list.get(position);
                        list.remove(position);
                        long result=sachsql.xoasach(sachclass);
                        notifyDataSetChanged();
                        if (result>0){
                            Toast.makeText(view.getContext(), "bạn đã xóa thành công", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(view.getContext(), "xóa thất bại", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
                builder.setNegativeButton("hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(view.getContext(), "bạn đã hủy xóa", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.create().show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView imgBook1;
        public TextView tvTensach1;
        public TextView tvTitle;
        public TextView tvMasach1;
        public TextView tvTitleSl;
        public TextView tvSoluong1;
        public TextView tvGiabia1;
        public ImageView imgdelete;
        public ViewHolder(View item) {
            super(item);
            imgBook1 = (ImageView) item.findViewById(R.id.img_book1);
            tvTensach1 = (TextView) item.findViewById(R.id.tv_tensach1);
            tvTitle = (TextView) item.findViewById(R.id.tv_title);
            tvMasach1 = (TextView) item.findViewById(R.id.tv_masach1);
            tvTitleSl = (TextView) item.findViewById(R.id.tv_title_sl);
            tvSoluong1 = (TextView) item.findViewById(R.id.tv_soluong1);
            tvGiabia1 = (TextView) item.findViewById(R.id.tv_giabiasach);
            imgdelete=item.findViewById(R.id.img_delete_book);
        }
    }


}
