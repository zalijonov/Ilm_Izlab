package uz.alijonovz.ilmizlab.utils

import com.orhanobut.hawk.Hawk
import uz.alijonovz.ilmizlab.model.category.Science

object PrefUtils {

    private const val PREF_CENTER = "pref_center"
    private const val PREF_TOKEN = "pref_token"

    fun setToken(value: String){
        Hawk.put(PREF_TOKEN, value)
    }

    fun getToken(): String{
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