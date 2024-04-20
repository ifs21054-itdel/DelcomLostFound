package com.ifs21054.delcomlostfound.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.ifs21054.delcomlostfound.data.pref.UserModel
import com.ifs21054.delcomlostfound.data.remote.MyResult
import com.ifs21054.delcomlostfound.data.remote.response.DelcomLostFoundsResponse
import com.ifs21054.delcomlostfound.data.remote.response.DelcomResponse
import com.ifs21054.delcomlostfound.data.repository.AuthRepository
import com.ifs21054.delcomlostfound.data.repository.LostFoundRepository
import com.ifs21054.delcomlostfound.presentation.ViewModelFactory
import kotlinx.coroutines.launch

class MainViewModel(
    private val authRepository: AuthRepository,
    private val lostfoundRepository: LostFoundRepository
) : ViewModel() {

    fun getSession(): LiveData<UserModel> {
        return authRepository.getSession().asLiveData()
    }

    fun logout() {
        viewModelScope.launch {
            authRepository.logout()
        }
    }

    fun getLostFounds(): LiveData<MyResult<DelcomLostFoundsResponse>> {
        return lostfoundRepository.getLostFounds(null,0,null).asLiveData()
    }

    fun getLostFound(): LiveData<MyResult<DelcomLostFoundsResponse>> {
        return lostfoundRepository.getLostFounds(null, 1, null).asLiveData()
    }

    fun putLostFound(
        lostfoundId: Int,
        title: String,
        description: String,
        isCompleted: Boolean,
        status: String
    ): LiveData<MyResult<DelcomResponse>> {
        return lostfoundRepository.putLostFound(
            lostfoundId,
            title,
            description,
            isCompleted,
            status
        )
    }


    companion object {
        @Volatile
        private var INSTANCE: MainViewModel? = null
        fun getInstance(
            authRepository: AuthRepository,
            lostfoundRepository: LostFoundRepository
        ): MainViewModel {
            synchronized(ViewModelFactory::class.java) {
                INSTANCE = MainViewModel(
                    authRepository,
                    lostfoundRepository
                )
            }
            return INSTANCE as MainViewModel
        }
    }
}