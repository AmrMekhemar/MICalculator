package com.tahhan.micalculator.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tahhan.micalculator.model.CalculatorRepository
import com.tahhan.micalculator.model.CalculatorRepositoryImpl

class CalculatorViewModelFactory(private val repo: CalculatorRepositoryImpl) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CalculatorViewModel(repo) as T
    }
}



