package com.manojbhadane.firestoredemo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot


class MainActivity : AppCompatActivity() {

    lateinit var mEdtName: EditText
    lateinit var mEdtMobile: EditText
    lateinit var mFirestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
    }

    fun init() {

        mEdtName = findViewById(R.id.editText) as EditText
        mEdtMobile = findViewById(R.id.editText1) as EditText

        mFirestore = FirebaseFirestore.getInstance()

        mFirestore.collection("UserInfo").get().addOnCompleteListener {
            OnCompleteListener<QuerySnapshot> {
                task ->
                if (task.isSuccessful) {

                    for (document in task.getResult()) {
                        Log.e("----Result-", "--" + document.data)
                    }
                } else {
                    Log.e("----Exception-", "--" + task.exception)
                }
            }
        }


        (findViewById(R.id.btnadd) as Button).setOnClickListener {
            addUserInfo()
        }
    }


    fun addUserInfo() {
        val user = HashMap<String, String>()
        user.put("Name", "" + mEdtName.text)
        user.put("Mobile", "" + mEdtMobile.text)

        var userinfo = user.toMap()

        mFirestore.collection("UserInfo")
                .add(userinfo)
                .addOnSuccessListener {
                    OnSuccessListener<DocumentReference> {
                        documentReference ->
                        Log.e("---id-", "-----" + documentReference.id)
                        Toast.makeText(this, "success", Toast.LENGTH_LONG).show()
                    }
                }
                .addOnFailureListener {
                    OnFailureListener {
                        exception ->
                        Log.e("---EE-", "-----" + exception.message)
                        Toast.makeText(this, "fail", Toast.LENGTH_LONG).show()
                    }
                }
    }
}
