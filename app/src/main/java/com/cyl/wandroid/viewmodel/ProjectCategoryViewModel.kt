package com.cyl.wandroid.viewmodel

import androidx.lifecycle.MutableLiveData
import com.cyl.wandroid.base.BaseRecyclerViewModel
import com.cyl.wandroid.base.BaseViewModel
import com.cyl.wandroid.http.bean.ArticleBean
import com.cyl.wandroid.http.bean.ProjectCategoryBean
import com.cyl.wandroid.http.bean.PublicAccountBean
import com.cyl.wandroid.repository.HomePublicAccountRepository
import com.cyl.wandroid.repository.ProjectCategoryRepository
import com.cyl.wandroid.repository.PublicAccountArticlesRepository

class ProjectCategoryViewModel : BaseViewModel() {
    private val projectCategoryRepository by lazy { ProjectCategoryRepository() }
    val categories: MutableLiveData<List<ProjectCategoryBean>> = MutableLiveData()

    fun getProjectCategory() {
        launch(block = {
            categories.value = projectCategoryRepository.getProjectCategory()
        })
    }
}