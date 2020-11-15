package com.kolis.movies_app.ui.start_info

import android.app.AlertDialog
import android.content.*
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewStub
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.preference.PreferenceManager
import com.kolis.movies_app.MainActivity
import com.kolis.movies_app.R
import com.kolis.movies_app.data.MovieRepositoryImpl
import com.kolis.movies_app.util.PasswordGenerator
import com.kolis.movies_app.util.PrefConstants

class StartInfoLastFragment : StartInfoFragment(), OnPasswordCheckObserver {
    var db = MovieRepositoryImpl()
    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        val title = view.findViewById<TextView>(R.id.title_info)
        title.visibility = View.INVISIBLE
        val stub = view.findViewById<ViewStub>(R.id.stub)
        stub.inflate()
        val skipButton = view.findViewById<TextView>(R.id.skip_button)
        skipButton.visibility = View.VISIBLE
        skipButton.setOnClickListener { v: View ->
            db.addProfile("Not registered", "Skipped registration")
            startActivity(Intent(v.context, MainActivity::class.java))
            requireActivity().finish()
        }
        val signIn = view.findViewById<TextView>(R.id.signIn)
        signIn.setOnClickListener { v: View? -> showLoginDialog() }
        val signUp = view.findViewById<TextView>(R.id.signUp)
        signUp.setOnClickListener { v: View? -> showGenerateLoginDialog() }
    }

    private fun showGenerateLoginDialog() {
        val login = PasswordGenerator.getRandomLogin()
        val password = PasswordGenerator.getRandomPassword()
        db.addProfile(login, password)
        val builder =
            AlertDialog.Builder(activity)
        val message = getString(R.string.login_password, login, password)
        builder.setTitle(getString(R.string.you_are_registered))
            .setMessage(message)
            .setPositiveButton(
                getString(R.string.copy_to_clipboard)
            ) { dialog: DialogInterface, id: Int ->
                val clipboard =
                    requireActivity().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clip = ClipData.newPlainText("label", message)
                clipboard.setPrimaryClip(clip)
                Toast.makeText(context, R.string.copied_to_clipboard, Toast.LENGTH_LONG).show()
                dialog.cancel()
            }
        builder.create().show()
    }

    private fun showLoginDialog() {
        val li = LayoutInflater.from(requireContext())
        val prompt = li.inflate(R.layout.login_dialog, null)
        val alertDialogBuilder =
            AlertDialog.Builder(requireContext())
        alertDialogBuilder.setView(prompt)
        val user = prompt.findViewById<EditText>(R.id.login_name)
        val pass = prompt.findViewById<EditText>(R.id.login_password)
        alertDialogBuilder.setTitle(getString(R.string.enter_login))
        alertDialogBuilder.setCancelable(false)
            .setPositiveButton("OK") { dialog, id ->
                val password = pass.text.toString()
                val username = user.text.toString()
                if (username.length < 1 || password.length < 1) {
                    Toast.makeText(
                        requireContext(),
                        "Invalid username or password",
                        Toast.LENGTH_LONG
                    ).show()
                    showLoginDialog()
                } else {
                    db.isRightPassword(username, password, this@StartInfoLastFragment)
                }
            }
        alertDialogBuilder.setNegativeButton("Cancel") { dialog, id -> dialog.cancel() }
        alertDialogBuilder.show()
    }

    override fun onPasswordCorrect(
        login: String,
        password: String
    ) {
        val pref =
            PreferenceManager.getDefaultSharedPreferences(requireContext())
        pref.edit().putBoolean(PrefConstants.IS_LOGGED_PREF, true).apply()
        pref.edit().putString(PrefConstants.USER_NAME_PREF, login).apply()
        pref.edit().putString(PrefConstants.USER_PASSWORD_PREF, password).apply()
        startActivity(Intent(requireContext(), MainActivity::class.java))
        requireActivity().finish()
    }

    override fun onPasswordWrong() {
        Toast.makeText(context, R.string.password_is_wrong, Toast.LENGTH_LONG).show()
    }
}