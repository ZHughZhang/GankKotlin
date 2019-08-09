package asad.com.gankkotlin.di

import android.app.Application
import asad.com.gankkotlin.App
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

/**
 * @ProjectName: GankKotlin
 * @Package: asad.com.gankkotlin.di
 * @ClassName: zlq
 * @Autor: Asia
 * @CreateDate: 2019-07-02 17:24
 * @UpdateDate: (更新时间)
 * @UpdateUser: (更新人)
 * @UpdateRemark: (更新说明)
 * @Description: (java类作用描述)
 * @Version: $VERSION
 * @Desc
 **/

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class
    ]
)
interface AppComponent {
    @Component.Builder
    interface Builder{
        @BindsInstance
        fun application (application: Application):Builder

        fun build(): AppComponent
    }
    fun inject(app: App)
}