package com.movis.app.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.movis.app.ApiManager.ApiManager
import com.movis.app.databinding.FragmentLoginBinding

class FragmentLogin(
    val eventclick: eventClick
) : Fragment() {

    lateinit var binding: FragmentLoginBinding
    val apiManager = ApiManager()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnGoRegister.setOnClickListener {
            eventclick.clickLogin()
        }


    }

    interface eventClick {
        fun clickLogin()
    }
}