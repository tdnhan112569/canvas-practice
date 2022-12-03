package com.example.barchartbycanvas

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.VectorDrawable
import android.os.Build
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*
import kotlin.math.roundToInt


class PieChartViewClick(context: Context, attrs: AttributeSet) : View(context, attrs) {

    companion object {
        val logTag = this.javaClass.simpleName
    }

//    private val borderSquarePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {

    private val circleWhitePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = ContextCompat.getColor(context, R.color.white)
        style = Paint.Style.FILL
    }

    private val circleSemiTransparent = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = ContextCompat.getColor(context, R.color.semi_transparent)
        style = Paint.Style.FILL
    }

    private val buyingPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = ContextCompat.getColor(context, R.color.color_5073B8)
        style = Paint.Style.FILL_AND_STROKE
        strokeWidth = stokeWidth
    }

    private val eatingPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = ContextCompat.getColor(context, R.color.color_6150B8)
        style = Paint.Style.FILL_AND_STROKE
        strokeWidth = stokeWidth
    }

    private val travelingPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = ContextCompat.getColor(context, R.color.color_A050B8)
        style = Paint.Style.FILL_AND_STROKE
        strokeWidth = stokeWidth
    }

    private val serviceHelpfulPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = ContextCompat.getColor(context, R.color.color_B85081)
        style = Paint.Style.FILL_AND_STROKE
        strokeWidth = stokeWidth
    }

    private val healthyPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = ContextCompat.getColor(context, R.color.color_FF3B3B)
        style = Paint.Style.FILL_AND_STROKE
        strokeWidth = stokeWidth
    }

    private val airplaneTicketPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = ContextCompat.getColor(context, R.color.color_3BD7FF)
        style = Paint.Style.FILL_AND_STROKE
        strokeWidth = stokeWidth
    }

    private val movingPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = ContextCompat.getColor(context, R.color.color_1CCB5C)
        style = Paint.Style.FILL_AND_STROKE
        strokeWidth = stokeWidth
    }

    private val entertainmentPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = ContextCompat.getColor(context, R.color.color_F1963A)
        style = Paint.Style.FILL_AND_STROKE
        strokeWidth = stokeWidth
    }
    private val skincarePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = ContextCompat.getColor(context, R.color.color_72B112)
        style = Paint.Style.FILL_AND_STROKE
        strokeWidth = stokeWidth
    }

    private val investmentPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = ContextCompat.getColor(context, R.color.color_F16548)
        style = Paint.Style.FILL_AND_STROKE
        strokeWidth = stokeWidth
    }

    private val educationPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = ContextCompat.getColor(context, R.color.color_0606E3)
        style = Paint.Style.FILL_AND_STROKE
        strokeWidth = stokeWidth
    }

    private val assurancePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = ContextCompat.getColor(context, R.color.color_0AB39C)
        style = Paint.Style.FILL_AND_STROKE
        strokeWidth = stokeWidth
    }

    private val otherServicePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = ContextCompat.getColor(context, R.color.color_F8BA5E)
        style = Paint.Style.FILL_AND_STROKE
        strokeWidth = stokeWidth
    }

    private val moneyPaint = TextPaint(Paint.ANTI_ALIAS_FLAG).apply {
        color = ContextCompat.getColor(context, R.color.color_353535)
        style = Paint.Style.FILL_AND_STROKE
        style = Paint.Style.FILL
        textSize = 14.spToPx
        typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD)
    }

    private val categoryPaint = TextPaint(Paint.ANTI_ALIAS_FLAG).apply {
        color = ContextCompat.getColor(context, R.color.color_707070)
        style = Paint.Style.FILL_AND_STROKE
        style = Paint.Style.FILL
        textSize = 14.spToPx
        typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL)
    }

    sealed class TypeSpending(
        name: String = "",
        open var price: Double = 0.0,
        open var bitmap: Bitmap,
        open var paint: Paint = Paint(),
        open var fromDeg: Float = 0f,
        open var toDeg: Float = 0f,
        open var isCurrent: Boolean = false
    )

    data class Buying(
        val name: String = "",
        override var price: Double = 0.0,
        override var bitmap: Bitmap = cloneBitmap(),
        override var paint: Paint = Paint(),
        override var fromDeg: Float = 0f,
        override var toDeg: Float = 0f,
        override var isCurrent: Boolean = false
    ) : TypeSpending(name, price, bitmap, paint, fromDeg, toDeg, isCurrent)

    data class Eating(
        val name: String = "",
        override var price: Double = 0.0,
        override var bitmap: Bitmap = cloneBitmap(),
        override var paint: Paint = Paint(),
        override var fromDeg: Float = 0f,
        override var toDeg: Float = 0f,
        override var isCurrent: Boolean = false
    ) : TypeSpending(name, price, bitmap, paint, fromDeg, toDeg, isCurrent)

    data class Traveling(
        val name: String = "",
        override var price: Double = 0.0,
        override var bitmap: Bitmap = cloneBitmap(),
        override var paint: Paint = Paint(),
        override var fromDeg: Float = 0f,
        override var toDeg: Float = 0f,
        override var isCurrent: Boolean = false
    ) : TypeSpending(name, price, bitmap, paint, fromDeg, toDeg, isCurrent)

    data class ServiceHelpful(
        val name: String = "",
        override var price: Double = 0.0,
        override var bitmap: Bitmap = cloneBitmap(),
        override var paint: Paint = Paint(),
        override var fromDeg: Float = 0f,
        override var toDeg: Float = 0f,
        override var isCurrent: Boolean = false
    ) : TypeSpending(name, price, bitmap, paint, fromDeg, toDeg, isCurrent)

    data class Healthy(
        val name: String = "",
        override var price: Double = 0.0,
        override var bitmap: Bitmap = cloneBitmap(),
        override var paint: Paint = Paint(),
        override var fromDeg: Float = 0f,
        override var toDeg: Float = 0f,
        override var isCurrent: Boolean = false
    ) : TypeSpending(name, price, bitmap, paint, fromDeg, toDeg, isCurrent)

    data class Airplane(
        val name: String = "",
        override var price: Double = 0.0,
        override var bitmap: Bitmap = cloneBitmap(),
        override var paint: Paint = Paint(),
        override var fromDeg: Float = 0f,
        override var toDeg: Float = 0f,
        override var isCurrent: Boolean = false
    ) : TypeSpending(name, price, bitmap, paint, fromDeg, toDeg, isCurrent)

    data class Moving(
        val name: String = "",
        override var price: Double = 0.0,
        override var bitmap: Bitmap = cloneBitmap(),
        override var paint: Paint = Paint(),
        override var fromDeg: Float = 0f,
        override var toDeg: Float = 0f,
        override var isCurrent: Boolean = false
    ) : TypeSpending(name, price, bitmap, paint, fromDeg, toDeg, isCurrent)

    data class Entertainment(
        val name: String = "",
        override var price: Double = 0.0,
        override var bitmap: Bitmap = cloneBitmap(),
        override var paint: Paint = Paint(),
        override var fromDeg: Float = 0f,
        override var toDeg: Float = 0f,
        override var isCurrent: Boolean = false
    ) : TypeSpending(name, price, bitmap, paint, fromDeg, toDeg, isCurrent)

    data class Skincare(
        val name: String = "",
        override var price: Double = 0.0,
        override var bitmap: Bitmap = cloneBitmap(),
        override var paint: Paint = Paint(),
        override var fromDeg: Float = 0f,
        override var toDeg: Float = 0f,
        override var isCurrent: Boolean = false
    ) : TypeSpending(name, price, bitmap, paint, fromDeg, toDeg, isCurrent)

    data class Investment(
        val name: String = "",
        override var price: Double = 0.0,
        override var bitmap: Bitmap = cloneBitmap(),
        override var paint: Paint = Paint(),
        override var fromDeg: Float = 0f,
        override var toDeg: Float = 0f,
        override var isCurrent: Boolean = false
    ) : TypeSpending(name, price, bitmap, paint, fromDeg, toDeg, isCurrent)

    data class Education(
        val name: String = "",
        override var price: Double = 0.0,
        override var bitmap: Bitmap = cloneBitmap(),
        override var paint: Paint = Paint(),
        override var fromDeg: Float = 0f,
        override var toDeg: Float = 0f,
        override var isCurrent: Boolean = false
    ) : TypeSpending(name, price, bitmap, paint, fromDeg, toDeg, isCurrent)

    data class Assurance(
        val name: String = "",
        override var price: Double = 0.0,
        override var bitmap: Bitmap = cloneBitmap(),
        override var paint: Paint = Paint(),
        override var fromDeg: Float = 0f,
        override var toDeg: Float = 0f,
        override var isCurrent: Boolean = false
    ) : TypeSpending(name, price, bitmap, paint, fromDeg, toDeg, isCurrent)

    data class Other(
        val name: String = "",
        override var price: Double = 0.0,
        override var bitmap: Bitmap = cloneBitmap(),
        override var paint: Paint = Paint(),
        override var fromDeg: Float = 0f,
        override var toDeg: Float = 0f,
        override var isCurrent: Boolean = false
    ) : TypeSpending(name, price, bitmap, paint, fromDeg, toDeg, isCurrent)

    //    }
