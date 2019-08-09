package asad.com.gankkotlin.widget

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import asad.com.gankkotlin.R

/**
 * @ProjectName: GankKotlin
 * @Package: asad.com.gankkotlin.widget
 * @ClassName: zlq
 * @Autor: Asia
 * @CreateDate: 2019-07-02 15:11
 * @UpdateDate: (更新时间)
 * @UpdateUser: (更新人)
 * @UpdateRemark: (更新说明)
 * @Description: (java类作用描述)
 * @Version: $VERSION
 * @Desc
 **/

class LoadingView1 : View {


    private var color = Color.BLUE

    private var radius = 40F

    private var duration = 500L

    private var cx= 0F
    private  var cy = 0F

    private var animSizeValue = radius



    private val mPaint1: Paint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = this@LoadingView1.color
            alpha = 128
            style = Paint.Style.FILL
        }
    }


    private val mPaint2: Paint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = this@LoadingView1.color
            alpha = 128
            style = Paint.Style.FILL
        }
    }

    private val sizeAnim: ValueAnimator by lazy {
        ValueAnimator.ofFloat(0F,radius).apply {
            duration = this@LoadingView1.duration
            repeatCount = -1
            repeatMode = ValueAnimator.REVERSE
            addUpdateListener {
                animSizeValue = (it.animatedValue as Float)
                postInvalidate()
            }
        }
    }



    constructor(context: Context?): super(context) {
        initView()
    }

    constructor(context: Context?,attributeSet: AttributeSet?) :super(context,attributeSet){
        val attrsArray = context?.obtainStyledAttributes(attributeSet, R.styleable.LoadingView1)
        attrsArray?.let {
            color = it.getColor(R.styleable.LoadingView1_color,color)
            duration = it.getInteger(R.styleable.LoadingView1_duration,duration.toInt()).toLong()
            radius   = it.getDimension(R.styleable.LoadingView1_redius,radius)
            it.recycle()

        }

        initView()
    }

    private  fun initView(){

    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.run {
            drawCircle(cx,cy,radius-animSizeValue,mPaint1)
            drawCircle(cx,cy,animSizeValue,mPaint2)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        cx = (measuredWidth / 2).toFloat()
        cy = (measuredHeight / 2).toFloat()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        if(!sizeAnim.isRunning){
            sizeAnim.start()
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        if(sizeAnim.isRunning){
            sizeAnim.cancel()
        }
    }
}
