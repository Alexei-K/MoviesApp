package com.kolis.test_catalog_app.ui.start_info;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.kolis.test_catalog_app.R;

public class StartInfoFragment extends Fragment {

    StartInfoViewModel viewModel;
    public static final String INFO_IMAGE = "info image";
    public static final String INFO_TITLE = "info title";
    public static final String INFO_TEXT = "info text";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(StartInfoViewModel.class);
        viewModel.setUpInfo(getArguments().getInt(INFO_IMAGE), getArguments().getString(INFO_TITLE), getArguments().getString(INFO_TEXT));
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.info_fragment, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        setObservers(view);
        super.onViewCreated(view, savedInstanceState);
    }

    private void setObservers(@NonNull View view) {
        viewModel.getImage().observe(getViewLifecycleOwner(), s -> {
            ImageView image = view.findViewById(R.id.info_image);
            image.setImageDrawable(ResourcesCompat.getDrawable(view.getResources(), s, null));
        });
        viewModel.getTitle().observe(getViewLifecycleOwner(), s -> {
            TextView mTitle = view.findViewById(R.id.title_info);
            mTitle.setText(s);
        });
        viewModel.getText().observe(getViewLifecycleOwner(), s -> {
            TextView mText = view.findViewById(R.id.text_info);
            mText.setText(s);
        });
    }
}
