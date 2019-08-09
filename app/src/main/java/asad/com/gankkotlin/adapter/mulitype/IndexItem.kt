package asad.com.gankkotlin.adapter.mulitype

import asad.com.gankkotlin.http.bean.TitleBean

/**
 * @ProjectName: GankKotlin
 * @Package: asad.com.gankkotlin.adapter.mulitype
 * @ClassName: zlq
 * @Autor: Asia
 * @CreateDate: 2019-07-08 16:48
 * @UpdateDate: (更新时间)
 * @UpdateUser: (更新人)
 * @UpdateRemark: (更新说明)
 * @Description: (java类作用描述)
 * @Version: $VERSION
 * @Desc
 **/
class IndexItem : MutillItemType{


    private val itemType: Int

    var item: TitleBean? = null

    constructor(itemType: Int) {
        this.itemType = itemType
    }

    constructor(itemType: Int,item: TitleBean){
        this.itemType = itemType
        this.item = item
    }



    override fun getType(): Int {
        return itemType
    }

}