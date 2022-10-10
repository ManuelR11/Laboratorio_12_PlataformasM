package com.example.laboratorio_12;

import android.provider.Settings.Secure.getString
import android.provider.SyncStateContract
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SessionViewModel:ViewModel() {
    private val _validAuthToken: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val validAuthToken: StateFlow<Boolean> = _validAuthToken

    fun triggerStateFlow(){
        viewModelScope.launch {
            _validAuthToken.value = true
            delay(30000L)
            _validAuthToken.value = false
        }
    }

    fun cerrarSesion(){
        _validAuthToken.value = false
    }

    fun inicioSesion(){
        _validAuthToken.value = true
    }
}