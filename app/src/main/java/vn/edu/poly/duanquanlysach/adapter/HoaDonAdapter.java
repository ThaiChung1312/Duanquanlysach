package vn.edu.poly.duanquanlysach.adapter;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import vn.edu.poly.duanquanlysach.R;
import vn.edu.poly.duanquanlysach.activity.HoadonActivity;
import vn.edu.poly.duanquanlysach.model.HoaDonChiTietClass;
import vn.edu.poly.duanquanlysach.model.Hoadonclass;
import vn.edu.poly.duanquanlysach.model.Nguoidungclass;
import vn.edu.poly.duanquanlysach.sqlite.Hdctsqlite;
import vn.edu.poly.duanquanlysach.sqlite.Hoadonsql;

public class HoaDonAdapter extends RecyclerView.Adapter<HoaDonAdapter.ViewHolder> {
    List<Hoadonclass> list;
    List<HoaDonChiTietClass> listHDCT;
    Context context;
    private AlertDialog alertDialog;

    public HoaDonAdapter(List<Hoadonclass> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hoadon, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.tvMahoadon.setText(list.get(position).getMahoadon());
        holder.tvNgaymua.setText(list.get(position).getNgaymuahang());
        holder.imgDeleteHoadon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                final Hoadonsql hoadonsql = new Hoadonsql(view.getContext());
                final Hoadonclass hoadonclass = list.get(position);
                Hdctsqlite hdctsqlite = new Hdctsqlite(view.getContext());
                final View alert = LayoutInflater.from(view.getContext()).inflate(R.layout.dialog_delete_user, null);
                builder.setView(alert);

                listHDCT = new ArrayList<>();
                listHDCT = hdctsqlite.getAllHoaDonChiTietByID(String.valueOf(list.get(position).getMahoadon()));
                Toast.makeText(view.getContext(), "sl" + listHDCT.size(), Toast.LENGTH_SHORT).show();
               builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                       if (listHDCT.size() > 0) {
                           Toast.makeText(view.getContext(), "muốn xóa hóa đơn bạn phải xóa hóa đơn chi tiết trước", Toast.LENGTH_SHORT).show();

                       } else {
                           list.remove(position);
                           hoadonsql.xoatHoaDon(hoadonclass);
                           notifyDataSetChanged();
                           Toast.makeText(view.getContext(), "xóa thành công" + (position + 1), Toast.LENGTH_SHORT).show();
                       }
                   }
               });



                builder.setNegativeButton("hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.create();
                alertDialog = builder.show();

            }
        });
        holder.imgUpdateHoadon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Hoadonclass hoadonclass = list.get(position);
                final Hoadonsql hoadonsql = new Hoadonsql(view.getContext());
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                View alert = LayoutInflater.from(view.getContext()).inflate(R.layout.dialog_add_hoadon, null);
                TextView tvNameDialog;
                final TextInputEditText edtMahoadon;
                final TextView tv_changeday;
                final TextView tv_ngaymua;
                final TextInputLayout tv1;
                Button btnhuyHoadon;

                tv_changeday = alert.findViewById(R.id.tv_changeday);
                tv1 = alert.findViewById(R.id.tv_1);
                edtMahoadon = (TextInputEditText) alert.findViewById(R.id.edt_mahoadon);
                tv_ngaymua = alert.findViewById(R.id.tv_ngay);
                btnhuyHoadon = (Button) alert.findViewById(R.id.btnhuy_hoadon);
                TextView tv_name_dialog = alert.findViewById(R.id.tv_name_dialog);
                final Button btn_name_sua = alert.findViewById(R.id.btnthem_hoadon);
                edtMahoadon.setEnabled(true);
                btn_name_sua.setText("Sửa");
                tv_name_dialog.setText("Sửa hóa đơn");
                edtMahoadon.setText(hoadonclass.getMahoadon());
                tv_ngaymua.setText(hoadonclass.getNgaymuahang());
                edtMahoadon.setEnabled(false);
                tv1.setEnabled(false);
                tv_ngaymua.setEnabled(false);
                btn_name_sua.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        btn_name_sua.setText("Sửa xong");
                        tv1.setEnabled(true);
                        edtMahoadon.setEnabled(false);
                        tv_ngaymua.setEnabled(true);
                        tv_changeday.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(final View v) {
                                Toast.makeText(v.getContext(), "chọn ngày", Toast.LENGTH_SHORT).show();
                                Calendar calendar = Calendar.getInstance();
                                int year = calendar.get(Calendar.YEAR);
                                int month = calendar.get(Calendar.MONTH);
                                int day = calendar.get(Calendar.DAY_OF_MONTH);
                                DatePickerDialog dialog = new DatePickerDialog(v.getContext(), new DatePickerDialog.OnDateSetListener() {
                                    @Override
                                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                        Toast.makeText(v.getContext(), year + ":" + (month + 1) + ":" + dayOfMonth, Toast.LENGTH_SHORT).show();
                                        tv_ngaymua.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                                    }
                                }, year, month, day);
                                dialog.show();
                            }
                        });
                        btn_name_sua.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                hoadonclass.setMahoadon(list.get(position).getMahoadon());
                                hoadonclass.setNgaymuahang(tv_ngaymua.getText().toString());
                                long result = hoadonsql.updateHoaDon(hoadonclass);
                                if (result > 0) {
                                    Toast.makeText(view.getContext(), "sửa thành công", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(view.getContext(), "sửa thất bại", Toast.LENGTH_SHORT).show();
                                }

                                list = hoadonsql.getallHoaDon();
                                notifyDataSetChanged();
                                alertDialog.dismiss();
                            }
                        });

                    }
                });
                btnhuyHoadon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });
                builder.setView(alert);
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
        public ImageView imgHoadon;
        public TextView tvMahoadon;
        public TextView tvNgaymua;
        public ImageView imgUpdateHoadon;
        public ImageView imgDeleteHoadon;

        public ViewHolder(View view) {
            super(view);
            imgHoadon = (ImageView) view.findViewById(R.id.img_hoadon);
            tvMahoadon = (TextView) view.findViewById(R.id.tv_mahoadon1);
            tvNgaymua = (TextView) view.findViewById(R.id.tv_ngaymua1);

            imgUpdateHoadon = (ImageView) view.findViewById(R.id.img_update_hoadon);
            imgDeleteHoadon = (ImageView) view.findViewById(R.id.img_delete_hoadon);
        }
    }
}
