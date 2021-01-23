package com.cornell.daily.sun.data

import com.google.gson.annotations.SerializedName

data class Post(@SerializedName(value = "post_info_dict") val postInfoDict: PostInfoDict)

