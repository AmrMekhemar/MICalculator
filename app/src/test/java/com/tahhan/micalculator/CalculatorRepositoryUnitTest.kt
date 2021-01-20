package com.tahhan.micalculator

import com.tahhan.micalculator.model.CalculatorRepositoryImpl
import org.junit.Test
import org.junit.Assert.*
import org.junit.Before
import java.lang.Exception


class CalculatorRepositoryUnitTest {
    lateinit var repo: CalculatorRepositoryImpl

    @Before
    fun setup() {
        repo = CalculatorRepositoryImpl(5f)
    }

    @Test
    fun operate_addition_isCorrect() {
        val actual = repo.operate("+", 5)
        val expected = 10F
        assertEquals(expected, actual)
    }

    @Test
    fun operate_division_isCorrect() {
        val actual = repo.operate("/", 5)
        val expected = 1F
        assertEquals(expected, actual)
    }

    @Test
    fun when_secondOperandIsZero_operate_division_shouldReturnAnException() {
        val actual = repo.operate("/", 0) as Exception
        val expected = "Can't Divide on Zero"
        assertEquals(expected, actual.message)
    }


    @Test
    fun operate_subtraction_isCorrect() {
        val actual = repo.operate("-", 5)
        val expected = 0F
        assertEquals(expected, actual)
    }

    @Test
    fun operate_multiplication_isCorrect() {
        val actual = repo.operate("*", 5)
        val expected = 25F
        assertEquals(expected, actual)
    }

    @Test
    fun when_operateFunctionIsCalled_firstOperandMustBeChanged() {
        repo.operate("*", 5)
        val expected = 25F
        assertEquals(expected, repo.firstOperand)
    }


}