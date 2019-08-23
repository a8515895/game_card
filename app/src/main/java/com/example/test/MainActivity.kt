package com.example.test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import kotlin.random.Random
import android.widget.LinearLayout
import android.app.ActionBar.LayoutParams
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import android.view.Menu
import android.widget.Toast
import java.util.Timer
import kotlin.concurrent.schedule
import android.os.CountDownTimer
import android.widget.TextView
import androidx.core.view.ViewCompat

class MainActivity : AppCompatActivity() {
    protected val arrImg = ArrayList<String>()
    var arrRandomImg = ArrayList<String>()
    var _result = ArrayList<Int>()
    var _clicked : Boolean = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()

    }
    fun clickImg(view : View){
        if(!_clicked){
            return;
        }
        val img : ImageView = view as ImageView
        val imgName = img.tag
        val imgId = getResources().getIdentifier(imgName.toString(),"drawable",packageName)
        img.setImageResource(imgId)
        _result.add(img.id);
        checkResult()
    }
    fun checkResult(){
        var img : ImageView;
        var check : Boolean = false;
        var r : ArrayList<String> = ArrayList();

        if(_result.size == 2) {
            _clicked = false;
            for (x in 0 until _result.size) {
                img = findViewById(_result[x]);
                r.add(x,img.tag.toString())
            }
            if(r[0] == r[1]) check = true;
            else check = false;
            val timer = object: CountDownTimer(1000, 1000) {
                override fun onTick(p0: Long) {

                }
                override fun onFinish() {
                    for (x in 0 until _result.size) {
                        img = findViewById(_result[x]);
                        if(check){
                            img.visibility = View.INVISIBLE
                        }else{
                            img.setImageResource(R.drawable.magic)
                        }
                        _clicked = true;

                    }
                    for (x in 0 until _result.size) {
                        _result.removeAt(0)
                    }
                }
            }
            timer.start()
//            Timer("SettingUp", false).schedule(1000) {
//                img = findViewById(_result[0]);
//                img.setImageResource(R.drawable.magic)
//                for (x in 0 until _result.size) {
//                    img = findViewById(_result[x]);
//                    Log.d("ID",img.tag.toString())
//                    img.setImageResource(R.drawable.magic)
//
//                }
//            }

        }
    }
    fun init(){
        var parents : LinearLayout;
        _randomImage(6)
        initBgGame()
        createTimeView()
        for (x in 0 until 2){
            parents = LinearLayout(this@MainActivity);
            parents.setLayoutParams(LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
            parents.setOrientation(LinearLayout.HORIZONTAL);
            createImageView(parents,x)
            linearLayoutContentImg.addView(parents)
        }
    }
    fun createTimeView(){
        var tv : TextView = TextView(this)
        tv.text = "1 : 00"
        tv.id = R.id.tv
        tv.textAlignment = View.TEXT_ALIGNMENT_CENTER
        linearLayoutContentImg.addView(tv)
    }
    fun restartLayout(v : View){
        linearLayoutContentImg.removeAllViews()
        init()
    }
    fun initImg(){
        arrImg.add("nobita")
        arrImg.add("nobita")
        arrImg.add("xuka")
        arrImg.add("xuka")
        arrImg.add("doreamon")
        arrImg.add("doreamon")
    }
    fun initBgGame(){
        val ran : Random = Random
        val i = ran.nextInt(1,10)
        val imgId = getResources().getIdentifier("g"+i,"drawable",packageName)
        linearLayoutContentImg.setBackgroundResource(imgId)
    }
    fun createImageView(layout : LinearLayout,index : Int){
        var img : ImageView
        var untilInt : Int = 3
        for (x in 0 until untilInt){
            img = ImageView(this@MainActivity)
            img.setOnClickListener({ v -> clickImg(v) })
            img.setImageResource(R.drawable.magic)
            img.id = index * untilInt + x;
            img.tag = arrRandomImg[index * untilInt + x];
            img.layoutParams = LinearLayout.LayoutParams(150, 250,1.0f);
            img.requestLayout()
            img.setPadding(0,5,0,5)
            layout.addView(img)
        }
    }
    fun _randomImage(length : Int) : ArrayList<String>{
        var arr : ArrayList<Int> = ArrayList();
        var ra : Random = Random;
        var i : Int = 0;
        initImg()
        while (arr.size < length){
            i = ra.nextInt(length)
            if(!isDuplicate(i,arr)){
                arr.add(i)
                arrRandomImg.add(i.toString())
            }
        }
        for(x in 0 until length){
            arrRandomImg.set(arr[x],arrImg[x])
        }
        return arrRandomImg;
    }
    fun isDuplicate(num : Int,arr : ArrayList<Int>) : Boolean{
        var status : Boolean = false;
        if(arr.size > 0){
            for(x in 0 until arr.size){
                if(arr[x] == num){
                    status = true;
                    break;
                }
            }
        }
        return  status;
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main,menu)
        return super.onCreateOptionsMenu(menu)
    }
}
