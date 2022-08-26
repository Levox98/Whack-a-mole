package com.levox.whackamole.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.levox.whackamole.R
import com.levox.whackamole.data.AppPreferences
import com.levox.whackamole.databinding.FragmentGameFinishedBinding
import java.lang.RuntimeException

class GameFinishedFragment : Fragment() {

    private val args by navArgs<GameFinishedFragmentArgs>()
    private val appPreferences: AppPreferences by lazy {
        AppPreferences(requireContext())
    }

    private var _binding: FragmentGameFinishedBinding? = null
    private val binding: FragmentGameFinishedBinding
        get() = _binding ?: throw RuntimeException("FragmentGameFinishedBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameFinishedBinding.inflate(inflater, container, false)
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
        binding.tvHighScore.text = String.format(
            getString(R.string.high_score),
            appPreferences.getHighScore().toString()
        )
        binding.tvScore.text = String.format(
            getString(R.string.score),
            args.result.score.toString()
        )
        binding.btnPlayAgain.setOnClickListener {
            findNavController().navigate(
                GameFinishedFragmentDirections.actionGameFinishedFragmentToGameFragment()
            )
        }
        binding.btnToMenu.setOnClickListener {
            findNavController().navigate(
                GameFinishedFragmentDirections.actionGameFinishedFragmentToStartFragment()
            )
        }
    }
}