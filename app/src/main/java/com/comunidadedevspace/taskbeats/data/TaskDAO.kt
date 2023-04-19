package com.comunidadedevspace.taskbeats.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface TaskDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE) // This will replace an existing task that has the same ID of the new one
    fun insert(task: Task)

    // Before was: fun getAll(): List<Task>
    // Here we created the LiveData
    @Query("Select * from task")
    fun getAll(): LiveData<List<Task>>

    // Update precisa encontrar a tarefa que queremos alterar
    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(task: Task)

    // Deletando todos
    @Query("DELETE from task")
    fun deleteAll()

    // Deletando pelo ID
    @Query("DELETE from task WHERE id =:id")
    fun deleteById(id: Int)
}