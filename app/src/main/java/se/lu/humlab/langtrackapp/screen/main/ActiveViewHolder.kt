package se.lu.humlab.langtrackapp.screen.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import se.lu.humlab.langtrackapp.R
import se.lu.humlab.langtrackapp.data.model.Assignment
import se.lu.humlab.langtrackapp.interfaces.OnSurveyRowClickedListener
import se.lu.humlab.langtrackapp.util.formatToReadable
import se.lu.humlab.langtrackapp.util.toDate

class ActiveViewHolder(theItemView: View,
                       onRowClickedListener: OnSurveyRowClickedListener
): RecyclerView.ViewHolder(theItemView) {

    private var task: TextView = itemView.findViewById(R.id.surveyRecyclerTitleTextView)
    private var date: TextView = itemView.findViewById(R.id.surveyRecyclerDateTextView)
    private var activeIndicator: ImageView = itemView.findViewById(R.id.activeIndicator)
    private var cellLayout: ConstraintLayout = itemView.findViewById(R.id.surveyCellLayout)
    private lateinit var item: Assignment

    init {
        theItemView.setOnClickListener { onRowClickedListener.rowClicked(item) }
    }

    fun bind(item: Assignment, pos: Int){
        this.item = item
        task.text = "Aktiv!!"
        if (item.isActive()){
            activeIndicator.visibility = View.VISIBLE
            date.text = "53 minuter kvar"//TODO: calculate time left
        }else{
            activeIndicator.visibility = View.GONE
            date.text = "Inaktiv, ${if (item.dataset != null) "besvarad" else "obesvarad"}"
        }
        date.text = item.publishAt.toDate()?.formatToReadable() ?: "noDate"
    }

    fun getItem(): Assignment {
        return item
    }

    /*private fun getDate(milli: Long): String{
        val formatter = SimpleDateFormat("dd MMMM yyyy    HH:mm",
            Locale("sv", "SE"))
        val calendar = Calendar.getInstance();
        calendar.timeInMillis = milli * 1000//TODO: temp, should be in milli
        return formatter.format(calendar.time)
    }*/


    companion object {
        fun newInstance(parent: ViewGroup,
                        onRowClickedListener: OnSurveyRowClickedListener
        ):ActiveViewHolder{
            return ActiveViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.recycle_surveyitem_row,
                    parent,
                    false
                ),
                onRowClickedListener
            )
        }
    }
}