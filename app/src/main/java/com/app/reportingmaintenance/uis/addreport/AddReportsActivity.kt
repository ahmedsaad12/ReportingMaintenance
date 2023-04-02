package com.app.reportingmaintenance.uis.addreport

import android.Manifest
import android.app.ProgressDialog
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.app.reportingmaintenance.R
import com.app.reportingmaintenance.databinding.ActivityAddreportBinding
import com.app.reportingmaintenance.databinding.ToolbarBinding
import com.app.reportingmaintenance.model.AddReportModel
import com.app.reportingmaintenance.model.DataModel
import com.app.reportingmaintenance.model.ReportModel
import com.app.reportingmaintenance.preferences.Preferences
import com.app.reportingmaintenance.share.Common
import com.app.reportingmaintenance.tags.Tags
import com.app.reportingmaintenance.uis.places.PlacesActivity
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import com.squareup.picasso.Picasso
import java.io.ByteArrayOutputStream
import java.io.File


class AddReportsActivity : AppCompatActivity() {
    private lateinit var dialog: ProgressDialog
    private var storageRef: StorageReference? = null
    private var binding: ActivityAddreportBinding? = null
    private var addDataModel: AddReportModel = AddReportModel()
    private var launcher: ActivityResultLauncher<Intent>? = null
    private val READ_PERM = Manifest.permission.READ_EXTERNAL_STORAGE
    private val write_permission = Manifest.permission.WRITE_EXTERNAL_STORAGE
    private val camera_permission = Manifest.permission.CAMERA

    private var dRef: DatabaseReference? = null
    var disAdapter: ArrayAdapter<String>? = null

    private var disList: MutableList<String>? = null
    var facAdapter: ArrayAdapter<String>? = null

    private var facList: MutableList<String>? = null
    var placeAdapter: ArrayAdapter<String>? = null
    private var preferences: Preferences? = null

    private var placeList: MutableList<String>? = null
    var peroirityAdapter: ArrayAdapter<String>? = null

