package com.example.proyectofinal


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Delete
import com.example.proyectofinal.model.User
import com.example.proyectofinal.repository.UserRepositoryImp
import dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories_InternalFactoryFactory_Factory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepositoryImp: UserRepositoryImp
): ViewModel(){
    private val _isLoading: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }

    val users: LiveData<List<User>> by lazy{
        userRepositoryImp.getAllUser()
    }

    val isLoading: LiveData<Boolean> get() = _isLoading

    fun addUser(){
        if(_isLoading.value == false)
            viewModelScope.launch(Dispatchers.IO){
                _isLoading.postValue(true)
                userRepositoryImp.getNewUser()
                _isLoading.postValue(false)
            }
    }

    fun deleteUser(toDelete: User){
        viewModelScope.launch(Dispatchers.IO){
            userRepositoryImp.deleteUser(toDelete)

        }

    }
}