package com.yung_coder.oluwole.tk.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*

/**
 * Created by yung on 10/19/17.
 */
class MySQLHelper(ctx: Context): ManagedSQLiteOpenHelper(ctx, "TK") {
    companion object {
        private var instance: MySQLHelper? = null

        fun getInstance(ctx: Context): MySQLHelper {
            if(instance == null){
                instance = MySQLHelper(ctx.applicationContext)
            }
            return instance as MySQLHelper
        }
    }

    override fun onCreate(p0: SQLiteDatabase?) {
        p0?.createTable(tables.TITLE_TABLE, true, tables.TOPIC to TEXT)
        p0?.createTable(tables.DETAILS_TABLE, true, tables.TOPIC to TEXT, tables.DETAILS to TEXT, tables.AMOUNT to TEXT, tables.DATE to TEXT)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}