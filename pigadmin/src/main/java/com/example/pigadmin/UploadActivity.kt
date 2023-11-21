package com.example.pigadmin

import android.app.Instrumentation.ActivityResult
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.graphics.alpha
import com.example.pigadmin.databinding.ActivityMainBinding
import com.example.pigadmin.databinding.ActivityUploadBinding
import com.google.android.gms.auth.api.signin.internal.Storage
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.selects.select

class UploadActivity : AppCompatActivity() {


    private lateinit var binding: ActivityUploadBinding
    private lateinit var databaseReference: DatabaseReference
    private lateinit var storageRef: StorageReference

    private var uri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadBinding.inflate(layoutInflater)
        setContentView(binding.root)


        databaseReference = FirebaseDatabase.getInstance().getReference("Phone Directory")
        storageRef = FirebaseStorage.getInstance().getReference("Images")


        val pickImage = registerForActivityResult(ActivityResultContracts.GetContent()) {
            binding.uploadImage.setImageURI(it)
            if (it != null) {

            }
        }


        binding.uploadImage.setOnClickListener {
            pickImage.launch("image/*")
        }


            val phoneID = databaseReference.push().key!!
            var userss: UserData

            uri?.let {
                storageRef.child(phoneID).putFile(it)
                    .addOnSuccessListener { task ->
                        task.metadata!!.reference!!.downloadUrl
                            .addOnSuccessListener { url ->
                                val imgUrl = url.toString()


                                binding.saveButton.setOnClickListener {
                                    val name = binding.uploadName.text.toString()
                                    val operator = binding.uploadOperator.text.toString()
                                    val location = binding.uploadLocation.text.toString()
                                    val phone = binding.uploadPhone.text.toString()

                                    val userss = UserData(name, operator, location, phone, imgUrl)
                                    databaseReference.child(phone).setValue(userss)
                                        .addOnSuccessListener {
                                            binding.uploadName.text.clear()
                                            binding.uploadOperator.text.clear()
                                            binding.uploadLocation.text.clear()
                                            binding.uploadPhone.text.clear()



                                            Toast.makeText(this, "Save", Toast.LENGTH_SHORT).show()

                                            val intent = Intent(
                                                this@UploadActivity,
                                                MainActivity::class.java
                                            )
                                            startActivity(intent)
                                            finish()


                                        }.addOnFailureListener {
                                        Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
                                    }
                                }

                            }
                    }


            }
        }
    }


