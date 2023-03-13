package com.example.todoapp.ui

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
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
import com.example.todoapp.data.Task
import java.util.*


class ListFragment : Fragment() {

    private var listener: NewTaskListener? = null

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
            ?.let { it1 -> parentFragmentManager.beginTransaction().remove(it1).commit() }
                (activity as MainActivity).toggleUI(UIMode.MODE_2)
            }
        })
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_list, container, false)

        val titleText = view.findViewById<EditText>(R.id.titleText)
        val descText = view.findViewById<EditText>(R.id.descriptionText)
        val docText = view.findViewById<EditText>(R.id.date_of_completion_text)
        val doneBtn = view.findViewById<ImageView>(R.id.doneBtn)

        docText.setOnClickListener{
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(requireContext(),
                { _, birthYear, monthOfYear, dayOfMonth ->
                    val dat = (dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + birthYear)
                    docText.setText(dat)
                },
                year, month, day
            )
            datePickerDialog.datePicker.minDate = c.timeInMillis
            datePickerDialog.show()
        }

        doneBtn.setOnClickListener{
            val title = titleText.text.toString()
            val desc = descText.text.toString()
            val date = docText.text.toString()
            val imm: InputMethodManager = requireActivity().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(doneBtn.windowToken, 0)
            listener?.onNewTask(task = Task(title,desc,date))
            activity?.onBackPressedDispatcher?.onBackPressed()
        }

        val viewModel = arguments?.getParcelable<ListViewModel>("item")
        if (viewModel != null) {
            titleText.setText(viewModel.title)
            descText.setText(viewModel.desc)
            docText.setText(viewModel.date)
        }

        return view
    }

    interface NewTaskListener{
        fun onNewTask(task: Task)
    }

}