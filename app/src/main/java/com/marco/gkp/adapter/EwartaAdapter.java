package com.marco.gkp.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.TextView;

import com.marco.gkp.R;
import com.marco.gkp.model.CariJemaatModel;
import com.marco.gkp.model.EwartaModel;

import java.util.ArrayList;
import java.util.List;

public class EwartaAdapter extends RecyclerView.Adapter<EwartaAdapter.MyHolder>{


    List<EwartaModel> ewartaList;
    Context context;

    public EwartaAdapter(List<EwartaModel> listdata) {
        this.ewartaList = listdata;
        notifyDataSetChanged();
    }

    @Override
    public EwartaAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_row_ewarta,parent,false);

        EwartaAdapter.MyHolder myHolder = new EwartaAdapter.MyHolder(view);
        return myHolder;
    }


    public void onBindViewHolder(final EwartaAdapter.MyHolder holder, int position) {
        final EwartaModel data = ewartaList.get(position);

        holder.tgl.setText(data.getDatetime());
        holder.ket_file.setText(data.getKetfile());



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    Intent toPDF = (new Intent(Intent.ACTION_VIEW, Uri.parse(data.getFile())));
                    v.getContext().startActivity(toPDF);
                } catch (Exception e) {
                    e.getStackTrace();
                }

//                Intent toProviderDetail = new Intent(v.getContext(), ProviderDetailActivity.class);
//                toProviderDetail.putExtra("id_provider", id_provider);
//                v.getContext().startActivity(toProviderDetail);
            }
        });


    }

    @Override
    public int getItemCount() {
        return ewartaList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder{

        public TextView tgl, ket_file;

        public MyHolder(View itemView) {
            super(itemView);
            tgl = (TextView) itemView.findViewById(R.id.tv_tanggal);
            ket_file = (TextView) itemView.findViewById(R.id.tv_ketfile);

        }
    }
}