package com.slowerror.tobuy.presentation.screens.color_picker

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.navigation.fragment.navArgs
import com.slowerror.tobuy.databinding.FragmentCustomColorPickerBinding
import com.slowerror.tobuy.presentation.base.BaseFragment
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

        viewModel.setPriority(safeArgs.priorityName)

        binding.redColorLabel.apply {
            colorNameTextView.text = "Red"
            colorSeekBar.setOnSeekBarChangeListener(SeekBarListener(viewModel::onRedChange))
        }

        binding.greenColorLabel.apply {
            colorNameTextView.text = "Green"
            colorSeekBar.setOnSeekBarChangeListener(SeekBarListener(viewModel::onGreenChange))
        }

        binding.blueColorLabel.apply {
            colorNameTextView.text = "Blue"
            colorSeekBar.setOnSeekBarChangeListener(SeekBarListener(viewModel::onBlueChange))
        }

        viewModel.viewStateLiveData.observe(viewLifecycleOwner) { state ->
            binding.nameTextView.text = state.getFormattedTitle()

            val color = Color.rgb(state.red, state.green, state.blue)

            binding.colorView.setBackgroundColor(color)
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

        override fun onStartTrackingTouch(seekBar: SeekBar?) {
            TODO("Not yet implemented")
        }

        override fun onStopTrackingTouch(seekBar: SeekBar?) {
            TODO("Not yet implemented")
        }

    }
}