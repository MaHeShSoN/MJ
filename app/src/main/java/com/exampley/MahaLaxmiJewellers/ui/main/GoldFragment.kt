package com.exampley.MahaLaxmiJewellers.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.exampley.MahaLaxmiJewellers.Activity.*
import com.exampley.MahaLaxmiJewellers.Neklec
import com.exampley.MahaLaxmiJewellers.R


class GoldFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_gold, container, false)
        val button : CardView = view.findViewById(R.id.Aad)
        button.setOnClickListener {
            Log.d("ERROR","Image Button is Clicked")
            startActivity(Intent(view.context, Aad::class.java))
        }
        val rakdiSet : CardView = view.findViewById(R.id.rakdi_set)
        rakdiSet.setOnClickListener {
            Log.d("ERROR","Image Button is Clicked")
            startActivity(Intent(view.context, RakdiSet::class.java))
        }
        val jhoomariya : CardView = view.findViewById(R.id.jhoomariya)
        jhoomariya.setOnClickListener {
            Log.d("ERROR","Image Button is Clicked")
            startActivity(Intent(view.context, Jumriya::class.java))
        }
        val baajubandh : CardView = view.findViewById(R.id.baajubandh)
        baajubandh.setOnClickListener {
            Log.d("ERROR","Image Button is Clicked")
            startActivity(Intent(view.context, Baajubandh::class.java))
        }
        val punach : CardView = view.findViewById(R.id.punach)
        punach.setOnClickListener {
            Log.d("ERROR","Image Button is Clicked")
            startActivity(Intent(view.context, Puncha::class.java))
        }
        val ring : CardView = view.findViewById(R.id.Ring)
        ring.setOnClickListener {
            Log.d("ERROR","Image Button is Clicked")
            startActivity(Intent(view.context, Ring::class.java))
        }
        val sheesh_phool : CardView = view.findViewById(R.id.sheesh_phool)
        sheesh_phool.setOnClickListener {
            Log.d("ERROR","Image Button is Clicked")
            startActivity(Intent(view.context, SheeshPhool::class.java))
        }
        val mangal_sutra : CardView = view.findViewById(R.id.mangal_sutra)
        mangal_sutra.setOnClickListener {
            Log.d("ERROR","Image Button is Clicked")
            startActivity(Intent(view.context, MangelSutra::class.java))
        }
        val kanti : CardView = view.findViewById(R.id.kanti)
        kanti.setOnClickListener {
            Log.d("ERROR","Image Button is Clicked")
            startActivity(Intent(view.context, Kanti::class.java))
        }
        val chain : CardView = view.findViewById(R.id.chain)
        chain.setOnClickListener {
            Log.d("ERROR","Image Button is Clicked")
            startActivity(Intent(view.context, Chain::class.java))
        }
        val patta_jhela : CardView = view.findViewById(R.id.patta_jhela)
        patta_jhela.setOnClickListener {
            Log.d("ERROR","Image Button is Clicked")
            startActivity(Intent(view.context, PattaJhala::class.java))
        }
        val chudiyan1 : CardView = view.findViewById(R.id.chudiyan)
        chudiyan1.setOnClickListener {
            Log.d("ERROR","Image Button is Clicked")
            startActivity(Intent(view.context, Chudiya::class.java))
        }
        val bajerkanto1 : CardView = view.findViewById(R.id.bajerkanto)
        bajerkanto1.setOnClickListener {
            Log.d("ERROR","Image Button is Clicked")
            startActivity(Intent(view.context, bajerkanto::class.java))
        }
        val Nath12 : CardView = view.findViewById(R.id.nath)
        Nath12.setOnClickListener {
            Log.d("ERROR","Image Button is Clicked")
            startActivity(Intent(view.context, Nath::class.java))
        }
        val bali : CardView = view.findViewById(R.id.baliya)
        bali.setOnClickListener {
            Log.d("ERROR","Image Button is Clicked")
            startActivity(Intent(view.context, Bali::class.java))
        }
        val hathful : CardView = view.findViewById(R.id.hathful)
        hathful.setOnClickListener {
            Log.d("ERROR","Image Button is Clicked")
            startActivity(Intent(view.context, Hathful::class.java))
        }

        val madaliyo : CardView = view.findViewById(R.id.madaliyo)
        madaliyo.setOnClickListener {
            Log.d("ERROR","Image Button is Clicked")
            startActivity(Intent(view.context, Madaliyo::class.java))
        }

        val Topas1 : CardView = view.findViewById(R.id.topas)
        Topas1.setOnClickListener {
            Log.d("ERROR","Image Button is Clicked")
            startActivity(Intent(view.context, Topas::class.java))
        }
        try{
            val ramnavmi : CardView = view.findViewById(R.id.ramnavmi)
            ramnavmi.setOnClickListener {
                Log.d("ERROR","Image Button is Clicked")
                startActivity(Intent(view.context, Ramnavmi::class.java))
            }
            val Pandel1 : CardView = view.findViewById(R.id.pendal)
            Pandel1.setOnClickListener {
                Log.d("ERROR","Image Button is Clicked")
                startActivity(Intent(view.context, Pandel::class.java))
            }
            val jodhahar : CardView = view.findViewById(R.id.jodhahar)
            jodhahar.setOnClickListener {
                Log.d("ERROR","Image Button is Clicked")
                startActivity(Intent(view.context, JodhaHar::class.java))
            }
            val neklec : CardView = view.findViewById(R.id.neklec)
            neklec.setOnClickListener {
                Log.d("ERROR","Image Button is Clicked")
                startActivity(Intent(view.context, Neklec::class.java))
            }


        }catch (e:Throwable){
            Log.d("ERROR", "Error is $e")
        }



        return view
    }

}