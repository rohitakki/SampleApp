package com.movie.app.base.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import com.movie.app.base.activity.BaseActivity
import com.movie.app.base.viewmodel.BaseViewModel

abstract class BaseFragment<T : ViewDataBinding, V : BaseViewModel> : Fragment(), LifecycleOwner {

    private var mActivity: BaseActivity<T, V>? = null
    private var mViewDataBinding: T? = null
    private var mViewModel: V? = null


    abstract fun getBindingVariable(): Int
    @LayoutRes
    abstract fun getLayoutId(): Int


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BaseActivity<*, *>) {
            mActivity
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mViewDataBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        return mViewDataBinding?.root
    }


    override fun onDetach() {
        mActivity = null
        super.onDetach()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewDataBinding!!.setVariable(getBindingVariable(), mViewModel)
        mViewDataBinding!!.executePendingBindings()
    }

    open fun getBaseActivity(): BaseActivity<T, V>? {
        return mActivity
    }

    open fun getViewDataBinding(): T? {
        return mViewDataBinding
    }

    open fun getViewModel(): V? {
        return mViewModel
    }
}

