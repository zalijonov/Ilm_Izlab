package uz.alijonovz.ilmizlab.screen.main

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.orhanobut.hawk.Hawk
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uz.alijonovz.ilmizlab.R
import uz.alijonovz.ilmizlab.api.ApiService
import uz.alijonovz.ilmizlab.databinding.ActivityMainBinding
import uz.alijonovz.ilmizlab.model.BaseResponse
import uz.alijonovz.ilmizlab.model.login.GetTokenModel
import uz.alijonovz.ilmizlab.screen.BaseActivity
import uz.alijonovz.ilmizlab.screen.MainViewModel
import uz.alijonovz.ilmizlab.screen.auth.LoginActivity
import uz.alijonovz.ilmizlab.screen.main.home.HomeFragment
import uz.alijonovz.ilmizlab.screen.main.map.MapsFragment
import uz.alijonovz.ilmizlab.screen.main.news.NewsFragment
import uz.alijonovz.ilmizlab.screen.main.search.SearchFragment
import uz.alijonovz.ilmizlab.screen.main.subscribed.SubscribedFragment
import uz.alijonovz.ilmizlab.utils.Constants
import uz.alijonovz.ilmizlab.utils.PrefUtils

class MainActivity : BaseActivity<ActivityMainBinding>() {
    private val homeFragment = HomeFragment.newInstance()
    private val searchFragment = SearchFragment.newInstance()
    private val mapsFragment = MapsFragment()
    private val newsFragment = NewsFragment.newInstance()
    private val subscribeFragment = SubscribedFragment.newInstance()
    var currentFragment: Fragment = homeFragment
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    lateinit var viewModel: MainViewModel
    private lateinit var toggle: ActionBarDrawerToggle

    override fun getViewBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun initView() {
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.error.observe(this){
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
        viewModel.tokenData.observe(this){
            binding.navigation.drUserName.text = it.fullname
            binding.navigation.drUserPhone.text = "+" + it.phone
            Glide.with(this)
                .load(Constants.IMAGE_URL + it.avatar)
                .into(binding.navigation.drUserImg)
        }
        viewModel.getUser()
        toggle = ActionBarDrawerToggle(this, binding.drawer, R.string.open, R.string.close)
        binding.drawer.addDrawerListener(toggle)
        toggle.syncState()

        binding.exitAuth.setOnClickListener {
            Hawk.deleteAll()
            binding.drawer.close()
        }

        binding.navigation.actionInfo.setOnClickListener {
            startActivity(Intent(this, InfoActivity::class.java))
        }
        binding.btnMenu.setOnClickListener {
            binding.drawer.openDrawer(GravityCompat.START)


            if (PrefUtils.getToken().isNullOrEmpty()) {

                binding.navigation.actionControlCenters.visibility = View.GONE
                binding.navigation.actionProfile.visibility = View.GONE
                binding.navigation.lyUser.visibility = View.GONE
                binding.exitAuth.visibility = View.GONE
                binding.navigation.login.visibility = View.VISIBLE

            } else {

                binding.navigation.login.visibility = View.GONE
                binding.exitAuth.visibility = View.VISIBLE
                binding.navigation.lyUser.visibility = View.VISIBLE
                binding.navigation.actionProfile.visibility = View.VISIBLE
                binding.navigation.actionControlCenters.visibility = View.VISIBLE
            }

        }

        binding.navigation.login.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        binding.navigation.actionCall.setOnClickListener {
            val mobilNumber = "+998934972205"
            val phoneIntent = Intent(Intent.ACTION_DIAL)
            phoneIntent.data = Uri.parse("tel:$mobilNumber")
            startActivity(phoneIntent)
        }

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        getCurrentLocation()

        binding.mapBtn.setOnClickListener {
            getCurrentLocation()
        }


        supportFragmentManager.beginTransaction().add(R.id.flContainer, currentFragment)
            .show(currentFragment).commit()

        binding.menu.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    supportFragmentManager.beginTransaction().detach(currentFragment)
                        .add(R.id.flContainer, homeFragment).attach(homeFragment).show(homeFragment)
                        .commit()
                    currentFragment = homeFragment
                }
                R.id.news -> {
                    supportFragmentManager.beginTransaction().detach(currentFragment)
                        .add(R.id.flContainer, newsFragment).attach(newsFragment).show(newsFragment)
                        .commit()
                    currentFragment = newsFragment
                }
                R.id.search -> {
                    supportFragmentManager.beginTransaction().detach(currentFragment)
                        .add(R.id.flContainer, searchFragment).attach(searchFragment)
                        .show(searchFragment).commit()
                    currentFragment = searchFragment
                }
                R.id.map -> {
                    supportFragmentManager.beginTransaction().detach(currentFragment)
                        .add(R.id.flContainer, mapsFragment).attach(mapsFragment).show(mapsFragment)
                        .commit()
                    currentFragment = mapsFragment
                }
                R.id.subscribed -> {
                    supportFragmentManager.beginTransaction().detach(currentFragment)
                        .add(R.id.flContainer, subscribeFragment).attach(subscribeFragment)
                        .show(subscribeFragment).commit()
                    currentFragment = subscribeFragment
                }
            }
            return@setOnItemSelectedListener true
        }
    }

    override fun onBackPressed() {
        android.app.AlertDialog.Builder(this).apply {
            setTitle("Iltimos, tasdiqlang.")
            setMessage("Ilovadan chiqmoqchimisiz?")

            setPositiveButton("Ha") { _, _ ->
                // if user press yes, then finish the current activity
                super.onBackPressed()
            }

            setNegativeButton("Yo'q"){_, _ ->
                // if user press no, then return the activity
                Toast.makeText(this@MainActivity, "Rahmat!",
                    Toast.LENGTH_LONG).show()
            }

            setCancelable(true)
        }.create().show()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == PERMISSION_REQUEST_ACCESS_LOCATION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(applicationContext, "Ruxsat berildi", Toast.LENGTH_SHORT).show()
                getCurrentLocation()
            } else {
                Toast.makeText(applicationContext, "Ruxsat berilmadi", Toast.LENGTH_SHORT).show()
            }
        }
    }


    companion object {
        private const val PERMISSION_REQUEST_ACCESS_LOCATION = 100

    }

    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager =
            getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    private fun getCurrentLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                fusedLocationProviderClient.lastLocation.addOnCompleteListener { task ->
                    val location: Location? = task.result
                    if (location == null) {
                        Toast.makeText(this, "Ma'lumot qabul qilinmadi", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "Hozirgi joy aniqlandi", Toast.LENGTH_SHORT).show()
                        Constants.currentLatitude = location.latitude
                        Constants.currentLongitude = location.longitude
                    }
                }
            } else {
                Toast.makeText(this, "Lokatsiyani yoqing", Toast.LENGTH_SHORT).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else {
            requestPermission()
        }
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            this, arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ),
            PERMISSION_REQUEST_ACCESS_LOCATION
        )
    }

    private fun checkPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }

    override fun loadData() {

    }

    override fun updateData() {

    }
}