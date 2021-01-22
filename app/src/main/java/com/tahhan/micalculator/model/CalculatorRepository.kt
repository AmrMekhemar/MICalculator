package com.tahhan.micalculator.model

interface CalculatorRepository {
     fun operate(operation:String,secondOperand:Int): Any
     fun redo():Float
     fun undo():Float
     fun reset()
}