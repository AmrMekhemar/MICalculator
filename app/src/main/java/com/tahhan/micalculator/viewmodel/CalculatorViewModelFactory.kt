package com.tahhan.micalculator.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tahhan.micalculator.model.CalculatorRepositoryImpl
import com.tahhan.micalculator.model.SharedPrefsManager

/**
 * Calculator viewModel factory
 *
 * @property repo
 * @property sharedPrefsManager
 * @constructor Create a CalculatorViewModelFactory object
 */
class CalculatorViewModelFactory(
    private val repo: CalculatorRepositoryImpl,
    private val sharedPrefsManager: SharedPrefsManager
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CalculatorViewModel(repo, sharedPrefsManager) as T
    }
}



