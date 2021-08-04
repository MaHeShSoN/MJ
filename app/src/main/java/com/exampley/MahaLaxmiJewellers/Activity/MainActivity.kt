package com.exampley.MahaLaxmiJewellers.Activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.exampley.MahaLaxmiJewellers.Adapter.PageAdapter
import com.exampley.MahaLaxmiJewellers.BuildConfig
import com.exampley.MahaLaxmiJewellers.R
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabItem
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayout.TabLayoutOnPageChangeListener
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    var drawerLayout: DrawerLayout? = null
    var toggle: ActionBarDrawerToggle? = null
    var navigationView: NavigationView? = null
    var pager: ViewPager? = null
    var mTabLayout: TabLayout? = null
    var firstItem: TabItem? = null
    var secondItem: TabItem? = null

    var adapter: PageAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        try{
            val toolbar = findViewById<Toolbar>(R.id.toolbar)
            setSupportActionBar(toolbar)
            pager = findViewById(R.id.viewpager)
            mTabLayout = findViewById(R.id.tablayout)
            firstItem = findViewById(R.id.firstItem)

            drawerLayout = findViewById(R.id.drawer)
            navigationView = findViewById(R.id.nav_view)
            navigationView!!.setNavigationItemSelectedListener(this@MainActivity)
            navigationView!!.bringToFront()
            toggle = ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.open,
                R.string.close
            )
            drawerLayout!!.addDrawerListener(toggle!!)
            toggle!!.isDrawerIndicatorEnabled = true
            toggle!!.syncState()
            adapter = PageAdapter(
                supportFragmentManager,
                FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,
                mTabLayout!!.tabCount
            )
            pager!!.adapter = adapter

            mTabLayout!!.addOnTabSelectedListener(object : OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab) {
                    pager!!.currentItem = tab.position
                }

                override fun onTabUnselected(tab: TabLayout.Tab) {}
                override fun onTabReselected(tab: TabLayout.Tab) {}
            })
            pager!!.addOnPageChangeListener(TabLayoutOnPageChangeListener(mTabLayout))




        }catch (e: Throwable){
            Log.d("ERROR", "Error in decliation $e")
        }

        callFab.setOnClickListener{
            Log.d("ERROR", "call button is clicked")
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:9828441285")
            startActivity(intent)
        }
        whatsupFab.setOnClickListener {
            Log.d("ERROR", "call button is clicked")
            navigateToWhatsApp()
        }
    }

    private fun aboutClicked() : Boolean{
        Log.d("ERROR", "Item is clicked")
        return true
    }


    override fun onBackPressed() {
        if (drawerLayout!!.isDrawerOpen(GravityCompat.START)) {
            drawerLayout!!.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    private fun navigateToWhatsApp() {
        try {
            val url = "https://api.whatsapp.com/send?phone=+91${getString(R.string.contact)}"
            val intent:Intent = Intent(Intent.ACTION_VIEW, Uri.parse(url)).setPackage("com.whatsapp")
            startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(
                this,
                "आपके फोन में व्हाट्सएप ऐप इंस्टॉल नहीं है $e",
                Toast.LENGTH_SHORT
            ).show()
        }

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.about -> {
                startActivity(Intent(this, AboutUs::class.java))
            }
            R.id.login ->
                startActivity(Intent(this, Varification_Activity::class.java))
            R.id.exit ->
                finish()
            R.id.share_app -> {
                try {
                    val shareIntent = Intent(Intent.ACTION_SEND)
                    shareIntent.type = "text/plain"
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Mahalaxmi Jewellers")
                    var shareMessage = "\nLet me recommend you this application\n\n"
                    shareMessage =
                        """
                        ${shareMessage}https://play.google.com/store/apps/details?id=${BuildConfig.APPLICATION_ID}
                        
                        
                        """.trimIndent()
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
                    startActivity(Intent.createChooser(shareIntent, "choose one"))
                } catch (e: java.lang.Exception) {
                    //e.toString();
                }
            }
            R.id.Privercy_Policy->
                startActivity(Intent(this,PrivercyPolicy::class.java))

            R.id.app_info->
                Toast.makeText(this,"App Version 1.1",Toast.LENGTH_SHORT).show()
        }
        drawerLayout!!.closeDrawer(GravityCompat.START)
        return true

    }

}