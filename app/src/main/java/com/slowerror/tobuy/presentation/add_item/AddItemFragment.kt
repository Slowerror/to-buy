package com.slowerror.tobuy.presentation.add_item

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.slowerror.tobuy.R
import com.slowerror.tobuy.databinding.FragmentAddItemBinding
import com.slowerror.tobuy.domain.model.Item
import com.slowerror.tobuy.presentation.base.BaseFragment
import kotlinx.coroutines.launch
import java.util.UUID

class AddItemFragment : BaseFragment() {

    private var _binding: FragmentAddItemBinding? = null
    private val binding get() = _binding!!

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
    }

    private fun saveItemToDatabase() {
        val titleItem = binding.titleEditText.text.toString().trim()

        if (titleItem.isEmpty()) {
            binding.titleTextField.error = "Required Field"
            return
        }

        binding.titleTextField.error = null

        val descriptionItem =  binding.descriptionEditText.text.toString().trim()

        val priorityItem = when (binding.priorityRadioGroup.checkedRadioButtonId) {
            R.id.RadioButtonLow -> 1
            R.id.RadioButtonMedium -> 2
            R.id.RadioButtonHigh -> 3
            else -> 0
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
                sharedViewModel.addItem(item).join()
                navigateBack()
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}