package com.movie.app.modules.trending

import android.app.Application
import androidx.annotation.NonNull
import androidx.hilt.lifecycle.ViewModelInject
import com.movie.app.base.viewmodel.BaseViewModel
import com.movie.app.repository.DataRepository

class TrendingViewModel @ViewModelInject constructor(
    @NonNull application: Application,
    private val repository: DataRepository
) : BaseViewModel(application) {

}