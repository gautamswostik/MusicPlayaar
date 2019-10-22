package com.example.mymusic

class SongInfo {
    var Title : String ? = null
    var AutherName : String ? = null
    var SongUrl : String ? = null
    constructor(Title:String , AutherName:String , SongUrl:String){
        this.Title =Title
        this.AutherName = AutherName
        this.SongUrl = SongUrl
    }
}