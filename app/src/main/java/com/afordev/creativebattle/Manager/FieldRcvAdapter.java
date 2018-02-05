package com.afordev.creativebattle.Manager;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.afordev.creativebattle.Data.Card;
import com.afordev.creativebattle.R;

import java.util.ArrayList;

/**
 * Created by pengu on 2017-08-10.
 */

public class FieldRcvAdapter extends RecyclerView.Adapter<FieldRcvAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<Card> list;
    private boolean isMyField;

    public FieldRcvAdapter(Context mContext, ArrayList<Card> list, boolean isMyField) {
        this.mContext = mContext;
        this.list = list;
        this.isMyField = isMyField;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvName, tvAtk, tvHp, tvCost, tvExplain;

        public ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.item_choose_tv_name);
            tvAtk = itemView.findViewById(R.id.item_card_tv_atk);
            tvHp = itemView.findViewById(R.id.item_card_tv_hp);
            tvCost = itemView.findViewById(R.id.item_card_tv_cost);
            tvExplain = itemView.findViewById(R.id.item_card_tv_explain);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

        }
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Card card = list.get(position);
        holder.tvName.setText(card.getName());
        holder.tvAtk.setText(card.getAtk()+"");
        holder.tvHp.setText(card.getHp()+"");
        holder.tvCost.setText(card.getCost()+"");
        holder.tvExplain.setText(card.getExplain());
    }

    private void removeItemView(int position) {
        notifyItemRemoved(position);
//        notifyItemRangeChanged(position, dbManager.getSize()); // 지워진 만큼 다시 채워넣기.
    }

    public void onRefresh(){
        notifyDataSetChanged();
    }
}