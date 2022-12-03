package com.example.barchartbycanvas

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.VectorDrawable
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.core.content.ContextCompat

fun cloneBitmap(): Bitmap {
    return Bitmap.createBitmap(15, 15, Bitmap.Config.ARGB_8888)
}

class PieChartView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    companion object {
        val logTag = this.javaClass.simpleName
    }

    private val listOfSpending: MutableList<TypeSpending> = mutableListOf()
    var totalPrice: Double = 0.0
    var startDeg = 0f
    var endDeg = 0f
    private val padding = 10.dpToPx
    private val line = 15.dpToPx
    private val removePaddingBetweenLineAndIcon = 10.dpToPx
    private var iconSize = 0
    var centerPoint: Pair<Float, Float> = Pair(0f, 0f)
    var rect = RectF()
    var pivot = 0
    var side = 0f
    var halfSide = 0f
    private var stokeWidth = 2.dpToPx.toFloat()

//    private val borderSquarePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
//        color = ContextCompat.getColor(context, R.color.xam_dam);
//        style = Paint.Style.STROKE
//        strokeWidth = 1.dpToPx.toFloat()
//    }
//
//    private val bitmapPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
//        isDither = true
//        isFilterBitmap = true
//    }

    private val circleWhitePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = ContextCompat.getColor(context, R.color.white);
        style = Paint.Style.FILL
    }

    private val circleSemiTransparent = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = ContextCompat.getColor(context, R.color.semi_transparent);
        style = Paint.Style.FILL
    }

    private val buyingPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = ContextCompat.getColor(context, R.color.color_5073B8);
        style = Paint.Style.FILL_AND_STROKE
        strokeWidth = stokeWidth
    }

    private val eatingPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = ContextCompat.getColor(context, R.color.color_6150B8);
        style = Paint.Style.FILL_AND_STROKE
        strokeWidth = stokeWidth
    }

    private val travelingPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = ContextCompat.getColor(context, R.color.color_A050B8);
        style = Paint.Style.FILL_AND_STROKE
        strokeWidth = stokeWidth
    }

    private val serviceHelpfulPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = ContextCompat.getColor(context, R.color.color_B85081);
        style = Paint.Style.FILL_AND_STROKE
        strokeWidth = stokeWidth
    }

    private val healthyPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = ContextCompat.getColor(context, R.color.color_FF3B3B);
        style = Paint.Style.FILL_AND_STROKE
        strokeWidth = stokeWidth
    }

    private val airplaneTicketPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = ContextCompat.getColor(context, R.color.color_3BD7FF);
        style = Paint.Style.FILL_AND_STROKE
        strokeWidth = stokeWidth
    }

    private val movingPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = ContextCompat.getColor(context, R.color.color_1CCB5C);
        style = Paint.Style.FILL_AND_STROKE
        strokeWidth = stokeWidth
    }
    private val entertainmentPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = ContextCompat.getColor(context, R.color.color_F1963A);
        style = Paint.Style.FILL_AND_STROKE
        strokeWidth = stokeWidth
    }

    private val skincarePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = ContextCompat.getColor(context, R.color.color_72B112);
        style = Paint.Style.FILL_AND_STROKE
        strokeWidth = stokeWidth
    }

    private val investmentPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = ContextCompat.getColor(context, R.color.color_F16548);
        style = Paint.Style.FILL_AND_STROKE
        strokeWidth = stokeWidth
    }

    private val educationPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = ContextCompat.getColor(context, R.color.color_0606E3);
        style = Paint.Style.FILL_AND_STROKE
        strokeWidth = stokeWidth
    }

    private val assurancePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = ContextCompat.getColor(context, R.color.color_0AB39C);
        style = Paint.Style.FILL_AND_STROKE
        strokeWidth = stokeWidth
    }

    private val otherServicePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = ContextCompat.getColor(context, R.color.color_F8BA5E);
        style = Paint.Style.FILL_AND_STROKE
        strokeWidth = stokeWidth
    }

    sealed class TypeSpending(
        name: String = "",
        open var price: Double = 0.0,
        open var bitmap: Bitmap,
        open var paint: Paint = Paint()
    )

    data class Buying(
        val name: String = "",
        override var price: Double = 0.0,
        override var bitmap: Bitmap = cloneBitmap(),
        override var paint: Paint = Paint()
    ) : TypeSpending(name, price, bitmap, paint)

    data class Eating(
        val name: String = "",
        override var price: Double = 0.0,
        override var bitmap: Bitmap = cloneBitmap(),
        override var paint: Paint = Paint()
    ) : TypeSpending(name, price, bitmap, paint)

    data class Traveling(
        val name: String = "",
        override var price: Double = 0.0,
        override var bitmap: Bitmap = cloneBitmap(),
        override var paint: Paint = Paint()
    ) : TypeSpending(name, price, bitmap, paint)

    data class ServiceHelpful(
        val name: String = "",
        override var price: Double = 0.0,
        override var bitmap: Bitmap = cloneBitmap(),
        override var paint: Paint = Paint()
    ) : TypeSpending(name, price, bitmap, paint)

    data class Healthy(
        val name: String = "",
        override var price: Double = 0.0,
        override var bitmap: Bitmap = cloneBitmap(),
        override var paint: Paint = Paint()
    ) : TypeSpending(name, price, bitmap, paint)

    data class Airplane(
        val name: String = "",
        override var price: Double = 0.0,
        override var bitmap: Bitmap = cloneBitmap(),
        override var paint: Paint = Paint()
    ) : TypeSpending(name, price, bitmap, paint)

    data class Moving(
        val name: String = "",
        override var price: Double = 0.0,
        override var bitmap: Bitmap = cloneBitmap(),
        override var paint: Paint = Paint()
    ) : TypeSpending(name, price, bitmap, paint)

    data class Entertainment(
        val name: String = "",
        override var price: Double = 0.0,
        override var bitmap: Bitmap = cloneBitmap(),
        override var paint: Paint = Paint()
    ) : TypeSpending(name, price, bitmap, paint)

    data class Skincare(
        val name: String = "",
        override var price: Double = 0.0,
        override var bitmap: Bitmap = cloneBitmap(),
        override var paint: Paint = Paint()
    ) : TypeSpending(name, price, bitmap, paint)

    data class Investment(
        val name: String = "",
        override var price: Double = 0.0,
        override var bitmap: Bitmap = cloneBitmap(),
        override var paint: Paint = Paint()
    ) : TypeSpending(name, price, bitmap, paint)

    data class Education(
        val name: String = "",
        override var price: Double = 0.0,
        override var bitmap: Bitmap = cloneBitmap(),
        override var paint: Paint = Paint()
    ) : TypeSpending(name, price, bitmap, paint)

    data class Assurance(
        val name: String = "",
        override var price: Double = 0.0,
        override var bitmap: Bitmap = cloneBitmap(),
        override var paint: Paint = Paint()
    ) : TypeSpending(name, price, bitmap, paint)

    data class Other(
        val name: String = "",
        override var price: Double = 0.0,
        override var bitmap: Bitmap = cloneBitmap(),
        override var paint: Paint = Paint()
    ) : TypeSpending(name, price, bitmap, paint)

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        setBackgroundColor(ContextCompat.getColor(context, R.color.color_0606E3))
        Log.d("PieChart", "onDraw")
        initData()
        onDrawPie(canvas)
    }

    private fun onDrawPie(canvas: Canvas) {
        var degree = 0f
        var sweepDegree = 0f
        var middleDegree = 0f
        var bitmap: Bitmap
        var bound: Rect
        canvas.apply {
            //canvas.drawRect(rect, borderSquarePaint)
            drawRect(rect, airplaneTicketPaint)
            for (type in listOfSpending) {
                degree = (type.price * 360f / totalPrice).toFloat()
                endDeg = startDeg + degree
                sweepDegree = endDeg - startDeg
                middleDegree = startDeg + sweepDegree / 2
                drawArc(rect, startDeg, sweepDegree, true, type.paint)
                save()
                Log.d(logTag, middleDegree.toString())
                rotate(middleDegree, centerPoint.first, centerPoint.second)
                val (x, y) = centerPoint
                drawLine(x, y, x + halfSide + line, y, type.paint)
                bitmap = Bitmap.createBitmap(type.bitmap)
                bitmap = bitmap.rotate(360f - middleDegree)
                canvas.drawBitmap(
                    bitmap,
                    x + halfSide + line - removePaddingBetweenLineAndIcon,
                    y - bitmap.height / 2,
                    null
                )
                restore()
                startDeg = endDeg
            }

            drawCircle(
                centerPoint.first, centerPoint.second,
                (halfSide / 1.6 + 5.dpToPx).toFloat(), circleSemiTransparent
            )
            drawCircle(
                centerPoint.first, centerPoint.second,
                (halfSide / 1.6).toFloat(), circleWhitePaint
            )
        }
    }

    private fun initData() {
        startDeg = -90f
        endDeg = 0f
        pivot = height.coerceAtMost(width)
        val bitmap = getBitmap(Education())
        iconSize = if (bitmap.width > bitmap.height) {
            bitmap.width
        } else bitmap.height
        Log.d(logTag, "$iconSize size of Icon")
        side = (pivot - padding * 2 - line * 2 - iconSize * 2).toFloat()
        centerPoint = Pair(width / 2f, height / 2f)
        halfSide = side / 2
        val (x, y) = centerPoint
        val topLeft = Pair(x - halfSide, y - halfSide)
        val topRight = Pair(x + halfSide, y - halfSide)
        var bottomLeft = Pair(x - halfSide, y + halfSide)
        var bottomRight = Pair(x + halfSide, y + halfSide)
        rect = RectF(topLeft.first, topLeft.second, topRight.first, bottomRight.second)

        listOfSpending.clear()
        addSpending(Airplane(), 20000.0)
        addSpending(Buying(), 20000.0)
        addSpending(Skincare(), 20000.0)
        addSpending(Education(), 20000.0)
        addSpending(Assurance(), 20000.0)
        addSpending(Entertainment(), 20000.0)
        addSpending(Eating(), 20000.0)
        addSpending(Healthy(), 20000.0)
        addSpending(Investment(), 20000.0)
        addSpending(Moving(), 20000.0)
        addSpending(Other(), 20000.0)
        addSpending(ServiceHelpful(), 20000.0)
        addSpending(Traveling(), 20000.0)

        totalPrice = listOfSpending.sumByDouble { it.price }
        Log.d(logTag, totalPrice.toString())
    }

    private fun addSpending(type: TypeSpending, price: Double) {
        val _name = getName(type)
        val bitmap = getBitmap(type)
        listOfSpending.add(
            when (type) {
                is Airplane -> Airplane(_name, price, bitmap, airplaneTicketPaint)
                is Assurance -> Assurance(_name, price, bitmap, assurancePaint)
                is Buying -> Buying(_name, price, bitmap, buyingPaint)
                is Eating -> Eating(_name, price, bitmap, eatingPaint)
                is Education -> Education(_name, price, bitmap, educationPaint)
                is Entertainment -> Entertainment(_name, price, bitmap, entertainmentPaint)
                is Healthy -> Healthy(_name, price, bitmap, healthyPaint)
                is Investment -> Investment(_name, price, bitmap, investmentPaint)
                is Moving -> Moving(_name, price, bitmap, movingPaint)
                is Other -> Other(_name, price, bitmap, otherServicePaint)
                is ServiceHelpful -> ServiceHelpful(_name, price, bitmap, serviceHelpfulPaint)
                is Skincare -> Skincare(_name, price, bitmap, skincarePaint)
                is Traveling -> Traveling(_name, price, bitmap, travelingPaint)
            }
        )
    }

    private fun getName(type: TypeSpending): String = when (type) {
        is Airplane -> resources.getString(R.string.text_title_airplane)
        is Assurance -> resources.getString(R.string.text_title_assurance)
        is Buying -> resources.getString(R.string.text_title_buying)
        is Eating -> resources.getString(R.string.text_title_eating)
        is Education -> resources.getString(R.string.text_title_education)
        is Entertainment -> resources.getString(R.string.text_title_entertainment)
        is Healthy -> resources.getString(R.string.text_title_healthy)
        is Investment -> resources.getString(R.string.text_title_investment)
        is Moving -> resources.getString(R.string.text_title_moving)
        is Other -> resources.getString(R.string.text_title_other)
        is ServiceHelpful -> resources.getString(R.string.text_title_service_helpful)
        is Skincare -> resources.getString(R.string.text_title_skincare)
        is Traveling -> resources.getString(R.string.text_title_traveling)
    }

    private fun getBitmap(type: TypeSpending): Bitmap = when (type) {
        is Airplane -> getIcon(R.drawable.ic_ve_may_bay)
        is Assurance -> getIcon(R.drawable.ic_bao_hiem)
        is Buying -> getIcon(R.drawable.ic_mua_sam)
        is Eating -> getIcon(R.drawable.ic_an_uong)
        is Education -> getIcon(R.drawable.ic_giao_duc)
        is Entertainment -> getIcon(R.drawable.ic_giai_tri)
        is Healthy -> getIcon(R.drawable.ic_suc_khoe)
        is Investment -> getIcon(R.drawable.ic_dau_tu)
        is Moving -> getIcon(R.drawable.ic_di_chuyen)
        is Other -> getIcon(R.drawable.ic_chi_khac)
        is ServiceHelpful -> getIcon(R.drawable.ic_dv_tien_ich)
        is Skincare -> getIcon(R.drawable.ic_lam_dep)
        is Traveling -> getIcon(R.drawable.ic_du_lich)
    }


    private fun getIcon(drawableId: Int): Bitmap {
        val bitmap: Bitmap
        when (val drawable = ContextCompat.getDrawable(context, drawableId)) {
            is BitmapDrawable -> {
                bitmap = drawable.bitmap
            }
            is VectorDrawable -> {
                bitmap = Bitmap.createBitmap(
                    iconSize, iconSize,
                    Bitmap.Config.ARGB_8888
                )
                val canvas = Canvas(bitmap)
                drawable.setBounds(0, 0, canvas.width, canvas.height)
                drawable.draw(canvas)
            }
            else -> {
                bitmap = cloneBitmap()
            }
        }
        return bitmap
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return super.onTouchEvent(event)

        event?.let {

        }
    }
}

fun Bitmap.rotate(degrees: Float): Bitmap {
    val matrix = Matrix().apply { postRotate(degrees) }
    return Bitmap.createBitmap(this, 0, 0, width, height, matrix, true)
}