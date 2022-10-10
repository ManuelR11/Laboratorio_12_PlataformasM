package com.example.laboratorio_12

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.laboratorio_12.databinding.FragmentLoginBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch



class LoginFragment : Fragment() {

    private val MainViewModel: SessionViewModel by activityViewModels()
    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
    }



    private fun setListeners() {
        binding.buttonLoginFragmentLogin.setOnClickListener {
            loginUser(
                email = binding.inputLayoutLoginFragmentEmail.editText!!.text.toString(),
                password = binding.inputLayoutLoginFragmentPassword.editText!!.text.toString()
            )

        }
    }

    private fun loginUser(email:String,password:String){
        binding.buttonLoginFragmentLogin.visibility = View.GONE
        binding.loginProgressBar.visibility = View.VISIBLE
        if (email == getString(R.string.my_email) && email==password){
            CoroutineScope(Dispatchers.Main).launch {
                delay(1000L)
            }
            requireView().findNavController().navigate(
                LoginFragmentDirections.actionLoginFragmentToHomeFragment()
            )
        }else{
            Toast.makeText(requireContext(), getString(R.string.error_email_password),Toast.LENGTH_LONG).show()
            CoroutineScope(Dispatchers.Main).launch {
                delay(1000L)
            }
            binding.buttonLoginFragmentLogin.visibility = View.VISIBLE
            binding.loginProgressBar.visibility = View.GONE
        }

    }


}