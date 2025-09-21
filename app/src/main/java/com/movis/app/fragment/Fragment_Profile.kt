package com.movis.app.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.movis.app.R
import com.movis.app.databinding.FragmentProfileBinding


class Fragment_Profile : Fragment(), FragmentLogin.eventClick , FragmentRegister.eventClick1{

    lateinit var binding: FragmentProfileBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(layoutInflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        changeFragment( FragmentLogin(this) )

    }

    private fun changeFragment(fragment: Fragment) {
        var transaction = childFragmentManager.beginTransaction()
        transaction.replace(R.id.FrameLayoutProfile, fragment)
        transaction.commit()
    }

    override fun clickLogin() {
        changeFragment(FragmentRegister(this) )
    }

    override fun clickRegister() {
        changeFragment(FragmentLogin(this) )
    }


}