package com.cyl.wandroid.sp

import com.cyl.wandroid.http.bean.UserBean
import com.google.gson.Gson

class UserSpHelper(filename: String = "Wandroid") : CommonSpHelper(filename) {
    private val gSON by lazy { Gson() }

    companion object {
        private const val KEY_USER_INFO = "user_info"

        fun newHelper(): UserSpHelper = UserSpHelper()
    }

    fun isLogin(): Boolean {
        return getValue(KEY_USER_INFO, "").isNotEmpty()
    }

    fun saveUserInfo(userBean: UserBean) {
        setValue(KEY_USER_INFO, gSON.toJson(userBean))
    }

    fun getUserInfo(): UserBean? {
        val value = getValue(KEY_USER_INFO, "")
        return if (value.isEmpty()) {
            null
        } else {
            gSON.fromJson(value, UserBean::class.java)
        }
    }

    fun clearUserInfo() {
        removeOf(KEY_USER_INFO)
    }
}