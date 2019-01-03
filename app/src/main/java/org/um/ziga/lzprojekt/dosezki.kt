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

        list.add(predmetiDosezkov(R.drawable.panteon_1,"Prva zmaga", "Uspešno ulovi zaklad vsaj enkrat"))
        list.add(predmetiDosezkov(R.drawable.panteon_5,"Začetnik", "Uspešno ulovi zaklad petkrat"))
        list.add(predmetiDosezkov(R.drawable.panteon_15,"Izkušen", "Uspešno ulovi zaklad petnajstkrat"))
        list.add(predmetiDosezkov(R.drawable.panteon_25,"As", "Uspešno ulovi zaklad petindvajsetkrat"))
        list.add(predmetiDosezkov(R.drawable.stopwatch,"Hitrost je pomembna", "Najdi zaklad v manj kot 10 minutah"))

        val adapter: MyListAdapter = MyListAdapter(this,R.layout.my_list_item,list)

        listView.adapter = adapter

    }
}
