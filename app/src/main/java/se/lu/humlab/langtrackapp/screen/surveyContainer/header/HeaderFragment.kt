package se.lu.humlab.langtrackapp.screen.surveyContainer.header

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
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.header_fragment.view.*
import se.lu.humlab.langtrackapp.R
import se.lu.humlab.langtrackapp.data.model.Question
import se.lu.humlab.langtrackapp.databinding.HeaderFragmentBinding
import se.lu.humlab.langtrackapp.interfaces.OnHeaderInteractionListener
import se.lu.humlab.langtrackapp.screen.surveyContainer.SurveyContainerViewModel
import se.lu.humlab.langtrackapp.screen.surveyContainer.SurveyContainerViewModelFactory

class HeaderFragment : Fragment(){

    private var listener: OnHeaderInteractionListener? = null
    lateinit var binding: HeaderFragmentBinding
    lateinit var question: Question
    private lateinit var viewModel : SurveyContainerViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //return super.onCreateView(inflater, container, savedInstanceState)
        binding = DataBindingUtil.inflate(inflater, R.layout.header_fragment, container,false)
        binding.setLifecycleOwner(this)
        binding.executePendingBindings()
        viewModel = ViewModelProviders.of(this,
            SurveyContainerViewModelFactory(binding.root.context)
        ).get(SurveyContainerViewModel::class.java)
        val v = binding.root
        v.headerCancelButton.setOnClickListener {
            listener?.headerCancelPressed(currentQuestion = question)
        }
        v.headerStartButton.setOnClickListener {
            listener?.headerGoToNextItem(currentQuestion = question)
        }
        return v
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnHeaderInteractionListener) {
            listener = context
            if (::binding.isInitialized) {
                //load survey
                setQuestion()
            }
        }else {
            throw RuntimeException(context.toString() + " must implement OnHeaderInteractionListener")
        }
    }

    fun setQuestion(){
        if (context is OnHeaderInteractionListener) {
            if (::binding.isInitialized) {
                val mAuth = FirebaseAuth.getInstance()
                binding.headerTitleTextView.text = question.title
                binding.headerTextTextView.setText(
                    getString(
                        R.string.headerGreetingText,
                        viewModel.getCurrentUser().userName,
                        question.text
                    )
                )
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
            HeaderFragment().apply {

            }
    }
}