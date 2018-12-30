package org.um.ziga.lzprojekt

import android.content.Context
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

class MyListAdapter(var hehe: Context, var resource: Int, var items: List<predmetiDosezkov>)
    : ArrayAdapter<predmetiDosezkov>(hehe, resource, items)
{
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var layoutInflater : LayoutInflater = LayoutInflater.from(hehe)

        val view: View = layoutInflater.inflate(resource,null)

        val imageView : ImageView = view.findViewById(R.id.imageView2)
        val textView : TextView = view.findViewById(R.id.textView2)

        val predmet : predmetiDosezkov = items[position]

        imageView.setImageDrawable(hehe.resources.getDrawable(predmet.image))
        textView.text = predmet.name

        return view
    }
}