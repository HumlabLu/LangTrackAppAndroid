package se.lu.humlab.langtrackapp.screen.login

/*
* Stephan Björck
* Humanistlaboratoriet
* Lunds Universitet
* stephan.bjorck@humlab.lu.se
* */

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.login_activity.*
import se.lu.humlab.langtrackapp.R
import se.lu.humlab.langtrackapp.data.model.User
import se.lu.humlab.langtrackapp.databinding.LoginActivityBinding
import se.lu.humlab.langtrackapp.popup.PopupAlert
import java.util.regex.Pattern

class LoginActivity : AppCompatActivity() {

    lateinit var mBind: LoginActivityBinding
    private lateinit var viewModel : LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBind = DataBindingUtil.setContentView(this,R.layout.login_activity)

        viewModel = ViewModelProviders.of(this,
            LoginViewModelFactory(this)
        ).get(LoginViewModel::class.java)

        mBind.logInButton.setOnClickListener {
            checkTextAndLogIn()
        }
        mBind.loginHelpButton.setOnClickListener {
            val alertFm = supportFragmentManager.beginTransaction()
            val width = (loginLayout.measuredWidth * 0.75).toInt()
            val alertPopup = PopupAlert.show(
                width = width,
                title = "THE LANG-TRACK-APP",
                textViewText = "Studera exponering för och användning av ett nytt språk med smartphone-teknik.\n\n",
                placecenter = true
            )
            alertPopup.show(alertFm, "alertPopup")
        }
        mBind.loginProgressbar.visibility = View.GONE
        mBind.logInEmailEditText.requestFocus()
    }

    private fun checkTextAndLogIn(){
        val username = mBind.logInEmailEditText.text.toString().trim()
        val password = mBind.logInPasswordEditText.text.toString().trim()

        if (username.isEmpty()){
            mBind.logInEmailEditText.error = "Ange användarnamn"
            mBind.logInEmailEditText.requestFocus()
            return
        }

        if (password.isEmpty()){
            mBind.logInPasswordEditText.error = "Ange lösenord"
            mBind.logInPasswordEditText.requestFocus()
            return
        }
        mBind.loginProgressbar.visibility = View.VISIBLE

        val userEmail = "${username}@humlablu.com"
        if (!isValidEmail(userEmail)){
            return
        }
        logIn(userEmail,password)
    }

    private fun isValidEmail(email: String): Boolean{
        val pattern: Pattern = Patterns.EMAIL_ADDRESS
        return pattern.matcher(email).matches()
    }

    fun logIn(email: String, password: String){
        val mAuth = FirebaseAuth.getInstance()
        mAuth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener {
                mBind.loginProgressbar.visibility = View.GONE
                if (it.isSuccessful){
                    val userEmail = mAuth.currentUser!!.email
                    val userName = userEmail?.substringBefore('@')
                    viewModel.setCurrentUser(User("",userName ?: "", userEmail ?: ""))
                    onBackPressed()
                }else{
                    Toast.makeText(this@LoginActivity,
                        "Autentisering misslyckades.",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }

    companion object{
        fun start(context: Context){
            context.startActivity(Intent(context,LoginActivity::class.java).apply {

            })
        }
    }
}
