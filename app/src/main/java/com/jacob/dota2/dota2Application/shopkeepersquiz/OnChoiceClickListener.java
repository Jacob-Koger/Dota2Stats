package com.jacob.dota2.dota2Application.shopkeepersquiz;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class OnChoiceClickListener implements View.OnClickListener {
    CharSequence item;
    Context context;
    ImageView iv;

    private ItemAnswerContainer itemAnswerContainer;

    public OnChoiceClickListener(CharSequence item, Context context, ImageView iv, ItemAnswerContainer itemAnswerContainer) {
        this.item = item;
        this.context = context;
        this.iv = iv;
        this.itemAnswerContainer = itemAnswerContainer;

    }

    @Override
    public void onClick(View view) {
        Toast.makeText(context, item, Toast.LENGTH_SHORT).show();
        Log.d("stuff", iv.getTag().toString());

    }
}