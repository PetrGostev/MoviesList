package ru.petrgostev.movieslist.list.pagging

import androidx.paging.PagingSource
import retrofit2.HttpException
import ru.petrgostev.movieslist.ResultsItem
import ru.petrgostev.movieslist.repository.Repository
import ru.petrgostev.movieslist.utils.Page
import java.io.IOException

class MoviesPagingSource (
    private val repository: Repository,
) : PagingSource<Int, ResultsItem>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ResultsItem> {
        val position = params.key ?: Page.START_OFFSET
        return try {
            val moviesEntities = repository.getResponse(position)

            LoadResult.Page(
                data = moviesEntities,
                prevKey = if (position == Page.START_OFFSET) null else position - Page.PAGE_SIZE,
                nextKey = if (moviesEntities.isEmpty()) null else position + Page.PAGE_SIZE
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }
}