    private var peroirityList: MutableList<String>? = null
    private val READ_REQ = 1
    val CAMERA_REQ = 2
    private var selectedReq = 0
    private var uri: Uri? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
// Remember that you should never show the action bar if the
// status bar is hidden, so hide that too if necessary.
        binding = DataBindingUtil.setContentView(this, R.layout.activity_addreport)
        intitView()

    }

    private fun intitView() {
        preferences = Preferences.newInstance()

        addDataModel.context = this
        setUpToolbar(
            binding!!.toolbar,
            "Add Report",
            R.color.white,
            R.color.black
        )
        binding!!.toolbar.llBack.setOnClickListener {
            finish()
        }
        dRef = FirebaseDatabase.getInstance().getReference(Tags.DATABASE_NAME)

        storageRef = Firebase.storage(Tags.Bucket_NAME).reference
        disList = mutableListOf()
        disAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, disList!!)
        facList = mutableListOf()
        facAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, facList!!)
        placeList = mutableListOf()
        placeAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, placeList!!)
        peroirityList = mutableListOf()
        peroirityList!!.add("hehi")
        peroirityList!!.add("medium")
        peroirityList!!.add("low")
        peroirityAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, peroirityList!!)

        binding!!.spType.adapter = disAdapter
        binding!!.spFac.adapter = facAdapter
        binding!!.spplace.adapter = placeAdapter
        binding!!.spPerority.adapter = peroirityAdapter
        binding!!.model = addDataModel
        launcher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result: ActivityResult ->
            if (result.resultCode == RESULT_OK && result.data != null) {
                if (selectedReq == READ_REQ) {
                    binding!!.icon.visibility = View.GONE
                    uri = result.data!!.data
                    val file = File(Common.getImagePath(this, uri!!)!!)
                    Picasso.get().load(file).fit().into(binding!!.image)
                    addDataModel.image = uri!!.toString()

                    binding!!.model = addDataModel
                    addDataModel.validate()
                } else if (selectedReq == CAMERA_REQ) {
                    val bitmap = result.data!!.extras!!["data"] as Bitmap?
                    binding!!.icon.visibility = View.GONE
                    uri = getUriFromBitmap(bitmap!!)
                    if (uri != null) {
                        val path: String = Common.getImagePath(this, uri!!)!!
                        Picasso.get().load(File(path)).fit().into(binding!!.image)
                        addDataModel.image = uri!!.toString()
                        binding!!.model = addDataModel
                        addDataModel.validate()
                    }
                }
            }
        }
        binding!!.btnadd.setOnClickListener {
            var intent = Intent(this, PlacesActivity::class.java)
            intent.putExtra("faculty_name", addDataModel.idfac)
            startActivity(intent)
        }
        binding!!.flGallery.setOnClickListener {
            closeSheet()
            checkReadPermission()
        }

        binding!!.flCamera.setOnClickListener {
            closeSheet()
            checkCameraPermission()
        }

        binding!!.btnCancel.setOnClickListener { closeSheet() }
        binding!!.flImage.setOnClickListener { openSheet() }
     /*   binding!!.btnLogin.setOnClickListener {
           dialog = Common.createProgressDialog(
                this,
                "wait"
            )!!
            dialog.setCancelable(false)
            dialog.show()
            val file = File(Common.getImagePath(this, uri!!)!!)

            val uploadTask = storageRef!!.child("file/${file.name}").putFile(uri!!)

            // On success, download the file URL and display it
            uploadTask.addOnSuccessListener {
                // using glide library to display the image
                storageRef!!.child("file/${file.name}").downloadUrl.addOnSuccessListener {
                    setuser(it)
                }.addOnFailureListener {
                    Log.e("Firebase", "Failed in downloading")
                }
            }.addOnFailureListener {
                Log.e("Firebase", "Image Upload fail")
            }
        }*/
        binding!!.spType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View, i: Int, l: Long) {

                addDataModel.iddis = disList!![i];

                binding!!.model = addDataModel
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
        }
        binding!!.spFac.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View, i: Int, l: Long) {

                addDataModel.idfac = facList!![i];

                binding!!.model = addDataModel
                getplace()
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
        }
        binding!!.spplace.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View, i: Int, l: Long) {

                addDataModel.idplace = placeList!![i];

                binding!!.model = addDataModel
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
        }
        binding!!.spPerority.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View, i: Int, l: Long) {

                addDataModel.periority = peroirityList!![i];

                binding!!.model = addDataModel
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
        }
      //  getData()
      //  getFac()
    }

    private fun getData() {
        val myMostViewedPostsQuery = dRef!!.child(Tags.TABLE_DisruptionTypes)
        disList!!.clear()
        myMostViewedPostsQuery.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                disList!!.clear()

                for (postSnapshot in dataSnapshot.children) {
                    // TODO: handle the post
                    Log.e(ContentValues.TAG, postSnapshot.value.toString())
                    val dataModel = postSnapshot.getValue<DataModel>()

                    disList!!.add(dataModel!!.name!!);

                }
                disAdapter!!.notifyDataSetChanged()


            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(ContentValues.TAG, "loadPost:onCancelled", databaseError.toException())
                // ...
            }
        })
    }

    private fun getFac() {
        val myMostViewedPostsQuery = dRef!!.child(Tags.TABLE_Faculties)
        facList!!.clear()
        myMostViewedPostsQuery.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                facList!!.clear()

                for (postSnapshot in dataSnapshot.children) {
                    // TODO: handle the post
                    Log.e(ContentValues.TAG, postSnapshot.value.toString())
                    val dataModel = postSnapshot.getValue<DataModel>()

                    facList!!.add(dataModel!!.name!!);

                }
                facAdapter!!.notifyDataSetChanged()


            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(ContentValues.TAG, "loadPost:onCancelled", databaseError.toException())
                // ...
            }
        })
    }

    private fun getplace() {
        val myMostViewedPostsQuery = dRef!!.child(Tags.TABLE_Places).orderByChild("faculty_name")
            .equalTo(addDataModel.idfac);
        placeList!!.clear()
        myMostViewedPostsQuery.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                placeList!!.clear()

                for (postSnapshot in dataSnapshot.children) {
                    // TODO: handle the post
                    Log.e(ContentValues.TAG, postSnapshot.value.toString())
                    val dataModel = postSnapshot.getValue<DataModel>()

                    placeList!!.add(dataModel!!.name!!);

                }
                placeAdapter!!.notifyDataSetChanged()


            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(ContentValues.TAG, "loadPost:onCancelled", databaseError.toException())
                // ...
            }
        })
    }

    fun openSheet() {
        binding!!.expandLayout.expand()
    }

    fun closeSheet() {
        binding!!.expandLayout.collapse()
    }

    fun checkReadPermission() {
        closeSheet()
        if (ActivityCompat.checkSelfPermission(
                this,
                READ_PERM
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this, arrayOf(READ_PERM), READ_REQ)
        } else {
            SelectImage(READ_REQ)
        }
    }

    fun checkCameraPermission() {
        closeSheet()
        if (ContextCompat.checkSelfPermission(
                this,
                write_permission
            ) == PackageManager.PERMISSION_GRANTED
            && ContextCompat.checkSelfPermission(
                this,
                camera_permission
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            SelectImage(CAMERA_REQ)
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(camera_permission, write_permission),
                CAMERA_REQ
            )
        }
    }

    private fun SelectImage(req: Int) {
        selectedReq = req
        val intent = Intent()
        if (req == READ_REQ) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                intent.action = Intent.ACTION_OPEN_DOCUMENT
                intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION)
            } else {
                intent.action = Intent.ACTION_GET_CONTENT
            }
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            intent.type = "image/*"
            launcher!!.launch(intent)
        } else if (req == CAMERA_REQ) {
            try {
                intent.action = MediaStore.ACTION_IMAGE_CAPTURE
                launcher!!.launch(intent)
            } catch (e: SecurityException) {
                Toast.makeText(this, "perm_image_denied", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(this, "perm_image_denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == READ_REQ) {
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                SelectImage(requestCode)
            } else {
                Toast.makeText(this, "perm_image_denied", Toast.LENGTH_SHORT)
                    .show()
            }
        } else if (requestCode == CAMERA_REQ) {
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                SelectImage(requestCode)
            } else {
                Toast.makeText(this, "perm_image_denied", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun getUriFromBitmap(bitmap: Bitmap): Uri? {
        val bytes = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        return Uri.parse(MediaStore.Images.Media.insertImage(this.contentResolver, bitmap, "", ""))
    }

    private fun setuser(uri: Uri) {

        val post = ReportModel(
            addDataModel.subject,
            addDataModel.desc,
            addDataModel.iddis,
            addDataModel.idfac,
            addDataModel.idplace,
            addDataModel.periority,
            uri.toString(),
            "new",
            preferences!!.getUserData(this).email!!.replaceAfter("@", "").replace("@", "")
        )
        val postValues = post.toMap()
        dRef!!.child(Tags.TABLE_REPORTS)
            .child(addDataModel.subject)
            .setValue(postValues).addOnSuccessListener {
                dialog.dismiss()
                finish()
            }.addOnFailureListener {
                Toast.makeText(this, "invaild user", Toast.LENGTH_LONG).show()

            }
    }

    fun setUpToolbar(
        binding: ToolbarBinding,
        title: String?,
        background: Int,
        arrowTitleColor: Int
    ) {
        binding.lang = "en"
        binding.title = title
        binding.llBack.setColorFilter(ContextCompat.getColor(this, arrowTitleColor))
        binding.tvTitle.setTextColor(ContextCompat.getColor(this, arrowTitleColor))
        binding.toolbar.setBackgroundResource(background)
        binding.llBack.setOnClickListener { v -> finish() }
    }
}
