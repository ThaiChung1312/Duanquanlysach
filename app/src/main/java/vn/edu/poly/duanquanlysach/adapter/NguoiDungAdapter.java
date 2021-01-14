package vn.edu.poly.duanquanlysach.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import vn.edu.poly.duanquanlysach.R;
import vn.edu.poly.duanquanlysach.activity.TheloaiActivity;
import vn.edu.poly.duanquanlysach.model.Nguoidungclass;
import vn.edu.poly.duanquanlysach.model.Theloaiclass;
import vn.edu.poly.duanquanlysach.sqlite.Nguoidungsql;

public class NguoiDungAdapter extends RecyclerView.Adapter<NguoiDungAdapter.ViewHolder> implements Filterable {
    List<Nguoidungclass> list;
    List<Nguoidungclass> listfull;
    Context context;


    public NguoiDungAdapter(List<Nguoidungclass> list, Context context) {
        this.list = list;
        this.context = context;
        listfull = new ArrayList<>(list);
    }

    //tạo view
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_nguoidung, parent, false);
        return new ViewHolder(view);
    }

    //gán giá trị
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.tvname.setText(list.get(position).getName());
        holder.tvphone.setText(list.get(position).getSdt());
        holder.tv_chucvu.setText(list.get(position).getChucvu());
        holder.imgavt.setImageResource(R.drawable.unnamed);
        holder.imgdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {


                final AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                final Nguoidungclass nguoidungclass = list.get(position);
                final Nguoidungsql nguoidungsql = new Nguoidungsql(view.getContext());
                builder.setTitle("Bạn có muốn xóa?");
                builder.setPositiveButton("xóa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

//                 gọi sql
                        Toast.makeText(view.getContext(), "bạn đã xóa " + list.get(position).getName(), Toast.LENGTH_SHORT).show();
                        nguoidungsql.xoanguoidung(nguoidungclass);
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
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgavt, imgphone, imgdelete;
        public TextView tvname, tvphone, tv_chucvu;
        Nguoidungsql nguoidungsql;

        public ViewHolder(final View itemview) {
            super(itemview);
            tv_chucvu = itemview.findViewById(R.id.tv_chucvu);
            imgavt = itemview.findViewById(R.id.imgavatar);
            tvname = itemview.findViewById(R.id.tvname);
            imgphone = itemview.findViewById(R.id.imgphone);
            tvphone = itemview.findViewById(R.id.tvphone);
            imgdelete = itemview.findViewById(R.id.imgdelete);
            nguoidungsql = new Nguoidungsql(itemview.getContext());

        }
    }

    @Override
    public Filter getFilter() {
        return listFilter;
    }

    private Filter listFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            List<Nguoidungclass> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(listfull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Nguoidungclass item : listfull) {
                    if (item.getName().toLowerCase().contains(filterPattern)) {
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
