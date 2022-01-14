package br.com.projeto.tasks.model

import androidx.databinding.ObservableField
import java.io.FileDescriptor

class TaskField {


    var id = 0
    val title: ObservableField<String> = ObservableField()
    val description: ObservableField<String> = ObservableField()
    val date: ObservableField<String> = ObservableField()
    val time: ObservableField<String> = ObservableField()

    constructor(id: Int, title: String, description: String, date: String, time: String) {
        this.id = id
        this.title.set(title)
        this.description.set(description)
        this.date.set(date)
        this.time.set(time)
    }

    constructor()

}
