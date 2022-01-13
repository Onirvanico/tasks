package br.com.projeto.tasks.model

import androidx.databinding.ObservableField

class TaskField {
    val title: ObservableField<String> = ObservableField()
    val description: ObservableField<String> = ObservableField()
    val date: ObservableField<String> = ObservableField()
    val time: ObservableField<String> = ObservableField()
}