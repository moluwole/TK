package com.yung_coder.oluwole.tk.db

import com.yung_coder.oluwole.tk.models.Models

/**
 * Created by yung on 10/19/17.
 */
interface DBSource {
    fun loadTitle(): ArrayList<Models.topics>
    fun saveTopic(topic: Models.topics)

    fun loadDetails(topic: String): ArrayList<Models.details>
    fun saveDetails(details: Models.details)

    fun deleteTopic(topic: String): Boolean
    fun deleteDetails(topic: String): Boolean

    fun deleteSingle(details:String): Boolean
}