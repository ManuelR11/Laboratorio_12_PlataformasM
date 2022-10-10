package com.example.laboratorio_12

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import coil.load
import com.example.laboratorio_12.databinding.FragmentHomeBinding
import kotlinx.coroutines.flow.collectLatest

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val homeViewModel: HomeViewModel by viewModels()
    private val mainViewModel: SessionViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel.triggerStateFlow()
        setListeners()
        setObservers()
    }

    private fun setObservers() {
        lifecycleScope.launchWhenCreated {
            mainViewModel.validAuthToken.collectLatest {
                verStatusMain(it)
            }
        }
        lifecycleScope.launchWhenCreated {
            homeViewModel.statusApp.collectLatest {
                verStatus(it)
            }
        }
    }

    private fun verStatusMain(it: Boolean) {
        when (it){
            true ->{
                // Mantiene la sesion
            }
            false ->{
                requireView().findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToLoginFragment()
                )
            }
        }

    }

    private fun verStatus(it: HomeViewModel.StatusApp) {
        when (it){
            is HomeViewModel.StatusApp.default ->{
                binding.apply {
                    binding.textView2.text = getString(R.string.intro)
                    binding.imageView.load(R.drawable.ic_baseline_android_24)
                    imageView.visibility = View.VISIBLE
                    textView2.visibility = View.VISIBLE
                    progressBar.visibility = View.GONE
                }
            }
            is HomeViewModel.StatusApp.succes ->{
                binding.apply {
                    binding.textView2.text = getString(R.string.exito)
                    binding.imageView.load(R.drawable.ic_baseline_check_24)
                    imageView.visibility = View.VISIBLE
                    textView2.visibility = View.VISIBLE
                    progressBar.visibility = View.GONE
                }
            }
            is HomeViewModel.StatusApp.failure ->{
                binding.apply {
                    binding.textView2.text = getString(R.string.falla)
                    binding.imageView.load(R.drawable.ic_baseline_warning_24)
                    imageView.visibility = View.VISIBLE
                    textView2.visibility = View.VISIBLE
                    progressBar.visibility = View.GONE
                }
            }
            is HomeViewModel.StatusApp.Empty ->{
                binding.apply {
                    textView2.text = getString(R.string.no_resul)
                    imageView.load(R.drawable.ic_baseline_not_interested_24)
                    imageView.visibility = View.VISIBLE
                    textView2.visibility = View.VISIBLE
                    progressBar.visibility = View.GONE

                }
            }
            is HomeViewModel.StatusApp.loading ->{
                binding.apply {
                    imageView.visibility = View.GONE
                    textView2.visibility = View.GONE
                    progressBar.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun setListeners() {
        binding.button7.setOnClickListener {
            homeViewModel.carga()
            homeViewModel.reset()
        }
        binding.button8.setOnClickListener {
            homeViewModel.carga()
            homeViewModel.exito()
        }
        binding.button9.setOnClickListener {
            homeViewModel.carga()
            homeViewModel.fallo()
        }
        binding.button10.setOnClickListener {
            homeViewModel.carga()
            homeViewModel.vacio()
        }
        binding.button2.setOnClickListener {
            mainViewModel.cerrarSesion()
            requireView().findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToLoginFragment()
            )
        }
        binding.button.setOnClickListener {
            mainViewModel.triggerStateFlow()
        }
    }
}