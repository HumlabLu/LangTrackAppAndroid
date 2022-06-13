package se.lu.humlab.langtrackapp.screen.about

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import se.lu.humlab.langtrackapp.data.RepositoryFactory

class TeamViewModelFactory(var context: Context): ViewModelProvider.Factory {

    val mTeamViewModel =  TeamViewModel(RepositoryFactory.getRepository(context))

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = TeamViewModel(RepositoryFactory.getRepository(context)) as T
}