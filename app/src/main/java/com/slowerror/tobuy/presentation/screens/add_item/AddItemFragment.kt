package com.slowerror.tobuy.presentation.screens.add_item

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavArgs
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
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

        sharedViewModel.transactionCompletedLiveData.observe(viewLifecycleOwner) { isCompleted ->
            if (isCompleted) {

                if (isInEditMode) {
                    navigateBack()
                    return@observe
                }

                Toast.makeText(requireContext(), "Item saved!", Toast.LENGTH_SHORT).show()

                binding.apply {
                    titleEditText.text = null
                    titleEditText.requestFocus()

                    descriptionEditText.text = null

                    priorityRadioGroup.check(R.id.RadioButtonLow)
                }


            }
        }

        binding.titleEditText.requestFocus()
        showKeyboard(binding.titleEditText)


        // Настройки экрана если мы находимся в режиме редактирования
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

                saveButton.text = "Update"
                (requireActivity() as AppCompatActivity).supportActionBar?.title = "Update item"
            }
        }

    }

    override fun onPause() {
        super.onPause()
        sharedViewModel.setFalseTransactionCompleted()
    }

    private fun saveItemToDatabase() {
        val titleItem = binding.titleEditText.text.toString().trim()

        if (titleItem.isEmpty()) {
            binding.titleTextField.error = "Required Field"
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