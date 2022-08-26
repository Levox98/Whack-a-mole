package com.levox.whackamole.presentation.fragments

import android.media.Image
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.levox.whackamole.R
import com.levox.whackamole.databinding.FragmentGameBinding
import com.levox.whackamole.domain.entity.GameResult
import com.levox.whackamole.presentation.viewmodel.GameViewModel
import com.levox.whackamole.presentation.viewmodel.GameViewModelFactory
import java.lang.RuntimeException

class GameFragment : Fragment() {

    private val viewModelFactory by lazy {
        GameViewModelFactory(requireActivity().application)
    }

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[GameViewModel::class.java]
    }

    private var _binding: FragmentGameBinding? = null
    private val binding: FragmentGameBinding
        get() = _binding ?: throw RuntimeException("FragmentGameBinding == null")

    private val options by lazy {
        mutableListOf<ImageView>().apply {
            add(binding.hole1)
            add(binding.hole2)
            add(binding.hole3)
            add(binding.hole4)
            add(binding.hole5)
            add(binding.hole6)
            add(binding.hole7)
            add(binding.hole8)
            add(binding.hole9)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observeViewModel() {
        viewModel.formattedTime.observe(viewLifecycleOwner) {time ->
            binding.tvTimer.text = time
        }
        viewModel.gameResult.observe(viewLifecycleOwner) {result ->
            finishGame(result)
        }
        viewModel.score.observe(viewLifecycleOwner) {score ->
            binding.tvScore.text = String.format(
                getString(R.string.score),
                score.toString()
            )
        }
    }

    private fun finishGame(result: GameResult) {
        findNavController().navigate(
            GameFragmentDirections.actionGameFragmentToGameFinishedFragment(result)
        )
    }
}