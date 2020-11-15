package com.kolis.movies_app.ui.start_info;

public interface OnPasswordCheckObserver {
    void onPasswordCorrect(String login, String password);

    void onPasswordWrong();
}
