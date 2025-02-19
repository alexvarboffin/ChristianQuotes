package com.christianquotestoinspire.bibleverses.motivation.author

import com.walhalla.core.domain.entity.Author

interface AuthorView {
    fun showAuthors(authors: List<Author>)
    fun onRetrievalFailed(s: String?)
}
