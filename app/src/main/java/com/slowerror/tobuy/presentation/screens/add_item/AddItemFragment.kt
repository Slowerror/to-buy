package com.slowerror.tobuy.presentation.screens.add_item

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.slowerror.tobuy.R
import com.slowerror.tobuy.databinding.FragmentAddItemBinding
import com.slowerror.tobuy.domain.model.Item
import com.slowerror.tobuy.presentation.base.BaseFragment
import kotlinx.coroutines.launch
import java.util.UUID


class AddItemFragment : BaseFragment() {

    private var _binding: FragmentAddItemBinding? = null
    private val binding get() = _binding!!

    private val safeArgs: AddItemFragmentArgs by navArgs()
    private val selectedItem: Item? by lazy {
        sharedViewModel.itemListLiveData.value?.find {
            it.id == safeArgs.selectedItemId
        }
    }

    private var isInEditMode = false
    private val regexToDefineQuantity = "\\[+(\\w)+]".toRegex()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddItemBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.saveButton.setOnClickListener {
            saveItemToDatabase()
        }

        settingUpSeekBar()

        observeViewModelData()
        requestFocusOnTitleAndShowKeyboard()

        // Настройки экрана если мы находимся в режиме редактирования
        loadEditModeScreen()

    }

    private fun settingUpSeekBar() {
        binding.quantitySeekBar.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                binding.titleEditText.apply {
                    val textTitle = text.toString().trim()

                    if (textTitle.isEmpty()) {
                        return
                    }


                    val newTextTitle = when {
                        progress == 1 -> textTitle.replace(regexToDefineQuantity, "")
                        textTitle.contains(regexToDefineQuantity) -> textTitle.replace(
                            regexToDefineQuantity,
                            "[${progress}]"
                        )

                        else -> "$textTitle [$progress]"
                    }

                    setText(newTextTitle)
                    setSelection(newTextTitle.length)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                val text = binding.titleEditText.text.toString().trim()
                if (text.isEmpty()) {
                    binding.titleEditText.text = null
                    Snackbar.make(requireView(), "Fill in the title!", Snackbar.LENGTH_SHORT).show()
                    requestFocusOnTitleAndShowKeyboard()
                }
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }

        })
    }

    private fun requestFocusOnTitleAndShowKeyboard() {
        binding.titleEditText.requestFocus()
        showKeyboard(binding.titleEditText)
    }

    private fun observeViewModelData() {
        sharedViewModel.transactionCompletedLiveData.observe(viewLifecycleOwner) { event ->
            event.getContent()?.let {
                if (isInEditMode) {
                    navigateBack()
                    return@observe
                }

                Snackbar.make(requireView(), "Item saved!", Snackbar.LENGTH_SHORT).show()

                binding.apply {
                    titleEditText.text = null
                    titleEditText.requestFocus()
                    descriptionEditText.text = null
                    priorityRadioGroup.check(R.id.RadioButtonLow)
                }
            }
        }
    }

    private fun loadEditModeScreen() {
        selectedItem?.let { item ->
            isInEditMode = true

            binding.apply {
                titleEditText.setText(item.title)
                titleEditText.setSelection(item.title.length)
                descriptionEditText.setText(item.description)

                when (item.priority) {
                    1 -> priorityRadioGroup.check(R.id.RadioButtonLow)
                    2 -> priorityRadioGroup.check(R.id.RadioButtonMedium)
                    else -> priorityRadioGroup.check(R.id.RadioButtonHigh)
                }

                saveButton.text = resources.getString(R.string.update_button)
                (requireActivity() as AppCompatActivity).supportActionBar?.title = "Update item"


                val titleText = titleEditText.text.toString()

                if (titleText.contains(regexToDefineQuantity)) {
                    val startIndex = titleText.indexOfLast { it == '[' } + 1
                    val endIndex = titleText.indexOfLast { it == ']' }
                    val progressQuantity = titleText.substring(startIndex, endIndex)

                    if (progressQuantity.isDigitsOnly()) {
                        quantitySeekBar.progress = progressQuantity.toInt()
                    } else {
                        Snackbar.make(
                            requireView(),
                            "Please, correct the quantity value!",
                            Snackbar.LENGTH_SHORT
                        ).show()

//                        titleEditText.requestFocus(startIndex)
                    }
                }

            }
        }
    }

    private fun saveItemToDatabase() {
        val titleItem = binding.titleEditText.text.toString().trim()

        if (titleItem.isEmpty()) {
            binding.apply {
                titleTextField.error = "Required Field"
                titleEditText.text = null
            }
            return
        }

        binding.titleTextField.error = null

        val descriptionItem = binding.descriptionEditText.text.toString().trim()

        val priorityItem = when (binding.priorityRadioGroup.checkedRadioButtonId) {
            R.id.RadioButtonLow -> 1
            R.id.RadioButtonMedium -> 2
            R.id.RadioButtonHigh -> 3
            else -> 0
        }

        if (isInEditMode) {
            selectedItem?.let {
                val updateItem = it.copy(
                    title = titleItem,
                    description = descriptionItem,
                    priority = priorityItem
                )

                sharedViewModel.updateItem(updateItem)
            }

            return
        }

        val item = Item(
            id = UUID.randomUUID().toString(),
            title = titleItem,
            description = descriptionItem,
            priority = priorityItem,
            createdAt = System.currentTimeMillis(),
            categoryId = ""
        )

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.CREATED) {
                sharedViewModel.addItem(item)
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}