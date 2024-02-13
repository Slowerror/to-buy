package com.slowerror.tobuy.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.slowerror.tobuy.R
import com.slowerror.tobuy.databinding.FragmentHomeBinding
import com.slowerror.tobuy.domain.model.Item
import com.slowerror.tobuy.presentation.base.BaseFragment

class HomeFragment : BaseFragment(), ItemOnClickInterface {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

//    private val viewModel by viewModel<BaseViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fab.setOnClickListener {
            navigateTo(R.id.action_homeFragment_to_addItemFragment)
        }

        val epoxyController = HomeController(this)
        binding.epoxyRw.setController(epoxyController)

        sharedViewModel.itemListLiveData.observe(viewLifecycleOwner) { itemList ->
            epoxyController.itemList = itemList as ArrayList<Item>
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDeleteItem(item: Item) {
        sharedViewModel.removeItem(item)
    }

    override fun onBumpPriority(item: Item) {
        TODO("Not yet implemented")
    }
}