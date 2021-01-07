package com.cornell.daily.sun.data

data class Section(val sectionType: SectionType, var posts: List<PostInfoDict>) {
    fun filterPosts(postID: Int) {
        posts = posts.filter { post -> postID != post.id }
    }

    fun clamp(count: Int) {
        if (posts.size > count) {
            posts = posts.slice(0..count)
        }
    }
}