package com.afordev.creativebattle.Data;

import com.afordev.creativebattle.R;

/**
 * Created by penguo on 2018-02-06.
 */

public class ChoiceData {
    protected String name, explain, summary;
    protected String type;
    protected int typeResId;

    public ChoiceData(String name, String explain, String type, String summary) {
        this.name = name;
        this.explain = explain;
        setType(type);
        this.summary = summary;
    }

    public ChoiceData(ChoiceData choice) {
        if (choice != null) {
            this.name = choice.getName();
            this.explain = choice.getExplain();
            setType(choice.getType());
            this.summary = choice.getSummary();
        }
    }

    public void setType(String type) {
        this.type = type;
        switch (type) {
            case ("card"):
                typeResId = R.drawable.ic_card;
                break;
            default:
                typeResId = R.drawable.ic_error;
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getName() {
        return name;
    }

    public String getExplain() {
        return explain;
    }

    public String getType() {
        return type;
    }

    public int getTypeResId() {
        return typeResId;
    }

    public String getSummary() {
        return summary;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
//        sb.append()

        return sb.toString();
    }
}
