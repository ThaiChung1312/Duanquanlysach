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

public class TopTrendAdapter extends RecyclerView.Adapter<TopTrendAdapter.ViewHolder> {
    List<Sachclass> list;
    Context context;
    Sachclass sachclass;

    public TopTrendAdapter(List<Sachclass> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sach, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.tvMasach1.setText(list.get(position).getMasach());
        holder.tvSoluong1.setText(list.get(position).getSoluong());
        holder.tvchiso.setText((position + 1) + "");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
//        public ImageView imgBook;
//        public Spinner spinner;
//        public TextView tvTensach;
//        public TextView tvSoluong;
//        public ImageView imgUpdateBook;
//        public ImageView imgDeleteBook;


        public ImageView imgBook1;
        public TextView tvTensach1;
        public TextView tvTitle;
        public TextView tvMasach1;
        public TextView tvTitleSl;
        public TextView tvSoluong1;
        TextView tvchiso;

        public ImageView imgdelete;

        public ViewHolder(View item) {
            super(item);

            tvchiso = item.findViewById(R.id.tv_chiso);
            imgBook1 = (ImageView) item.findViewById(R.id.img_book1);
            tvTensach1 = (TextView) item.findViewById(R.id.tv_tensach1);
            tvTitle = (TextView) item.findViewById(R.id.tv_title);
            tvMasach1 = (TextView) item.findViewById(R.id.tv_masach1);
            tvTitleSl = (TextView) item.findViewById(R.id.tv_title_sl);
            tvSoluong1 = (TextView) item.findViewById(R.id.tv_soluong1);

            imgdelete = item.findViewById(R.id.img_delete_book);


        }

    }


}
