package org.um.ziga.lzprojekt

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import android.content.Intent
import android.text.Layout
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.*


const val EXTRA_MESSAGE = "org.um.ziga.lzprojekt.MESSAGE"
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //val uporabniskiView = findViewById<LinearLayout>(R.id.VpisiLinearLayout);
        //uporabniskiView.visibility;

        val uIme = findViewById<EditText>(R.id.UporabniskoIme);
        val uGeslo = findViewById<EditText>(R.id.geslo);
        val gumb = findViewById<Button>(R.id.GumbVpisi);

        gumb.setOnClickListener(){

            if( (uIme.text.toString() == "Žiga") && (uGeslo.text.toString() == "123") ){
                val message = uIme.text.toString()
                val intent = Intent(this, Uporabnik::class.java).apply { // pošljemo ime v drug activity
                    putExtra(EXTRA_MESSAGE, message)
                }
                uIme.setText("")
                uGeslo.setText("")
                startActivity(intent)
            }
            else{
                // custom View toast
                val inflater = layoutInflater
                val container: ViewGroup = findViewById(R.id.custom_toast_container)
                val layout: ViewGroup = inflater.inflate(R.layout.custom_toast, container) as ViewGroup
                val text: TextView = layout.findViewById(R.id.text)
                text.text = "This is a custom toast"
                with (Toast(applicationContext)) {
                    setGravity(Gravity.CENTER_VERTICAL, 0, 0)
                    duration = Toast.LENGTH_LONG
                    view = layout
                    show()
                }


                //val context = applicationContext
                //val text = "Napačno uporabniško ime ali geslo";
                //val duration = Toast.LENGTH_SHORT

                //val toast = Toast.makeText(context, text, duration)
                //toast.show()
            }


        }

    }

}
