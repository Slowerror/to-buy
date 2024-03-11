package com.slowerror.tobuy.presentation.screens.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.slowerror.tobuy.R
import com.slowerror.tobuy.databinding.FragmentProfileBinding
import com.slowerror.tobuy.presentation.base.BaseFragment

class ProfileFragment : BaseFragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val profileController = ProfileController(
        onCategoryEmptyStateClicked = ::onCategoryEmptyStateClicked
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.profileRw.setController(profileController)

        sharedViewModel.categoryListLiveData.observe(viewLifecycleOwner) { categories ->
            profileController.categories = categories
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onCategoryEmptyStateClicked() {
        navigateTo(R.id.action_profileFragment_to_addCategoryFragment)
    }
}