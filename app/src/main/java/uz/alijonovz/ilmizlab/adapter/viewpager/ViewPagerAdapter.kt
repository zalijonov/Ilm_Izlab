package uz.alijonovz.ilmizlab.adapter.viewpager

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import uz.alijonovz.ilmizlab.R
import uz.alijonovz.ilmizlab.model.OfferModel
import uz.alijonovz.ilmizlab.utils.Constants
import java.util.*

class ViewPagerAdapter(val context: Context, var items: List<OfferModel>) : PagerAdapter() {
    override fun getCount(): Int {
        return items.count()
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object` as RelativeLayout
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val mLayoutInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val itemView: View = mLayoutInflater.inflate(R.layout.image_slider_item, container, false)

        Glide.with(context).load(Constants.IMAGE_URL + items[position].image)
            .into(itemView.findViewById(R.id.tvImage))

        Objects.requireNonNull(container).addView(itemView)

        return itemView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as RelativeLayout)
    }
}

class ViewPagerAdapterC(val context: Context, var items: MutableList<String>) : PagerAdapter() {
    override fun getCount(): Int {
        return items.count()
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object` as RelativeLayout
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val mLayoutInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val itemView: View = mLayoutInflater.inflate(R.layout.image_slider_item, container, false)

        Glide.with(context).load(Constants.IMAGE_URL + items[position])
            .into(itemView.findViewById(R.id.tvImage))

        Objects.requireNonNull(container).addView(itemView)

        return itemView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as RelativeLayout)
    }
}