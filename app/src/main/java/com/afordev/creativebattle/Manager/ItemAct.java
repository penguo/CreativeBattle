package com.afordev.creativebattle.Manager;

import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.afordev.creativebattle.R;

/**
 * Created by penguo on 2018-02-07.
 */

public class ItemAct {
    private LinearLayout layoutBtn;
    private ImageView ivIcon;

    public ItemAct(View view) {
        layoutBtn = view.findViewById(R.id.item_act_layout);
        ivIcon = view.findViewById(R.id.item_act_iv);
    }

    public void setIcon(int resId) {
        ivIcon.setImageResource(resId);
    }

    public void setOnClickListener(View.OnClickListener listener) {
        if(listener!=null){
            layoutBtn.setOnClickListener(listener);
        }else{
            layoutBtn.setOnClickListener(null);
        }
    }
}
