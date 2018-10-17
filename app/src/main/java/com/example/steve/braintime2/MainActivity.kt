package com.example.steve.braintime2

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {
    var cDT : CountDownTimer? = null
    var userAnswer = 0
    var trueAnswer = 0
    var score = 0
    var isStart = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun start(view: View){
        if (!isStart){
            time(30)
            startBtn.text = "Stop"
            isStart = true
            start1Game()
        }else{
            cDT?.cancel()
            startBtn.text = "Start"
            isStart = false
        }
    }

    fun start1Game(){
        val ansBtn = genPos()
        val ansPos = genAns()
        val trueAnsBtn = findViewById<Button>(ansBtn[0])
        val ansBtn1 = findViewById<Button>(ansBtn[1])
        val ansBtn2 = findViewById<Button>(ansBtn[2])
        val ansBtn3 = findViewById<Button>(ansBtn[3])
        scoreView.text = score.toString()
        questionView.text = ansPos[0].toString() + " + " + ansPos[1].toString()
        trueAnsBtn.text = ansPos[2].toString()
        ansBtn1.text = ansPos[3].toString()
        ansBtn2.text = ansPos[4].toString()
        ansBtn3.text = ansPos[5].toString()
        trueAnswer = ansPos[2]
    }

    fun IntRange.random() =
            Random().nextInt((endInclusive + 1) - start) +  start

    fun genAns() : MutableList<Int>{   // trả về mảng chứa 2 số của câu hỏi và 4 câu trả lời, câu trả lời đúng nằm ở vị trí thứ 3
        val random = Random()
        val Quest = createQ()
        val ans1 = Quest[0] + random.nextInt(10) + Quest[1] + random.nextInt(10)
        val ans2 = Quest[0] + random.nextInt(10) + Quest[1] + random.nextInt(10)
        val ans3 = random.nextInt(30)
        val arrQuestAndAns : MutableList<Int> = mutableListOf(Quest[0], Quest[1], Quest[2], ans1, ans2, ans3)
        return arrQuestAndAns
    }

    fun genPos() : MutableList<Int>{   // trả về mảng chứa 4 id, id của thằng chứa câu đúng sẽ ở position 0
        val random = Random()
        var arrAnsBtn : MutableList<Int> = mutableListOf<Int>(answer1.id, answer2.id, answer3.id, answer4.id)
        val ranPosition = (0..3).random()
        val ansPos = arrAnsBtn[ranPosition]
        arrAnsBtn.removeAt(ranPosition)
        arrAnsBtn.add(0, ansPos)
        return arrAnsBtn
    }

    fun compareAns(userAns : Int, trueAns : Int) : Boolean{
        return userAns == trueAns
    }

    fun choose(view: View){
        val ansChooseBtn = findViewById<Button>(view.id).text
        userAnswer = ansChooseBtn.toString().toInt()
        val compare = compareAns(userAnswer, trueAnswer)
        if (compare){
            resultView.text = "Exactly"
            score += 1
            start1Game()
        }else{
            resultView.text = "Wrong"
            start1Game()
        }
    }

    fun createQ() : IntArray {
        val random = Random()
        val intA = random.nextInt(30)
        val intB = random.nextInt(30)
        val res = intA + intB
        val arr : IntArray = intArrayOf(intA, intB, res)
        return arr
    }

    fun time(progress: Int){
        cDT = object : CountDownTimer(progress.toLong()*1000 + 100, 1000){
            override fun onTick(millisUntilFinished: Long) {
                val secs = (millisUntilFinished/1000).toInt()
                val mins = secs/60
                if (secs < 9){
                    timeView.text = "0 : 0$secs"
                }else{
                    timeView.text = "${mins} : ${secs - mins*60}"
                }
                Log.i("second...", millisUntilFinished.toString())
            }
            override fun onFinish() {
                Toast.makeText(applicationContext, "Ring Ring Ring", Toast.LENGTH_SHORT).show()
                reset()
                Log.i("Second", "Done")
            }
        }.start()
    }

    fun hideAll(){
//        goBtn.visibility = View.VISIBLE
        timeView.visibility = View.INVISIBLE
        resultView.visibility = View.INVISIBLE
        scoreView.visibility = View.INVISIBLE
        questionView.visibility = View.INVISIBLE
        answer1.visibility = View.INVISIBLE
        answer2.visibility = View.INVISIBLE
        answer3.visibility = View.INVISIBLE
        answer4.visibility = View.INVISIBLE
    }

    fun reset(){
//        goBtn.visibility = View.INVISIBLE
        timeView.visibility = View.VISIBLE
        resultView.visibility = View.VISIBLE
        scoreView.visibility = View.VISIBLE
        questionView.visibility = View.VISIBLE
        answer1.visibility = View.VISIBLE
        answer2.visibility = View.VISIBLE
        answer3.visibility = View.VISIBLE
        answer4.visibility = View.VISIBLE
    }

}
