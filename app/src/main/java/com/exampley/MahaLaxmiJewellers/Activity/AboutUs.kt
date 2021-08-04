package com.exampley.MahaLaxmiJewellers.Activity


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.exampley.MahaLaxmiJewellers.R
import mehdi.sakout.aboutpage.AboutPage
import mehdi.sakout.aboutpage.Element
import java.util.*


class AboutUs : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_us)
        val adsElement = Element()
        val aboutPage = AboutPage(this)
            .isRTL(false)
            .setDescription("Hari Kishan Soni,Mahalaxmi Jewellers, " +
                    "Ganash Mandir ka Pass," +
                    "Iilaji Bazar,Pipar City,Jodhpur,Rajasthan")

            .addGroup("CONNECT WITH US!")
            .addEmail("sonim1234567@gmail.com ")
            .addFacebook("https://www.facebook.com/harikishan.soni.92") //Your instagram id
            .addItem(createCopyright())
            .create()
        setContentView(aboutPage)
    }

    private fun createCopyright(): Element {
        val copyright = Element()
        @SuppressLint("DefaultLocale") val copyrightString = String.format(
            "Copyright by ML Jewellers",
            Calendar.getInstance()[Calendar.YEAR]
        )
        copyright.title = copyrightString
        // copyright.setIcon(R.mipmap.ic_launcher);
        copyright.gravity = Gravity.CENTER
        copyright.onClickListener =
            View.OnClickListener {
                Toast.makeText(this@AboutUs, copyrightString, Toast.LENGTH_SHORT).show()
            }
        return copyright
    }
}