package uz.alijonovz.ilmizlab.screen.center

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uz.alijonovz.ilmizlab.R
import uz.alijonovz.ilmizlab.adapter.viewpager.ViewPagerAdapterC
import uz.alijonovz.ilmizlab.adapter.viewpager.autoScroll
import uz.alijonovz.ilmizlab.api.ApiService
import uz.alijonovz.ilmizlab.databinding.ActivityCenterBinding
import uz.alijonovz.ilmizlab.model.BaseResponse
import uz.alijonovz.ilmizlab.model.center.CenterModel
import uz.alijonovz.ilmizlab.model.center.request.Subscribe
import uz.alijonovz.ilmizlab.screen.BaseActivity
import uz.alijonovz.ilmizlab.screen.MainViewModel
import uz.alijonovz.ilmizlab.screen.center.courses.CourseFragment
import uz.alijonovz.ilmizlab.screen.center.news.NewsCenterFragment
import uz.alijonovz.ilmizlab.screen.center.rating.RateActivity
import uz.alijonovz.ilmizlab.screen.center.rating.RatingFragment
import java.math.RoundingMode
import java.text.DecimalFormat
import java.util.*

class CenterActivity : BaseActivity<ActivityCenterBinding>() {
    lateinit var item: CenterModel
    lateinit var viewModel: MainViewModel
    var fragmentCourse = CourseFragment.newInstance()
    var fragmentNews = NewsCenterFragment.newInstance()
    var fragmentRating = RatingFragment.newInstance()
    var currentFragment: Fragment = fragmentCourse

    override fun getViewBinding(): ActivityCenterBinding {
        return ActivityCenterBinding.inflate(layoutInflater)
    }

    @SuppressLint("SetTextI18n")
    override fun initView() {
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.error.observe(this){
            binding.tvMessage.text = it
            binding.layoutSubscribe.visibility = View.VISIBLE
        }
        item = intent.getSerializableExtra("center_extra") as CenterModel

        val info = "\uD83C\uDFE2 Markaz nomi: ${item.name} \n" +
                "⭐️ Qo'yilgan bsholar: ${item.rating.subSequence(0, 3)}\n" +
                "\uD83D\uDC65 Obunachilar soni: ${item.subsribers_count}\n" +
                "\uD83D\uDCB0 O'rtacha oylik to'lov: ${item.monthly_payment_min}-${item.monthly_payment_max}\n" +
                "\uD83D\uDCDE Telefon raqam: ${item.phone}\n" +
                "\uD83D\uDCCD Manzil: ${item.address}\n" +
                "\uD83D\uDCC4 Batafsil: http://api.ilm-izlab.uz/center/${item.id}\n" +
                "© ILM-IZLAB\n" +
                "\uD83D\uDCF2 Ilovani yuklab olish: http://ilm-izlab.uz"

        val bundleCourse = Bundle()
        bundleCourse.putInt("extra_course", item.id)
        fragmentCourse.arguments = bundleCourse

        val bundleNews = Bundle()
        bundleNews.putInt("extra_news", item.id)
        fragmentNews.arguments = bundleNews

        val bundleRating = Bundle()
        bundleRating.putInt("extra_rating", item.id)
        fragmentRating.arguments = bundleRating

        binding.btnShare.setOnClickListener {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, info)
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }

        binding.btnRate.setOnClickListener {
            val intent = Intent(this, RateActivity::class.java)
            intent.putExtra("extra_center_rate", item)
            startActivity(intent)
        }

