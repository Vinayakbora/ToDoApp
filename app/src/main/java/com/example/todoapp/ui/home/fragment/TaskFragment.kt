package com.example.todoapp.ui.home.fragment

import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.example.todoapp.R
import com.example.todoapp.data.TaskModel
import com.example.todoapp.databinding.FragmentTaskBinding
import com.example.todoapp.ui.home.activity.TaskActivity
import com.example.todoapp.utils.UIMode
import java.util.*

class TaskFragment : Fragment() {

    private lateinit var binding: FragmentTaskBinding
    private var listener: NewTaskListener? = null
    private var modeEditing = false
    private lateinit var editTitleText: String
    private lateinit var editDescText: String
    private lateinit var editDocText: String
    private var editPos: Int = 0
    private var isTitleValidated: Boolean = false
    private var isDOCValidated: Boolean = false

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
                (activity as TaskActivity).toggleUI(UIMode.MODE_2)
            }
        })
    }

    @Suppress("DEPRECATION")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTaskBinding.inflate(inflater, container, false)

        val ediTaskModel = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable("item", TaskModel::class.java)
        } else {
            arguments?.getParcelable("item")
        }


        ediTaskModel?.let {
            modeEditing = true
            binding.titleText.setText(ediTaskModel.title)
            binding.descriptionText.setText(ediTaskModel.desc)
            binding.dateOfCompletionText.setText(ediTaskModel.date)
        }

        binding.dateOfCompletionText.setOnClickListener {
            completionDatePicker()
        }

        binding.doneBtn.setOnClickListener {

            isTitleValidated = validateTitleText(binding.titleText.text.toString(),binding.titleText)
            isDOCValidated = validateDOCText(binding.dateOfCompletionText.text.toString(),binding.dateOfCompletionText)

            if(isTitleValidated && isDOCValidated){
                editTitleText =  binding.titleText.text.toString()
                editDescText = binding.descriptionText.text.toString()
                editDocText = binding.dateOfCompletionText.text.toString()
                ediTaskModel?.let { editPos=it.id }

                if (modeEditing) {
                    ediTaskModel?.let {
                        listener?.onEditTask(TaskModel(editPos,editTitleText, editDescText, editDocText))
                    }
                }
                else
                    listener?.onNewTask(TaskModel(0,editTitleText, editDescText, editDocText))
                activity?.onBackPressedDispatcher?.onBackPressed()
            }

            val imm: InputMethodManager = requireActivity().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow( binding.doneBtn.windowToken, 0)

        }
        return binding.root
    }

    interface NewTaskListener {
        fun onNewTask(task: TaskModel)
        fun onEditTask(task: TaskModel)
    }

    private fun completionDatePicker(){
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, completionYear, monthOfYear, dayOfMonth ->
                val timePickerDialog = TimePickerDialog(
                    requireContext(),
                    { _, hourOfDay, minute ->
                        val dat = (dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year + "   " + hourOfDay + ":" + minute )
                        binding.dateOfCompletionText.setText(dat)
                        Calendar.getInstance().apply {
                            set(Calendar.YEAR, completionYear)
                            set(Calendar.MONTH, monthOfYear)
                            set(Calendar.DAY_OF_MONTH, dayOfMonth)
                            set(Calendar.HOUR_OF_DAY, hourOfDay)
                            set(Calendar.MINUTE, minute)
                            set(Calendar.SECOND, 0)
                        }.timeInMillis
                    },
                    c.get(Calendar.HOUR_OF_DAY),
                    c.get(Calendar.MINUTE),
                    true
                )
                timePickerDialog.show()
            },
            year, month, day
        )
        datePickerDialog.datePicker.minDate = c.timeInMillis
        datePickerDialog.show()
    }

    private fun validateTitleText(title: String, value: EditText): Boolean{
        return if (title.isNotEmpty()) {
            true
        } else {
            value.error = "Title cannot be blank"
            false
        }
    }

    private fun validateDOCText(doc: String, value: EditText): Boolean{
        return if (doc.isNotEmpty()) {
            true
        } else {
            value.error = "Completion Date cannot be empty"
            false
        }
    }
}