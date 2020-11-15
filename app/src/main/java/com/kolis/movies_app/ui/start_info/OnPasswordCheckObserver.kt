package com.kolis.movies_app.ui.start_info

interface OnPasswordCheckObserver {
    fun onPasswordCorrect(login: String, password: String)
    fun onPasswordWrong()
}