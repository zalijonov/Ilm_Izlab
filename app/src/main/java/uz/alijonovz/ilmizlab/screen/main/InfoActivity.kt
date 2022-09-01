package uz.alijonovz.ilmizlab.screen.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import uz.alijonovz.ilmizlab.databinding.ActivityInfoBinding

class InfoActivity : AppCompatActivity() {
    lateinit var binding: ActivityInfoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCloseInfo.setOnClickListener {
            finish()
        }
    }
}