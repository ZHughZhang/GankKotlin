package asad.com.gankkotlin.viewmodel

import android.arch.lifecycle.MutableLiveData
import asad.com.gankkotlin.App
import asad.com.gankkotlin.db.AppDataBase
import asad.com.gankkotlin.http.BaseGankSubscriber
import asad.com.gankkotlin.http.ErrorCode
import asad.com.gankkotlin.http.RetroFitHolder
import asad.com.gankkotlin.http.response.gank.CategoryResponse
import asad.com.gankkotlin.repository.Resource
import asad.com.gankkotlin.repository.entitiy.SearchKey
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

/**
 * @ProjectName: GankKotlin
 * @Package: asad.com.gankkotlin.viewmodel
 * @ClassName: zlq
 * @Autor: Asia
 * @CreateDate: 2019-07-06 20:44
 * @UpdateDate: (更新时间)
 * @UpdateUser: (更新人)
 * @UpdateRemark: (更新说明)
 * @Description: (java类作用描述)
 * @Version: $VERSION
 * @Desc
 **/

class SearchViewModel : BaseViewModel(){


    private var listData: MutableLiveData<Resource<CategoryResponse>> = MutableLiveData()
    private var keyData: MutableLiveData<List<SearchKey>> = MutableLiveData()
    private var disposable: CompositeDisposable = CompositeDisposable()




    fun  getListData() = listData

    fun getKeydata() = keyData





    override fun start() {
        super.start()
        loadKey()
    }

    override fun stop() {
        super.stop()
        listRequst.mSub?.cancel()
    }

    fun loadData(key: String,pageIndex: Int){
        RetroFitHolder.gankInstance().search(key,"all",pageIndex,listRequst)
    }

    private fun loadKey(){
        disposable.add(AppDataBase.getInstance(App.INSTANCE).getSearchDao().getKeys()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                keyData.value = it
            })

    }

    fun addKey(key: SearchKey){
        disposable.add(saveKey(key)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                loadKey()
            }
        )
    }

    fun clearKey() {
        disposable.add(deleteKey()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                loadKey()
            })
    }

    private fun deleteKey(): Completable{
        return Completable.fromAction{
            AppDataBase.getInstance(App.INSTANCE).getSearchDao().deleteAll()
        }
    }

    private fun saveKey(key: SearchKey): Completable{
        return Completable.fromAction {
            AppDataBase.getInstance(App.INSTANCE).getSearchDao().insert(key)
        }

    }


    private val listRequst = object: BaseGankSubscriber<CategoryResponse>() {

        override fun start() {
            super.start()
            listData.value = Resource.loading(null)
        }
        override fun onSuccess(t: CategoryResponse) {
            listData.value = Resource.success(t)
        }

        override fun onFail(code: ErrorCode, errorMsg: String) {
            listData.value = Resource.error(null,errorMsg)
        }

    }






}