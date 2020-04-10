package com.example.alarmclock;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.List;

public class Adapter extends BaseAdapter {
    private final List<Alarm> items;
    private final LayoutInflater layoutInflater;
    private final OnItemClickListener onItemClickListener;

    public Adapter(Context ctx, List<Alarm> items, OnItemClickListener onItemClickListener) {
        this.items = items;
        layoutInflater = LayoutInflater.from(ctx);
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Alarm getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return items.get(position).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {
        view = layoutInflater.inflate(R.layout.row_item, null);
        ((TextView) view.findViewById(R.id.timeTxt)).setText(
                String.format("%02d", Integer.valueOf(items.get(i).getHour()) % 12)
                        + ":" + String.format("%02d", Integer.valueOf(items.get(i).getMin()) % 60));
        ((TextView) view.findViewById(R.id.periodTxt)).setText(" " + (Integer.valueOf(items.get(i).getHour()) > 12 ? "PM" : "AM"));
        ToggleButton toggleButton = view.findViewById(R.id.enableBtn);
        toggleButton.setChecked(items.get(i).getEnable() == 1);
        toggleButton.setOnCheckedChangeListener((v, checked) -> onItemClickListener.onEnableItemClick(items.get(i)));
        view.findViewById(R.id.deleteBtn).setOnClickListener((v) -> onItemClickListener.onDeleteItemClick(items.get(i)));
        return view;
    }

    interface OnItemClickListener {
        void onEnableItemClick(Alarm alarm);

        void onDeleteItemClick(Alarm alarm);
    }
}
