package com.kolis.test_catalog_app.ui.start_info;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.HashMap;

public class InfoTabsAdapter extends FragmentPagerAdapter {
    private final HashMap<Integer, Fragment> fragmentsMap = new HashMap<>();

    public InfoTabsAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentsMap.get(position);
    }

    @Override
    public int getCount() {
        return fragmentsMap.size();
    }

    public void addFragment(int position, Fragment fragment) {
        fragmentsMap.put(position, fragment);
    }
}
