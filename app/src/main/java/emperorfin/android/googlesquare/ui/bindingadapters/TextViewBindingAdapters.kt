package emperorfin.android.googlesquare.ui.bindingadapters

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import emperorfin.android.googlesquare.R
import emperorfin.android.googlesquare.ui.screens.venuesoverview.enums.VenuesRequestStatus


/**
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Monday 07th March, 2022.
 */



@BindingAdapter("bindVenueCategory")
fun setVenueCategory(textView: TextView, category: String?){
    category?.let {
        if (it.isEmpty()){
            textView.text = textView.context.getString(R.string.message_no_category)
        }else{
            textView.text = category
        }
    }
}

@BindingAdapter("bindVenueDistance")
fun setVenueDistance(textView: TextView, distance: String?){
    distance?.let {
        textView.text = "$distance meters"
    }
}

@BindingAdapter("bindVenuesRequestStatusText")
fun setVenuesRequestStatusText(textView: TextView, requestStatus: VenuesRequestStatus?){
    requestStatus?.let {
        when(it){
            VenuesRequestStatus.LOADING -> {
                textView.visibility = View.VISIBLE
                textView.text = textView.context.getString(R.string.loading)
            }
            VenuesRequestStatus.ERROR -> {
                textView.visibility = View.VISIBLE
                textView.text = textView.context.getString(R.string.error_displaying_location_results)
            }
            VenuesRequestStatus.NO_DATA -> {
                textView.visibility = View.VISIBLE
                textView.text = textView.context.getString(R.string.search_glyph_text)
            }
            VenuesRequestStatus.DONE -> {
                textView.visibility = View.GONE
            }
        }
    }
}