package com.comunidadedevspace.taskbeats.presentation

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.comunidadedevspace.taskbeats.TaskBeatsApplication
import com.comunidadedevspace.taskbeats.data.Task
import com.comunidadedevspace.taskbeats.data.TaskDAO
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// Here we are extending/inheriting the/from 'ViewModel()' class in our TaskListViewModel class
// Our TaskListViewModel will depend on the taskDAO so we can have access to the functions in it
class TaskListViewModel(
    private val taskDAO: TaskDAO,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    val taskListLiveData: LiveData<List<Task>> = taskDAO.getAll()

    fun execute(taskAction: TaskAction) {
        when (taskAction.actionType) {
            ActionType.DELETE.name -> deleteById(taskAction.task!!.id)
            ActionType.CREATE.name -> insertIntoDataBase(taskAction.task!!)
            ActionType.UPDATE.name -> updateIntoDataBase(taskAction.task!!)
            ActionType.DELETE_ALL.name -> deleteAll()
        }
    }

    private fun deleteById(id: Int) {
//        viewModelScope.launch(Dispatchers.IO) {
        viewModelScope.launch(dispatcher) {
            taskDAO.deleteById(id)
        }
//        CoroutineScope(Dispatchers.IO).launch {
//            taskDAO.deleteById(id)
//      //    listFromDataBase()
//        }
    }

    private fun insertIntoDataBase(task: Task) {
//        viewModelScope.launch(Dispatchers.IO) {
        viewModelScope.launch(dispatcher) {
            taskDAO.insert(task)
        }
//        CoroutineScope(Dispatchers.IO).launch {
//            taskDAO.insert(task)
//     //        listFromDataBase()
//        }
    }

    private fun updateIntoDataBase(task: Task) {
//        viewModelScope.launch(Dispatchers.IO) {
        viewModelScope.launch(dispatcher) {
            taskDAO.update(task)
        }
//        CoroutineScope(Dispatchers.IO).launch {
//            taskDAO.update(task)
//      //      listFromDataBase()
//        }
    }

    private fun deleteAll() {
//        viewModelScope.launch(Dispatchers.IO) {
        viewModelScope.launch(dispatcher) {
            taskDAO.deleteAll()
//            listFromDataBase()
        }
    }

    // Having a companion object, we will be able to use to function inside without having to create an TaskListViewModel
    companion object {
        fun create(application: Application): TaskListViewModel {
            val dataBaseInstance = (application as TaskBeatsApplication).getAppDataBase()
            val dao = dataBaseInstance.taskDao()
            return TaskListViewModel(dao)
        }
    }
}