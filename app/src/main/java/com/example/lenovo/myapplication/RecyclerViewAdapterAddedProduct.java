package com.example.lenovo.myapplication;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by kensi on 20/04/2018.
 */

public class RecyclerViewAdapterAddedProduct extends RecyclerView.Adapter<RecyclerViewAdapterAddedProduct.MyRecyclerView> {

    private Context mContext;
    private List<Produkt> mData;

    public RecyclerViewAdapterAddedProduct(Context mContext, List<Produkt> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public MyRecyclerView onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.item_produkt, parent, false);
        return new MyRecyclerView(view);
    }

    @Override
    public void onBindViewHolder(MyRecyclerView holder, int position) {
        holder.tvNazwa.setText(mData.get(position).getName());
        holder.chkSelected.setChecked(mData.get(position).isSelected());
        holder.chkSelected.setTag(mData.get(position));
    }

    public void updateData(List<Produkt> viewModels) {
        mData.clear();
        mData.addAll(viewModels);
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyRecyclerView extends RecyclerView.ViewHolder
    {
        private TextView tvNazwa;
        private CheckBox chkSelected;
        private RelativeLayout rl_item_produkt;

        public MyRecyclerView(View itemView) {
            super(itemView);

            tvNazwa = (TextView) itemView.findViewById(R.id.tvNazwa);
            chkSelected = (CheckBox) itemView.findViewById(R.id.chkSelected);
            rl_item_produkt = (RelativeLayout) itemView.findViewById(R.id.rl_item_produkt);

        }
    }
}
