package com.tahhan.micalculator.view

import android.graphics.Color.GRAY
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tahhan.micalculator.App.Companion.sharedPrefsManager
import com.tahhan.micalculator.R
import com.tahhan.micalculator.model.CalculatorRepositoryImpl
import com.tahhan.micalculator.toast
import com.tahhan.micalculator.viewmodel.CalculatorViewModel
import com.tahhan.micalculator.viewmodel.CalculatorViewModelFactory
import kotlinx.android.synthetic.main.fragment_calculator.*

class CalculatorFragment : Fragment() {
    private var operationIsSelected = false
    private var numberIsEntered = false
    private var firstOperand = 0.0F
    private var operation = ""
    private var secondOperand = ""
    private val primaryColor by lazy {
        ContextCompat.getColor(requireContext(), R.color.purple_700)
    }
    private val viewModel: CalculatorViewModel by viewModels {
        CalculatorViewModelFactory(CalculatorRepositoryImpl(firstOperand), sharedPrefsManager)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_calculator, container, false)
    }

    override fun onStart() {
        super.onStart()
        firstOperand = sharedPrefsManager.firstOperand
        resultTextView.text = firstOperand.toString()
        setupListeners()
        observeFirstOperand()
        populateRV()
    }



    private fun observeOperationHistory() {
        viewModel.getOperationHistoryLiveData().observe(viewLifecycleOwner, Observer {
            operationsHistoryTextView.visibility = View.VISIBLE
            historyRV.visibility = View.VISIBLE
            historyRV.adapter = OperationAdapter(it) { operationItem ->
                secondOperand = operationItem.secondOperand.toString()
                operation = operationItem.operation
                when (operation) {
                    "+" -> operation = "-"
                    "-" -> operation = "+"
                    "*" -> operation = "/"
                    "/" -> operation = "*"
                    else -> {
                    }
                }
                operate(secondOperand)
            }
        })
    }

    private fun observeFirstOperand() {
        viewModel.getFirstOperandLiveData().observe(viewLifecycleOwner, Observer {
            firstOperand = it.toFloat()
            resultTextView.text = firstOperand.toString()
        })
    }


    private fun setupListeners() {
        divisionButton.setOnClickListener {
            operation = "/"
            makeButtonsNonClickable(operation)
        }
        minusButton.setOnClickListener {
            operation = "-"
            makeButtonsNonClickable(operation)
        }
        plusButton.setOnClickListener {
            operation = "+"
            makeButtonsNonClickable(operation)
        }
        multiplyButton.setOnClickListener {
            operation = "*"
            makeButtonsNonClickable(operation)
        }



        resetButton.setOnClickListener {
            secondOperand = ""
            makeButtonsClickable()
            viewModel.reset()
        }

        deselectButton.setOnClickListener {
            operation = ""
            makeButtonsClickable()
        }
        redoButton.setOnClickListener {
            viewModel.redo()
            makeButtonsClickable()
        }
        undoButton.setOnClickListener {
            viewModel.undo()
            makeButtonsClickable()
        }
        setEqualButtonState()
        setEnterNumberEditTextChangeListener()
    }

    private fun setEnterNumberEditTextChangeListener() {
        with(enterNumberEditText) {
            doOnTextChanged { text, _, _, _ ->
                setNumberIsEnteredState(text)
            }
        }
    }

    private fun setNumberIsEnteredState(text: CharSequence?) {
        numberIsEntered = true
        setEqualButtonState()
        if (text?.isNotEmpty()!!) {
            numberIsEntered = true
            setEqualButtonState()
        } else {
            numberIsEntered = false
            setEqualButtonState()
        }
    }

    private fun operate(secondOperand: String) {
        val returnValue = viewModel.operate(operation, secondOperand.toInt())
        if (returnValue !is Exception) {
            firstOperand = returnValue.toString().toFloat()
            resultTextView.text = firstOperand.toString()
        } else {
            returnValue.message?.let { requireContext().toast(it) }
        }
    }


    private fun makeButtonsNonClickable(operation: String) {
        when (operation) {
            "+" -> {
                setPlusOnlyClickable()
            }
            "-" -> {
                setMinusOnlyClickable()
            }
            "/" -> {
                setDivisionOnlyClickable()
            }
            "*" -> {
                setMultiplyOnlyClickable()
            }
        }
        operationIsSelected = true
        setEqualButtonState()
    }

    private fun setMultiplyOnlyClickable() {
        divisionButton.isClickable = false
        plusButton.isClickable = false
        minusButton.isClickable = false
        divisionButton.setBackgroundColor(GRAY)
        plusButton.setBackgroundColor(GRAY)
        minusButton.setBackgroundColor(GRAY)
    }

    private fun setDivisionOnlyClickable() {
        minusButton.isClickable = false
        plusButton.isClickable = false
        multiplyButton.isClickable = false
        minusButton.setBackgroundColor(GRAY)
        plusButton.setBackgroundColor(GRAY)
        multiplyButton.setBackgroundColor(GRAY)
    }

    private fun setMinusOnlyClickable() {
        divisionButton.isClickable = false
        plusButton.isClickable = false
        multiplyButton.isClickable = false
        divisionButton.setBackgroundColor(GRAY)
        plusButton.setBackgroundColor(GRAY)
        multiplyButton.setBackgroundColor(GRAY)
    }

    private fun setPlusOnlyClickable() {
        divisionButton.isClickable = false
        minusButton.isClickable = false
        multiplyButton.isClickable = false
        multiplyButton.setBackgroundColor(GRAY)
        divisionButton.setBackgroundColor(GRAY)
        minusButton.setBackgroundColor(GRAY)
    }

    private fun makeButtonsClickable() {
        divisionButton.isClickable = true
        minusButton.isClickable = true
        multiplyButton.isClickable = true
        plusButton.isClickable = true
        multiplyButton.setBackgroundColor(primaryColor)
        divisionButton.setBackgroundColor(primaryColor)
        minusButton.setBackgroundColor(primaryColor)
        plusButton.setBackgroundColor(primaryColor)
        enterNumberEditText.text?.clear()
    }

    private fun populateRV() {
        historyRV.layoutManager = LinearLayoutManager(
            requireContext(),
            RecyclerView.VERTICAL, false
        )
        observeOperationHistory()
    }

    private fun setEqualButtonState() {
        with(equalButton) {
            if (operationIsSelected && numberIsEntered) {
                setBackgroundColor(primaryColor)
                setOnClickListener {
                    secondOperand = enterNumberEditText.text.toString()
                    operate(secondOperand)
                    makeButtonsClickable()
                    populateRV()
                    isClickable = false
                    operationIsSelected = false
                    numberIsEntered = false
                    setBackgroundColor(GRAY)
                }
            } else isClickable = false
        }
    }
}