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
import com.tahhan.micalculator.viewmodel.CalculatorViewModel
import com.tahhan.micalculator.viewmodel.CalculatorViewModelFactory
import kotlinx.android.synthetic.main.fragment_calculator.*
import kotlin.math.min

/**
 * Calculator fragment
 *
 */
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

    /** Inflate the layout for this fragment
     *
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_calculator, container, false)
    }

    /**
     * setting up the layout and binding data to views
     * and subscribing to the livedata objects
     */
    override fun onStart() {
        super.onStart()
        firstOperand = sharedPrefsManager.firstOperand
        resultTextView.text = firstOperand.toString()
        setupListeners()
        observeFirstOperand()
        populateRV()
    }


    /**
     * Observes the History liveData object and binds the data to recyclerView
     * and sets the itemListener for the recyclerView
     */
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

    /**
     * Observes the firOperand liveData object and binds the value to the view
     */
    private fun observeFirstOperand() {
        viewModel.getFirstOperandLiveData().observe(viewLifecycleOwner, Observer {
            firstOperand = it.toFloat()
            resultTextView.text = firstOperand.toString()
        })
    }

    /**
     * Setup listeners for the views
     *
     */
    private fun setupListeners() {
        divisionButton.setOnClickListener {
            operation = "/"
            requireView().makeButtonsNonClickable(
                operation,
                plusButton,
                minusButton,
                multiplyButton
            )
            operationIsSelected = true
            setEqualButtonState()
        }
        minusButton.setOnClickListener {
            operation = "-"
            requireView().makeButtonsNonClickable(
                operation,
                plusButton,
                divisionButton,
                multiplyButton
            )
            operationIsSelected = true
            setEqualButtonState()
        }
        plusButton.setOnClickListener {
            operation = "+"
            requireView().makeButtonsNonClickable(
                operation,
                divisionButton,
                minusButton,
                multiplyButton
            )
            operationIsSelected = true
            setEqualButtonState()
        }
        multiplyButton.setOnClickListener {
            operation = "*"
            requireView().makeButtonsNonClickable(
                operation,
                plusButton,
                minusButton,
                divisionButton
            )
            operationIsSelected = true
            setEqualButtonState()
        }



        resetButton.setOnClickListener {
            secondOperand = ""
            requireView().makeButtonsClickable(
                divisionButton,
                minusButton,
                multiplyButton, plusButton, enterNumberEditText
            )
            viewModel.reset()
        }

        deselectButton.setOnClickListener {
            operation = ""
            requireView().makeButtonsClickable(
                divisionButton,
                minusButton,
                multiplyButton, plusButton, enterNumberEditText
            )
            numberIsEntered = false
            operationIsSelected = false
            setEqualButtonState()
        }
        redoButton.setOnClickListener {
            viewModel.redo()
            requireView().makeButtonsClickable(
                divisionButton,
                minusButton,
                multiplyButton, plusButton, enterNumberEditText
            )
        }
        undoButton.setOnClickListener {
            viewModel.undo()
            requireView().makeButtonsClickable(
                divisionButton,
                minusButton,
                multiplyButton, plusButton, enterNumberEditText
            )
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
                    this.makeButtonsClickable(
                        divisionButton,
                        minusButton,
                        multiplyButton, plusButton, enterNumberEditText
                    )
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