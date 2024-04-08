package com.example.pme.add

import android.annotation.SuppressLint
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class DatabaseHelper private constructor(context: Context?) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE ${RecordEntry.TABLE_NAME} (" +
                "${RecordEntry.COLUMN_ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                "${RecordEntry.COLUMN_DATE} TEXT," +
                "${RecordEntry.COLUMN_TIME} TEXT," +
                "${RecordEntry.COLUMN_SYSTOLIC} INTEGER," +
                "${RecordEntry.COLUMN_DIASTOLIC} INTEGER," +
                "${RecordEntry.COLUMN_PULSE} INTEGER)")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS ${RecordEntry.TABLE_NAME}")
        onCreate(db)
    }

    fun insert(record: Record){
        this.writableDatabase.execSQL("INSERT INTO records (date, time, systolic, diastolic, pulse) " +
                "VALUES ('${record.date}', '${record.time}', ${record.systolic}, ${record.diastolic}, ${record.pulse})")
    }

    fun delete(id:Int){
        this.writableDatabase.execSQL("DELETE FROM records WHERE id = $id")
    }

    @SuppressLint("Range")
    fun getAllRecords(): ArrayList<Record> {
        val recordList = ArrayList<Record>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM ${RecordEntry.TABLE_NAME} ORDER BY ${RecordEntry.COLUMN_ID} DESC", null)
        cursor.use {
            while (cursor.moveToNext()) {
                val id = cursor.getInt(cursor.getColumnIndex(RecordEntry.COLUMN_ID))
                val date = cursor.getString(cursor.getColumnIndex(RecordEntry.COLUMN_DATE))
                val time = cursor.getString(cursor.getColumnIndex(RecordEntry.COLUMN_TIME))
                val systolic = cursor.getInt(cursor.getColumnIndex(RecordEntry.COLUMN_SYSTOLIC))
                val diastolic = cursor.getInt(cursor.getColumnIndex(RecordEntry.COLUMN_DIASTOLIC))
                val pulse = cursor.getInt(cursor.getColumnIndex(RecordEntry.COLUMN_PULSE))
                val record = Record(id, date, time, systolic, diastolic, pulse)
                recordList.add(record)
            }
        }
        db.close()
        return recordList
    }

    @SuppressLint("Range")
    fun getRecords(num:Int): ArrayList<Record> {
        val recordList = ArrayList<Record>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM ${RecordEntry.TABLE_NAME} ORDER BY ${RecordEntry.COLUMN_ID} DESC LIMIT $num", null)
        cursor.use {
            while (cursor.moveToNext()) {
                val id = cursor.getInt(cursor.getColumnIndex(RecordEntry.COLUMN_ID))
                val date = cursor.getString(cursor.getColumnIndex(RecordEntry.COLUMN_DATE))
                val time = cursor.getString(cursor.getColumnIndex(RecordEntry.COLUMN_TIME))
                val systolic = cursor.getInt(cursor.getColumnIndex(RecordEntry.COLUMN_SYSTOLIC))
                val diastolic = cursor.getInt(cursor.getColumnIndex(RecordEntry.COLUMN_DIASTOLIC))
                val pulse = cursor.getInt(cursor.getColumnIndex(RecordEntry.COLUMN_PULSE))
                val record = Record(id, date, time, systolic, diastolic, pulse)
                recordList.add(record)
            }
        }
        db.close()
        return recordList
    }
    @SuppressLint("Range", "Recycle")
    private fun getCriticalMeasure(columnName: String, operation:String): Int{
        val db = this.readableDatabase
        var value = 0
        val cursor = db.rawQuery("SELECT $operation($columnName) FROM ${RecordEntry.TABLE_NAME}", null)
        if (cursor.moveToFirst()) {
            value =  cursor.getInt(0)
        }
        db.close()
        return value
    }

    fun getStatistic(): StatisticManager {
        val pulseStatistic = getStatisticForMeasure(RecordEntry.COLUMN_PULSE)
        val systolicStatistic = getStatisticForMeasure(RecordEntry.COLUMN_SYSTOLIC)
        val diastolicStatistic = getStatisticForMeasure(RecordEntry.COLUMN_DIASTOLIC)

        return StatisticManager(pulseStatistic, systolicStatistic, diastolicStatistic)
    }

    private fun getStatisticForMeasure(measure: String): StatisticManager.StatisticValues {
        val max = getCriticalMeasure(measure, "MAX")
        val min = getCriticalMeasure(measure, "MIN")
        val avg = getCriticalMeasure(measure, "AVG")
        return StatisticManager.StatisticValues(max, min, avg)
    }

    companion object {
        private const val DATABASE_NAME = "PME_database"
        private const val DATABASE_VERSION = 1

        private var instance: DatabaseHelper? = null

        fun getInstance(context: Context): DatabaseHelper {
            if (instance == null) {
                instance = DatabaseHelper(context.applicationContext)
            }
            return instance as DatabaseHelper
        }

        object RecordEntry {
            const val TABLE_NAME = "records"
            const val COLUMN_ID = "id"
            const val COLUMN_DATE = "date"
            const val COLUMN_TIME = "time"
            const val COLUMN_SYSTOLIC = "systolic"
            const val COLUMN_DIASTOLIC = "diastolic"
            const val COLUMN_PULSE = "pulse"
        }
    }
}

