package se.lu.humlab.langtrackapp.screen.surveyContainer

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import se.lu.humlab.langtrackapp.data.RepositoryFactory

class SurveyContainerViewModelFactory(var context: Context): ViewModelProvider.Factory {

    private val mMainViewModel =  SurveyContainerViewModel(RepositoryFactory.getRepository(context))

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = mMainViewModel as T
}