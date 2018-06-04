package com.example.lenovo.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Paint;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import static com.example.lenovo.myapplication.MainActivity.baza;
import static com.example.lenovo.myapplication.MainActivity.getListId;

/**
 * Created by kensi on 20/04/2018.
 */

public class RecyclerViewAdapterAddedProduct extends RecyclerView.Adapter<RecyclerViewAdapterAddedProduct.MyRecyclerView> {

    private Context mContext;
    private List<Produkt> mData;
    final String id_listy = MainActivity.getListId();
    int id_listyy = Integer.parseInt(id_listy);
 //   final String id_listy = MainActivity.getListId();


    public RecyclerViewAdapterAddedProduct(Context mContext, List<Produkt> mData) {
        this.mContext = mContext;
        this.mData = mData;

    }

    @Override
    public MyRecyclerView onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.item_produkt2, parent, false);
        return new MyRecyclerView(view);
    }

 //   public void setSelected(int id) {

  //  }
    @Override
    public void onBindViewHolder(final MyRecyclerView holder, final int position) {
        holder.tvNazwa.setText(mData.get(position).getName());
        holder.chkSelected.setChecked(mData.get(position).isSelected());
        holder.chkSelected.setTag(mData.get(position));
     //   holder.tvIlosc.setText(baza.getIloscByName(mData.get(position).getName()) + "kg");
        if (mData.get(position).getJednostka() == null) {
            mData.get(position).setJednostka(" ");
        }
     //   holder.tvIlosc.setText(mData.get(position).getIlosc().toString() + " " + mData.get(position).getJednostka());
        if (baza.getIloscByProduktAndList(id_listyy, mData.get(position).getId()) == 0.0f) {
            holder.tvIlosc.setText(" ");
        }
        else if(baza.getIloscByProduktAndList(id_listyy, mData.get(position).getId()) % 1 == 0 ){
            holder.tvIlosc.setText((int)Math.round(baza.getIloscByProduktAndList(id_listyy,mData.get(position).getId())) + " " + baza.getJednostkaByProduktAndList(id_listyy, mData.get(position).getId()));
        }
        else {
            holder.tvIlosc.setText(baza.getIloscByProduktAndList(id_listyy,mData.get(position).getId()) + " " + baza.getJednostkaByProduktAndList(id_listyy, mData.get(position).getId()));
        }
        holder.chkSelected.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
                CheckBox cb = (CheckBox) view;
                Produkt produkt = (Produkt) cb.getTag();

                produkt.setSelected((cb.isChecked()));

                if (cb.isChecked()) {
                    baza.updateSelected(1, mData.get(position).getId(), id_listyy);
                }
            //    setStrikeThroughText(holder, position);
                Produkt p = mData.get(position);
                mData.remove(position);
                mData.add(mData.size(), p);
                notifyDataSetChanged();

              //  notifyItemMoved(position, mData.size()-1);
              //  notify
              //  notifyItemInserted(mData.size());
          //      mData.add(mData.size()-1, p);
             //   notifyDataSetChanged();
            //    notifyItemMoved(position,mData.size());
        //        mData.remove(mData.get(position));

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

        holder.rl_item_produkt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEditDialog(position);
            }
        });
        holder.rl_item_produkt.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                showActionsDialog(position);
                return false;
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

    private void updateProduktName(String name, String oldName, int position) {
        Produkt p = mData.get(position);
        p.setName(name);
        baza.updateProdukt(p, oldName);
        mData.set(position,p);
        notifyItemChanged(position);
    }

    private void updateIlosc(String ilosc, String n, int position) {
        Produkt p = mData.get(position);
        Float f= Float.parseFloat(ilosc);
    //    p.setIlosc(f);
     //   baza.updateProdukt(p, n);
    //    mData.set(position,p);
        baza.updateIlosc(f, mData.get(position).getId(), id_listyy);
        notifyItemChanged(position);
    }

    private void updateJednostka(String jednostka, String n, int position) {
        Produkt p = mData.get(position);
        String s = jednostka;
     //   p.setJednostka(s);
     //   baza.updateProdukt(p, n);
     //   mData.set(position,p);
        baza.updateJednostka(s, mData.get(position).getId(), id_listyy);
        notifyItemChanged(position);
    }

    private void updateCena(String cena, String n, int position) {
        Produkt p = mData.get(position);
        Float f= Float.parseFloat(cena);
        p.setCena_max(f);
        baza.updateProdukt(p, n);
        mData.set(position,p);
        notifyItemChanged(position);
    }
    private void deleteProdukt(int position) {
        baza.deleteProdukt(mData.get(position));
        mData.remove(position);
        // notifyItemRemoved(position);
        notifyDataSetChanged();
    }
    private void showActionsDialog(final int position) {
        CharSequence colors[] = new CharSequence[]{"Zmien nazwe", "Usun"};

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Wybierz opcje");
        builder.setItems(colors, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    showChangeNameDialog(position);
                }
                else{
                    deleteProdukt(position);
                }

            }
        });
        builder.show();
    }

    private EditText nameEditText;
    private EditText iloscEditText;
    private EditText jednostkaEditText;
    private EditText cenaEditText;


    private void showEditDialog(final int position) {
        final String oldName = mData.get(position).getName();
     //   final String oldIlosc = mData.get(position).getIlosc().toString();
        final String oldIlosc = baza.getIloscByProduktAndList(id_listyy,mData.get(position).getId()).toString();
   //     final String oldJednostka = mData.get(position).getJednostka();
        final String oldJednostka = baza.getJednostkaByProduktAndList(id_listyy, mData.get(position).getId());
        final String oldCena = mData.get(position).getCena_max().toString();

        //    Toast.makeText(context, "Edit", Toast.LENGTH_SHORT).show();
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.dialog_set_details_added_produkt, null);
    //    nameEditText = (EditText) view.findViewById(R.id.change_name_produkt_edit_text2);
        iloscEditText = (EditText) view.findViewById(R.id.et_edit_ilosc);
        jednostkaEditText = (EditText) view.findViewById(R.id.et_edit_jednostka);
        cenaEditText = (EditText) view.findViewById(R.id.et_edit_cena);

    //    nameEditText.setText(oldName);
        iloscEditText.setText(oldIlosc);
        jednostkaEditText.setText(oldJednostka);
        cenaEditText.setText(oldCena);

        builder.setView(view)
                .setTitle("Edycja")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                 //       String name = nameEditText.getText().toString();
                        String ilosc = iloscEditText.getText().toString();
                        String jednostka = jednostkaEditText.getText().toString();
                        String cena = cenaEditText.getText().toString();

                //        updateProduktName(name,oldName,position);
                        updateIlosc(ilosc,oldName, position);
                        updateCena(cena, oldName, position);
                        updateJednostka(jednostka, oldName, position);
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();
    }


    private void showChangeNameDialog(final int position) {
        final String oldName = mData.get(position).getName();
        //    Toast.makeText(context, "Edit", Toast.LENGTH_SHORT).show();
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.dialog_change_produkt_name, null);
        nameEditText = (EditText) view.findViewById(R.id.change_name_produkt_edit_text);
        builder.setView(view)
                .setTitle("Zmiena nazwy")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String name = nameEditText.getText().toString();
                        // changeNameItem(name,position);
                        updateProduktName(name,oldName,position);

                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();
    }

    public static class MyRecyclerView extends RecyclerView.ViewHolder
    {
        private TextView tvNazwa;
        private CheckBox chkSelected;
        private RelativeLayout rl_item_produkt;
        private TextView tvIlosc;

        public MyRecyclerView(View itemView) {
            super(itemView);

            tvNazwa = (TextView) itemView.findViewById(R.id.tvNazwa);
            chkSelected = (CheckBox) itemView.findViewById(R.id.chkSelected);
            rl_item_produkt = (RelativeLayout) itemView.findViewById(R.id.rl_item_produkt);
            tvIlosc = (TextView) itemView.findViewById(R.id.tvIlosc);
        }
    }
}
