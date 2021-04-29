package com.example.myapplication.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.myapplication.fragments.ProductListFragment
import com.example.myapplication.models.Category2
//import com.example.myapplication.fragments.ProductListFragment

import com.example.practice12.Models.Category

class ViewPagerAdapter (fm: FragmentManager) : FragmentPagerAdapter(fm){

    var mFragment: ArrayList<Fragment> = ArrayList()
    var mTitle: ArrayList<String> = ArrayList()

    override fun getCount(): Int {
        return  mFragment.size
    }
    override fun getItem(position: Int): Fragment {
        return mFragment[position]
    }
    fun addFragment(category: Category2){
        mTitle.add(category.catName)
        mFragment.add(ProductListFragment.newInstance(category.catId))
    }
    override fun getPageTitle(position: Int): CharSequence? {
        return mTitle[position]
    }
}