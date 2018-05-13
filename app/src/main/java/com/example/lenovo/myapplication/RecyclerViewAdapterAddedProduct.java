package com.example.lenovo.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Paint;
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
 //   final String id_listy = MainActivity.getListId();


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
    public void onBindViewHolder(final MyRecyclerView holder, final int position) {
        holder.tvNazwa.setText(mData.get(position).getName());
        holder.chkSelected.setChecked(mData.get(position).isSelected());
        holder.chkSelected.setTag(mData.get(position));


        holder.chkSelected.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
                CheckBox cb = (CheckBox) view;
                Produkt produkt = (Produkt) cb.getTag();

                produkt.setSelected((cb.isChecked()));
                setStrikeThroughText(holder, position);
           //     MainActivity.itemCounter[Integer.parseInt(id_listy)]--;
             //   notifyDataSetChanged();
          //      produkt.getName()
              //  mData.get(position).setSelected(cb.isChecked());
             //   String produkt_name = mData.get(position).getName();
             //   String produkt_name = holder.tvNazwa.getText().toString();
             //   if (produkt_name.equals(mData.get(position).getName())) {
              //      strikeThroughText(holder.tvNazwa);
              //      holder.rl_item_produkt.setBackgroundColor(R.color.colorPrimary);
             //   }

            //    TextView tv =  (TextView) view.findViewWithTag(cb.getTag());
             //   strikeThroughText(tv);
             //   notifyDataSetChanged();
            }
        });

    }

    public void updateData(List<Produkt> viewModels) {
        mData.clear();
        mData.addAll(viewModels);
        notifyDataSetChanged();
    }
    @Override
    public  int getItemCount() {
        return mData.size();
    }

  //  public  static int getItemCounter() {
    //    return itemCounter;
  //  }
    private void strikeThroughText(TextView textView) {
        textView.setPaintFlags(textView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
    }

    @SuppressLint("ResourceAsColor")
    private void setStrikeThroughText(final MyRecyclerView holder, final int position) {
        strikeThroughText(holder.tvNazwa);
        holder.rl_item_produkt.setBackgroundColor(R.color.colorPrimary);
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
