package com.afordev.creativebattle.Manager;

import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.afordev.creativebattle.Data.CardData;
import com.afordev.creativebattle.R;

/**
 * Created by penguo on 2018-02-05.
 */

public class ItemCard extends CardData {
    private ImageView ivOutline;
    private CardView cardView;
    private TextView tvPrefix, tvName, tvExplain, tvCost, tvHp, tvAtk;

    public ItemCard(View view, CardData card) {
        super(card);
        ivOutline = view.findViewById(R.id.item_card_iv_outline);
        cardView = view.findViewById(R.id.item_card_cardview);
        tvPrefix = view.findViewById(R.id.item_card_tv_prefix);
        tvName = view.findViewById(R.id.item_card_tv_name);
        tvExplain = view.findViewById(R.id.item_card_tv_explain);
        tvCost = view.findViewById(R.id.item_card_tv_cost);
        tvHp = view.findViewById(R.id.item_card_tv_hp);
        tvAtk = view.findViewById(R.id.item_card_tv_atk);
        if (card != null) {
            ivOutline.setVisibility(View.GONE);
            cardView.setVisibility(View.VISIBLE);
            tvPrefix.setText(prefix);
            tvName.setText(name);
            tvExplain.setText(explain);
            tvCost.setText(cost + "");
            tvHp.setText(hp + "");
            tvAtk.setText(atk + "");
        } else {
            ivOutline.setVisibility(View.VISIBLE);
            cardView.setVisibility(View.GONE);
        }
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
    }

    public void setCard(CardData card) {
        prefix = card.getPrefix();
        name = card.getName();
        explain = card.getExplain();
        cost = card.getCost();
        hp = card.getHp();
        atk = card.getAtk();
        if (card != null) {
            ivOutline.setVisibility(View.GONE);
            cardView.setVisibility(View.VISIBLE);
            tvPrefix.setText(prefix);
            tvName.setText(name);
            tvExplain.setText(explain);
            tvCost.setText(cost + "");
            tvHp.setText(hp + "");
            tvAtk.setText(atk + "");
        } else {
            ivOutline.setVisibility(View.VISIBLE);
            cardView.setVisibility(View.GONE);
        }
    }

    public void setReadyMove() {
        if (stamina > 0) {
//            cardView.setCardBackgroundColor();
        }
    }

    public void setReadyTargeted(){

    }

    @Override
    public void setPrefix(String prefix) {
        super.setPrefix(prefix);
        tvPrefix.setText(prefix);
    }

    @Override
    public void setName(String name) {
        super.setName(name);
        tvName.setText(name);
    }

    @Override
    public void setExplain(String explain) {
        super.setExplain(explain);
        tvExplain.setText(explain);
    }

    @Override
    public void setCost(int cost) {
        super.setCost(cost);
        tvCost.setText(cost + "");
    }

    @Override
    public void setHp(int hp) {
        super.setHp(hp);
        tvHp.setText(hp + "");
    }

    @Override
    public void setAtk(int atk) {
        super.setAtk(atk);
        tvAtk.setText(atk + "");
    }

    public TextView getTvAtk() {
        return tvAtk;
    }

    public TextView getTvCost() {
        return tvCost;
    }

    public TextView getTvExplain() {
        return tvExplain;
    }

    public TextView getTvHp() {
        return tvHp;
    }

    public TextView getTvName() {
        return tvName;
    }

    public TextView getTvPrefix() {
        return tvPrefix;
    }
}
