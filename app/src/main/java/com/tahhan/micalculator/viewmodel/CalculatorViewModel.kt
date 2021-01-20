package com.tahhan.micalculator.viewmodel

import androidx.lifecycle.ViewModel
import com.tahhan.micalculator.model.CalculatorRepository

class CalculatorViewModel(
    private val repository: CalculatorRepository
) : ViewModel(), CalculatorRepository {

    override fun operate(operation: String, secondOperand: Int): Any =
        repository.operate(operation, secondOperand)


    override fun redo() {
        TODO("Not yet implemented")
    }

    override fun undo() {
        TODO("Not yet implemented")
    }


}