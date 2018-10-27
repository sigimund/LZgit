package org.um.ziga.lovznamenitosti

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText;

class MainActivity : AppCompatActivity() {

    private EditText upIme;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
