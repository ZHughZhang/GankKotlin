package asad.com.gankkotlin.ui.activity

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import asad.com.gankkotlin.R
import asad.com.gankkotlin.databinding.ActivityScanResultBinding
import asad.com.gankkotlin.ui.base.BaseActivity
import com.githang.statusbar.StatusBarCompat

/**
 * @ProjectName: GankKotlin
 * @Package: asad.com.gankkotlin.ui.activity
 * @ClassName: zlq
 * @Autor: Asia
 * @CreateDate: 2019-07-08 16:28
 * @UpdateDate: (更新时间)
 * @UpdateUser: (更新人)
 * @UpdateRemark: (更新说明)
 * @Description: (java类作用描述)
 * @Version: $VERSION
 * @Desc
 **/

class ScanResultActivity  : BaseActivity<ActivityScanResultBinding> () {


    companion object{


        fun  start (context: Context,result: String) {
            var intent = Intent(context,ScanResultActivity::class.java)
            intent.putExtra("data",result)
            context.startActivity(intent)
        }
    }


    override fun getLayoutId(): Int {
        return R.layout.activity_scan_result
    }

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
        var result = intent.getStringExtra("data")
        binding.tvTitle.text = "扫描结果"
        binding.tvResult.text = result
    }

    override fun iniListener() {
        super.iniListener()
        binding.ivBack.setOnClickListener {
            this.finish()
        }
    }


    override fun onResume() {
        super.onResume()
        StatusBarCompat.setStatusBarColor(this,Color.WHITE,true)
    }

}