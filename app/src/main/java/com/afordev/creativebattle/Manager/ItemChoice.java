package com.afordev.creativebattle.Manager;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.afordev.creativebattle.Data.ChoiceData;
import com.afordev.creativebattle.R;

/**
 * Created by penguo on 2018-02-06.
 */

public class ItemChoice extends ChoiceData {
    private TextView tvName, tvExplain, tvSummary;
    private ImageView ivSummary;

    public ItemChoice(View view, ChoiceData choice) {
        super(choice);
        tvName = view.findViewById(R.id.item_choose_tv_name);
        tvExplain = view.findViewById(R.id.item_choose_tv_explain);
        tvSummary = view.findViewById(R.id.item_choose_tv_summary);
        ivSummary = view.findViewById(R.id.item_choose_iv_summary);

        if (choice != null) {
            tvName.setText(name);
            tvExplain.setText(explain);
            tvSummary.setText(summary);
            try {
                ivSummary.setImageResource(typeResId);
            }catch (Exception e){
                ivSummary.setImageResource(R.drawable.ic_error);
            }
        }
    }

    public void setChoice(ChoiceData choice){
        name = choice.getName();
        explain = choice.getExplain();
        summary = choice.getSummary();
        setType(choice.getType());
        if (choice != null) {
            tvName.setText(name);
            tvExplain.setText(explain);
            tvSummary.setText(summary);
            try {
                ivSummary.setImageResource(typeResId);
            }catch (Exception e){
                ivSummary.setImageResource(R.drawable.ic_error);
            }
        }
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
    public void setType(String type) {
        super.setType(type);
        try {
            ivSummary.setImageResource(typeResId);
        }catch (Exception e){
            ivSummary.setImageResource(R.drawable.ic_error);
        }
    }

    @Override
    public void setSummary(String summary) {
        super.setSummary(summary);
        tvSummary.setText(summary);
    }

}
