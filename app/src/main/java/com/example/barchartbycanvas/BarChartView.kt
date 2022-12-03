package com.example.barchartbycanvas

import android.content.Context
import android.content.res.Resources.getSystem
import android.graphics.*
import android.graphics.Paint.ANTI_ALIAS_FLAG
import android.os.Build
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import kotlin.math.max
import kotlin.math.min


class BarChartView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private var paymentAmount = 100.0
    private var savingAmount = 100.0
    private var totalPaymentAndSavingAmount = 0.0
    var moneyA = "1.000.000.000.000.000.000"

    private var creditAmount = 100.0
    private var loanAmount =  95.0
    private var totalCreditAndLoanAmount = 0.0
    var moneyB = "1.000.000.000.000.000.000"

    private var heightYa = 0.0
    private var heightYb = 0.0
    private var maxHeightOfBar = 0.0
    private var minHeightOfBar = 1.dpToPx.toFloat()

    private var max = 0.0
    private var min = 0.0

    private val savingMoneyPaint = Paint(ANTI_ALIAS_FLAG).apply {
        color = ContextCompat.getColor(context, R.color.xanh_la_cay);
        style = Paint.Style.FILL
    }

    private val paymentMoneyPaint = Paint(ANTI_ALIAS_FLAG).apply {
        color = ContextCompat.getColor(context, R.color.xanh_duong);
        style = Paint.Style.FILL
    }

    private val creditCardPaint = Paint(ANTI_ALIAS_FLAG).apply {
        color = ContextCompat.getColor(context, R.color.cam);
        style = Paint.Style.FILL
    }

    private val loansPaint = Paint(ANTI_ALIAS_FLAG).apply {
        color = ContextCompat.getColor(context, R.color.mau_do);
        style = Paint.Style.FILL
    }

    private val titlePaint = TextPaint(ANTI_ALIAS_FLAG).apply {
        color = ContextCompat.getColor(context, R.color.mau_text)
        style = Paint.Style.FILL
        textSize = 12.spToPx
        typeface = Typeface.SANS_SERIF
    }

    private val totalAssetsPaint = TextPaint(ANTI_ALIAS_FLAG).apply {
        color = ContextCompat.getColor(context, R.color.xanh_duong);
        style = Paint.Style.FILL
        textSize = 12.spToPx
        typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD)
    }

    private val totalDebitPaint = TextPaint(ANTI_ALIAS_FLAG).apply {
        color = ContextCompat.getColor(context, R.color.mau_do);
        style = Paint.Style.FILL
        textSize = 12.spToPx
        typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD)
    }

    private val boxTextPaint = Paint(ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL_AND_STROKE
        color = ContextCompat.getColor(context, R.color.xam_nhat)
        strokeWidth = 1.dpToPx.toFloat()
        setShadowLayer(12f, 2.dpToPx.toFloat(), 2.dpToPx.toFloat(), Color.LTGRAY)
    }

    private val borderBoxTextPaint = Paint(ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        color = ContextCompat.getColor(context, R.color.xam_dam)
        strokeWidth = 2.dpToPx.toFloat()
    }

    /* Box message variable */

    /*borderRadiusBoxMessage and paddingVerticalBoxMessage need to be the same*/
    private var borderRadiusBoxMessage = 4.dpToPx
    private val paddingVerticalBoxMessage = borderRadiusBoxMessage
    /**/

    private var heightOfBoxMessageContainer = 0.0

    private val heightArrow = 6.dpToPx
    private val widthArrow = 4.dpToPx
    private val paddingHorizontalBoxMessage = 10.dpToPx
    private val marginHorizontalBoxMessage = 10.dpToPx
    private val marginVerticalBoxMessage = 5.dpToPx

    var heightOfContainerBoxMessageA = 0.0
    var midXOfBarA = 0.0
    var widthOfTextMoneyA = 0f
    var halfWidthTextMoneyA = 0f
    lateinit var pathBoxAShapeInside: Path
    lateinit var pathBoxAShapeOutside: Path
    var xStartTextMoneyA: Float = 0f
    var fromYOfMoneyAText = 0f
    var standardRightXBoxMessageA: Float = 0f

    var heightOfContainerBoxMessageB = 0.0
    var midXOfBarB = 0.0
    var widthOfTextMoneyB = 0f
    var halfWidthTextMoneyB = 0f
    lateinit var pathBoxBShapeInside: Path
    lateinit var pathBoxBShapeOutside: Path
    var xStartTextMoneyB: Float = 0f
    var fromYOfMoneyBText = 0f
    var standardLeftXBoxMessageB: Float = 0f

    /* Box message variable */

    /* Static layout variable */
    val spacingMultiplier = 1f
    val spacingAddition = 0f
    val includePadding = false
    val alignment = Layout.Alignment.ALIGN_CENTER
    lateinit var staticLayoutTitleBarA: StaticLayout
    lateinit var staticLayoutTitleBarB: StaticLayout
    lateinit var staticLayoutMoneyTextBarA: StaticLayout
    lateinit var staticLayoutMoneyTextBarB: StaticLayout
    /* Static layout variable */

    /* Tinh truc X */
    private var fourOnTenX = 0.0
    private var widthOfBar = 0.0

    private var spaceBetween2Bar = 0.0
    private var fromX = 0.0
    private var leftBarA = 0.0
    private var rightBarA = 0.0
    private var leftBarB = 0.0
    private var rightBarB = 0.0
    /* Tinh truc X */

    /*  Title Note variable */
    private var heightOfContainerViewTitle = 0.0
    private var paddingVerticalTitleNote = 5.dpToPx
    /* Title Note variable*/

    private fun initSizeXAxis() {
        fourOnTenX = width.toDouble().times(0.4)
        widthOfBar = width.toDouble().times(0.3)
        spaceBetween2Bar = fourOnTenX.div(4)
        fromX = (fourOnTenX - spaceBetween2Bar).div(2)
        leftBarA = fromX
        rightBarA = fromX + widthOfBar
        leftBarB = rightBarA + spaceBetween2Bar
        rightBarB = leftBarB + widthOfBar
        Log.d("Size in dp:", " data ${width.pxToDp} ${height.pxToDp}")
        Log.d("Size in pixel: ", " data $width $height")
        totalPaymentAndSavingAmount = paymentAmount + savingAmount
        //moneyA = totalPaymentAndSavingAmount.toString()
        totalCreditAndLoanAmount = creditAmount + loanAmount
        //moneyB = totalCreditAndLoanAmount.toString()
        max = max(totalPaymentAndSavingAmount, totalCreditAndLoanAmount)
        min = min(totalPaymentAndSavingAmount, totalCreditAndLoanAmount)
    }

    private fun initLayoutDataTitleNote() {
        var textBarA: String
        var textBarB: String

        resources.run {
            textBarA = getString(R.string.assets)
            textBarB = getString(R.string.debit)
        }

        val widthLayoutTextOfBarA = (rightBarA - leftBarA).toInt()
        val widthLayoutTextOfBarB = (rightBarB - leftBarB).toInt()

        staticLayoutTitleBarA = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            StaticLayout.Builder
                .obtain(textBarA, 0, textBarA.length, titlePaint, widthLayoutTextOfBarA)
                .setAlignment(alignment)
                .setLineSpacing(spacingAddition, spacingMultiplier)
                .setIncludePad(includePadding)
                .build()
        } else {
            StaticLayout(
                textBarA,
                titlePaint,
                widthLayoutTextOfBarA,
                alignment,
                spacingMultiplier,
                spacingAddition,
                includePadding
            )
        }

        staticLayoutTitleBarB = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            StaticLayout.Builder
                .obtain(textBarB, 0, textBarB.length, titlePaint, widthLayoutTextOfBarB)
                .setAlignment(alignment)
                .setLineSpacing(spacingAddition, spacingMultiplier)
                .setIncludePad(includePadding)
                .build()
        } else {
            StaticLayout(
                textBarB,
                titlePaint,
                widthLayoutTextOfBarB,
                alignment,
                spacingMultiplier,
                spacingAddition,
                includePadding
            )
        }
        heightOfContainerViewTitle =
            if (staticLayoutTitleBarA.height > staticLayoutTitleBarB.height) {
                staticLayoutTitleBarA.height.toDouble() + paddingVerticalTitleNote * 2
            } else {
                staticLayoutTitleBarB.height.toDouble() + paddingVerticalTitleNote * 2
            }
    }

    private fun initLayoutDataBoxMessage() {
        initLayoutBoxMessageA()
        initLayoutBoxMessageB()
        initSizeBoxMessageContainer()
    }

    private fun initSizeBoxMessageContainer() {
        var heightBarA = 0f
        var heightBarB = 0f
        fromYOfMoneyBText = 0f
        fromYOfMoneyAText = 0f
        heightOfBoxMessageContainer = 0.0
        when {
            max == min && max != 0.0 -> {
                heightOfBoxMessageContainer += marginVerticalBoxMessage * 2
                if (standardRightXBoxMessageA >= standardLeftXBoxMessageB - 1.dpToPx) {
                    heightOfBoxMessageContainer += heightOfContainerBoxMessageA + heightOfContainerBoxMessageB + marginVerticalBoxMessage
                    fromYOfMoneyBText -= heightOfContainerBoxMessageA.toFloat() + marginVerticalBoxMessage
                } else {
                    heightOfBoxMessageContainer += max(
                        heightOfContainerBoxMessageA,
                        heightOfContainerBoxMessageB
                    )
                }
            }
            max == min && max == 0.0 -> {
                heightOfBoxMessageContainer = 0.0
            }
            max == totalPaymentAndSavingAmount -> {
                heightBarA = height.toFloat()
                heightBarB =
                    ((totalCreditAndLoanAmount / totalPaymentAndSavingAmount) * heightBarA).toFloat()
                if (heightBarB + heightOfContainerBoxMessageB + marginVerticalBoxMessage > heightBarA) {
                    fromYOfMoneyAText -= ((heightBarB + heightOfContainerBoxMessageB + marginVerticalBoxMessage) - heightBarA ).toFloat()
                    heightOfBoxMessageContainer =
                        (heightBarB + heightOfContainerBoxMessageB + marginVerticalBoxMessage) - heightBarA + heightOfContainerBoxMessageA + marginVerticalBoxMessage * 2
                } else {
                    heightOfBoxMessageContainer = heightOfContainerBoxMessageA + marginVerticalBoxMessage * 2
                }
            }
            max == totalCreditAndLoanAmount -> {
                heightBarB = height.toFloat()
                heightBarA =
                    ((totalPaymentAndSavingAmount/ totalCreditAndLoanAmount) * heightBarB).toFloat()
                if (heightBarA + heightOfContainerBoxMessageA + marginVerticalBoxMessage > heightBarB) {
                    fromYOfMoneyBText -= ((heightBarA + heightOfContainerBoxMessageA + marginVerticalBoxMessage) - heightBarB).toFloat()
                    heightOfBoxMessageContainer =
                        (heightBarA + heightOfContainerBoxMessageA + marginVerticalBoxMessage) - heightBarB + heightOfContainerBoxMessageB + marginVerticalBoxMessage * 2
                } else {
                    heightOfBoxMessageContainer = heightOfContainerBoxMessageB + marginVerticalBoxMessage * 2
                }
            }
        }
    }

    private fun initLayoutBoxMessageA() {
        heightOfContainerBoxMessageA = 0.0
        midXOfBarA = leftBarA + (rightBarA - leftBarA).div(2)
        widthOfTextMoneyA = totalAssetsPaint.measureText(moneyA, 0, moneyA.length)
        if (widthOfTextMoneyA > (width - marginHorizontalBoxMessage * 2 - paddingHorizontalBoxMessage * 2).toFloat()) {
            widthOfTextMoneyA =
                (width - marginHorizontalBoxMessage * 2 - paddingHorizontalBoxMessage * 2).toFloat()
        }
        Log.d("Canvas", "Width of text: ${widthOfTextMoneyA}")
        halfWidthTextMoneyA = widthOfTextMoneyA.div(2)

        staticLayoutMoneyTextBarA = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            StaticLayout.Builder
                .obtain(moneyA, 0, moneyA.length, totalAssetsPaint, widthOfTextMoneyA.toInt())
                .setAlignment(alignment)
                .setLineSpacing(spacingAddition, spacingMultiplier)
                .setIncludePad(includePadding)
                .build()
        } else {
            StaticLayout(
                moneyA,
                totalAssetsPaint,
                widthOfTextMoneyA.toInt(),
                alignment,
                spacingMultiplier,
                spacingAddition,
                includePadding
            )
        }

        heightOfContainerBoxMessageA += staticLayoutMoneyTextBarA.height
        heightOfContainerBoxMessageA += heightArrow
        heightOfContainerBoxMessageA += borderRadiusBoxMessage
        heightOfContainerBoxMessageA += paddingVerticalBoxMessage

        initPathBoxA()
    }

    private fun initLayoutBoxMessageB() {
        midXOfBarB = leftBarB + (rightBarB - leftBarB).div(2)
        widthOfTextMoneyB = totalDebitPaint.measureText(moneyB, 0, moneyB.length)
        if (widthOfTextMoneyB > (width - marginHorizontalBoxMessage * 2 - paddingHorizontalBoxMessage * 2).toFloat()) {
            widthOfTextMoneyB =
                (width - marginHorizontalBoxMessage * 2 - paddingHorizontalBoxMessage * 2).toFloat()
        }
        halfWidthTextMoneyB = widthOfTextMoneyB.div(2)

        staticLayoutMoneyTextBarB = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            StaticLayout.Builder
                .obtain(moneyB, 0, moneyB.length, totalDebitPaint, widthOfTextMoneyB.toInt())
                .setAlignment(alignment)
                .setLineSpacing(spacingAddition, spacingMultiplier)
                .setIncludePad(includePadding)
                .build()
        } else {
            StaticLayout(
                moneyB,
                totalDebitPaint,
                widthOfTextMoneyB.toInt(),
                alignment,
                spacingMultiplier,
                spacingAddition,
                includePadding
            )
        }

        heightOfContainerBoxMessageB += staticLayoutMoneyTextBarB.height
        heightOfContainerBoxMessageB += heightArrow
        heightOfContainerBoxMessageB += borderRadiusBoxMessage
        heightOfContainerBoxMessageB += paddingVerticalBoxMessage

        initPathBoxB()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        reset()
        initSizeXAxis()
        initLayoutDataBoxMessage()
        initLayoutDataTitleNote()
        onDrawChartPart(canvas, heightOfBoxMessageContainer.toFloat(), heightOfContainerViewTitle.toFloat())
        onDrawBoxMoney(canvas)
        onDrawTitle(canvas, (heightOfBoxMessageContainer + maxHeightOfBar).toFloat())
    }

    private fun onDrawChartPart(
        canvas: Canvas,
        distanceFromTop: Float = 0f,
        distanceFromBottom: Float = 0f
    ) {
        canvas.apply {
            maxHeightOfBar = (height + -distanceFromBottom - distanceFromTop).toDouble()
            var yaMin = 0f
            var ybMin = 0f
            /* Tinh truc Y */
            when(max) {
                0.0 -> {
                    heightYa = maxHeightOfBar
                    heightYb = maxHeightOfBar
                    yaMin = (heightYa - minHeightOfBar).toFloat()
                    ybMin = (heightYb - minHeightOfBar).toFloat()
                    drawRect(
                        leftBarA.toFloat(),
                        yaMin,
                        rightBarA.toFloat(),
                        heightYa.toFloat(),
                        paymentMoneyPaint
                    )
                    drawRect(
                        leftBarB.toFloat(),
                        ybMin,
                        rightBarB.toFloat(),
                        heightYb.toFloat(),
                        loansPaint
                    )
                }
                totalPaymentAndSavingAmount -> { // TH: lay Cot A lam chuan
                    /*Ve bar A - start*/
                    heightYa = maxHeightOfBar

                    /*
                    * Tinh yaMid de trung gian
                    * ve tkThanhToan y tu maxYa den yaMid va tkTietKiem la phan con lai
                    * cong them distanceFromTop
                    * */
                    yaMin = distanceFromTop
                    var yaMid = (heightYa - (paymentAmount * heightYa) / totalPaymentAndSavingAmount)
                    yaMid += distanceFromTop
                    val yaMax = (heightYa + distanceFromTop).toFloat()


                    // ve tai khoan tiet kiem, o tren cung bar A
                    drawRect(
                        leftBarA.toFloat(),
                        yaMin,
                        rightBarA.toFloat(),
                        yaMid.toFloat(),
                        savingMoneyPaint
                    )

                    // ve tai khoan thanh toan, o duoi cung bar A
                    drawRect(
                        leftBarA.toFloat(),
                        yaMid.toFloat(), //Top + khoang cach so voi View theo tham so truyen vao
                        rightBarA.toFloat(),
                        yaMax,
                        paymentMoneyPaint
                    )
                    /*------------------------------Ve bar A - end*/

                    /* Vẽ bar B - start*/

                    /*
                        Vi dua vao max totalCreditAndLoanAmount va totalPaymentAndSavingAmount lam moc chieu cao
                        => tu chieu cao A se tinh chieu cao B, lay A lam chuan chia ty le
                    */
                    when(totalCreditAndLoanAmount) {
                        0.0 -> {
                            heightYb = (height - distanceFromBottom).toDouble()
                            ybMin = (heightYb - minHeightOfBar).toFloat()
                            drawRect(
                                leftBarB.toFloat(),
                                ybMin,
                                rightBarB.toFloat(),
                                heightYb.toFloat(),
                                loansPaint
                            )
                        }
                        else -> {
                            heightYb = (totalCreditAndLoanAmount / totalPaymentAndSavingAmount) * heightYa

                            // Tinh ybMin va ybMid, phai lay Bar A lam chuan theo
                            ybMin = (heightYa + distanceFromTop - heightYb).toFloat()

                            val heightOfTkTinDung = (creditAmount / totalPaymentAndSavingAmount) * heightYa

                            val ybMid = ybMin + heightOfTkTinDung
                            val ybMax = yaMax

                            // ve credit bar part
                            drawRect(
                                leftBarB.toFloat(),
                                ybMin,
                                rightBarB.toFloat(),
                                ybMid.toFloat(),
                                creditCardPaint
                            )

                            // ve Loan bar part
                            drawRect(
                                leftBarB.toFloat(),
                                ybMid.toFloat(),
                                rightBarB.toFloat(),
                                ybMax,
                                loansPaint
                            )
                        }
                    }

                }
                totalCreditAndLoanAmount -> { // TH: lay Cot B lam chuan
                    heightYb = maxHeightOfBar

                    var ybMid = heightYb - (loanAmount * heightYb) / totalCreditAndLoanAmount
                    ybMid += distanceFromTop

                    ybMin = distanceFromTop
                    val ybMax = (heightYb + distanceFromTop).toFloat()

                    // Credit bar
                    drawRect(
                        leftBarB.toFloat(),
                        ybMin,
                        rightBarB.toFloat(),
                        ybMid.toFloat(),
                        creditCardPaint
                    )

                    // Loan bar
                    drawRect(
                        leftBarB.toFloat(),
                        ybMid.toFloat(),
                        rightBarB.toFloat(),
                        ybMax,
                        loansPaint
                    )

                    // Vẽ bar A
                    when(totalPaymentAndSavingAmount) {
                        0.0 -> {
                            heightYa =  (height - distanceFromBottom).toDouble()
                            yaMin = (heightYa - minHeightOfBar).toFloat()
                            drawRect(
                                leftBarA.toFloat(),
                                yaMin,
                                rightBarA.toFloat(),
                                heightYa.toFloat(),
                                paymentMoneyPaint
                            )
                        }
                        else -> {
                            heightYa = (totalPaymentAndSavingAmount / totalCreditAndLoanAmount) * heightYb

                            yaMin = (heightYb + distanceFromTop - heightYa).toFloat()
                            val heightOfTkTietKiem = (savingAmount / totalCreditAndLoanAmount) * heightYb
                            val yaMid = yaMin + heightOfTkTietKiem
                            val yaMax = ybMax

                            // ve tai khoan tiet kiem
                            drawRect(
                                leftBarA.toFloat(),
                                yaMin.toFloat(),
                                rightBarA.toFloat(),
                                yaMid.toFloat(),
                                savingMoneyPaint
                            )
                            // ve tai khoan thanh toan
                            drawRect(
                                leftBarA.toFloat(),
                                yaMid.toFloat(),
                                rightBarA.toFloat(),
                                yaMax,
                                paymentMoneyPaint
                            )
                        }
                    }
                }
            }
            fromYOfMoneyAText = if(fromYOfMoneyAText == 0f)
                    (yaMin - heightOfContainerBoxMessageA - marginVerticalBoxMessage).toFloat()
            else fromYOfMoneyAText + (yaMin - heightOfContainerBoxMessageA - marginVerticalBoxMessage).toFloat()
            fromYOfMoneyBText = if(fromYOfMoneyBText == 0f)
                    (ybMin - heightOfContainerBoxMessageB - marginVerticalBoxMessage).toFloat()
            else fromYOfMoneyBText + (ybMin - heightOfContainerBoxMessageB - marginVerticalBoxMessage).toFloat()
        }
    }

    private fun onDrawTitle(canvas: Canvas, fromY: Float = 0f) {
        val centerYPoint = heightOfContainerViewTitle.div(2)
        canvas.apply {
            // draw text Bar A
            val heightOfLayoutTextBarA = staticLayoutTitleBarA.height
            val centerHeightOfLayoutTextBarA = heightOfLayoutTextBarA.div(2)
            canvas.save()
            canvas.translate(
                leftBarA.toFloat(), fromY + (centerYPoint - centerHeightOfLayoutTextBarA).toFloat()
            )
            staticLayoutTitleBarA.draw(canvas)
            canvas.restore()

            // draw text Bar B
            val heightOfLayoutTextBarB = staticLayoutTitleBarB.height
            val centerHeightOfLayoutTextBarB = heightOfLayoutTextBarB.div(2)
            canvas.save()
            canvas.translate(
                leftBarB.toFloat(),
                fromY + (centerYPoint - centerHeightOfLayoutTextBarB).toFloat()
            )
            staticLayoutTitleBarB.draw(canvas)
            canvas.restore()
        }
    }

    private fun initPathBoxA() {
        pathBoxAShapeInside = Path().apply {

            /* Vẽ mũi tên */
            moveTo(
                midXOfBarA.toFloat(),
                fromYOfMoneyAText + staticLayoutMoneyTextBarA.height + paddingVerticalBoxMessage + heightArrow
            )
            lineTo(
                midXOfBarA.toFloat() + widthArrow,
                fromYOfMoneyAText + staticLayoutMoneyTextBarA.height + paddingVerticalBoxMessage
            )
            moveTo(
                midXOfBarA.toFloat() + 1.dpToPx,
                fromYOfMoneyAText + staticLayoutMoneyTextBarA.height + paddingVerticalBoxMessage + heightArrow
            )
            lineTo(
                midXOfBarA.toFloat() - widthArrow,
                fromYOfMoneyAText + staticLayoutMoneyTextBarA.height + paddingVerticalBoxMessage
            )

            var standardLeftXBoxMessageA =
                midXOfBarA.toFloat() - halfWidthTextMoneyA - paddingHorizontalBoxMessage
            if (standardLeftXBoxMessageA < marginHorizontalBoxMessage) {
                standardLeftXBoxMessageA = marginHorizontalBoxMessage.toFloat()
            }
            // draw radius bottom left, square size by [borderRadiusBoxMessage]
            var xEndRadius = standardLeftXBoxMessageA + borderRadiusBoxMessage

            lineTo(
                xEndRadius,
                fromYOfMoneyAText + staticLayoutMoneyTextBarA.height + paddingVerticalBoxMessage
            )

            var xStartRadius = standardLeftXBoxMessageA
            var yStartRadius =
                fromYOfMoneyAText + staticLayoutMoneyTextBarA.height + paddingVerticalBoxMessage - borderRadiusBoxMessage
            var yEndRadius =
                fromYOfMoneyAText + staticLayoutMoneyTextBarA.height + paddingVerticalBoxMessage
            val rectBottomLeftCorner = RectF(xStartRadius, yStartRadius, xEndRadius, yEndRadius)

            arcTo(rectBottomLeftCorner, 90f, 90f)

            // draw radius topLeft, square size by [borderRadiusBoxMessage]
            yStartRadius = fromYOfMoneyAText - paddingVerticalBoxMessage
            yEndRadius = fromYOfMoneyAText - paddingVerticalBoxMessage + 6.dpToPx
            lineTo(standardLeftXBoxMessageA, yEndRadius)
            val rectTopLeftCorner = RectF(xStartRadius, yStartRadius, xEndRadius, yEndRadius)
            arcTo(rectTopLeftCorner, 180f, 90f)

            // draw radius topRight, square size by [borderRadiusBoxMessage]
            var standardRightXBoxMessage =
                standardLeftXBoxMessageA + widthOfTextMoneyA + paddingHorizontalBoxMessage * 2 //+ addWidth
            if (standardRightXBoxMessage > width - marginHorizontalBoxMessage) {
                standardRightXBoxMessage = (width - marginHorizontalBoxMessage).toFloat()
            }
            xStartRadius = standardRightXBoxMessage - borderRadiusBoxMessage
            xEndRadius = standardRightXBoxMessage
            lineTo(xStartRadius, yStartRadius)
            val rectTopRightCorner = RectF(xStartRadius, yStartRadius, xEndRadius, yEndRadius)
            arcTo(rectTopRightCorner, 270f, 90f)

            // draw radius bottomRight, square size by [borderRadiusBoxMessage]

            yStartRadius = fromYOfMoneyAText + staticLayoutMoneyTextBarA.height
            yEndRadius = yStartRadius + borderRadiusBoxMessage
            lineTo(standardRightXBoxMessage, yStartRadius)
            val rectBottomRightCorner =
                RectF(xStartRadius, yStartRadius, xEndRadius, yEndRadius)
            arcTo(rectBottomRightCorner, 0f, 90f)

            // Close path
            lineTo((midXOfBarA + widthArrow).toFloat(), yEndRadius)

            xStartTextMoneyA = standardLeftXBoxMessageA + paddingHorizontalBoxMessage
        }

        pathBoxAShapeOutside = Path().apply {

            /* Vẽ mũi tên */
            moveTo(
                midXOfBarA.toFloat(),
                fromYOfMoneyAText + staticLayoutMoneyTextBarA.height + paddingVerticalBoxMessage + heightArrow
            )
            lineTo(
                midXOfBarA.toFloat() + widthArrow,
                fromYOfMoneyAText + staticLayoutMoneyTextBarA.height + paddingVerticalBoxMessage
            )
            moveTo(
                midXOfBarA.toFloat() + 1.dpToPx,
                fromYOfMoneyAText + staticLayoutMoneyTextBarA.height + paddingVerticalBoxMessage + heightArrow
            )
            lineTo(
                midXOfBarA.toFloat() - widthArrow,
                fromYOfMoneyAText + staticLayoutMoneyTextBarA.height + paddingVerticalBoxMessage
            )

            var standardLeftXBoxMessage =
                midXOfBarA.toFloat() - halfWidthTextMoneyA - paddingHorizontalBoxMessage
            if (standardLeftXBoxMessage < marginHorizontalBoxMessage) {
                standardLeftXBoxMessage = marginHorizontalBoxMessage.toFloat()
            }
            // draw radius bottom left, square size by [borderRadiusBoxMessage]
            var xEndRadius = standardLeftXBoxMessage + borderRadiusBoxMessage
            lineTo(
                xEndRadius,
                fromYOfMoneyAText + staticLayoutMoneyTextBarA.height + paddingVerticalBoxMessage
            )
            var xStartRadius = standardLeftXBoxMessage
            var yStartRadius =
                fromYOfMoneyAText + staticLayoutMoneyTextBarA.height + paddingVerticalBoxMessage - borderRadiusBoxMessage
            var yEndRadius =
                fromYOfMoneyAText + staticLayoutMoneyTextBarA.height + paddingVerticalBoxMessage
            val rectBottomLeftCorner = RectF(xStartRadius, yStartRadius, xEndRadius, yEndRadius)
            arcTo(rectBottomLeftCorner, 90f, 90f)

            // draw radius topLeft, square size by [borderRadiusBoxMessage]
            yStartRadius = fromYOfMoneyAText - paddingVerticalBoxMessage
            yEndRadius = fromYOfMoneyAText - paddingVerticalBoxMessage + 6.dpToPx
            lineTo(standardLeftXBoxMessage, yEndRadius)
            val rectTopLeftCorner = RectF(xStartRadius, yStartRadius, xEndRadius, yEndRadius)
            arcTo(rectTopLeftCorner, 180f, 90f)

            // draw radius topRight, square size by [borderRadiusBoxMessage]
            var standardRightXBoxMessage =
                standardLeftXBoxMessage + widthOfTextMoneyA + paddingHorizontalBoxMessage * 2

            if (standardRightXBoxMessage > width - marginHorizontalBoxMessage) {
                standardRightXBoxMessage = (width - marginHorizontalBoxMessage).toFloat()
            }

            standardRightXBoxMessageA = standardRightXBoxMessage

            xStartRadius = standardRightXBoxMessage - borderRadiusBoxMessage
            xEndRadius = standardRightXBoxMessage
            lineTo(xStartRadius, yStartRadius)
            val rectTopRightCorner = RectF(xStartRadius, yStartRadius, xEndRadius, yEndRadius)
            arcTo(rectTopRightCorner, 270f, 90f)

            // draw radius bottomRight, square size by [borderRadiusBoxMessage]

            yStartRadius = fromYOfMoneyAText + staticLayoutMoneyTextBarA.height
            yEndRadius = yStartRadius + borderRadiusBoxMessage
            lineTo(standardRightXBoxMessage, yStartRadius)
            val rectBottomRightCorner =
                RectF(xStartRadius, yStartRadius, xEndRadius, yEndRadius)
            arcTo(rectBottomRightCorner, 0f, 90f)

            // Close path
            lineTo((midXOfBarA + widthArrow).toFloat(), yEndRadius)
        }
    }

    private fun initPathBoxB() {
        pathBoxBShapeInside = Path().apply {
            /* Vẽ mũi tên */
            moveTo(
                midXOfBarB.toFloat(),
                fromYOfMoneyBText + staticLayoutMoneyTextBarB.height + paddingVerticalBoxMessage + heightArrow
            )
            lineTo(
                midXOfBarB.toFloat() - widthArrow,
                fromYOfMoneyBText + staticLayoutMoneyTextBarB.height + paddingVerticalBoxMessage
            )
            moveTo(
                midXOfBarB.toFloat(),
                fromYOfMoneyBText + staticLayoutMoneyTextBarB.height + paddingVerticalBoxMessage + heightArrow
            )
            lineTo(
                midXOfBarB.toFloat() + widthArrow,
                fromYOfMoneyBText + staticLayoutMoneyTextBarB.height + paddingVerticalBoxMessage
            )

            var additionSpace = 0f
            var standardRightXBoxMessageB =
                midXOfBarB.toFloat() + halfWidthTextMoneyB + paddingHorizontalBoxMessage
            if (standardRightXBoxMessageB > (width - marginHorizontalBoxMessage).toFloat()) {
                additionSpace =
                    standardRightXBoxMessageB - (width - marginHorizontalBoxMessage).toFloat()
                standardRightXBoxMessageB = (width - marginHorizontalBoxMessage).toFloat()
            }

            // draw radius bottom right, square size by [borderRadiusBoxMessage]
            var xStartRadius = standardRightXBoxMessageB - borderRadiusBoxMessage
            var yEndRadius =
                fromYOfMoneyBText + staticLayoutMoneyTextBarB.height + paddingVerticalBoxMessage
            lineTo(xStartRadius, yEndRadius)
            var xEndRadius = standardRightXBoxMessageB
            var yStartRadius = yEndRadius - borderRadiusBoxMessage
            val rectBottomRightCorner =
                RectF(xStartRadius, yStartRadius, xEndRadius, yEndRadius)
            arcTo(rectBottomRightCorner, 90f, -90f)

            // draw radius top right, square size by [borderRadiusBoxMessage]
            yStartRadius =
                yStartRadius - staticLayoutMoneyTextBarB.height - borderRadiusBoxMessage
            yEndRadius = yStartRadius + borderRadiusBoxMessage
            lineTo(xEndRadius, yEndRadius)
            val rectTopRightCorner = RectF(xStartRadius, yStartRadius, xEndRadius, yEndRadius)
            arcTo(rectTopRightCorner, 0f, -90f)

            // draw radius top left, square size by [borderRadiusBoxMessage]
            var standardLeftXBoxMessage =
                midXOfBarB.toFloat() - halfWidthTextMoneyB - paddingHorizontalBoxMessage - additionSpace
            if (standardLeftXBoxMessage < marginHorizontalBoxMessage) {
                standardLeftXBoxMessage = marginHorizontalBoxMessage.toFloat()
            }
            standardLeftXBoxMessageB = standardLeftXBoxMessage
            xStartRadius = standardLeftXBoxMessage
            xEndRadius = standardLeftXBoxMessage + borderRadiusBoxMessage
            val rectTopLeftCorner = RectF(xStartRadius, yStartRadius, xEndRadius, yEndRadius)
            arcTo(rectTopLeftCorner, 270f, -90f)

            // draw radius bottom left, square size by [borderRadiusBoxMessage]
            yEndRadius =
                fromYOfMoneyBText + staticLayoutMoneyTextBarB.height + paddingVerticalBoxMessage
            yStartRadius = yEndRadius - borderRadiusBoxMessage
            val rectBottomLeftCorner = RectF(xStartRadius, yStartRadius, xEndRadius, yEndRadius)
            arcTo(rectBottomLeftCorner, 180f, -90f)

            // Close path
            lineTo((midXOfBarB - widthArrow).toFloat(), yEndRadius)

            xStartTextMoneyB = standardLeftXBoxMessage + paddingHorizontalBoxMessage
        }

        pathBoxBShapeOutside = Path().apply {

            /* Vẽ mũi tên */
            moveTo(
                midXOfBarB.toFloat(),
                fromYOfMoneyBText + staticLayoutMoneyTextBarB.height + paddingVerticalBoxMessage + heightArrow
            )
            lineTo(
                midXOfBarB.toFloat() - widthArrow,
                fromYOfMoneyBText + staticLayoutMoneyTextBarB.height + paddingVerticalBoxMessage
            )
            moveTo(
                midXOfBarB.toFloat(),
                fromYOfMoneyBText + staticLayoutMoneyTextBarB.height + paddingVerticalBoxMessage + heightArrow
            )
            lineTo(
                midXOfBarB.toFloat() + widthArrow,
                fromYOfMoneyBText + staticLayoutMoneyTextBarB.height + paddingVerticalBoxMessage
            )

            var additionSpace = 0f
            var standardRightXBoxMessage =
                midXOfBarB.toFloat() + halfWidthTextMoneyB + paddingHorizontalBoxMessage
            if (standardRightXBoxMessage > (width - marginHorizontalBoxMessage).toFloat()) {
                additionSpace =
                    standardRightXBoxMessage - (width - marginHorizontalBoxMessage).toFloat()
                standardRightXBoxMessage = (width - marginHorizontalBoxMessage).toFloat()
            }

            // draw radius bottom right, square size by [borderRadiusBoxMessage]
            var xStartRadius = standardRightXBoxMessage - borderRadiusBoxMessage
            var yEndRadius =
                fromYOfMoneyBText + staticLayoutMoneyTextBarB.height + paddingVerticalBoxMessage
            lineTo(xStartRadius, yEndRadius)
            var xEndRadius = standardRightXBoxMessage
            var yStartRadius = yEndRadius - borderRadiusBoxMessage
            val rectBottomRightCorner =
                RectF(xStartRadius, yStartRadius, xEndRadius, yEndRadius)
            arcTo(rectBottomRightCorner, 90f, -90f)

            // draw radius top right, square size by [borderRadiusBoxMessage]
            yStartRadius =
                yStartRadius - staticLayoutMoneyTextBarB.height - borderRadiusBoxMessage
            yEndRadius = yStartRadius + borderRadiusBoxMessage
            lineTo(xEndRadius, yEndRadius)
            val rectTopRightCorner = RectF(xStartRadius, yStartRadius, xEndRadius, yEndRadius)
            arcTo(rectTopRightCorner, 0f, -90f)

            // draw radius top left, square size by [borderRadiusBoxMessage]
            var standardLeftXBoxMessage =
                midXOfBarB.toFloat() - halfWidthTextMoneyB - paddingHorizontalBoxMessage - additionSpace
            if (standardLeftXBoxMessage < marginHorizontalBoxMessage) {
                standardLeftXBoxMessage = marginHorizontalBoxMessage.toFloat()
            }
            xStartRadius = standardLeftXBoxMessage
            xEndRadius = standardLeftXBoxMessage + borderRadiusBoxMessage
            val rectTopLeftCorner = RectF(xStartRadius, yStartRadius, xEndRadius, yEndRadius)
            arcTo(rectTopLeftCorner, 270f, -90f)

            // draw radius bottom left, square size by [borderRadiusBoxMessage]
            yEndRadius =
                fromYOfMoneyBText + staticLayoutMoneyTextBarB.height + paddingVerticalBoxMessage
            yStartRadius = yEndRadius - borderRadiusBoxMessage
            val rectBottomLeftCorner = RectF(xStartRadius, yStartRadius, xEndRadius, yEndRadius)
            arcTo(rectBottomLeftCorner, 180f, -90f)

            // Close path
            lineTo((midXOfBarB - widthArrow).toFloat(), yEndRadius)
        }
    }

    private fun onDrawBoxMoney(canvas: Canvas) {
        canvas.apply {
            /*
            *  Draw money Bar A
            * */
            initPathBoxA()
            drawPath(pathBoxAShapeOutside, borderBoxTextPaint)
            drawPath(pathBoxAShapeInside, boxTextPaint)
            save()
            translate(xStartTextMoneyA, fromYOfMoneyAText)
            staticLayoutMoneyTextBarA.draw(canvas)
            restore()
            /*
            *  Draw money Bar B
            * */
            initPathBoxB()
            drawPath(pathBoxBShapeOutside, borderBoxTextPaint)
            drawPath(pathBoxBShapeInside, boxTextPaint)
            save()
            translate(xStartTextMoneyB, fromYOfMoneyBText)
            staticLayoutMoneyTextBarB.draw(canvas)
            restore()
        }
    }

    fun reset() {
        heightYa = 0.0
        heightYb = 0.0
        maxHeightOfBar = 0.0
        heightOfBoxMessageContainer = 0.0

        heightOfContainerBoxMessageA = 0.0
        midXOfBarA = 0.0
        widthOfTextMoneyA = 0f
        halfWidthTextMoneyA = 0f
        xStartTextMoneyA = 0f
        fromYOfMoneyAText = 0f
        standardRightXBoxMessageA = 0f

        heightOfContainerBoxMessageB = 0.0
        midXOfBarB = 0.0
        widthOfTextMoneyB = 0f
        halfWidthTextMoneyB = 0f
        xStartTextMoneyB = 0f
        fromYOfMoneyBText = 0f
        standardLeftXBoxMessageB = 0f
        fourOnTenX = 0.0
        widthOfBar = 0.0

        spaceBetween2Bar = 0.0
        fromX = 0.0
        leftBarA = 0.0
        rightBarA = 0.0
        leftBarB = 0.0
        rightBarB = 0.0
        heightOfContainerViewTitle = 0.0
    }
}

val Int.pxToDp: Int get() = (this / getSystem().displayMetrics.density).toInt()

val Int.dpToPx: Int get() = (this * getSystem().displayMetrics.density).toInt()

val Int.spToPx: Float get() = (this * getSystem().displayMetrics.scaledDensity)

val Int.pxToSp: Float get() = (this / getSystem().displayMetrics.scaledDensity)