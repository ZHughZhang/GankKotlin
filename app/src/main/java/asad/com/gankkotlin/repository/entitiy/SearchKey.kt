package asad.com.gankkotlin.repository.entitiy

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * @ProjectName: GankKotlin
 * @Package: asad.com.gankkotlin.repository.entitiy
 * @ClassName: zlq
 * @Autor: Asia
 * @CreateDate: 2019-07-02 17:17
 * @UpdateDate: (更新时间)
 * @UpdateUser: (更新人)
 * @UpdateRemark: (更新说明)
 * @Description: (java类作用描述)
 * @Version: $VERSION
 * @Desc
 **/
@Entity(tableName = "search_key_tb")
data class SearchKey(
    @PrimaryKey
    @ColumnInfo(name = "key")
    val key: String,
    @ColumnInfo(name = "time")
    val time: Long
)
