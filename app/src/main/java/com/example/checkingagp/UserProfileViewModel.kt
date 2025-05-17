package com.example.checkingagp

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import javax.inject.Inject

@HiltViewModel
class UserProfileViewModel @Inject constructor(
    private val myDao: My_DAO
) : ViewModel(){

    private val _userProfileData = MutableStateFlow<MainUserProfileData?>(null)
    val userProfileData : StateFlow<MainUserProfileData?>get() = _userProfileData.asStateFlow()
    
    val tag = "UserProfile"

    fun errorHandlingMechanism(block : suspend () -> Unit){
        viewModelScope.launch { 
            try {
                block()
            }
            catch (e : Exception){
                Log.e(tag , "Error is : ${e.message}")
            }
        }
    }
    fun fetchUserDataFromUserName(userName : String ){
        errorHandlingMechanism {
           val result =  myDao.getUserDataFromUserName(userName)
            Log.d(tag,"Result is fetched")
            _userProfileData.value = result
        }
    }
}