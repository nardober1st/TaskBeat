package com.comunidadedevspace.taskbeats.presentation

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.comunidadedevspace.taskbeats.TaskBeatsApplication
import com.comunidadedevspace.taskbeats.data.Task
import com.comunidadedevspace.taskbeats.data.TaskDAO
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskDetailViewModel(
    private val taskDAO: TaskDAO,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    fun execute(taskAction: TaskAction) {
        when (taskAction.actionType) {
            ActionType.DELETE.name -> deleteById(taskAction.task!!.id)
            ActionType.CREATE.name -> insertIntoDataBase(taskAction.task!!)
            ActionType.UPDATE.name -> updateIntoDataBase(taskAction.task!!)
//            ActionType.DELETE_ALL.name -> deleteAll()
        }
    }

    private fun deleteById(id: Int) {
        viewModelScope.launch(dispatcher) {
            taskDAO.deleteById(id)
        }
    }

    private fun insertIntoDataBase(task: Task) {
        viewModelScope.launch(dispatcher) {
            taskDAO.insert(task)
        }
    }

    private fun updateIntoDataBase(task: Task) {
        viewModelScope.launch(dispatcher) {
            taskDAO.update(task)
        }

    }

//    private fun deleteAll() {
//        viewModelScope.launch(dispatcher) {
//            taskDAO.deleteAll()
//        }
//    }

    companion object {

        fun getVMFactory(application: Application): ViewModelProvider.Factory {
            val dataBaseInstance = (application as TaskBeatsApplication).getAppDataBase()
            val dao = dataBaseInstance.taskDao()
            val factory = object : ViewModelProvider.Factory {

                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return TaskDetailViewModel(dao) as T
                }
            }
            return factory
        }
    }
}