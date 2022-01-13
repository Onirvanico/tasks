package br.com.projeto.tasks

import android.app.Application
import br.com.projeto.tasks.database.TaskRoomDataBase

class TaskApp : Application() {

    val db: TaskRoomDataBase by lazy {
        TaskRoomDataBase.getDataBase(this)
    }
}