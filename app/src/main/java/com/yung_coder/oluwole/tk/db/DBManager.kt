package com.yung_coder.oluwole.tk.db

import android.content.Context
import android.util.Log
import com.yung_coder.oluwole.tk.models.Models
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select

/**
 * Created by yung on 10/19/17.
 */
class DBManager(context: Context): DBSource {

    var database: MySQLHelper = MySQLHelper.getInstance(context)

    override fun loadDetails(topic: String): ArrayList<Models.details> {
        return database.use {
            val parser = classParser<Models.details>()
            select(tables.DETAILS_TABLE)
                    .whereSimple("${tables.TOPIC} = ?", topic)
                    .parseList(parser).toCollection(ArrayList())
        }
    }

    override fun loadTitle(): ArrayList<Models.topics> {
        return database.use {
            val parser = classParser<Models.topics>()
            select(tables.TITLE_TABLE).parseList(parser).toCollection(ArrayList())
        }
    }

    override fun saveDetails(details: Models.details) {
        database.use {
            insert(tables.DETAILS_TABLE, tables.TOPIC to details.title, tables.DETAILS to details.details_text,
                    tables.AMOUNT to details.amount, tables.DATE to details.date)
        }
    }

    override fun saveTopic(topic: Models.topics) {
        database.use {
            insert(tables.TITLE_TABLE, tables.TOPIC to topic.title)
        }
    }

    override fun deleteSingle(details: String): Boolean {
        var deleted = false
        database.use {
            try {
                beginTransaction()
                val result = delete(tables.DETAILS_TABLE, "${tables.DETAILS} = {details}", "details" to details) > 0
                deleted = if (result){
                    setTransactionSuccessful()
                    true
                }
                else{
                    false
                }
            }
            catch (e: Throwable){
                e.printStackTrace()
                Log.e("Error", e.message)
                deleted = false
            }
            finally {
                endTransaction()
            }
        }
        return deleted
    }

    override fun deleteDetails(topic: String): Boolean {
        var deleted = false
        database.use {
            try{
                beginTransaction()
                val result = delete(tables.DETAILS_TABLE, "${tables.TOPIC} = {topic}", "topic" to topic ) > 0
                deleted = if(result){
                    setTransactionSuccessful()
                    true
                } else{
                    false
                }
            }
            catch (e: Throwable){
                e.printStackTrace()
                Log.e("Delete Error", e.message)
                deleted = false
            }
            finally {
                endTransaction()
            }
        }

        return deleted
    }

    override fun deleteTopic(topic: String): Boolean {
        var deleted = false
        database.use {
            try {
                beginTransaction()
                val result = delete(tables.TITLE_TABLE, "${tables.TOPIC} = {topic}", "topic" to topic) > 0
                deleted = if (result) {
                    setTransactionSuccessful()
                    true
                } else {
                    false
                }
            } catch (e: Throwable) {
                e.printStackTrace()
                Log.e("ERROR", e.message)
                deleted = false
            } finally {
                endTransaction()
            }
        }
        return deleted
    }
}