package org.um.ziga.lzprojekt

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView

class dosezki : AppCompatActivity() {

    private lateinit var listView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dosezki)

        listView = findViewById<ListView>(R.id.dosezki_list_view)

        val list = mutableListOf<predmetiDosezkov>()

        list.add(predmetiDosezkov(R.drawable.panteon_1,"Prva zmaga"))
        list.add(predmetiDosezkov(R.drawable.panteon_5,"Začetnik"))
        list.add(predmetiDosezkov(R.drawable.panteon_15,"Izkušen"))
        list.add(predmetiDosezkov(R.drawable.panteon_25,"As"))
        list.add(predmetiDosezkov(R.drawable.stopwatch,"Hitrost je pomembna"))

        val adapter: MyListAdapter = MyListAdapter(this,R.layout.my_list_item,list)

        listView.adapter = adapter

    }
}
