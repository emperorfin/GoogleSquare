package emperorfin.android.googlesquare.ui.bindingadapters

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import emperorfin.android.googlesquare.BuildConfig
import emperorfin.android.googlesquare.R
import emperorfin.android.googlesquare.ui.screens.venuesoverview.enums.VenuesRequestStatus
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.request.RequestOptions


/**
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Monday 07th March, 2022.
 */



@BindingAdapter(value = ["bindVenueIconUrl", "bindImageWidth", "bindImageHeight"], requireAll = false)
fun setVenueIconUrl(imageView: ImageView, iconUrl: String?, iconWidth: Int?, iconHeight: Int?){
    iconUrl?.let {
//        val imgUri = imageUrl.toUri().buildUpon().scheme("https").build()

        val glideUrl = GlideUrl(iconUrl){
            mapOf(Pair("Authorization", "Bearer ${BuildConfig.API_KEY}"))
        }

        Glide.with(imageView.context)
            .load(if(iconUrl.contains("http")) iconUrl else R.mipmap.ic_launcher)
            // This is not necessary since the FourSquare webservice doesn't require authorization
            // to display images.
            //.load(glideUrl)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.loading_animation)
                    .error(R.drawable.ic_broken_image)
                    .apply {
                        if(iconWidth != null && iconHeight != null)
                            override(iconWidth, iconHeight)
                        else if(iconWidth == null && iconHeight == null)
                        //Do not set override(..., ...)
                        else if(iconWidth == null || iconHeight == null)
                            throw IllegalArgumentException(
                                imageView
                                    .context
                                    .getString(R.string.error_either_width_or_height_must_not_be_null)
                            )
                    })
            .into(imageView)
    }
}

@BindingAdapter("bindVenuesRequestStatusImage")
fun setVenuesRequestStatusImage(imageView: ImageView, requestStatus: VenuesRequestStatus?){
    requestStatus?.let {
        when(it){
            VenuesRequestStatus.LOADING -> {
                imageView.visibility = View.VISIBLE
                imageView.setImageResource(R.drawable.loading_animation)
            }
            VenuesRequestStatus.ERROR -> {
                imageView.visibility = View.VISIBLE
                imageView.setImageResource(R.drawable.ic_connection_error_no_data_black)
            }
            VenuesRequestStatus.NO_DATA -> {
                imageView.visibility = View.VISIBLE
                imageView.setImageResource(R.drawable.ic_search_glyph)
            }
            VenuesRequestStatus.DONE -> {
                imageView.visibility = View.GONE
            }
        }
    }
}