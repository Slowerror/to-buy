package com.slowerror.tobuy.presentation.screens.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import com.airbnb.epoxy.EpoxyTouchHelper
import com.slowerror.tobuy.R
import com.slowerror.tobuy.databinding.FragmentHomeBinding
import com.slowerror.tobuy.domain.model.Item
import com.slowerror.tobuy.presentation.base.BaseFragment
import com.slowerror.tobuy.presentation.screens.home.bottom_sheet.SortOrderBottomSheetDialogFragment

class HomeFragment : BaseFragment(), ItemOnClickInterface {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

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

        sharedViewModel.homeViewStateLiveData.observe(viewLifecycleOwner) { state ->
            epoxyController.viewState = state
        }

        EpoxyTouchHelper.initSwiping(binding.epoxyRw)
            .right()
            .withTarget(HomeController.ItemEpoxyModel::class.java)
            .andCallbacks(object :
                EpoxyTouchHelper.SwipeCallbacks<HomeController.ItemEpoxyModel>() {
                override fun onSwipeCompleted(
                    model: HomeController.ItemEpoxyModel?,
                    itemView: View?,
                    position: Int,
                    direction: Int
                ) {
                    val itemThatWasRemoved = model?.item ?: return
                    sharedViewModel.removeItem(itemThatWasRemoved.item)
                }
            })

        menuOptions()
    }

    private fun menuOptions() {
        (requireActivity() as MenuHost).addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_home_fragment, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.listSort -> {
                        SortOrderBottomSheetDialogFragment().show(childFragmentManager, null)
                        true
                    }

                    else -> false
                }
            }

        }, viewLifecycleOwner, Lifecycle.State.CREATED)

    }


    override fun onResume() {
        super.onResume()
        hideKeyboard(requireView())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onBumpPriority(item: Item) {
        sharedViewModel.onBumpPriority(item)
    }

    override fun onItemSelected(item: Item) {
        navigateTo(HomeFragmentDirections.actionHomeFragmentToAddItemFragment(item.id))
    }
}