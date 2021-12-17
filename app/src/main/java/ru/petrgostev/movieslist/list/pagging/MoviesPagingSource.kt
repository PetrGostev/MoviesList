package ru.petrgostev.movieslist.list.pagging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import retrofit2.HttpException
import ru.petrgostev.movieslist.ResultsItem
import ru.petrgostev.movieslist.repository.Repository
import java.io.IOException


class MoviesPagingSource (
    private val repository: Repository,
) : PagingSource<Int, ResultsItem>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ResultsItem> {
        val position = params.key ?: STARTING_PAGE
        return try {
            val moviesEntities = repository.getResponse(position)

            LoadResult.Page(
                data = moviesEntities,
                prevKey = if (position == STARTING_PAGE) null else position - NETWORK_PAGE_SIZE,
                nextKey = if (moviesEntities.isEmpty()) null else position + NETWORK_PAGE_SIZE
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ResultsItem>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    companion object {
        private const val STARTING_PAGE = 1
        private const val NETWORK_PAGE_SIZE = 20
    }
}