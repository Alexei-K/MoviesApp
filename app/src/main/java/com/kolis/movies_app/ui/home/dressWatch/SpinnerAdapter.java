package com.kolis.test_catalog_app.ui.home.dressWatch;

import android.content.Context;
import android.widget.ArrayAdapter;

public class SpinnerAdapter extends ArrayAdapter<String> {

    private String[] values;

    public SpinnerAdapter(Context context, int textViewResourceId,
                          String[] values) {
        super(context, textViewResourceId, values);
        this.values = values;
    }

    @Override
    public int getCount() {
        return values.length;
    }

    @Override
    public String getItem(int position) {
        return values[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

}