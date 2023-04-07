package com.comunidadedevspace.taskbeats

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import java.io.Serializable

class MainActivity : AppCompatActivity() {

    private var taskList = arrayListOf(
        Task(0, "Academia", "Treino de corrida"),
        Task(1, "Mercado", "Comprar Arroz e Feijao"),
        Task(2, "DevSpace", "Gravar video sobre cores no taskBeats"),
        Task(3, "Juca", "Sair para passear com o Juquinha")
    )

    private lateinit var containerContent: LinearLayout

    // adapter
    private val adapter: TaskListAdapter = TaskListAdapter(::onListItemClicked)

    private val startForResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            // pegando resultado
            val data = result.data
            val taskAction = data?.getSerializableExtra(TASK_ACTION_RESULT) as TaskAction
            val task: Task = taskAction.task

            if (taskAction.actionType == ActionType.DELETE.name) {
                val newList = arrayListOf<Task>()
                    .apply {
                        addAll(taskList)
                    }

                // removendo item da lista
                newList.remove(task)
                showMessage(containerContent, "${task.title} was deleted from your task list!")

                if (newList.size == 0) {
                    containerContent.visibility = View.VISIBLE
                }

                // Atualizar o adapter
                adapter.submitList(newList)
                taskList = newList

            } else if (taskAction.actionType == ActionType.CREATE.name) {
                val newList = arrayListOf<Task>()
                    .apply {
                        addAll(taskList)
                    }
                // adicionando item da lista
                newList.add(task)
                showMessage(containerContent, "${task.title} was added to your task list!")

                // atualizar o adapter
                adapter.submitList(newList)
                taskList = newList

            }
            else if (taskAction.actionType == ActionType.UPDATE.name) {

                val tempEmptyList = arrayListOf<Task>()
                taskList.forEach {
                    if (it.id == task.id) {
                        val newItem = Task(it.id, task.title, task.description)
                        tempEmptyList.add(newItem)
                    } else {
                        tempEmptyList.add(it)
                    }
                }

                showMessage(containerContent, "${task.title} was updated")
                adapter.submitList(tempEmptyList)
                taskList = tempEmptyList
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_list)

        val dataBase = Room.databaseBuilder(
            applicationContext,
            AppDataBase::class.java, "taskbeats-database"
        ).build()

        val dao = dataBase.taskDao()
        val task = Task(title = "Academia", description = "Treino de corrida")

        CoroutineScope(IO).launch {
            dao.insert(task)
        }

//        passar a lista pra cima, fora desse scope
//        val taskList = listOf(
//            Task(0, "Title0", "Desc0"),
//            Task(1, "Title1", "Desc1")
//        )

        containerContent = findViewById(R.id.container_content)

        adapter.submitList(taskList)

        // RecyclerView
        val rvTasks: RecyclerView = findViewById(R.id.rv_task_list)
        rvTasks.adapter = adapter

//        val list = listOf<String>("Title1", "Title 2", "Title 3")
//        val adapter = TaskListAdapter(list)
//
//        taskList.adapter = adapter

        val floatActionButton = findViewById<FloatingActionButton>(R.id.float_button_actions)
        floatActionButton.setOnClickListener {
            openTaskListDetail(null)
        }
    }

    private fun showMessage(view: View, message: String) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG)
            .setAction("Action", null)
            .show()
    }

    private fun onListItemClicked(task: Task) {
        openTaskListDetail(task)
    }

    private fun openTaskListDetail(task: Task? = null) {
        val intent = TaskDetailActivity.start(this, task)
        startForResult.launch(intent)
    }
}

// Crud
// Create
// Read
// Update
// Delete
enum class ActionType {
    DELETE,
    UPDATE,
    CREATE
}

data class TaskAction(
    val task: Task,
    val actionType: String
) : Serializable

const val TASK_ACTION_RESULT = "TASK_ACTION_RESULT"

