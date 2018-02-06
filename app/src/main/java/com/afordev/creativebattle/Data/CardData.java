package com.afordev.creativebattle.Data;

/**
 * Created by penguo on 2018-02-01.
 */

public class CardData {
    protected String prefix, name, explain;
    protected int cost, hp, atk, stamina;
    protected String special1, special2;

    public CardData(String prefix, String name, String explain, int cost, int hp, int atk, String special1, String special2) {
        this.prefix = prefix;
        this.name = name;
        this.explain = explain;
        this.cost = cost;
        this.hp = hp;
        this.atk = atk;
        this.special1 = special1;
        this.special2 = special2;
    }

    public CardData(CardData card) {
        if (card != null) {
            this.prefix = card.getPrefix();
            this.name = card.getName();
            this.explain = card.getExplain();
            this.cost = card.getCost();
            this.hp = card.getHp();
            this.atk = card.getAtk();
            this.special1 = card.getSpecial1();
            this.special2 = card.getSpecial2();
        }
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
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

    public void setSpecial1(String special1) {
        this.special1 = special1;
    }

    public void setSpecial2(String special2) {
        this.special2 = special2;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public String getPrefix() {
        return prefix;
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

    public String getSpecial1() {
        return special1;
    }

    public String getSpecial2() {
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
        sb.append(special2 + "");
        return sb.toString();
    }
}
