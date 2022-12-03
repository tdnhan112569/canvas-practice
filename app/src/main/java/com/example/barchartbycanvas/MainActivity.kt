package com.example.barchartbycanvas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    override fun onResume() {
        super.onResume()
//        val barchart = this.findViewById<BarChartView>(R.id.barchart)
//        val pieChart = this.findViewById<PieChartView>(R.id.pieChart)
//        val progressBar = this.findViewById<ProgressBarSpending>(R.id.progressBar)
//        progressBar.setCategory(ProgressBarSpending.Category("2", 60.0))
//        Handler(Looper.getMainLooper()).postDelayed({
//            pieChart.invalidate()
//            barchart.invalidate()
//            progressBar.setCategory(ProgressBarSpending.Category("10", 20.0))
//                                                    }, 3000)

    }
}