/*
    We dont need TaskListActivity anymore
 */

//package com.comunidadedevspace.taskbeats.presentation
//
//import android.app.Activity
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import android.view.Menu
//import android.view.MenuInflater
//import android.view.MenuItem
//import android.view.View
//import android.widget.LinearLayout
//import androidx.activity.result.ActivityResult
//import androidx.activity.result.contract.ActivityResultContracts
//import androidx.lifecycle.Observer
//import androidx.recyclerview.widget.RecyclerView
//import com.comunidadedevspace.taskbeats.R
//import com.comunidadedevspace.taskbeats.data.Task
//import com.google.android.material.floatingactionbutton.FloatingActionButton
//import com.google.android.material.snackbar.Snackbar
//import java.io.Serializable
//
//class TaskListActivity : AppCompatActivity() {
//
////    private var taskList = arrayListOf(
////        Task(0, "Academia", "Treino de corrida"),
////        Task(1, "Mercado", "Comprar Arroz e Feijao"),
////        Task(2, "DevSpace", "Gravar video sobre cores no taskBeats"),
////        Task(3, "Juca", "Sair para passear com o Juquinha")
////    )
//
//    // Some changes
//
//    private lateinit var containerContent: LinearLayout
//
//    // adapter
//    private val adapter: TaskListAdapter by lazy {
//        TaskListAdapter(::onListItemClicked)
//    }
//
//    private val viewModel: TaskListViewModel by lazy {
//        TaskListViewModel.create(application)
//    }
//
////    lateinit var dataBase: AppDataBase
////
////    private val dao by lazy {
////        dataBase.taskDao()
////    }
//
//    private val startForResult = registerForActivityResult(
//        ActivityResultContracts.StartActivityForResult()
//    ) { result: ActivityResult ->
//        if (result.resultCode == Activity.RESULT_OK) {
//            // pegando resultado
//            val data = result.data
//            val taskAction = data?.getSerializableExtra(TASK_ACTION_RESULT) as TaskAction
//
////            viewModel.execute(taskAction)
//
//            // We are gonna move the 'when' code to our ViewModel
//
//            /*
//            val task: Task = taskAction.task
//            when (taskAction.actionType) {
//                ActionType.DELETE.name -> deleteById(task.id)
//
//                                val newList = arrayListOf<Task>()
//                                    .apply {
//                                        addAll(taskList)
//                                    }
//
//                                // removendo item da lista
//                                newList.remove(task)
//                                showMessage(containerContent, "${task.title} was deleted from your task list!")
//
//
//                                // Atualizar o adapter
//                                adapter.submitList(newList)
//                                taskList = newList
//
//                ActionType.CREATE.name -> insertIntoDataBase(task)
//
//                                val newList = arrayListOf<Task>()
//                                    .apply {
//                                        addAll(taskList)
//                                    }
//                                // adicionando item da lista
//                                newList.add(task)
//                                showMessage(containerContent, "${task.title} was added to your task list!")
//
//                                // atualizar o adapter
//                                adapter.submitList(newList)
//                                taskList = newList
//
//                ActionType.UPDATE.name -> updateIntoDataBase(task)
//
//                                val tempEmptyList = arrayListOf<Task>()
//                                taskList.forEach {
//                                    if (it.id == task.id) {
//                                        val newItem = Task(it.id, task.title, task.description)
//                                        tempEmptyList.add(newItem)
//                                    } else {
//                                        tempEmptyList.add(it)
//                                    }
//                                }
//
//                                if (newList.size == 0) {  Moved to private fun listFromDataBase() {
//                                    containerContent.visibility = View.VISIBLE
//                                }
//
//                                showMessage(containerContent, "${task.title} was updated")
//                                adapter.submitList(tempEmptyList)
//                                taskList = tempEmptyList
//
//                           }
//             */
//        }
//    }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_task_list)
//        setSupportActionBar(findViewById(R.id.toolbar))
//
//        // val task = Task(title = "Academia", description = "Treino de corrida") // No need to insert 'ID' because it will be auto generated by a @PrimaryKey
//
//
////        passar a lista pra cima, fora desse scope
////        val taskList = listOf(
////            Task(0, "Title0", "Desc0"),
////            Task(1, "Title1", "Desc1")
////        )
//
//        containerContent = findViewById(R.id.container_content)
//
//        // RecyclerView
//        val rvTasks: RecyclerView = findViewById(R.id.rv_task_list)
//        rvTasks.adapter = adapter
//
////        val list = listOf<String>("Title1", "Title 2", "Title 3")
////        val adapter = TaskListAdapter(list)
////
////        taskList.adapter = adapter
//
//        val floatActionButton = findViewById<FloatingActionButton>(R.id.float_button_actions)
//        floatActionButton.setOnClickListener {
//            openTaskListDetail(null)
//        }
//    }
//
//    // This is a singleton, the instance value will never change, making the app less 'expensive'
//    override fun onStart() {
//        super.onStart()
//        // We dont need dataBase anymore
////        dataBase = (application as TaskBeatsApplication).getAppDataBase()
//
//        // Only place 'listFromDataBase()' needs to be called
//        listFromDataBase()
//    }
//
//    private fun deleteAll() {
//        val taskAction = TaskAction(null, ActionType.DELETE_ALL.name)
////        viewModel.execute(taskAction)
////        CoroutineScope(IO).launch {
////            dao.deleteAll()
//////            listFromDataBase()
////        }
//    }
//
//    // We are moving all of this commented code from below to our ViewModel
//
//    /*
//    private fun insertIntoDataBase(task: Task) {
//        CoroutineScope(IO).launch {
//            dao.insert(task)
////            listFromDataBase()
//        }
//    }
//
//    private fun updateIntoDataBase(task: Task) {
//        CoroutineScope(IO).launch {
//            dao.update(task)
////            listFromDataBase()
//        }
//    }
//
//    private fun deleteById(id: Int) {
//        CoroutineScope(IO).launch {
//            dao.deleteById(id)
////            listFromDataBase()
//        }
//    }
//
//     */
//
//    private fun listFromDataBase() {
//
//        // Observer
//        val listObserver = Observer<List<Task>> { listTasks ->
//            if (listTasks.isEmpty()){
//                containerContent.visibility = View.VISIBLE
//            } else {
//                containerContent.visibility = View.GONE
//            }
//            adapter.submitList(listTasks)
//        }
//
//        // Connect Observer to LiveData
//        viewModel.taskListLiveData.observe(this@TaskListActivity, listObserver)
//
//        /*
//        dao.getAll().observe(this@TaskListActivity, listObserver)
//
//        CoroutineScope(IO).launch {
//
//            val myDataBaseList: List<Task> = dao.getAll()
//            adapter.submitList(myDataBaseList)
//
//            CoroutineScope(Main).launch {
//                if (myDataBaseList.isEmpty()) {
//                    containerContent.visibility = View.VISIBLE
//                } else {
//                    containerContent.visibility = View.GONE
//                }
//            }
//        }
//         */
//    }
//
//    private fun showMessage(view: View, message: String) {
//        Snackbar.make(view, message, Snackbar.LENGTH_LONG)
//            .setAction("Action", null)
//            .show()
//    }
//
//    private fun onListItemClicked(task: Task) {
//        openTaskListDetail(task)
//    }
//
//    private fun openTaskListDetail(task: Task? = null) {
//        val intent = TaskDetailActivity.start(this, task)
//        startForResult.launch(intent)
//    }
//
//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        val inflater: MenuInflater = menuInflater
//        inflater.inflate(R.menu.menu_task_list, menu)
//        return true
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        return when (item.itemId) {
//            R.id.delete_all_task -> {
//                // Deletar todas as tarefas
//                deleteAll()
//                true
//            }
//            else -> super.onOptionsItemSelected(item)
//        }
//    }
//}
//
//// Crud
//// Create
//// Read
//// Update
//// Delete
////enum class ActionType {
////    DELETE,
////    DELETE_ALL,
////    UPDATE,
////    CREATE
////}
////
////data class TaskAction(
////    val task: Task?,
////    val actionType: String
////) : Serializable
//
//const val TASK_ACTION_RESULT = "TASK_ACTION_RESULT"

