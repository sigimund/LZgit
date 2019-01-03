package org.um.ziga.lzprojekt

import android.app.ActivityOptions
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.WindowManager
import kotlinx.android.synthetic.main.activity_intro_screen_activity.*

class intro_screen_activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT < 16) {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
        }
        else{
            // Hide the status bar.
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
            // Remember that you should never show the action bar if the
            // status bar is hidden, so hide that too if necessary.
            actionBar?.hide()
        }

        setContentView(R.layout.activity_intro_screen_activity)

        val fileplace = Uri.parse("android.resource://" + packageName + "/raw/" + R.raw.intro_animacija)

        videoView.setVideoURI(fileplace)
        videoView.setOnPreparedListener{ videoView.start() }

        Handler().postDelayed({
            val intent = Intent(this, MainActivity::class.java).apply {}
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
        }, 3000)

        /**/
        //val videoView = findViewById<View>(R.id.videoView)
        //videoView.setVideoPath(Uri.parse(fileplace))
        //videoView.start()

    }
}
