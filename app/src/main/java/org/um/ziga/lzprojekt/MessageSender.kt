package org.um.ziga.lzprojekt

import android.os.AsyncTask
import java.io.*
import java.net.Socket
import java.net.UnknownHostException

class MessageSender() : AsyncTask<Void,Void,String>() {

    private val size = 1024

    var response = ""

    override fun doInBackground(vararg p0: Void?): String {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

        val socket = Socket("192.168.1.101",8080)
        val byteArrayOutputStream = ByteArrayOutputStream(size)
        val inputStream = socket.getInputStream()
        val buffer = ByteArray(size)
        var bytesRead: Int

        try {
            while (true) {
                bytesRead = inputStream.read(buffer)
                if (bytesRead == -1) { break }

                byteArrayOutputStream.write(buffer, 0, bytesRead)
                response += byteArrayOutputStream.toString("UTF-8")
            }
        } catch (e: UnknownHostException) {
            e.printStackTrace()
        } catch (e: IOError) {
            e.printStackTrace()
        } finally {
            socket.close()
        }
        return response
        //rezultat(response)

    }

    fun readStream(inputStream: BufferedInputStream): String {
        val bufferedReader = BufferedReader(InputStreamReader(inputStream))
        val stringBuilder = StringBuilder()
        bufferedReader.forEachLine { stringBuilder.append(it) }
        return stringBuilder.toString()
    }

    fun rezultat(rez:String): String {
        return rez
    }

    override fun onPreExecute() {
        super.onPreExecute()
        // ...
    }

    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)
        // ...
    }
}