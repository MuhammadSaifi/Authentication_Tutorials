package com.muhammad.authentication_tutorials


class UserProfile {
    lateinit var userAge: String
    lateinit var userEmail: String
    lateinit var userName: String

    constructor(){}
    constructor(userAge: String , userEmail: String, userName: String){
        this.userAge = userAge
        this.userEmail = userEmail
        this.userName = userName
    }
}