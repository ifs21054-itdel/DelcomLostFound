package com.ifs21054.delcomlostfound.data.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.ifs21054.delcomlostfound.data.local.entity.DelcomLostFoundEntity
import com.ifs21054.delcomlostfound.data.local.room.DelcomLostFoundDatabase
import com.ifs21054.delcomlostfound.data.local.room.IDelcomLostFoundDao
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class LocalLostFoundRepository(context: Context) {
    private val mDelcomLostFoundDao: IDelcomLostFoundDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()
    init {
        val db = DelcomLostFoundDatabase.getInstance(context)
        mDelcomLostFoundDao = db.delcomLostFoundDao()
    }
    fun getAllLostFounds(): LiveData<List<DelcomLostFoundEntity>?> = mDelcomLostFoundDao.getAllLostFounds()
    fun get(lostfoundId: Int): LiveData<DelcomLostFoundEntity?> = mDelcomLostFoundDao.get(lostfoundId)
    fun insert(lostfound: DelcomLostFoundEntity) {
        executorService.execute { mDelcomLostFoundDao.insert(lostfound) }
    }
    fun delete(lostfound: DelcomLostFoundEntity) {
        executorService.execute { mDelcomLostFoundDao.delete(lostfound) }
    }
    companion object {
        @Volatile
        private var INSTANCE: LocalLostFoundRepository? = null
        fun getInstance(
            context: Context
        ): LocalLostFoundRepository {
            synchronized(LocalLostFoundRepository::class.java) {
                INSTANCE = LocalLostFoundRepository(
                    context
                )
            }
            return INSTANCE as LocalLostFoundRepository
        }
    }
}