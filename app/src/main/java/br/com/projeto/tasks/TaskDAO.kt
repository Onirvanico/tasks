package br.com.projeto.tasks

import androidx.room.*
import br.com.projeto.tasks.model.Task
import kotlinx.coroutines.flow.Flow


@Dao
interface TaskDAO {

    @Query("SELECT * FROM task ORDER BY title ASC")
    fun getAllTasks(): Flow<List<Task>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTask(task: Task)

    @Update
    suspend fun updateTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)



}