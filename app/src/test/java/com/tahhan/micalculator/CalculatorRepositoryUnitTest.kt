package com.tahhan.micalculator

import com.tahhan.micalculator.model.CalculatorRepository
import com.tahhan.micalculator.model.CalculatorRepositoryImpl
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class CalculatorRepositoryUnitTest {
    lateinit var repo: CalculatorRepositoryImpl

    @Before
    fun setup() {
        repo = CalculatorRepositoryImpl()
    }

    @Test
    fun operate_addition_isCorrect() {
        repo.firstOperand = 0
        val actual = repo.operate("+", 5)
        val expected = 5
        assertEquals(expected, actual)
    }

    @Test
    fun operate_division_isCorrect() {
        repo.firstOperand = 10
        val actual = repo.operate("/", 5)
        val expected = 2.0F
        assertEquals(expected, actual)
    }

    @Test
    fun when_firstOperandIsZero_operate_division_shouldReturnAnException() {
        repo.firstOperand = 0
        val actual = repo.operate("/", 5)
        val expected = Exception("Can't Divide on Zero")
        assertEquals(expected, actual)
    }


    @Test
    fun operate_subtraction_isCorrect() {
        repo.firstOperand = 0
        val actual = repo.operate("-", 5)
        val expected = -5
        assertEquals(expected, actual)
    }

    @Test
    fun operate_multiplication_isCorrect() {
        repo.firstOperand = 5
        val actual = repo.operate("*", 5)
        val expected = 25
        assertEquals(expected, actual)
    }

    @Test
    fun when_operateFunctionIsCalled_firstOperandMustBeChanged() {
        repo.firstOperand = 5
        repo.operate("*", 5)
        val expected = 25
        assertEquals(expected, repo.firstOperand)
    }



}