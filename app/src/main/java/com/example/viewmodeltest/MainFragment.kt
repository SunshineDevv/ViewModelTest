package com.example.viewmodeltest

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.viewmodeltest.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private var binding: FragmentMainBinding? = null

    private var taskList: List<TaskModel> = listOf()

    private lateinit var adapter:TaskAdapter

    private val taskViewModel: TaskViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i("AAA","view created")

        adapter = TaskAdapter(taskList) { task ->
            showDeleteTaskDialog(task)
        }
        binding?.recyclerViewFrag?.layoutManager = LinearLayoutManager(requireContext())
        binding?.recyclerViewFrag?.adapter = adapter

        taskViewModel.tasks.observe(viewLifecycleOwner) { tasks ->
            adapter.updateTasks(tasks)
        }

        binding?.buttonAdd?.setOnClickListener {
            showAddTaskDialog()
        }

    }

    private fun showDeleteTaskDialog(task: TaskModel){
        val dialogBuilder = AlertDialog.Builder(requireContext())
        dialogBuilder.setTitle("Delete this task?")

        dialogBuilder.setPositiveButton("Yes"){ dialog, _ ->
            taskViewModel.removeTask(task)
        }

        dialogBuilder.setNegativeButton("No") { dialog, _ ->
            dialog.cancel()
        }
        dialogBuilder.show()
    }

    private fun showAddTaskDialog() {
        val dialogBuilder = AlertDialog.Builder(requireContext())

        val inflater = layoutInflater
        val dialogView = inflater.inflate(R.layout.dialog_add_task, null)
        dialogBuilder.setView(dialogView)

        val nameInput = dialogView.findViewById<EditText>(R.id.taskNameInput)
        val descriptionInput = dialogView.findViewById<EditText>(R.id.taskDescriptionInput)

        dialogBuilder.setTitle("Add New Task")
        dialogBuilder.setPositiveButton("Add") { dialog, _ ->
            val taskName = nameInput.text.toString()
            val taskDescription = descriptionInput.text.toString()
            if (taskName.isNotEmpty() && taskDescription.isNotEmpty()) {
                taskViewModel.addTask(taskName, taskDescription)
            }
            dialog.dismiss()
        }
        dialogBuilder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.cancel()
        }
        dialogBuilder.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.i("AAA","destroy view")
    }
}