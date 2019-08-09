package asad.com.gankkotlin.db.dao

import android.arch.persistence.room.*
import asad.com.gankkotlin.repository.entitiy.SearchKey
import io.reactivex.Flowable

/**
 * @ProjectName: GankKotlin
 * @Package: asad.com.gankkotlin.db.dao
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

@Dao
interface SearchKeyDao {
    @Query("SELECT * FROM SEARCH_KEY_TB ORDER BY time LIMIT 20")
    fun getKeys(): Flowable<List<SearchKey>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(key: SearchKey)
    @Delete
    fun delete(key: SearchKey)

    @Query("DELETE FROM SEARCH_KEY_TB")
    fun deleteAll()
}