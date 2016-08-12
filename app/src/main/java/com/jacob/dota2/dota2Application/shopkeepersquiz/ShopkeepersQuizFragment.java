package com.jacob.dota2.dota2Application.shopkeepersquiz;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jacobkoger.dota2Application.R;
import com.google.gson.Gson;
import com.jacob.dota2.dota2Application.data.items.ItemData;
import com.jacob.dota2.dota2Application.data.items.Items;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ShopkeepersQuizFragment extends Fragment {
    private final List<ItemData> itemsList = new ArrayList<>(0);
    private final List<ItemData> createdList = new ArrayList<>(0);
    private Context mContext;
    private TextView howtoplay;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();

        try (InputStream in = mContext.getAssets().open("Items.json")) {
            final Items Il = new Gson().fromJson(new InputStreamReader(in), Items.class);
            itemsList.addAll(Il.getItemData());
            for (int i = 0, size = itemsList.size(); i < size; i++) {
                ItemData items = itemsList.get(i);
                if (items.getCreated()) {
                    createdList.add(items);

                }
            }

            for (int o = 0, s = createdList.size(); o < s; o++) {
                Log.d("nonCreatedList", String.valueOf(createdList.get(o).getComponents().size()));
            }
        } catch (IOException ignored) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return (inflater.inflate(R.layout.fragment_shopkeepers_quiz, container, false));
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        howtoplay = (TextView) view.findViewById(R.id.howtoplayButton);
        howtoplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}