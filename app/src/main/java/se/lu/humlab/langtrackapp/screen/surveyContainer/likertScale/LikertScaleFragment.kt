package se.lu.humlab.langtrackapp.screen.surveyContainer.likertScale

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import se.lu.humlab.langtrackapp.R
import se.lu.humlab.langtrackapp.data.model.Question
import se.lu.humlab.langtrackapp.databinding.HeaderFragmentBinding
import se.lu.humlab.langtrackapp.databinding.LikertScaleFragmentBinding
import se.lu.humlab.langtrackapp.interfaces.OnLikertScaleInteraktionListener

class LikertScaleFragment : Fragment(){

    private var listener: OnLikertScaleInteraktionListener? = null
    lateinit var binding: LikertScaleFragmentBinding
    lateinit var question: Question

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
        binding.likertScaleNextButton.setOnClickListener {
            listener?.likertScaleGoToNextItem(currentQuestion = question)
        }
        binding.likertScaleBackButton.setOnClickListener {
            listener?.likertScaleGoToPrevoiusItem(currentQuestion = question)
        }
        return v
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnLikertScaleInteraktionListener) {
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
        binding.textView9.text = "Här kommer texten:\n\n${question.title}\n${question.text}"
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
        fun newInstance(question: Question) =
            LikertScaleFragment().apply {
                this.question = question
            }
    }
}