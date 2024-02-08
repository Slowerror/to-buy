package com.slowerror.tobuy.presentation

import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.activityViewModel

abstract class BaseFragment : Fragment() {
    protected val sharedViewModel by activityViewModel<BaseViewModel>()
}