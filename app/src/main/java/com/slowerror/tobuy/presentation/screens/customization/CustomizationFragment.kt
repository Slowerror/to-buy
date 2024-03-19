package com.slowerror.tobuy.presentation.screens.customization

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.slowerror.tobuy.R
import com.slowerror.tobuy.databinding.FragmentCustomizationBinding
import com.slowerror.tobuy.domain.model.Category
import com.slowerror.tobuy.presentation.base.BaseFragment

class CustomizationFragment : BaseFragment(), CategoryOnClickInterface {

    private var _binding: FragmentCustomizationBinding? = null
    private val binding get() = _binding!!

    private val customizationController = CustomizationController(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCustomizationBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.profileRw.setController(customizationController)

        sharedViewModel.categoryListLiveData.observe(viewLifecycleOwner) { categories ->
            customizationController.categories = categories
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClickButtonToAddCategory() {
        navigateTo(R.id.action_profileFragment_to_addCategoryFragment)
    }

    override fun onPrioritySelected(priorityName: String) {
        navigateTo(
            CustomizationFragmentDirections
                .actionProfileFragmentToCustomColorPickerFragment(priorityName)
        )
    }

    override fun removeCategory(category: Category) {
        sharedViewModel.removeCategory(category)
    }
}