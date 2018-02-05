package com.afordev.creativebattle.Data;

import java.util.ArrayList;

/**
 * Created by penguo on 2018-02-01.
 */

public class Card {
    private String name, explain;
    private int cost;
    private int hp, atk;
    private int stamina;
    private int special1, special2;

    public Card(String name, String explain, int cost, int hp, int atk, int special1, int special2) {
        this.name = name;
        this.explain = explain;
        this.cost = cost;
        this.hp = hp;
        this.atk = atk;
        this.special1 = special1;
        this.special2 = special2;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAtk(int atk) {
        this.atk = atk;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public void setStamina(int stamina) {
        this.stamina = stamina;
    }

    public void setSpecial1(int special1) {
        this.special1 = special1;
    }

    public void setSpecial2(int special2) {
        this.special2 = special2;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public String getName() {
        return name;
    }

    public int getAtk() {
        return atk;
    }

    public int getCost() {
        return cost;
    }

    public int getHp() {
        return hp;
    }

    public int getStamina() {
        return stamina;
    }

    public int getSpecial1() {
        return special1;
    }

    public int getSpecial2() {
        return special2;
    }

    public String getExplain() {
        return explain;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(name + ",");
        sb.append(explain + ",");
        sb.append(cost + ",");
        sb.append(hp + ",");
        sb.append(atk + ",");
        sb.append(special1 + ",");
        sb.append(special2+"");
        return sb.toString();
    }
}
