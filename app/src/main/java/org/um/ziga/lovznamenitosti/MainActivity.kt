package org.um.ziga.lovznamenitosti

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText;

class MainActivity : AppCompatActivity() {

    private EditText upIme;
    private EditText upGeslo;
    private Button gumb;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main);

        upIme = (EditText)findViewById(R.id.upIme);
        upGeslo = (EditText)findViewById(R.id.upGeslo);
        gumb = (Button)findViewById(R.id.gumb);
    }
}
