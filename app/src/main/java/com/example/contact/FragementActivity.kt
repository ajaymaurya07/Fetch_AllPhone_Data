package com.example.contact

import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.CallLog
import android.provider.ContactsContract
import android.provider.Telephony
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.contact.adapter.ViewPagerAdapter
import com.example.contact.databinding.ActivityFragementBinding
import com.example.contact.dataclass.CalllogModel
import com.example.contact.dataclass.Contact
import com.example.contact.dataclass.MessageModel
import com.example.contact.viewmodel.MyViewModel
import com.google.android.material.tabs.TabLayoutMediator

class FragementActivity : AppCompatActivity() {
    lateinit var binding: ActivityFragementBinding
    lateinit var viewModel: MyViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_fragement)

        var tabnames= arrayOf("contact","message","calllog")
        viewModel=ViewModelProvider(this)[MyViewModel::class.java]
        var viewPagerAdapter= ViewPagerAdapter(this)
        binding.viewPager.adapter=viewPagerAdapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = tabnames[position]
        }.attach()


//        read contact
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.READ_CONTACTS), 100)
        } else {
            // Permissions already granted
            getContacts()
        }


//        for message
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.READ_SMS),
                101
            )
        } else {
            // Permissions already granted
            fetchSMS()
        }


//        FOR LOGCALL

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.READ_CALL_LOG),
                102
            )
        } else {
            // Permissions already granted
            getRecentCalls()
        }

    }


    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            100 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getContacts()
                } else {
                    // Handle permission denied for contacts
                    // toast("Permission must be granted to display contacts information")
                }
            }
            101 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    fetchSMS()
                } else {
                    // Handle permission denied for SMS
                    // toast("Permission must be granted to display SMS information")
                }
            }
            102 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getRecentCalls()
                } else {
                    // Handle permission denied for SMS
                    // toast("Permission must be granted to display SMS information")
                }
            }
            // Add more cases if needed for other permissions
        }
    }

    private fun fetchSMS() {
        val list: MutableList<MessageModel> = mutableListOf()
        val contentResolver: ContentResolver = contentResolver
        // Uri for SMS content
        val uri: Uri = Uri.parse("content://sms")
        // Columns to retrieve
        val projection = arrayOf(
            Telephony.Sms._ID,
            Telephony.Sms.ADDRESS,
            Telephony.Sms.BODY,
            Telephony.Sms.DATE,
            Telephony.Sms.TYPE
        )
        // Sort by date in descending order
        val sortOrder = Telephony.Sms.DATE + " DESC"
        // Query the SMS content provider
        val cursor: Cursor? = contentResolver.query(uri, projection, null, null, sortOrder)

        cursor?.use {
            while (it.moveToNext()) {
                val messageId = it.getLong(it.getColumnIndexOrThrow(Telephony.Sms._ID))
                val address = it.getString(it.getColumnIndexOrThrow(Telephony.Sms.ADDRESS))
                val body = it.getString(it.getColumnIndexOrThrow(Telephony.Sms.BODY))
                val date = it.getLong(it.getColumnIndexOrThrow(Telephony.Sms.DATE))
                val type = it.getInt(it.getColumnIndexOrThrow(Telephony.Sms.TYPE))

                list.add(MessageModel(messageId,address,body,date,type))
            }
        }
        viewModel.messagealldata.value=list
    }



    @SuppressLint("Range")
    fun getRecentCalls() {
        val contentResolver: ContentResolver = contentResolver
        val uri: Uri = CallLog.Calls.CONTENT_URI
        var list: MutableList<CalllogModel> = mutableListOf()

        // Projection to specify which columns you want to retrieve
        val projection = arrayOf(
            CallLog.Calls._ID,
            CallLog.Calls.NUMBER,
            CallLog.Calls.DATE,
            CallLog.Calls.DURATION,
            CallLog.Calls.TYPE
        )
        // Sorting by date in descending order to get the most recent calls first
        val sortOrder = "${CallLog.Calls.DATE} DESC"

        // Querying the call log
        val cursor: Cursor? = contentResolver.query(uri, projection, null, null, sortOrder)

        cursor?.use {
            if (it.moveToFirst()) {
                do {
                    val id = it.getLong(it.getColumnIndex(CallLog.Calls._ID))
                    val number = it.getString(it.getColumnIndex(CallLog.Calls.NUMBER))
                    val date = it.getLong(it.getColumnIndex(CallLog.Calls.DATE))
                    val duration = it.getLong(it.getColumnIndex(CallLog.Calls.DURATION))
                    val type = it.getInt(it.getColumnIndex(CallLog.Calls.TYPE))
                    list.add(CalllogModel(id,number,date,duration,type))
                } while (it.moveToNext())
            } else {
                Log.d("RecentCall", "Call log is empty.")
            }
        }
        viewModel.calllogAlldata.value=list
    }

    @SuppressLint("Range")
    private fun getContacts(){
        val resolver: ContentResolver = contentResolver
        val cursor = resolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null)
        val list: MutableList<Contact> = mutableListOf()

        cursor?.use {
            if (it.count > 0) {
                while (it.moveToNext()) {
                    val id = it.getString(it.getColumnIndex(ContactsContract.Contacts._ID))
                    val name = it.getString(it.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                    // Check if the contact has phone numbers
                    val hasPhoneNumber =
                        it.getInt(it.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0

                    var no :String=""

                    if (hasPhoneNumber) {
                        // Retrieve phone numbers for the contact
                        val cursorPhone = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?", arrayOf(id), null)

                        cursorPhone?.use { phoneCursor ->
                            while (phoneCursor.moveToNext()) {
                                val phoneNumber =
                                    phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                                Log.e("Name ===>", phoneNumber)
                                no =phoneNumber
                            }
                        }
                    }
                    list.add(Contact(id, name, no))

                }
            } else {
                //   toast("No contacts available!")
            }
        }

         viewModel.contactalldata.value=list
    }





}