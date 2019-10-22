package com.example.mymusic

import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Toast
import androidx.core.app.ActivityCompat
import kotlinx.android.synthetic.main.activity_music.*
import kotlinx.android.synthetic.main.ticket.view.*
import java.lang.Exception

class MusicActivity : AppCompatActivity() {

    var listSongs = ArrayList<SongInfo>()
    var adapter : mysongAdapter? = null
    var mp : MediaPlayer ? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music)
        checkUserPermission()
    }

    inner class mysongAdapter: BaseAdapter {
        var myListSong = ArrayList<SongInfo>()
        constructor(myListSong :  ArrayList<SongInfo>):super(){
            this.myListSong = myListSong
        }
        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val myView = layoutInflater.inflate(R.layout.ticket , null)
            val song =this.myListSong[position]
            myView.songName.text =song.Title
            myView.AuthName.text = song.AutherName

            //to play the song
            myView.songName.setOnClickListener {
                mp = MediaPlayer()

                if(mp !== null) {

                    try {
                        mp!!.reset()
                        mp!!.setDataSource(song.SongUrl)
                        mp!!.prepare()
                        mp!!.start()
                    }
                    catch (e: Exception) {
                        Toast.makeText(this@MusicActivity , "catch" , Toast.LENGTH_SHORT).show()
                    }
                }
            }
            return myView
        }

        override fun getItem(Item: Int): Any {
            return this.myListSong[Item]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            return this.myListSong.size
        }

    }


    fun checkUserPermission(){
        if(Build.VERSION.SDK_INT>= 23){
            if(ActivityCompat.checkSelfPermission(this , android.Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
                    requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                        REQUEST_CODE_ASK_PERMISSIONS)
                return
            }
        }
        LoadSong()
    }

    private val REQUEST_CODE_ASK_PERMISSIONS =123

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray)
    {
        when(requestCode){
            REQUEST_CODE_ASK_PERMISSIONS-> if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
            LoadSong()
            }else
            {
                Toast.makeText(this , "Permission Denied" ,Toast.LENGTH_SHORT).show()
            }
            else ->super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    fun LoadSong(){
        val allSongURI = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val selection = MediaStore.Audio.Media.IS_MUSIC+"!=0"
        val cursor =contentResolver.query(allSongURI , null ,  selection , null ,null)

        if(cursor != null)
        {
            if(cursor.moveToNext()){
                do{
                    val songURL = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA))
                    val songgAuthor = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST))
                    val songName = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME))

                    listSongs.add(SongInfo(songName , songgAuthor , songURL))
                }while (cursor.moveToNext())
            }
            cursor.close()
            adapter = mysongAdapter(listSongs)
            songList.adapter = adapter

        }
    }


}
