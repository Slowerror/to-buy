package com.slowerror.tobuy.presentation.screens.color_picker

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import com.slowerror.tobuy.R
import com.slowerror.tobuy.databinding.FragmentCustomColorPickerBinding
import com.slowerror.tobuy.presentation.base.BaseFragment
import com.slowerror.tobuy.utils.SharedPrefUtil
import org.koin.androidx.viewmodel.ext.android.viewModel

class CustomColorPickerFragment : BaseFragment() {

    private var _binding: FragmentCustomColorPickerBinding? = null
    private val binding get() = _binding!!

    private val safeArgs: CustomColorPickerFragmentArgs by navArgs()
    private val viewModel: CustomColorPickerViewModel by viewModel<CustomColorPickerViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCustomColorPickerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.setPriority(safeArgs.priorityName) { red, green, blue ->
            binding.redColorLabel.colorSeekBar.progress = red
            binding.greenColorLabel.colorSeekBar.progress = green
            binding.blueColorLabel.colorSeekBar.progress = blue
        }

        binding.redColorLabel.apply {
            colorNameTextView.text = getString(R.string.red_color_name)
            colorSeekBar.setOnSeekBarChangeListener(SeekBarListener(viewModel::onRedChange))
        }

        binding.greenColorLabel.apply {
            colorNameTextView.text = getString(R.string.green_color_name)
            colorSeekBar.setOnSeekBarChangeListener(SeekBarListener(viewModel::onGreenChange))
        }

        binding.blueColorLabel.apply {
            colorNameTextView.text = getString(R.string.blue_color_name)
            colorSeekBar.setOnSeekBarChangeListener(SeekBarListener(viewModel::onBlueChange))
        }

        viewModel.viewStateLiveData.observe(viewLifecycleOwner) { state ->
            binding.nameTextView.text = state.getFormattedTitle()

            val color = Color.rgb(state.red, state.green, state.blue)

            binding.colorView.setBackgroundColor(color)
        }

        binding.saveColorButton.setOnClickListener {
            val viewState = viewModel.viewStateLiveData.value ?: return@setOnClickListener

            val color = Color.rgb(viewState.red, viewState.green, viewState.blue)

            when(safeArgs.priorityName.lowercase()) {
                "low" -> { SharedPrefUtil.setLowPriorityColor(color) }
                "medium" -> { SharedPrefUtil.setMediumPriorityColor(color) }
                "high" -> { SharedPrefUtil.setHighPriorityColor(color) }
            }

            Toast.makeText(requireContext(), "Color was saved!", Toast.LENGTH_SHORT).show()
            navigateUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private class SeekBarListener(
        private val onProgressChange: (Int) -> Unit
    ) : SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            onProgressChange(progress)
        }

        override fun onStartTrackingTouch(seekBar: SeekBar?) {}

        override fun onStopTrackingTouch(seekBar: SeekBar?) {}

    }
}