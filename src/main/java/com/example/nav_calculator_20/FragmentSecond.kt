package com.example.nav_calculator_20

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.OnBackPressedCallback

enum class CalculateFun(val value:String){
    ADD("add"),
    SUB("sub"),
    DIV("div"),
    MUL("mul")

}
class FragmentSecond : Fragment() {
    private var viewForRestore:View?=null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val view=inflater.inflate(R.layout.fragment_second, container, false)
        viewForRestore=view
        onRestore(savedInstanceState,view)
        clickResultButton(view)
        onBackPressed()
        return view
    }

    private fun onRestore(savedInstanceState: Bundle?,view:View){
        val cal=view.findViewById<Button>(R.id.cal_button)
        if(savedInstanceState!=null){
            cal.text=savedInstanceState.getString("button")
        }else{
            parentFragmentManager.setFragmentResultListener("fragment1",this){requestKey,bundle->
                val result=bundle.getString("key")
                cal.text=result
            }
        }
    }
    private fun onBackPressed(){
        val callback=object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                val a=activity?.supportFragmentManager?.beginTransaction()
                a?.replace(R.id.container,FragmentFirst(),"fragment1a")
                a?.addToBackStack(null)?.commit()
                Log.i("fragment2$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$", "handleOnBackPressed: ")
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(callback)
    }
    private fun clickResultButton(view:View){
        val cal=view.findViewById<Button>(R.id.cal_button)
        cal.setOnClickListener {
            val editText=view.findViewById<EditText>(R.id.edittext_id).text.toString()
            val editText1=view.findViewById<EditText>(R.id.edittext_id1).text.toString()
            if(editText.isNotEmpty() && editText1.isNotEmpty()){
                val number1= Integer.parseInt(editText)
                val number2= Integer.parseInt(editText1)
                val result = calculate(number1,number2,cal)
                val bundle = Bundle()
                val resultTemplate="Number1   : $number1\nNumber2  : $number2"
                bundle.putString("result", "$resultTemplate\nResult      : $result")
                if(cal.text.equals(CalculateFun.DIV.value) && result==-1)
                    bundle.putString("result","$resultTemplate\nCan not be Divided by Zero")
                parentFragmentManager.setFragmentResult("fragment2",bundle)
            }
            else{
                val text = "Invalid Inputs"
                val duration = Toast.LENGTH_SHORT
                val toast = Toast.makeText(context, text, duration)
                toast.show()
            }
            parentFragmentManager.beginTransaction().remove(this)
            val a=activity?.supportFragmentManager?.beginTransaction()
            a?.replace(R.id.container,FragmentFirst(),"fragment1a")
            a?.addToBackStack(null)?.commit()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("button",viewForRestore?.findViewById<Button>(R.id.cal_button)?.text.toString())
    }

    private fun calculate(num1:Int,num2:Int,calculate:Button):Int{
        when(calculate.text){
            CalculateFun.ADD.value-> return num1+num2
            CalculateFun.SUB.value-> return num1-num2
            CalculateFun.DIV.value-> {
                if(num2==0)return -1
                else return num1/num2
            }
            CalculateFun.MUL.value->return num1*num2
        }
        return 0
    }
}