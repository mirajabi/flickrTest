package com.example.straiberrytest.presentation.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.straiberrytest.R
import kotlinx.android.synthetic.main.item_progress.view.*


class FlickrLoadStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<FlickrLoadStateAdapter.LoadStateViewHolder>() {

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {

        val progress = holder.itemView.loadmore_progress
        val btnRetry = holder.itemView.loadmore_retry
        val txtErrorMessage = holder.itemView.loadmore_errortxt
        val layoutError = holder.itemView.loadmore_errorlayout

        layoutError.isVisible = loadState !is LoadState.Loading
        btnRetry.isVisible = loadState !is LoadState.Loading
        txtErrorMessage.isVisible = loadState !is LoadState.Loading
        progress.isVisible = loadState is LoadState.Loading

        if (loadState is LoadState.Error) {
            when(loadState.error.localizedMessage){
                "Unable to resolve host \"dorj.store\": No address associated with hostname" ->{
                    txtErrorMessage.text = "اتصال به سرور برقرار نشد, تلاش مجدد"
                }
            }
        }

        btnRetry.setOnClickListener {
            retry.invoke()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        return LoadStateViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_progress, parent, false)
        )
    }

    class LoadStateViewHolder(private val view: View) : RecyclerView.ViewHolder(view)
}