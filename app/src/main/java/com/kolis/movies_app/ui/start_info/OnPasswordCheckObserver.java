package com.kolis.test_catalog_app.ui.start_info;

public interface OnPasswordCheckObserver {
    void onPasswordCorrect(String login, String password);

    void onPasswordWrong();
}