//        isFilterBitmap = true
//        isDither = true
//    private val bitmapPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
//
//    }
//        strokeWidth = 1.dpToPx.toFloat()
//        style = Paint.Style.STROKE
//        color = ContextCompat.getColor(context, R.color.xam_dam);

    private val listOfSpending: MutableList<TypeSpending> = mutableListOf()
    var totalPrice: Double = 0.0
    var startDeg = 0f
    var endDeg = 0f
    private val padding = 10.dpToPx
    private val line = 22.dpToPx
    private val removePaddingBetweenLineAndIcon = 10.dpToPx
    private var iconSize = 0
    var centerPoint: Pair<Float, Float> = Pair(0f, 0f)
    var rectOfNormalPie = RectF()
    var rectOfSelectedPie = RectF()
    var rectImageOutter = Rect()
    var rectImageInner = Rect()
    var pivot = 0
    var side = 0f
    private var halfSide = 0f
    private var radiusCircleWhiteInside = 0f
    private var stokeWidth = 0.dpToPx.toFloat()
    private var currentDeg = 0f

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        Log.d("PieChart", "onDraw")
        initData()
        onDrawPie(canvas)
    }

    private fun formatMoney(value: Double): String {
        val myFormatter = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            DecimalFormat("###,###,###.###", DecimalFormatSymbols(Locale.US))
        } else DecimalFormat("###,###,###.###", DecimalFormatSymbols(Locale("en", "in")))
        return myFormatter.format(value)
    }


    private fun onDrawText(canvas: Canvas, type: TypeSpending) {
        val textMoney = formatMoney(type.price)
        var widthTextMoney = moneyPaint.measureText(textMoney, 0 , textMoney.length)
        var textMoneyLayout : StaticLayout = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            StaticLayout.Builder
                .obtain(textMoney, 0, textMoney.length, moneyPaint, widthTextMoney.toInt())
                .setAlignment(Layout.Alignment.ALIGN_CENTER)
                .setLineSpacing(0f, 1f)
                .setIncludePad(false)
                .build()
        } else {
            StaticLayout(
                textMoney,
                moneyPaint,
                widthTextMoney.toInt(),
                Layout.Alignment.ALIGN_CENTER,
                1f,
                0f,
                false
            )
        }

        while (textMoneyLayout.width > rectImageInner.right - rectImageInner.left - 8.dpToPx) {
            moneyPaint.apply {
                textSize -= 1.spToPx
            }
            widthTextMoney = moneyPaint.measureText(textMoney)
            textMoneyLayout = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                StaticLayout.Builder
                    .obtain(textMoney, 0, textMoney.length, moneyPaint, widthTextMoney.toInt())
                    .setAlignment(Layout.Alignment.ALIGN_CENTER)
                    .setLineSpacing(0f, 1f)
                    .setIncludePad(false)
                    .build()
            } else {
                StaticLayout(
                    textMoney,
                    moneyPaint,
                    widthTextMoney.toInt(),
                    Layout.Alignment.ALIGN_CENTER,
                    1f,
                    0f,
                    false
                )
            }
        }



        canvas.save()
        canvas.translate((centerPoint.first - textMoneyLayout.width / 2).toFloat(), centerPoint.second - textMoneyLayout.height)
        textMoneyLayout.draw(canvas)

        val textCategory = getName(type)
        var widthTextCategory = categoryPaint.measureText(textCategory)
        var textCategoryLayout : StaticLayout =  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            StaticLayout.Builder
                .obtain(textCategory, 0, textCategory.length, categoryPaint, widthTextCategory.toInt())
                .setAlignment(Layout.Alignment.ALIGN_CENTER)
                .setLineSpacing(0f, 1f)
                .setIncludePad(false)
                .build()
        } else {
            StaticLayout(
                textCategory,
                categoryPaint,
                widthTextCategory.toInt(),
                Layout.Alignment.ALIGN_CENTER,
                1f,
                0f,
                false
            )
        }



        while (textCategoryLayout.width > rectImageInner.right - rectImageInner.left - 8.dpToPx) {
            moneyPaint.apply {
                textSize -= 1.spToPx
            }
            widthTextCategory = categoryPaint.measureText(textCategory)
            textCategoryLayout =   if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                StaticLayout.Builder
                    .obtain(textCategory, 0, textCategory.length, categoryPaint, widthTextCategory.toInt())
                    .setAlignment(Layout.Alignment.ALIGN_CENTER)
                    .setLineSpacing(0f, 1f)
                    .setIncludePad(false)
                    .build()
            } else {
                StaticLayout(
                    textCategory,
                    categoryPaint,
                    widthTextCategory.toInt(),
                    Layout.Alignment.ALIGN_CENTER,
                    1f,
                    0f,
                    false
                )
            }
        }
        canvas.restore()
        canvas.save()
        canvas.translate((centerPoint.first - textCategoryLayout.width / 2).toFloat(), centerPoint.second + 2.dpToPx)
        textCategoryLayout.draw(canvas)
        canvas.restore()
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
    }

    init {
        this.isFocusable = true;
        this.isFocusableInTouchMode = true;
    }

    private fun onDrawPie(canvas: Canvas) {
        var degree = 0f
        var sweepDegree = 0f
        var middleDegree = 0f
        var bitmap: Bitmap
        var bound: Rect
        var xOfLine = 0f
        var yOfLine = 0f
        val drawableOutter = ContextCompat.getDrawable(context, R.drawable.ic_group_outter)
        val drawableInner = ContextCompat.getDrawable(context, R.drawable.ic_bg_inner)
        canvas.apply {
            //canvas.drawRect(rect, borderSquarePaint)
            drawableOutter?.apply {
                bounds = rectImageOutter
                draw(canvas)
            }
            for (type in listOfSpending) {
                degree = (type.price * 360f / totalPrice).toFloat()
                endDeg = startDeg + degree
                type.fromDeg = startDeg
                type.toDeg = endDeg
                sweepDegree = endDeg - startDeg
                middleDegree = startDeg + sweepDegree / 2
                if(currentDeg - 90f in startDeg..endDeg) {
                    type.isCurrent = true
                } else {
                    type.isCurrent = false
                    drawArc(rectOfNormalPie, startDeg, sweepDegree, true, type.paint)
                }



                save()
                Log.d(logTag, middleDegree.toString())
                rotate(middleDegree, centerPoint.first, centerPoint.second)
                val (x, y) = centerPoint
                drawLine(x, y, x + halfSide + line, y, type.paint.apply {
                    strokeWidth = 1.dpToPx.toFloat()
                })
                bitmap = Bitmap.createBitmap(type.bitmap)
                bitmap = bitmap.rotate(360f - middleDegree)
                canvas.drawBitmap(
                    bitmap,
                    x + halfSide + line - removePaddingBetweenLineAndIcon,
                    y - bitmap.height / 2,
                    null
                )
                type.paint.apply {
                    strokeWidth = 0.dpToPx.toFloat()
                }
                restore()
                startDeg = endDeg
            }

            drawCircle(
                centerPoint.first, centerPoint.second,
                radiusCircleWhiteInside, circleWhitePaint
            )

            val currentPie = listOfSpending.first { it.isCurrent }
            drawArc(rectOfSelectedPie, currentPie.fromDeg, currentPie.toDeg - currentPie.fromDeg, true, currentPie.paint)

            drawCircle(
                centerPoint.first, centerPoint.second,
                (halfSide / 1.7).toFloat(), circleWhitePaint
            )
            val sizeArcInner = 3.dpToPx
            drawArc(
                RectF(
                    rectImageInner.left.toFloat() - sizeArcInner,
                    rectImageInner.top.toFloat() - sizeArcInner,
                    rectImageInner.right.toFloat() + sizeArcInner,
                    rectImageInner.bottom.toFloat() + sizeArcInner
                ), 0f , 360f, true, circleWhitePaint
            )
            drawableInner?.apply {
                setBounds(
                    rectImageInner.left,
                    rectImageInner.top,
                    rectImageInner.right,
                    rectImageInner.bottom
                )
                draw(canvas)
            }
            onDrawText(canvas, currentPie)
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
        halfSide = side / 1.8f

        val (x, y) = centerPoint
        val topLeft = Pair(x - halfSide, y - halfSide)
        val topRight = Pair(x + halfSide, y - halfSide)
        var bottomLeft = Pair(x - halfSide, y + halfSide)
        var bottomRight = Pair(x + halfSide, y + halfSide)

        radiusCircleWhiteInside = (halfSide / 1.35).toFloat()
        rectOfNormalPie = RectF(topLeft.first, topLeft.second, topRight.first, bottomRight.second)
        val innerSize = 34.dpToPx
        rectImageInner = Rect(topLeft.first.roundToInt() + innerSize,
                                topLeft.second.roundToInt() + innerSize,
                                topRight.first.roundToInt() - innerSize,
                                bottomRight.second.roundToInt() - innerSize
            )

        val selectedPieSize = 6.dpToPx
        rectOfSelectedPie = RectF(
            topLeft.first - selectedPieSize,
            topLeft.second - selectedPieSize,
            topRight.first + selectedPieSize ,
            bottomRight.second + selectedPieSize)

        val imgOutterSize = 18.dpToPx

        rectImageOutter = Rect(topLeft.first.roundToInt() - imgOutterSize,
            topLeft.second.roundToInt() - imgOutterSize,
            topRight.first.roundToInt() + imgOutterSize ,
            bottomRight.second.roundToInt() + imgOutterSize)

        listOfSpending.clear()
        //addSpending(Airplane(), 20000.0)
        //addSpending(Buying(), 20000.0)

        addSpending(Education(), 350000000000000.0)

        addSpending(Entertainment(), 150000000000000.0)
        //addSpending(Eating(), 20000.0)
        addSpending(Skincare(), 200000000000000.0)
        addSpending(Healthy(), 200000000000000.0)
       /* addSpending(Investment(), 20000.0)
        addSpending(Moving(), 20000.0)
        addSpending(Other(), 20000.0)*/
        addSpending(ServiceHelpful(), 200000000000000.0)
       /* addSpending(Traveling(), 20000.0)
        addSpending(Assurance(), 20000.0)*/

        totalPrice = listOfSpending.sumOf { it.price }
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

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event?.let {
            when(event.action) {
                MotionEvent.ACTION_MOVE -> {
                    Log.e("Custom", "X: " + it.x.toString() + " , Y: " + it.y)
                }
                MotionEvent.ACTION_DOWN -> {
                    Log.e("Custom", "X: " + it.x.toString() + " , Y: " + it.y)
                }
                MotionEvent.ACTION_UP -> {
                    Log.e("Custom", "X: " + it.x.toString() + " , Y: " + it.y)
                }
                else -> {}
            }
            val degreeCal = calculateAngle(it.y, it.x, centerPoint.second, centerPoint.first)
            val finalDegree = if(degreeCal < 0) {
                degreeCal * -1f
            } else {
                360 - degreeCal
            }
            currentDeg = finalDegree
            invalidate()
            Log.e("Degree",
                finalDegree.toString()
            )

        }
        return true
    }

    private fun calculateAngle(
        touchX: Float,
        touchY: Float,
        centerX: Float,
        centerY: Float
    ): Float {
        val deltaX = centerX - touchX
        val deltaY = centerY - touchY
        return Math.toDegrees(kotlin.math.atan2(deltaY.toDouble(), deltaX.toDouble())).toFloat()
    }

}