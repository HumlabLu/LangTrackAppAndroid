package se.lu.humlab.langtrackapp.interfaces

/*
* Stephan Björck
* Humanistlaboratoriet
* Lunds Universitet
* stephan.bjorck@humlab.lu.se
* */

import se.lu.humlab.langtrackapp.data.model.Question

interface OnLikertScalesItemClickedListener {

    fun goToNextItem()
    fun goToPrevoiusItem()
}