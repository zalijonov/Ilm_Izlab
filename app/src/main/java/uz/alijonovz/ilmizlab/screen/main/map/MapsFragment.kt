package uz.alijonovz.ilmizlab.screen.main.map

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import uz.alijonovz.ilmizlab.R
import uz.alijonovz.ilmizlab.databinding.FragmentMapsBinding
import uz.alijonovz.ilmizlab.model.category.CategoryIdModel
import uz.alijonovz.ilmizlab.model.center.CenterModel
import uz.alijonovz.ilmizlab.model.region.RegionIdModel
import uz.alijonovz.ilmizlab.model.request.GetCenterByIdRequest
import uz.alijonovz.ilmizlab.screen.category.CategoryListActivity
import uz.alijonovz.ilmizlab.screen.center.CenterActivity
import uz.alijonovz.ilmizlab.screen.main.MainViewModel
import uz.alijonovz.ilmizlab.screen.region.RegionActivity
import uz.alijonovz.ilmizlab.utils.Constants
import java.util.*

class MapsFragment : Fragment() {
    var index = 0
    lateinit var viewModel: MainViewModel
    lateinit var binding: FragmentMapsBinding
    private var centerList = listOf<CenterModel>()
    var regionId: Int = 0
    var districtId: Int = 0
    var regionName: String = "Farg'ona viloyati"
    var search = ""
    var categoryId: Int = 0
    var scienceId: Int = 0
    var categoryName: String = "Hammasi"
    var longitude: Double = 0.0
    var latitude: Double = 0.0
    internal var mCurrLocationMarker: Marker? = null
    private val callback = OnMapReadyCallback { googleMap ->
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */

        googleMap.clear()
        var title: String = ""
        for (it in centerList) {
            it.courses.forEach {
                title += it.name + ","
            }
            googleMap.addMarker(
                MarkerOptions().position(
                    LatLng(
                        it.latitude.toDouble(),
                        it.longitude.toDouble()
                    )
                ).title(it.name).snippet(title)
            )
        }
        var zoomLevel = 15.0f
        val latLng = LatLng(Constants.currentLatitude, Constants.currentLongitude)
        val markerOptions = MarkerOptions()
        markerOptions.position(latLng)
        markerOptions.title("Current Position")
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
        mCurrLocationMarker = googleMap!!.addMarker(markerOptions)
        googleMap.moveCamera(
            CameraUpdateFactory.newLatLngZoom(
                LatLng(Constants.currentLatitude, Constants.currentLongitude),
                zoomLevel
            )
        )

        googleMap.setOnMarkerClickListener {
            it.showInfoWindow()
            centerList.forEach { center ->
                if (it.title == center.name) {
//                    binding.tvRegion.text = center.address
                    binding.tvCenter.text = center.name
                    binding.tvSciences.text = it.snippet
                    binding.tvRating.text = center.rating.subSequence(0, 3)
                    binding.latitude.text = center.latitude
                    binding.longitude.text = center.longitude
                    Glide.with(binding.root).load(Constants.IMAGE_URL + center.main_image)
                        .into(binding.imgCenter)
                    googleMap?.animateCamera(
                        CameraUpdateFactory.newLatLng(
                            LatLng(
                                center.latitude.toDouble(),
                                center.longitude.toDouble()
                            )
                        )
                    )
                    googleMap?.animateCamera(
                        CameraUpdateFactory.newLatLngZoom(
                            LatLng(
                                center.latitude.toDouble(),
                                center.longitude.toDouble()
                            ), 17f
                        )
                    )
                }

            }

            for (i in centerList.indices) {
                if (centerList[i].name == it.title) {
                    index = i
                    longitude = centerList[i].longitude.toDouble()
                    latitude = centerList[i].latitude.toDouble()
                }
            }
            if(it.title == "Current Position"){
                binding.cardInfo.visibility = View.GONE
            } else {
                binding.cardInfo.visibility = View.VISIBLE
            }
            return@setOnMarkerClickListener true
        }


        googleMap.setOnMapClickListener {
            binding.cardInfo.visibility = View.GONE
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        loadData()
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this)
        }
        binding = FragmentMapsBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.centerData.observe(requireActivity()) {
            centerList = it
            val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
            mapFragment?.getMapAsync(callback)
        }
        loadData()

        binding.btnMore.setOnClickListener {
            var intent = Intent(requireActivity(), CenterActivity::class.java)
            intent.putExtra("center_extra", centerList[index])
            startActivity(intent)
        }

        binding.btnGo.setOnClickListener {
            var uri = String.format(Locale.ENGLISH, "google.navigation:q=${latitude},${longitude}")
            var intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
            intent.setPackage("com.google.android.apps.maps")
            intent.resolveActivity(it.context.packageManager)?.let {
                startActivity(intent)
            }
        }

        binding.tvRegion.text = regionName
        binding.tvScience.text = "Hammasi"

        binding.cardRegion.setOnClickListener {
            centerList = emptyList()
            loadData()
            startActivity(Intent(requireActivity(), RegionActivity::class.java))
        }

        binding.cardScience.setOnClickListener {
            centerList = emptyList()
            loadData()
            startActivity(Intent(requireActivity(), CategoryListActivity::class.java))
        }

    }

    override fun onResume() {
        super.onResume()

        centerList = emptyList()
        viewModel.centerData.observe(requireActivity()) {
            centerList = it
            val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
            mapFragment?.getMapAsync(callback)
        }
        loadData()


        binding.tvRegion.text = regionName
        binding.tvScience.text = if (categoryName == "") "Hammasi" else categoryName
    }

    override fun onDestroy() {
        super.onDestroy()
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this)
        }
    }

    @Subscribe
    fun onEventRegion(districtModel: RegionIdModel) {
        this.regionId = districtModel.region_id
        this.districtId = districtModel.district_id
        this.regionName = districtModel.region_name
        loadData()
    }

    @Subscribe
    fun onEventCategory(category: CategoryIdModel) {
        this.categoryId = category.category_id
        this.scienceId = category.science_id
        this.categoryName = category.category_name
        loadData()
    }

    private fun loadData() {
        viewModel.loadCenters(
            GetCenterByIdRequest(
                limit = 0,
                category_id = categoryId,
                district_id = districtId,
                science_id = scienceId,
                region_id = regionId,
                longitude = Constants.currentLongitude,
                latitude = Constants.currentLatitude
            )
        )

    }
}