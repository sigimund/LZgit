package org.um.ziga.lzprojekt

import android.app.ActivityOptions
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import android.content.Intent
import android.graphics.Typeface
import android.os.Handler
import android.text.Layout
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.*
import org.w3c.dom.Text
import java.io.File
import java.util.*
import kotlin.concurrent.schedule


const val EXTRA_MESSAGE = "org.um.ziga.lzprojekt.MESSAGE"
class MainActivity : AppCompatActivity() {
val imeDatoteke = "Uporabniska_Imena.txt"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //val uporabniskiView = findViewById<LinearLayout>(R.id.VpisiLinearLayout);
        //uporabniskiView.visibility;

        val uIme = findViewById<EditText>(R.id.UporabniskoIme);
        val uGeslo = findViewById<EditText>(R.id.geslo);
        val gumb = findViewById<Button>(R.id.GumbVpisi);

        val regText = findViewById<TextView>(R.id.LinkRegistracija)
        indeterminateBar.visibility = View.INVISIBLE

        //val typeface = Typeface.createFromAsset(applicationContext.assets, "f")

        // za dopolnjevanje vnosa------------------------------------------------------------

        val datoteka = File(imeDatoteke)

        if(datoteka.exists()){

            val dopolniUI = mutableListOf("")

            File(applicationContext.filesDir, imeDatoteke).bufferedReader().use {
                //it.readText()
                dopolniUI += it.readLine().toString()
            }

            var adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,dopolniUI)
            ACUporabniskoIme.threshold = 0
            ACUporabniskoIme.setAdapter(adapter)

        }
        else{
            // create a new file
            val isNewFileCreated :Boolean = datoteka.createNewFile()
            if(isNewFileCreated){
                val dopolniUI = mutableListOf("")

                File(applicationContext.filesDir, imeDatoteke).bufferedReader().use {
                    //it.readText()
                    dopolniUI += it.readLine().toString()
                }

                var adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,dopolniUI)
                ACUporabniskoIme.threshold = 0
                ACUporabniskoIme.setAdapter(adapter)
            }
        }


        //-----------------------------------------------------------------------------------


        regText.setOnClickListener(){

            val intent = Intent(this,registracija::class.java)
            startActivity(intent,ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
        }

        gumb.setOnClickListener(){

            indeterminateBar.visibility = View.VISIBLE


            if( (uIme.text.toString() == "ziga") && (uGeslo.text.toString() == "123") ){

                /*applicationContext.openFileOutput(imeDatoteke, Context.MODE_PRIVATE).use {
                    it.write(uIme.text.toString().toByteArray())
                    it.write("\n".toByteArray())
                }*/


                Handler().postDelayed({
                    indeterminateBar.visibility = View.INVISIBLE

                    val message = uIme.text.toString()
                    val intent = Intent(this, Uporabnik::class.java).apply { // pošljemo ime v drug activity
                        putExtra(EXTRA_MESSAGE, message)
                    }
                    uIme.setText("")
                    uGeslo.setText("")
                    startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
                }, 3000)
            }
            else{
                Handler().postDelayed({

                    indeterminateBar.visibility = View.INVISIBLE
                    // custom View toast
                    val inflater = layoutInflater
                    val container = findViewById<ViewGroup>(R.id.custom_toast_container)
                    val layout = inflater.inflate(R.layout.custom_toast, container) as ViewGroup
                    val text = layout.findViewById<TextView>(R.id.text)
                    text.text = "Napačno uporabniško ime ali geslo"
                    with (Toast(applicationContext)) {
                        setGravity(Gravity.CENTER, 0, 0)
                        duration = Toast.LENGTH_LONG
                        view = layout
                        show()
                    }
                }, 3000)
            }

        }

    }


}