        binding.btnMap.setOnClickListener {
            val uri = String.format(
                Locale.ENGLISH,
                "geo:%f,%f?q=${item.latitude.toDouble()},${item.longitude.toDouble()}",
                item.latitude.toDouble(),
                item.longitude.toDouble()
            )
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri + Uri.encode(item.name)))
            intent.setPackage("com.google.android.apps.maps")
            intent.resolveActivity(packageManager)?.let {
                startActivity(intent)
            }
        }

        binding.btnCall.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:${item.phone}")
            startActivity(intent)
        }

        binding.btnMore.setOnClickListener {
            val dialog = BottomSheetDialog(this)
            val view = layoutInflater.inflate(R.layout.bottom_sheet_dialog, null)
            view.findViewById<TextView>(R.id.description).text = item.comment
            dialog.setCancelable(true)
            dialog.setContentView(view)
            dialog.show()
        }

        supportFragmentManager.beginTransaction().add(R.id.flContainer, fragmentCourse)
            .hide(fragmentCourse).commit()
        supportFragmentManager.beginTransaction().add(R.id.flContainer, fragmentNews)
            .hide(fragmentNews).commit()
        supportFragmentManager.beginTransaction().add(R.id.flContainer, fragmentRating)
            .hide(fragmentRating).commit()

        supportFragmentManager.beginTransaction().show(currentFragment).commit()
        binding.tvCourse.setTextColor(ContextCompat.getColor(this, R.color.primaryColor))

        binding.tvCourse.setOnClickListener {
            binding.tvCourse.setTextColor(ContextCompat.getColor(this, R.color.primaryColor))
            binding.tvNews.setTextColor(ContextCompat.getColor(this, R.color.black))
            binding.tvMarks.setTextColor(ContextCompat.getColor(this, R.color.black))
            supportFragmentManager.beginTransaction().hide(currentFragment).show(fragmentCourse)
                .commit()
            currentFragment = fragmentCourse

        }
        binding.tvNews.setOnClickListener {
            binding.tvCourse.setTextColor(ContextCompat.getColor(this, R.color.black))
            binding.tvNews.setTextColor(ContextCompat.getColor(this, R.color.primaryColor))
            binding.tvMarks.setTextColor(ContextCompat.getColor(this, R.color.black))
            supportFragmentManager.beginTransaction().hide(currentFragment).show(fragmentNews)
                .commit()
            currentFragment = fragmentNews

        }
        binding.tvMarks.setOnClickListener {
            binding.tvCourse.setTextColor(ContextCompat.getColor(this, R.color.black))
            binding.tvNews.setTextColor(ContextCompat.getColor(this, R.color.black))
            binding.tvMarks.setTextColor(ContextCompat.getColor(this, R.color.primaryColor))
            supportFragmentManager.beginTransaction().hide(currentFragment).show(fragmentRating)
                .commit()
            currentFragment = fragmentRating

        }

        binding.btnFollow.setOnClickListener {
            viewModel.setSubscriber(item.id)
        }

        binding.btnOk.setOnClickListener {
            binding.layoutSubscribe.visibility = View.GONE
        }

        supportFragmentManager.beginTransaction().show(currentFragment).commit()
        fragmentCourse.arguments = bundleCourse

        binding.btnBack.setOnClickListener {
            finish()
        }

        item.images.add(0, item.main_image)
        val df = DecimalFormat("#.#")
        df.roundingMode = RoundingMode.UP
        val s = item.rating.toDoubleOrNull() ?: 0.0
        binding.tvTitle.text = item.name
        binding.tvTitle1.text = item.name
        binding.tvRating.text = "${item.rating_count} ta - ${df.format(s)}/o'rtacha"
        binding.tvSubscribers.text = item.subsribers_count.toString()
        binding.tvPrice.text = "${item.monthly_payment_min} dan ${item.monthly_payment_max} gacha"
        binding.tvAddress.text =
            "${item.region.region_name}, ${item.district.district_name}, ${item.address}"
        binding.tvPhone.text = item.phone
        binding.tvComment.text = item.comment

        binding.viewPager.adapter = ViewPagerAdapterC(this, item.images)
        binding.viewPager.autoScroll(2000)
    }

    override fun loadData() {
//
    }

    override fun updateData() {
//
    }


}