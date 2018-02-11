package com.afordev.creativebattle.Manager;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.afordev.creativebattle.Data.CardData;
import com.afordev.creativebattle.GameActivity;
import com.afordev.creativebattle.R;

/**
 * Created by penguo on 2018-02-05.
 */

public class ItemCard extends CardData {
    private CardData card;
    private Context mContext;
    private GameSystem mGameSystem;
    private ImageView ivOutline;
    private CardView cardView;
    private ConstraintLayout layout, layoutAct;
    private TextView tvPrefix, tvName, tvExplain, tvCost, tvHp, tvAtk;
    private ItemAct itemAct0, itemAct1, itemAct2;
    private boolean isSelected;

    public ItemCard(Context mContext, View view, CardData card, String side) {
        super(card);
        this.mGameSystem = ((GameActivity) mContext).mGameSystem;
        this.card = card;
        ivOutline = view.findViewById(R.id.item_card_iv_outline);
        cardView = view.findViewById(R.id.item_card_cardview);
        layout = view.findViewById(R.id.item_card_layout);
        layoutAct = view.findViewById(R.id.item_card_layout_act);
        tvPrefix = view.findViewById(R.id.item_card_tv_prefix);
        tvName = view.findViewById(R.id.item_card_tv_name);
        tvExplain = view.findViewById(R.id.item_card_tv_explain);
        tvCost = view.findViewById(R.id.item_card_tv_cost);
        tvHp = view.findViewById(R.id.item_card_tv_hp);
        tvAtk = view.findViewById(R.id.item_card_tv_atk);
        isSelected = false;
        itemAct0 = new ItemAct(layoutAct.findViewById(R.id.item_card_act_0));
        itemAct1 = new ItemAct(layoutAct.findViewById(R.id.item_card_act_1));
        itemAct2 = new ItemAct(layoutAct.findViewById(R.id.item_card_act_2));
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
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isSelected = true;
                onRefresh();
            }
        });
        onRefresh();
    }

    public void setCard(CardData card) {
        this.card = card;
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
        onRefresh();
    }

    public void onRefresh() {
        if (stamina > 0) {
            layout.setEnabled(true);
        } else {
            layout.setEnabled(false);
        }
        if (isSelected) {
            layoutAct.setVisibility(View.VISIBLE);
        } else {
            layoutAct.setVisibility(View.GONE);
        }
    }

    public void setReady() {
        if (card != null) {
            stamina = 1;
            onRefresh();
        }
    }


    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
        onRefresh();
    }

    public boolean isSelected() {
        return isSelected;
    }

    @Override
    public void setStamina(int stamina) {
        super.setStamina(stamina);
        onRefresh();
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
}
