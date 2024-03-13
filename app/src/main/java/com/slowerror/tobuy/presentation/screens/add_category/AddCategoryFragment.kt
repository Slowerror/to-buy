package com.slowerror.tobuy.presentation.screens.add_category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.slowerror.tobuy.databinding.FragmentAddCategoryBinding
import com.slowerror.tobuy.domain.model.Category
import com.slowerror.tobuy.presentation.base.BaseFragment
import java.util.UUID

class AddCategoryFragment : BaseFragment() {

    private var _binding: FragmentAddCategoryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedViewModel.transactionCompletedLiveData.observe(viewLifecycleOwner) { event ->
            event.getContent()?.let {
                navigateBack()
            }
        }

        binding.categoryEditText.requestFocus()
        showKeyboard(binding.categoryEditText)

        binding.saveButton.setOnClickListener {
            saveCategoryToDatabase()
        }
    }

    private fun saveCategoryToDatabase() {
        val categoryName = binding.categoryEditText.text.toString().trim()

        if (categoryName.isEmpty()) {
            binding.apply {
                categoryTextField.error = "Required Field"
                categoryEditText.text = null
            }
            return
        }

        binding.categoryTextField.error = null

        val newCategory = Category(
            id = UUID.randomUUID().toString(),
            name = categoryName
        )

        sharedViewModel.addCategory(newCategory)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}