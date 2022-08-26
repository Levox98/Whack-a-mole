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
        arrayOf(
           binding.hole1,
           binding.hole2,
           binding.hole3,
           binding.hole4,
           binding.hole5,
           binding.hole6,
           binding.hole7,
           binding.hole8,
           binding.hole9
        )
    }

    private var previousHole: ImageView? = null

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
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
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
        viewModel.molePosition.observe(viewLifecycleOwner) {position ->
            if (previousHole == null) {
                previousHole = options[position]
            } else {
                previousHole!!.isClickable = false
                previousHole!!.setImageResource(R.drawable.ic_hole_large)
                previousHole = options[position]
            }
            with(options[position]) {
                setImageResource(R.drawable.ic_mole)
                isClickable = true
                setOnClickListener {
                    viewModel.increaseScore()
                    setImageResource(R.drawable.ic_hole_large)
                    isClickable = false
                }
            }
        }
    }

    private fun finishGame(result: GameResult) {
        findNavController().navigate(
            GameFragmentDirections.actionGameFragmentToGameFinishedFragment(result)
        )
    }
}