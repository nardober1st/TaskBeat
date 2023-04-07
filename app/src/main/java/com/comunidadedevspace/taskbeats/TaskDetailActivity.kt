package com.comunidadedevspace.taskbeats

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.google.android.material.snackbar.Snackbar

class TaskDetailActivity : AppCompatActivity() {

    private var task: Task? = null
    private lateinit var buttonAdd: Button

    companion object {
        private const val TASK_DETAIL_EXTRA = "task.extra.detail"

        fun start(context: Context, task: Task?): Intent {
            val intent = Intent(context, TaskDetailActivity::class.java)
                .apply {
                    putExtra(TASK_DETAIL_EXTRA, task)
                }
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_detail)
        setSupportActionBar(findViewById(R.id.toolbar))

        // Recuperar a task
        task = intent.getSerializableExtra(TASK_DETAIL_EXTRA) as Task?

        val editTextTitle = findViewById<EditText>(R.id.edit_text_title)
        val editTextDescription = findViewById<EditText>(R.id.edit_text_description)
        buttonAdd = findViewById(R.id.button_add)

        if (task != null) {
            editTextTitle.setText(task!!.title)
            editTextDescription.setText(task!!.description)
        }

        buttonAdd.setOnClickListener {
            val title = editTextTitle.text.toString()
            val description = editTextDescription.text.toString()

            if (title.isNotEmpty() && description.isNotEmpty()) {
                if (task == null) {
                    addOrUpdateTask(0, title, description, ActionType.CREATE)
                } else {
                    addOrUpdateTask(task!!.id, title, description, ActionType.UPDATE)
                }
            } else {
                showMessage(it, "Fields are required")
            }
        }

        // Recuperar campo do XML
        // titleDetailTextview = findViewById(R.id.text_view_detail_title)

        // Setar um novo texto na tela
        // titleDetailTextview.text = task?.title ?: "Adicione uma tarefa"
    }

    private fun addOrUpdateTask(
        id: Int,
        title: String,
        description: String,
        actionType: ActionType
    ) {
        val task = Task(id, title, description)
        returnAction(task, actionType)
    }

    // Ciclo de vida da activity
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_task_detail, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.delete_task -> {

                if (task != null) {
                    returnAction(task!!, ActionType.DELETE)
                } else {
                    showMessage(buttonAdd, "Item not found")
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    // Return Action to Main Screen
    private fun returnAction(task: Task, actionType: ActionType) {
        val intent = Intent()
            .apply {
                val taskAction = TaskAction(task, actionType.name)
                putExtra(TASK_ACTION_RESULT, taskAction)
            }
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    private fun showMessage(view: View, message: String) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG)
            .setAction("Action", null)
            .show()
    }
}
