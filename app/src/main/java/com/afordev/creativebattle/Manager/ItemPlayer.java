package com.afordev.creativebattle.Manager;

import android.view.View;
import android.widget.TextView;

import com.afordev.creativebattle.Data.UserData;
import com.afordev.creativebattle.R;

/**
 * Created by penguo on 2018-02-06.
 */

public class ItemPlayer extends UserData {
    private GameSystem mGameSystem;
    private int hp, cost, costMax;
    private TextView tvName, tvHp, tvCost;

    public ItemPlayer(GameSystem mGameSystem, View view, UserData user) {
        super(user);
        this.mGameSystem = mGameSystem;
        tvName = view.findViewById(R.id.item_player_tv_name);
        tvHp = view.findViewById(R.id.item_player_tv_hp);
        tvCost = view.findViewById(R.id.item_player_tv_cost);
        hp = 40;
        cost = 0;
        costMax = 0;
        if (user != null) {
            tvName.setText(name);
            tvHp.setText(hp + "");
            tvCost.setText(cost + "");
        } else {
            tvName.setText("wait");
            tvHp.setText(0+"");
            tvCost.setText(0+"");
        }
    }

    public void addTurn(){
        costMax++;
        setCost(costMax);
    }

    public void setHp(int hp) {
        this.hp = hp;
        tvHp.setText(hp + "");
    }

    public void setCost(int cost) {
        this.cost = cost;
        tvCost.setText(cost + "");
    }

    public void setCostMax(int costMax) {
        this.costMax = costMax;
    }

    @Override
    public void setName(String name) {
        this.name = name;
        tvName.setText(name);
    }

    public int getHp() {
        return hp;
    }

    public int getCost() {
        return cost;
    }

    public int getCostMax() {
        return costMax;
    }
}
