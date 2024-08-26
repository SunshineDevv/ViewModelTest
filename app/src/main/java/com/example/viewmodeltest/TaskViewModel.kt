package com.example.viewmodeltest

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TaskViewModel: ViewModel() {
    private val _tasks = MutableLiveData<List<TaskModel>>()
    val tasks: LiveData<List<TaskModel>> = _tasks

    // вот тут вопрос, можно ли просто по сути в 9 строке инициализировать (emptyList()) и не делать потом эту функцию init
    init {
        _tasks.value = listOf()
    }

    fun addTask(name: String, description: String) {
        val currentList = _tasks.value ?: listOf()
        val updatedList = currentList + TaskModel(name, description)
        _tasks.value = updatedList
    }

    fun removeTask(task: TaskModel){
        val currentList = _tasks.value ?: listOf()
        val updateList = currentList.filter { it.name != task.name }
        _tasks.value = updateList
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("AAA","model cleared")
    }
}