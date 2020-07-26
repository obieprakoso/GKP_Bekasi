package com.marco.gkp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.marco.gkp.R;
import com.marco.gkp.model.CariJemaatModel;

import java.util.ArrayList;
import java.util.List;

public class JemaatAdapter extends RecyclerView.Adapter<JemaatAdapter.MyHolder>{


    List<CariJemaatModel> jemaatList;
    List<CariJemaatModel> jemaatSearchList;
    Context context;

    public JemaatAdapter(List<CariJemaatModel> listdata) {
        this.jemaatList = listdata;
        jemaatSearchList = new ArrayList<>(listdata);
        notifyDataSetChanged();
    }

    @Override
    public JemaatAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_row_cari_jemaat,parent,false);

        JemaatAdapter.MyHolder myHolder = new JemaatAdapter.MyHolder(view);
        return myHolder;
    }


    public void onBindViewHolder(final JemaatAdapter.MyHolder holder, int position) {
        final CariJemaatModel data = jemaatList.get(position);

        holder.name.setText(data.getName());
        holder.no_regis.setText(data.getNo_regis());
        holder.alamat.setText(data.getAlamat());
        holder.status.setText(data.getStatus());
        holder.kelamin.setText(data.getJenis_kelamin());

        if (data.getJenis_kelamin().equals("Laki-Laki")){

            holder.img_gander.setImageResource(R.drawable.man);

        }else {
            holder.img_gander.setImageResource(R.drawable.woman);
        }



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent toProviderDetail = new Intent(v.getContext(), ProviderDetailActivity.class);
//                toProviderDetail.putExtra("id_provider", id_provider);
//                v.getContext().startActivity(toProviderDetail);
            }
        });


    }

    @Override
    public int getItemCount() {
        return jemaatList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder{

        public TextView name, no_regis, alamat, status, kelamin;
        public ImageView img_gander;

        public MyHolder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.tv_nama);
            alamat = (TextView) itemView.findViewById(R.id.tv_alamat);
            no_regis = (TextView) itemView.findViewById(R.id.tv_noregis);
            status = (TextView) itemView.findViewById(R.id.tv_status);
            kelamin = (TextView) itemView.findViewById(R.id.tv_kelamin);
            img_gander = (ImageView) itemView.findViewById(R.id.img_nama);

//            name = (TextView) itemView.findViewById(R.id.tv_name_jemaat);
//            no_regis = (TextView) itemView.findViewById(R.id.tv_noregis_jemaat);
////            jenis_kelamin = (TextView) itemView.findViewById(R.id.city);
//            alamat = (TextView) itemView.findViewById(R.id.tv_jemaat_alamat);

        }
    }

    public Filter getFilter() {
        return jemaatFilter;
    }

    private Filter jemaatFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<CariJemaatModel> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(jemaatSearchList);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (CariJemaatModel item : jemaatSearchList) {
                    if (item.getName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                    else if (item.getNo_regis().toLowerCase().contains(filterPattern)){
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
            jemaatList.clear();
            jemaatList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
}