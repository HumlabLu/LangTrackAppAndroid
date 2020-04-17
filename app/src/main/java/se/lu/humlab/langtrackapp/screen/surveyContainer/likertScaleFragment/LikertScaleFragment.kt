package se.lu.humlab.langtrackapp.screen.surveyContainer.likertScaleFragment

/*
* Stephan Björck
* Humanistlaboratoriet
* Lunds Universitet
* stephan.bjorck@humlab.lu.se
* */

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.likert_scale_fragment.*
import kotlinx.android.synthetic.main.likert_scale_fragment.view.*
import kotlinx.android.synthetic.main.likert_scale_fragment.view.likertScaleRadioGroup
import se.lu.humlab.langtrackapp.R
import se.lu.humlab.langtrackapp.data.model.Answer
import se.lu.humlab.langtrackapp.data.model.Question
import se.lu.humlab.langtrackapp.databinding.LikertScaleFragmentBinding
import se.lu.humlab.langtrackapp.interfaces.OnQuestionInteractionListener

class LikertScaleFragment : Fragment(){

    private var listener: OnQuestionInteractionListener? = null
    lateinit var binding: LikertScaleFragmentBinding
    lateinit var question: Question
    var theAnswer: Answer? = null
    var selectedRadioButton = 3

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //return super.onCreateView(inflater, container, savedInstanceState)
        binding = DataBindingUtil.inflate(inflater, R.layout.likert_scale_fragment, container,false)
        binding.setLifecycleOwner(this)
        binding.executePendingBindings()
        val v = binding.root
        v.likertScaleNextButton.setOnClickListener {

            listener?.nextQuestion(question)
            theAnswer = null
            /*if (question.skip != null){
                if (question.skip?.ifChosen == selectedRadioButton){
                    listener?.goToNextItemWithSkipLogic(question)
                }else listener?.nextItem(current = question)
            }else listener?.nextItem(current = question)*/
        }
        v.likertScaleBackButton.setOnClickListener {
            listener?.prevoiusQuestion(current = question)
            theAnswer = null
        }
        v.likertScaleRadioGroup.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId != -1) {
                val radio: RadioButton = v.findViewById(checkedId)
                selectedRadioButton = (radio.tag as String).toInt()
                listener?.setLikertAnswer(selectedRadioButton)
            }
        }
        return v
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnQuestionInteractionListener) {
            listener = context
            if (::binding.isInitialized) {
                //load survey
                setQuestion()
            }
        }else {
            throw RuntimeException(context.toString() + " must implement OnLikertScaleInteraktionListener")
        }
    }

    fun setQuestion(){
        if (::binding.isInitialized) {
            binding.likertScaleRadioGroup.clearCheck()
            binding.likertScaleDescriptionTextView.text = question.description
            binding.likertScaleTextTextView.text = question.text
            if (theAnswer != null) {
                if (theAnswer!!.likertAnswer != null){
                    if (theAnswer!!.index == question.index){
                        val theRadioButton = when (theAnswer?.likertAnswer){
                            0 -> binding.likertScaleRadioButton1.id
                            1 -> binding.likertScaleRadioButton2.id
                            2 -> binding.likertScaleRadioButton3.id
                            3 -> binding.likertScaleRadioButton4.id
                            4 -> binding.likertScaleRadioButton5.id
                            else -> null
                        }
                        if (theRadioButton != null) {
                            binding.likertScaleRadioGroup.check(theRadioButton)
                        }
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        //update question
        setQuestion()
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            LikertScaleFragment().apply {

            }
    }
}