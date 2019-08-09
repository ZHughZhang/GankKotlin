package asad.com.gankkotlin.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import asad.com.gankkotlin.db.dao.SearchKeyDao
import asad.com.gankkotlin.repository.entitiy.SearchKey

/**
 * @ProjectName: GankKotlin
 * @Package: asad.com.gankkotlin.db
 * @ClassName: zlq
 * @Autor: Asia
 * @CreateDate: 2019-07-02 17:05
 * @UpdateDate: (更新时间)
 * @UpdateUser: (更新人)
 * @UpdateRemark: (更新说明)
 * @Description: (java类作用描述)
 * @Version: $VERSION
 * @Desc
 **/
@Database(entities = [SearchKey::class],version = 1,exportSchema = false)
abstract  class AppDataBase: RoomDatabase()  {
    companion object{
        @Volatile private var INSTANCE : AppDataBase? = null

        /**
         * 获取数据库实例
         * */
        fun  getInstance(context: Context): AppDataBase=
            INSTANCE?: synchronized(this){
                INSTANCE?: buildDatabase(context).also {
                    INSTANCE  = it
                }
            }


        private fun buildDatabase(context: Context) = Room
            .databaseBuilder(context.applicationContext,
            AppDataBase::class.java,
            "gank.db")
            .build()
    }


    abstract fun getSearchDao(): SearchKeyDao


}