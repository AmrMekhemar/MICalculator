package com.tahhan.micalculator.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tahhan.micalculator.model.CalculatorRepository
import com.tahhan.micalculator.model.CalculatorRepositoryImpl
import com.tahhan.micalculator.model.Operation

class CalculatorViewModel(
    private val repository: CalculatorRepositoryImpl
) : ViewModel(), CalculatorRepository {

    override fun operate(operation: String, secondOperand: Int): Any =
        repository.operate(operation, secondOperand)


    override fun redo() {
        repository.redo()
    }

    override fun undo() {
        repository.undo()
    }

    override fun reset() {
        repository.reset()
    }

    fun getOperationHistoryLiveData(): MutableLiveData<List<Operation>> =
        repository.operationHistoryLiveData


    fun getFirstOperandLiveData(): MutableLiveData<Float> = repository.firstOperandLiveData

}