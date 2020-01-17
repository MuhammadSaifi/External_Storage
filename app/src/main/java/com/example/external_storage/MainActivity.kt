package com.example.external_storage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import java.io.*

class MainActivity : AppCompatActivity() {

    // External Storage like our SD card and phone storage.
    // where we will store the data of our user.
    // here first we check device has space or not.
    // after that user will enter name of his folder
    // and type information that we will store in our external storage.


    // filepath means our mobile storage etc.
    private val filepath = "MyFileStorage"

    internal var myExternalFile: File?=null


    // in isExternalStorageReadOnly first we check storage readable or not.
    // if our data readonly we can write also check in bolean format.
    // if readable then true if not then false.

    private val isExternalStorageReadOnly: Boolean get() {
        val extStorageState = Environment.getExternalStorageState()
        return if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)) {
            true
        } else {
            false
        }
    }

    // after that we will check storage available or not in boolean format.
    // if yes then we will perform action.

    private val isExternalStorageAvailable: Boolean get() {
        val extStorageState = Environment.getExternalStorageState()
        return if (Environment.MEDIA_MOUNTED.equals(extStorageState)) {
            true
        } else{
            false
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fileName = findViewById(R.id.editTextFile) as EditText
        val fileData = findViewById(R.id.editTextData) as EditText
        val saveButton = findViewById<Button>(R.id.button_save) as Button
        val viewButton = findViewById(R.id.button_view) as Button



        saveButton.setOnClickListener(View.OnClickListener {

            //  get file name of from user and save in variable.
            // other our default file name already declared in uper.

            myExternalFile = File(getExternalFilesDir(filepath), fileName.text.toString())
            try {

                // now we deal with our files.
                //fileOutPutStream in that variable we save file name and create.
                // in our storage.

                val fileOutPutStream = FileOutputStream(myExternalFile)

                // now we write in that file in the form of array.
                // and close file .
                // write will be done .

                fileOutPutStream.write(fileData.text.toString().toByteArray())
                fileOutPutStream.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            Toast.makeText(applicationContext,"data save",Toast.LENGTH_SHORT).show()
        })

        // now we read our data and display it.

        viewButton.setOnClickListener(View.OnClickListener {
        // again we get path of our file.
            myExternalFile = File(getExternalFilesDir(filepath), fileName.text.toString())

            val filename = fileName.text.toString()
            myExternalFile = File(getExternalFilesDir(filepath),filename)
            if(filename.toString()!=null && filename.toString().trim()!=""){
                var fileInputStream = FileInputStream(myExternalFile)
                var inputStreamReader: InputStreamReader = InputStreamReader(fileInputStream)
                val bufferedReader: BufferedReader = BufferedReader(inputStreamReader)
                val stringBuilder: StringBuilder = StringBuilder()
                var text: String? = null
                while ({ text = bufferedReader.readLine(); text }() != null) {
                    stringBuilder.append(text)
                }
                fileInputStream.close()
                //Displaying data on EditText
                Toast.makeText(applicationContext,stringBuilder.toString(),Toast.LENGTH_SHORT).show()
            }
        })

        if (!isExternalStorageAvailable || isExternalStorageReadOnly) {
            saveButton.isEnabled = false
        }
    }
}
