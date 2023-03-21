package com.example.todoapp.ui

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.example.todoapp.R
import com.example.todoapp.data.ListViewModel
import com.example.todoapp.utils.UIMode
import java.util.*

class TaskFragment : Fragment() {

    private var listener: NewTaskListener? = null
    private var modeEditing = false
    private var editingPos: Int? = null
    private lateinit var titleText: EditText
    private lateinit var descText: EditText
    private lateinit var docText: EditText
    private lateinit var doneBtn: ImageView
    private lateinit var editTitleText: String
    private lateinit var editDescText: String
    private lateinit var editDocText: String

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is NewTaskListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement NewTaskListener")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                parentFragmentManager.findFragmentById(R.id.fragment_container)
                    ?.let { tF ->
                        parentFragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_left, R.anim.exit_to_left)
                            .remove(tF)
                            .commit() }
                (activity as MainActivity).toggleUI(UIMode.MODE_2)
            }
        })
    }

    @Suppress("DEPRECATION")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_task, container, false)

        titleText = view.findViewById(R.id.titleText)
        descText = view.findViewById(R.id.descriptionText)
        docText = view.findViewById(R.id.date_of_completion_text)
        doneBtn = view.findViewById(R.id.doneBtn)

        val editViewModel = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable("item", ListViewModel::class.java)
        } else {
            arguments?.getParcelable("item")
        }

        editingPos = arguments?.getInt("itemPos")
        editViewModel?.let {
            modeEditing = true
            titleText.setText(editViewModel.title)
            descText.setText(editViewModel.desc)
            docText.setText(editViewModel.date)
        }

        docText.setOnClickListener {
            completionDatePicker()
        }

        doneBtn.setOnClickListener {
            editTitleText = titleText.text.toString()
            editDescText = descText.text.toString()
            editDocText = docText.text.toString()
            val imm: InputMethodManager =
                requireActivity().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(doneBtn.windowToken, 0)
            if (modeEditing) {
                editingPos?.let { pos -> listener?.onEditTask(ListViewModel(editTitleText, editDescText, editDocText), pos) }
            }
            else
                listener?.onNewTask(task = ListViewModel(editTitleText, editDescText, editDocText))
            activity?.onBackPressedDispatcher?.onBackPressed()
        }
        return view
    }

    interface NewTaskListener {
        fun onNewTask(task: ListViewModel)
        fun onEditTask(task: ListViewModel, pos: Int)
    }

    private fun completionDatePicker(){
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, birthYear, monthOfYear, dayOfMonth ->
                val dat = (dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + birthYear)
                docText.setText(dat)
            },
            year, month, day
        )
        datePickerDialog.datePicker.minDate = c.timeInMillis
        datePickerDialog.show()
    }
}