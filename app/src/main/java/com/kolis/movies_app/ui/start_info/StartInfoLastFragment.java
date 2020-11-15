package com.kolis.test_catalog_app.ui.start_info;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.preference.PreferenceManager;

import com.kolis.test_catalog_app.MainActivity;
import com.kolis.test_catalog_app.R;
import com.kolis.test_catalog_app.data.DressRepositoryImpl;
import com.kolis.test_catalog_app.util.PasswordGenerator;

import static com.kolis.test_catalog_app.util.PrefConstants.IS_LOGGED_PREF;
import static com.kolis.test_catalog_app.util.PrefConstants.USER_NAME_PREF;
import static com.kolis.test_catalog_app.util.PrefConstants.USER_PASSWORD_PREF;

public class StartInfoLastFragment extends StartInfoFragment implements OnPasswordCheckObserver {

    DressRepositoryImpl db = new DressRepositoryImpl();

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView title = view.findViewById(R.id.title_info);
        title.setVisibility(View.INVISIBLE);
        ViewStub stub = view.findViewById(R.id.stub);
        stub.inflate();
        TextView skipButton = view.findViewById(R.id.skip_button);
        skipButton.setVisibility(View.VISIBLE);
        skipButton.setOnClickListener(v -> {
            startActivity(new Intent(v.getContext(), MainActivity.class));
            requireActivity().finish();
        });
        TextView signIn = view.findViewById(R.id.signIn);
        signIn.setOnClickListener(v -> {
            showLoginDialog();
        });
        TextView signUp = view.findViewById(R.id.signUp);
        signUp.setOnClickListener(v -> {
            showGenerateLoginDialog();
        });

    }

    private void showGenerateLoginDialog() {
        String login = PasswordGenerator.getRandomLogin();
        String password = PasswordGenerator.getRandomPassword();
        db.addProfile(login, password);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        String message = getString(R.string.login_password, login, password);
        builder.setTitle(getString(R.string.you_are_registered))
                .setMessage(message)
                .setPositiveButton(getString(R.string.copy_to_clipboard), (dialog, id) -> {
                    ClipboardManager clipboard = (ClipboardManager) requireActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("label", message);
                    clipboard.setPrimaryClip(clip);
                    dialog.cancel();
                });
        builder.create().show();
    }


    private void showLoginDialog() {
        LayoutInflater li = LayoutInflater.from(requireContext());
        View prompt = li.inflate(R.layout.login_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(requireContext());
        alertDialogBuilder.setView(prompt);
        final EditText user = prompt.findViewById(R.id.login_name);
        final EditText pass = prompt.findViewById(R.id.login_password);

        alertDialogBuilder.setTitle(getString(R.string.enter_login));
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        String password = pass.getText().toString();
                        String username = user.getText().toString();
                        if (username.length() < 1 || password.length() < 1) {
                            Toast.makeText(requireContext(), "Invalid username or password", Toast.LENGTH_LONG).show();
                            showLoginDialog();
                        } else {
                            db.isRightPassword(username, password, StartInfoLastFragment.this);
                        }

                    }
                });

        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();

            }
        });
        alertDialogBuilder.show();
    }

    @Override
    public void onPasswordCorrect(String login, String password) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(requireContext());
        pref.edit().putBoolean(IS_LOGGED_PREF, true).apply();
        pref.edit().putString(USER_NAME_PREF, login).apply();
        pref.edit().putString(USER_PASSWORD_PREF, password).apply();
        startActivity(new Intent(requireContext(), MainActivity.class));
        requireActivity().finish();
    }

    @Override
    public void onPasswordWrong() {
        Toast.makeText(getContext(), R.string.password_is_wrong, Toast.LENGTH_LONG).show();
    }
}
