package com.tahhan.micalculator

import androidx.lifecycle.MutableLiveData
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.tahhan.micalculator.model.CalculatorRepositoryImpl
import com.tahhan.micalculator.model.Operation
import com.tahhan.micalculator.viewmodel.CalculatorViewModel
import com.tahhan.micalculator.model.SharedPrefsManager
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CalculatorViewModelUnitTests {
    @Mock
    lateinit var repo: CalculatorRepositoryImpl

    @Mock
    lateinit var sharedPrefsManager: SharedPrefsManager
    lateinit var viewModel: CalculatorViewModel

    @Mock
    lateinit var exception: Exception

    @Mock
    lateinit var historyListLiveData: MutableLiveData<List<Operation>>
    @Mock
    lateinit var firstOperandLiveData : MutableLiveData<Float>


    @Before
    fun setup() {
        // with assumption that firstOperand = 5
        viewModel = CalculatorViewModel(repo, sharedPrefsManager)
        whenever(repo.operate("+", 5)).thenReturn(10F)
        whenever(repo.operate("-", 5)).thenReturn(0F)
        whenever(repo.operate("/", 5)).thenReturn(1F)
        whenever(repo.operate("/", 0)).thenReturn(exception)
        whenever(repo.operate("*", 5)).thenReturn(25F)
        whenever(repo.redo()).thenReturn(10F)
        whenever(repo.undo()).thenReturn(5F)
        whenever(repo.operationHistoryLiveData).thenReturn(historyListLiveData)
        whenever(repo.firstOperandLiveData).thenReturn(firstOperandLiveData)
    }

    @Test
    fun operate_should_return10_whenAdding5() {
        val returnValue = viewModel.operate("+", 5)
        assertEquals(10F, returnValue)
    }

    @Test
    fun operate_should_return0_whenSubtracting5() {
        val returnValue = viewModel.operate("-", 5)
        assertEquals(0F, returnValue)
    }

    @Test
    fun operate_should_returnException_whenDividingBy0() {
        val returnValue = viewModel.operate("/", 0)
        assertEquals(exception, returnValue)
    }

    @Test
    fun operate_should_return1_whenDividingBy5() {
        val returnValue = viewModel.operate("/", 5)
        assertEquals(1F, returnValue)
    }

    @Test
    fun operate_should_return25_whenMultiplyBy5() {
        val returnValue = viewModel.operate("*", 5)
        assertEquals(25F, returnValue)
    }

    @Test
    fun operate_delegates_theOperationToTheRepository() {
        viewModel.operate("+", 5)
        verify(repo).operate("+", 5)
    }

    @Test
    fun redo_returns10() {
        val returnValue = viewModel.redo()
        assertEquals(10F, returnValue)
    }

    @Test
    fun redo_delegates_toTheRepository() {
        viewModel.redo()
        verify(repo).redo()
    }

    @Test
    fun undo_returns5() {
        val returnValue = viewModel.undo()
        assertEquals(5F, returnValue)
    }

    @Test
    fun undo_delegates_toTheRepository() {
        viewModel.undo()
        verify(repo).undo()
    }

    @Test
    fun reset_delegates_toTheRepository() {
        viewModel.reset()
        verify(repo).reset()
    }

    @Test
    fun getOperationHistoryLiveData_returnMutableLiveDataObject() {
        val returnValue = viewModel.getOperationHistoryLiveData()
        assertEquals(historyListLiveData, returnValue)
    }

    @Test
    fun getOperationHistoryLiveData_DelegatesToRepo() {
        viewModel.getOperationHistoryLiveData()
        verify(repo).operationHistoryLiveData
    }
    @Test
    fun getFirstOperandLiveData_returnMutableLiveDataObject() {
        val returnValue = viewModel.getOperationHistoryLiveData()
        assertEquals(historyListLiveData, returnValue)
    }
    @Test
    fun getFirstOperandLiveData_delegatesToRepository(){
        viewModel.getFirstOperandLiveData()
        verify(repo).firstOperandLiveData
    }

}


