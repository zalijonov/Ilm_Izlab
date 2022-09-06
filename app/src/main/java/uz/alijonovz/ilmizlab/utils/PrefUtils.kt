package uz.alijonovz.ilmizlab.utils

import com.orhanobut.hawk.Hawk
import uz.alijonovz.ilmizlab.model.category.Science

object PrefUtils {

    private const val PREF_CENTER = "pref_center"
    private const val PREF_TOKEN = "pref_token"
    private const val PREF_REGION_ID = "pref_region_id"
    private const val PREF_DISTRICT_ID = "pref_district_id"
    private const val PREF_CATEGORY_ID = "pref_category_id"
    private const val PREF_SCIENCE_ID = "pref_science_id"

    fun setCategoryId(value: Int) {
        Hawk.put(PREF_CATEGORY_ID, value)
    }

    fun getCategoryId(): Int {
        return Hawk.get(PREF_CATEGORY_ID, 0)
    }

    fun setScienceId(value: Int) {
        Hawk.put(PREF_SCIENCE_ID, value)
    }

    fun getScienceId(): Int {
        return Hawk.get(PREF_SCIENCE_ID, 0)
    }

    fun setRegionId(value: Int) {
        Hawk.put(PREF_REGION_ID, value)
    }

    fun getRegion(): Int {
        return Hawk.get(PREF_REGION_ID, 0)
    }

    fun setDistrictId(value: Int) {
        Hawk.put(PREF_DISTRICT_ID, value)
    }

    fun getDistrictId(): Int {
        return Hawk.get(PREF_DISTRICT_ID, 0)
    }

    fun setToken(value: String) {
        Hawk.put(PREF_TOKEN, value)
    }

    fun getToken(): String {
        return Hawk.get(PREF_TOKEN, "")
    }

    fun setCenters(item: Science) {
        val items = Hawk.get(PREF_CENTER, arrayListOf<Int>())
        if (items.filter { it == item.id }.firstOrNull() != null) {
            items.remove(item.id)
        } else {
            items.add(item.id)
        }

        Hawk.put(PREF_CENTER, items)
    }

    fun getCenterLists(): ArrayList<Int> {
        return Hawk.get(PREF_CENTER, arrayListOf())
    }

    fun checkCenter(item: Science): Boolean {
        val items = Hawk.get(PREF_CENTER, arrayListOf<Int>())
        return items.filter { it == item.id }.firstOrNull() != null
    }
}