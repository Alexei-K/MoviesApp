package com.kolis.test_catalog_app.ui.start_info;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.kolis.test_catalog_app.MainActivity;
import com.kolis.test_catalog_app.R;
import com.kolis.test_catalog_app.util.PrefConstants;

import static androidx.fragment.app.FragmentPagerAdapter.BEHAVIOR_SET_USER_VISIBLE_HINT;

public class StartInfoActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkRegistration();
        setContentView(R.layout.activity_register);
        ViewPager pager = findViewById(R.id.register_viewPager);
        TabLayout tabs = findViewById(R.id.register_tabs);
        setUpAdapter(pager);
        tabs.setupWithViewPager(pager);
    }

    private void setUpAdapter(ViewPager pager) {
        InfoTabsAdapter adapter = new InfoTabsAdapter(getSupportFragmentManager(), BEHAVIOR_SET_USER_VISIBLE_HINT);

        StartInfoFragment firstFragment = new StartInfoFragment();
        setFragmentBundle(firstFragment, R.drawable.registration_1,
                getString(R.string.welcome_to_fluxstore), getString(R.string.register_screen_text));
        adapter.addFragment(0, firstFragment);

        StartInfoFragment secondFragment = new StartInfoFragment();
        setFragmentBundle(secondFragment, R.drawable.registration_2,
                getString(R.string.second_register_screen_title), getString(R.string.register_screen_text));
        adapter.addFragment(1, secondFragment);

        StartInfoLastFragment thirdFragment = new StartInfoLastFragment();
        setFragmentBundle(thirdFragment, R.drawable.registration_3,
                "", getString(R.string.register_screen_text));
        adapter.addFragment(2, thirdFragment);

        pager.setAdapter(adapter);
    }

    private void setFragmentBundle(StartInfoFragment fragment, int imgId, String title, String text) {
        Bundle bundle = new Bundle();
        bundle.putInt(StartInfoFragment.INFO_IMAGE, imgId);
        bundle.putString(StartInfoFragment.INFO_TITLE, title);
        bundle.putString(StartInfoFragment.INFO_TEXT, text);
        fragment.setArguments(bundle);
    }

    private void checkRegistration() {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        if (pref.getBoolean(PrefConstants.IS_LOGGED_PREF, false)) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }

    }

    @Nullable
    @Override
    public View onCreateView(@Nullable View parent, @NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
        return super.onCreateView(parent, name, context, attrs);
    }
}
