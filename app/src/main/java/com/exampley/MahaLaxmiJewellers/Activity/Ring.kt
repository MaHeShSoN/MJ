package com.exampley.MahaLaxmiJewellers.Activity

import android.app.Dialog
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.telephony.PhoneNumberUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.webkit.MimeTypeMap
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.exampley.MahaLaxmiJewellers.Adapter.ButtonClicer
import com.exampley.MahaLaxmiJewellers.Adapter.ImageAdapter
import com.exampley.MahaLaxmiJewellers.Adapter.ImageViewer
import com.exampley.MahaLaxmiJewellers.Model.Post
import com.exampley.MahaLaxmiJewellers.R
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.github.chrisbanes.photoview.PhotoView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.io.ByteArrayOutputStream


class Ring : AppCompatActivity(), ImageViewer, ButtonClicer {

    lateinit var mImageView: ImageView
    lateinit var mImageUri: Uri
    lateinit var mImageUri1: Uri
    lateinit var mSRef: StorageReference
    lateinit var mDbRef: DatabaseReference
//    lateinit var mProBar: ProgressBar


    lateinit var mRecyclerView: RecyclerView
    lateinit var mAdapter: ImageAdapter
    lateinit var mDbRef2: DatabaseReference
    lateinit var db: FirebaseFirestore
    lateinit var imgCollections: CollectionReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ring)
        try {
            supportActionBar!!.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.gold)))

            mSRef = FirebaseStorage.getInstance().getReference("ring")
            mDbRef = FirebaseDatabase.getInstance().getReference("uploads")

            supportActionBar!!.title = "अंगूठी"
            try{

                if(!isNetworkConnected()){
                    Toast.makeText(this,"Please turn on your Internet",Toast.LENGTH_SHORT).show()
                }

            }catch (e: Throwable){
                Log.d("ERROR", "Error is $e")
            }
            db = FirebaseFirestore.getInstance()
            imgCollections = db.collection("ring")

            setUpRecyclerView()


        } catch (e: Throwable) {
            Log.d("ERROR", "Error in onCreate $e")
        }

    }
    private fun isNetworkConnected(): Boolean {
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.activeNetworkInfo != null && cm.activeNetworkInfo!!.isConnected
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.add, menu)

        GlobalScope.launch(Dispatchers.IO) {

            try {
                val user = Firebase.auth.currentUser
                Log.d("ERROR", "user is ${user.toString()}")

                if (user != null) {
                    // User is signed in.

                } else {
                    // No user is signed in.
                    val myitem: MenuItem = menu!!.findItem(R.id.add)
                    Log.d("ERROR", "user is null")
                    myitem.isVisible = false

                }
            } catch (e: Throwable) {
                Log.d("ERROR", "Error is $e")
            }

        }


        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.add ->
                try {
                    //For chosing Image
                    imageSelector()
                    Log.d("ERROR", "Add Button is clicked")

                } catch (e: Throwable) {
                    Log.d("ERROR", e.toString())
                }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun imageSelector() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, 1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.data != null) {
            mImageUri = data.data!!
//            Picasso.get().load(mImageUri).into(mImageView)
            uploadImage()
        }
    }

    private fun getFileExtension(uri: Uri): String? {
        val cR = contentResolver
        val mime = MimeTypeMap.getSingleton()
        return mime.getExtensionFromMimeType(cR.getType(uri))
    }

    private fun uploadImage() {
        Log.d("ERROR", "Uploading Process start")

        GlobalScope.launch(Dispatchers.IO) {
            val fileRef = mSRef.child(
                "${System.currentTimeMillis()}" + "." + getFileExtension(
                    mImageUri
                )
            )
            fileRef.putFile(mImageUri).await().task.addOnSuccessListener {
                fileRef.downloadUrl.addOnSuccessListener {
                    val url = it.toString()
                    val upload = Post(url)
                    val uploadId: String? = mDbRef.push().key
                    imgCollections.document(uploadId!!).set(upload)
                    Log.d("ERROR", "Uploading Process is finish")
                }.addOnFailureListener {
                    Log.d("ERROR", "ERROR in downlodurl $it")
                }
            }
        }
    }

    private fun setUpRecyclerView() {
        Log.d("ERROR", "RecyclerView Setup is Start")

        try {
            mRecyclerView = findViewById(R.id.recycler_view_ring)
            Log.d("ERROR", "RecyclerView Id is $mRecyclerView")


            val query = imgCollections.orderBy("mimageUri", Query.Direction.DESCENDING)
            Log.d("ERROR", "RecyclerView Query is $query")

            val recyclerViewOptions = FirestoreRecyclerOptions.Builder<Post>().setQuery(
                query,
                Post::class.java
            ).build()
            Log.d("ERROR", "RecyclerView Options is $recyclerViewOptions")


            mAdapter = ImageAdapter(recyclerViewOptions, this,this)
            Log.d("ERROR", "RecyclerView adapter  is $mAdapter")


            mRecyclerView.adapter = mAdapter
            mRecyclerView.layoutManager = LinearLayoutManager(this)
            mRecyclerView.setHasFixedSize(true)

        } catch (e: Throwable) {
            Log.d("ERROR", "Error in  setup of recyclerview $e")
        }


    }

    override fun onStart() {
        super.onStart()
        mAdapter.startListening()

    }

    override fun onStop() {
        super.onStop()
        mAdapter.stopListening()

    }

    override fun onImageClicked(postId: String) {
        val openDialog = Dialog(this)

        openDialog.setContentView(R.layout.fragment_fullscreen)
        val closeButton = openDialog.findViewById<Button>(R.id.close_btn)
        closeButton.setOnClickListener {
            openDialog.dismiss()
        }
        val progressBar = openDialog.findViewById<ProgressBar>(R.id.progressBar_fullscreen)


        GlobalScope.launch(Dispatchers.IO) {

            val shareButton = openDialog.findViewById<Button>(R.id.share_btn)
            val imageView = openDialog.findViewById<PhotoView>(R.id.imageView_fullScreen)
            val docRef = imgCollections.document(postId)

            docRef.get().await().reference.addSnapshotListener { value, error ->
                val imageUrl = value?.get("mimageUri") as String
                setImage(imageUrl, imageView)
                //Share Buttom ClickListner
                shareButton.setOnClickListener {
                    val builder: AlertDialog.Builder = AlertDialog.Builder(this@Ring)
                    val layoutInflaterAndroid = LayoutInflater.from(this@Ring)
                    val view: View = layoutInflaterAndroid.inflate(R.layout.dialog_send, null)
                    builder.setView(view)
                    builder.setCancelable(false)
                    val alertDialog: AlertDialog = builder.create()
                    alertDialog.show()

                    //send to paa
                    view.findViewById<TextView>(R.id.send_papa).setOnClickListener {
                        val sendIntent = Intent("android.intent.action.SEND")
                        val image: Bitmap = getbitmapFromView(imageView)
                        val uri = getImageUri(this@Ring ,image)
                        sendIntent.component =
                            ComponentName("com.whatsapp", "com.whatsapp.ContactPicker")
                        sendIntent.type = "image"
                        sendIntent.putExtra(Intent.EXTRA_STREAM, uri)
                        sendIntent.putExtra(
                            "jid",
                            PhoneNumberUtils.stripSeparators("919828441285") + "@s.whatsapp.net"
                        )
                        sendIntent.putExtra(
                            Intent.EXTRA_TEXT,
                            "sample text you want to send along with the image"
                        )
                        startActivity(sendIntent)
                    }
                    view.findViewById<TextView>(R.id.send_other).setOnClickListener {
                        try{
                            Log.d("ERROR","ImageView is $imageView")
                            shareItem(imageView)
                        }catch (e:Throwable){
                            Log.d("ERROR", "Error is $e")
                        }

                    }
                    //back button
                    view.findViewById<TextView>(R.id.back_btn).setOnClickListener {
                        alertDialog.dismiss()
                    }


                }
                try{
                    progressBar.visibility = View.GONE
                }catch (e:Throwable){
                    Log.d("ERROR", "Error in $e")
                }
            }


        }
        openDialog.show()
    }



    private fun shareItem(
        imageView: ImageView
    ) {
        try{

            val image: Bitmap = getbitmapFromView(imageView)
            Log.d("ERROR","image is $image")
            val share = Intent(Intent.ACTION_SEND)
            share.type = "image/*"
            share.putExtra(Intent.EXTRA_STREAM, getImageUri(this, image))
            Log.d("ERROR","imageUri is ${getImageUri(this, image)}")
            startActivity(Intent.createChooser(share, "share view"))
        }catch (e:Throwable){
            Log.d("ERROR", "Error is $e")
        }
    }

    private fun getImageUri(context: Context, mimage: Bitmap): Uri? {
        val bytes = ByteArrayOutputStream()
        mimage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(
            context.contentResolver,
            mimage,
            "title",
            null
        )
        return Uri.parse(path)
    }

    private fun getbitmapFromView(imageView: ImageView): Bitmap {
        val bitmap: Bitmap =  Bitmap.createBitmap(imageView.width,
            imageView.height,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        imageView.draw(canvas)
        return bitmap
    }


    private fun navigateToWhatsApp(s: String): Boolean {
        val packageManager = packageManager
        var is_installed: Boolean

        try {
            packageManager.getPackageInfo(s, PackageManager.GET_ACTIVITIES)
            is_installed = true
        } catch (e: PackageManager.NameNotFoundException) {
            is_installed = false
            e.printStackTrace()
        }
        return is_installed
    }


    private fun setImage(imageUrl: String, imageView: ImageView?) {
        try {
            Picasso.get().load(imageUrl).into(imageView)
        } catch (e: Throwable) {
            Log.d("ERROR", "Error in setImage $e")
        }

    }

    override fun onButtonClicked(postId: String) {
        val docRef = imgCollections.document(postId)
        docRef.delete().addOnSuccessListener {
            Log.d("ERROR","image deleted $postId")
        }.addOnFailureListener {
            Log.d("ERROR","image is not delete $it")
        }
    }


}
