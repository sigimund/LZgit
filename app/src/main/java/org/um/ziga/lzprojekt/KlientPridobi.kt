package org.um.ziga.lzprojekt

import android.os.AsyncTask
import android.widget.Toast
import java.io.*
import java.net.Socket
import java.net.UnknownHostException

class KlientPridobi() : AsyncTask<Void, Void, String>() {

    private val size = 1024

    var response = ""

    override fun doInBackground(vararg p0: Void?): String {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

        val socket = Socket("164.8.223.36",8080)

        try {
            socket.use {
                it.outputStream.write("Uspešno sem slikal sliko".toByteArray())
                var msg = it.getInputStream().read().toString()
                if(msg == "ne"){
                    //val toast = Toast.makeText(applicationContext, "Ni pravilni objekt", Toast.LENGTH_SHORT)
                    //toast.show()
                }
                else if(msg == "da"){
                    //uspesenZadetek()
                }
            }
        } catch (e: UnknownHostException) {
            e.printStackTrace()
        } catch (e: IOError) {
            e.printStackTrace()
        } finally {
            socket.close()
        }
        return response

    }
}