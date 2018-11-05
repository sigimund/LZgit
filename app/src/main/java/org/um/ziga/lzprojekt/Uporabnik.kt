package org.um.ziga.lzprojekt

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.widget.TextView
import android.widget.Toast


class Uporabnik : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_uporabnik)

        val message = "Dobrodošli nazaj " + intent.getStringExtra(EXTRA_MESSAGE)
        val context = applicationContext
        val duration = Toast.LENGTH_SHORT

        val msg = intent.getStringExtra(EXTRA_MESSAGE);
        val name = findViewById<TextView>(R.id.UporabniskoIme);
        name.setText(msg)

        val toast = Toast.makeText(context, message, duration)
        toast.show()
    }
}
