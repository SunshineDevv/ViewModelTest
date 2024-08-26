package com.example.viewmodeltest

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.viewmodeltest.databinding.NoteItemBinding

class TaskAdapter(private var taskList: List<TaskModel>, private val onTaskClick: (TaskModel) -> Unit ) :  RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    class TaskViewHolder(val binding: NoteItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskAdapter.TaskViewHolder {
        val binding = NoteItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskAdapter.TaskViewHolder, position: Int) {
        val task = taskList[position]
        val taskName = taskList[position].name
        val taskDescription = taskList[position].description
        holder.binding.tvName.text = taskName
        holder.binding.tvDescription.text = taskDescription
        holder.binding.deleteButton.setOnClickListener { onTaskClick(task) }
        holder.itemView.setOnClickListener { }
    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateTasks(newTasks: List<TaskModel>) {
        taskList = newTasks
        notifyDataSetChanged()
    }
}