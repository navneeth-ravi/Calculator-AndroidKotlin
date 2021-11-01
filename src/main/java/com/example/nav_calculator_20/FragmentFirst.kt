package com.example.nav_calculator_20

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment


class FragmentFirst : Fragment() {
    private var viewForRestore:View?=null
    var finish=true
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        finish=true
        val view=inflater.inflate(R.layout.fragment_first, container, false)
        viewForRestore=view
        view.findViewById<LinearLayout>(R.id.answerGrp).visibility=View.GONE
        onRestore(view,savedInstanceState)
        buttonUsage(view)
        setFragmentListener(view)
        onBackPressed(view)
        return view
    }
    private  fun onBackPressed(view:View){
        val answerGrp =view.findViewById<LinearLayout>(R.id.answerGrp)
        val callback = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                if(answerGrp.visibility==View.VISIBLE){
                    finish=!finish
                    val a=activity?.supportFragmentManager?.beginTransaction()
                    a?.replace(R.id.container,FragmentFirst(),"fragment1a")
                    a?.addToBackStack(null)?.commit()
                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(callback)
    }

    private fun onRestore(view:View,savedInstanceState: Bundle?){
//        ch=true
        val answerGrp =view.findViewById<LinearLayout>(R.id.answerGrp)
        val viewGrp = view.findViewById<LinearLayout>(R.id.viewGroup)
        val answerText=view.findViewById<TextView>(R.id.answer_id)
        if(savedInstanceState !=null){
            if(savedInstanceState.getInt("visible")==View.GONE){
                answerGrp.visibility=View.VISIBLE
                viewGrp.visibility=View.GONE
                answerText.text=savedInstanceState.getString("result")
            }
        }
    }

    private fun setFragmentListener(view:View){
        parentFragmentManager.setFragmentResultListener("fragment2",this){requestKey,bundle->
            val answerGrp =view.findViewById<LinearLayout>(R.id.answerGrp)
            val viewGrp = view.findViewById<LinearLayout>(R.id.viewGroup)
            val answerText=view.findViewById<TextView>(R.id.answer_id)
            viewGrp.visibility=View.GONE
            answerGrp.visibility=View.VISIBLE
            answerText.text=bundle.getString("result")
        }
    }

    private fun setFragmentForResult(bundle:Bundle){
        parentFragmentManager.setFragmentResult("fragment1",bundle)
        val a=activity?.supportFragmentManager?.beginTransaction()
        a?.replace(R.id.container,FragmentSecond(),"fragment2a")
        a?.addToBackStack(null)?.commit()
    }

    private fun buttonUsage(view:View){
        val answerGrp =view.findViewById<LinearLayout>(R.id.answerGrp)
        val viewGrp = view.findViewById<LinearLayout>(R.id.viewGroup)
        val addButton = view.findViewById<Button>(R.id.add)
        addButton.setOnClickListener {
            val bundle=Bundle()
            bundle.putString("key","add")
            setFragmentForResult(bundle)
        }
        view.findViewById<Button>(R.id.sub).setOnClickListener {
            val bundle=Bundle()
            bundle.putString("key","sub")
            setFragmentForResult(bundle)
        }
        view.findViewById<Button>(R.id.mul).setOnClickListener {
            val bundle=Bundle()
            bundle.putString("key","mul")
            setFragmentForResult(bundle)
        }
        view.findViewById<Button>(R.id.div).setOnClickListener {
            val bundle=Bundle()
            bundle.putString("key","div")
            setFragmentForResult(bundle)
        }
        val reset=view.findViewById<Button>(R.id.reset)
        reset.setOnClickListener {
            viewGrp.visibility=View.VISIBLE
            answerGrp.visibility=View.GONE
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        val answerText=viewForRestore?.findViewById<TextView>(R.id.answer_id)
        val visible=viewForRestore?.findViewById<LinearLayout>(R.id.viewGroup)
        outState.putInt("visible",if(visible?.visibility!=null) visible.visibility else -1)
        outState.putString("result",answerText?.text.toString())
        super.onSaveInstanceState(outState)

    }
}