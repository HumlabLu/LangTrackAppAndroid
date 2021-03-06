package se.lu.humlab.langtrackapp.screen.overview

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import se.lu.humlab.langtrackapp.data.RepositoryFactory

class OverviewViewModelFactory(var context: Context): ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>):
            T = OverviewViewModel(RepositoryFactory.getRepository(context)) as T
}