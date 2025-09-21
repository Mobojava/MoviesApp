package com.movis.app.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.movis.app.ApiManager.ApiManager
import com.movis.app.databinding.FragmentLoginBinding
import com.movis.app.databinding.FragmentRegisterBinding
import com.movis.app.model.Model_user
import com.movis.app.model.RegisterRequest
import kotlin.math.log

class FragmentRegister(
    val eventclick: FragmentRegister.eventClick1

) : Fragment() {
    val apiManager = ApiManager()
    lateinit var binding: FragmentRegisterBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnGoLogin.setOnClickListener {
            eventclick.clickRegister(

            )
        }
        binding.btnRegister.setOnClickListener {
            val name_register = binding.userNameRegister.text.toString()
            val userNameRegister = binding.userNameRegister.text.toString()
            val passwordRegister = binding.passwordRegister.text.toString()
            if (name_register!= ""||userNameRegister!=""||passwordRegister != ""){
                sendInfoUserServer(name_register, userNameRegister, passwordRegister)
                binding.animationLotti.visibility = View.VISIBLE
            }else{
                Snackbar.make(
                    binding.root,
                    "Complete all fields.",
                    Snackbar.LENGTH_SHORT
                ).show()

            }
        }

    }

    private fun sendInfoUserServer(
        nameRegister: String,
        userNameRegister: String,
        passwordRegister: String
    ) {
        apiManager.registerUser(
            RegisterRequest(nameRegister,userNameRegister,passwordRegister),
            object : ApiManager.ApiCallback<Model_user>{
                override fun onSuccess(data: Model_user) {
                    Log.v("Register",data.toString())
                    binding.animationLotti.visibility = View.GONE
                }

                override fun onError(data: String) {
                    Log.v("Register",data)
                    binding.animationLotti.visibility = View.GONE
                }
            })

    }

    interface eventClick1{
        fun clickRegister()
    }
}