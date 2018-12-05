package org.um.ziga.lzprojekt

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_zemljevid.*
import kotlin.system.exitProcess

class Uporabnik : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_uporabnik)

        val message = "Dobrodošli nazaj " + intent.getStringExtra(EXTRA_MESSAGE)
        val context = applicationContext
        val duration = Toast.LENGTH_SHORT

        val msg = intent.getStringExtra(EXTRA_MESSAGE);
        //val name = findViewById<TextView>(R.id.UporabniskoIme);
        //name.setText(msg)

        val toast = Toast.makeText(context, message, duration)
        toast.show()


        val gumbStart = findViewById<Button>(R.id.novLov)
        val gumbTocke = findViewById<Button>(R.id.tocke)
        val gumbDosezki = findViewById<Button>(R.id.dosezki)
        val gumbIzhod = findViewById<Button>(R.id.izhod)

        gumbStart.setOnClickListener(){
            val intent = Intent(this, Zemljevid::class.java).apply {
                putExtra(EXTRA_MESSAGE, message)
            }
            startActivity(intent)
        }

        gumbTocke.setOnClickListener(){

        }

        gumbDosezki.setOnClickListener(){

        }

        gumbIzhod.setOnClickListener(){// izhod
            moveTaskToBack(true);
            exitProcess(-1)
            //finishAffinity() // potrebuje minimalni API level 16
        }

    }
}
