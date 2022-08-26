package com.levox.whackamole.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.levox.whackamole.R
import com.levox.whackamole.data.AppPreferences
import com.levox.whackamole.databinding.FragmentStartBinding
import java.lang.RuntimeException

class StartFragment : Fragment() {

    private val appPreferences: AppPreferences by lazy {
        AppPreferences(requireContext())
    }

    private var _binding: FragmentStartBinding? = null
    private val binding: FragmentStartBinding
        get() = _binding ?: throw RuntimeException("FragmentStartBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViews()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun bindViews() {
        binding.btnPlay.setOnClickListener { launchGameFragment() }
        binding.tvHighScore.text = String.format(
            getString(R.string.high_score),
            appPreferences.getHighScore().toString()
        )
    }

    private fun launchGameFragment() {
        findNavController().navigate(
            StartFragmentDirections.actionStartFragmentToGameFragment()
        )
    }
}