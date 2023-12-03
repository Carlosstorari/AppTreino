package com.chscorp.apptreino.repository

import androidx.annotation.StringRes

class Resource<T>(val content: T, @StringRes val error: Int? = null)