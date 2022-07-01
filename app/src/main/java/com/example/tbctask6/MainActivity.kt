package com.example.tbctask6

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var name: EditText
    private lateinit var lastName: EditText
    private lateinit var age: EditText
    private lateinit var email: EditText
    private lateinit var addBtn: Button
    private lateinit var removeBtn: Button
    private lateinit var updateBtn: Button
    private lateinit var displayTv: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        email = findViewById(R.id.emailText)
        name = findViewById(R.id.nameText)
        lastName = findViewById(R.id.lastNameText)
        age = findViewById(R.id.ageText)
        addBtn = findViewById(R.id.addBtn)
        removeBtn = findViewById(R.id.removeBtn)
        updateBtn = findViewById(R.id.updateBtn)
        displayTv = findViewById(R.id.displayTv)

        findViewById<Button>(R.id.addBtn).setOnClickListener(this)
        findViewById<Button>(R.id.removeBtn).setOnClickListener(this)
        findViewById<Button>(R.id.updateBtn).setOnClickListener(this)
    }

    private var localDataBase = mutableListOf<User>()

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.addBtn -> addUser()
            R.id.removeBtn -> removeUser()
            R.id.updateBtn -> updateUser()
        }
    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    private fun updateText(string: String) {
        displayTv.append(string)
        if (string == "Error") {
            displayTv.setTextColor(Color.parseColor("#FF0202"))
        } else if (string == "Success") {
            displayTv.setTextColor(Color.parseColor("#FF9CF436"))
        }
    }

    private fun clearText() {
        displayTv.text = ""
    }


    private fun checkInputs(): Boolean {
        if (lastName.text.isNullOrEmpty() || age.text.isNullOrEmpty() || name.text.isNullOrEmpty())
            return false

        if (email.text.isNullOrEmpty() || !email.text.isValidEmail())
            return false

        return true
    }

    private fun CharSequence?.isValidEmail() =
        !isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()


    private fun addUser() {
        if (checkInputs() && email.text.toString() != User.email) {
            localDataBase.add(User)
            clearText()
            showToast("added successfully")
            updateText("Success")
        } else if (email.text.toString() == User.email) {
            clearText()
            showToast("User already exist")
            updateText("Error")
        }

    }

    private fun removeUser() {
        if (checkInputs() && localDataBase.contains<Any>(email.text.toString())) {
            localDataBase.remove(User)
            clearText()
            showToast("removed successfully")
            updateText("Success")
        } else
            clearText()
        showToast("user doesn't exist")
        updateText("Error")
    }

    private fun updateUser() {
        if (checkInputs() && email.text.toString() == User.email) {
            localDataBase.find { it.email == User.email }?.name = name.toString()
            localDataBase.find { it.email == User.email }?.lastName = lastName.toString()
            clearText()
            showToast("updated successfully")
            updateText("Success")
        } else
            clearText()
        showToast("user doesn't exist")
        updateText("Error")

    }


}

