package com.norbertneudert.openmygarage.ui.main

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.norbertneudert.openmygarage.R
import com.norbertneudert.openmygarage.ui.main.logTab.LogTabFragment
import com.norbertneudert.openmygarage.ui.main.mainTab.MainTabFragment
import com.norbertneudert.openmygarage.ui.main.plateTab.PlateTabFragment

private val TAB_TITLES = arrayOf(
    R.string.tab_text_1,
    R.string.tab_text_2,
    R.string.tab_text_3
)

class SectionsPagerAdapter(private val context: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        when(position) {
            1 -> return LogTabFragment.newInstance()
            2 -> return PlateTabFragment.newInstance()
            else -> return MainTabFragment.newInstance()
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int {
        return 3
    }
}