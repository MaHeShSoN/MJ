package com.exampley.MahaLaxmiJewellers.Adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import com.exampley.MahaLaxmiJewellers.Model.Post
import com.exampley.MahaLaxmiJewellers.R
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.lang.Exception


class ImageAdapter(
    options: FirestoreRecyclerOptions<Post>,
    private val listener: ImageViewer,
    private val listener_delete: ButtonClicer
) : FirestoreRecyclerAdapter<Post, ImageAdapter.ImageViewHolder>(
    options
) {



    class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView : ImageView = itemView.findViewById(R.id.image_view_upload)
        val delete_btn : Button = itemView.findViewById(R.id.delete_btn)
        val progress_ : ProgressBar = itemView.findViewById(R.id.image_card_progressBar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val viewHolder = ImageViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.image_card,
                parent,
                false
            )
        )

        viewHolder.imageView.setOnClickListener {
            listener.onImageClicked(snapshots.getSnapshot(viewHolder.adapterPosition).id)
        }
        viewHolder.delete_btn.setOnClickListener {
            listener_delete.onButtonClicked(snapshots.getSnapshot(viewHolder.adapterPosition).id)
        }

        Log.d("ERROR", "View is Created $viewHolder")
        return viewHolder
    }




    override fun onBindViewHolder(holder: ImageViewHolder, position: Int, model: Post) {
        try{

            val db = FirebaseFirestore.getInstance()

            val snapshot =snapshots.getSnapshot(holder.adapterPosition)
            val docId =snapshot.id
            val docRef = db.collection("images")
            val auth = Firebase.auth
            val currentUser =auth.currentUser!!.uid
        }catch (e: Throwable){
            Log.d("ERROR", "Error is $e")
        }
        try{
            Log.d(
                "ERROR",
                "Picasso is setting up to load (${model.mimageUri}) into ${holder.imageView} "
            )
            Picasso.get().load(model.mimageUri).noFade().into(holder.imageView,object : Callback{
                override fun onSuccess() {
                    holder.progress_.visibility = View.GONE
                }

                override fun onError(e: Exception?) {

                }
            })



            val user = Firebase.auth.currentUser
            if (user != null) {
                // User is signed in.
            } else {
                // No user is signed in.
                holder.delete_btn.visibility = View.GONE
            }
        }catch (e: Throwable){
            Log.d("ERROR", "Error while Using Picasso $e")
        }

    }
}
interface ImageViewer{
    fun onImageClicked(postId: String)
}
interface ButtonClicer{
    fun onButtonClicked(postId: String)
}


