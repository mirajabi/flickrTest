package com.example.straiberrytest

import android.os.Bundle
import android.os.Handler
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.paging.LoadState
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.straiberrytest.domain.model.ErrorModel
import com.example.straiberrytest.presentation.StrAiBerryViewModel
import com.example.straiberrytest.presentation.main.adapter.AdapterPhotoFlickr
import com.example.straiberrytest.presentation.main.adapter.FlickrLoadStateAdapter
import com.example.straiberrytest.util.calculateNoOfColumns
import com.example.straiberrytest.util.gone
import com.example.straiberrytest.util.show
import com.example.straiberrytest.util.toast
import com.pnikosis.materialishprogress.ProgressWheel
import dagger.android.support.DaggerAppCompatActivity
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {

    private val mDisposable = CompositeDisposable()
    private lateinit var adapterFlickr: AdapterPhotoFlickr
    private lateinit var liManager: LinearLayoutManager
    private lateinit var progressCollection: ProgressWheel
    private lateinit var CollectionPhoto: RecyclerView
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModelphoto: StrAiBerryViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)
            .get(StrAiBerryViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progressCollection = findViewById(R.id.progressCollection)
        CollectionPhoto = findViewById(R.id.CollectionPhoto)

        initAdapterPhotos()
        initDetailsPhoto()

        mDisposable.add(viewModelphoto.loadFlickrDefaultPhotos().subscribe {
            adapterFlickr.submitData(lifecycle, it)
        })


    }

    private fun initDetailsPhoto(){
        viewModelphoto.loadPhotoDetails("50724746773")
        viewModelphoto.isLoad.observe(this, Observer {
            it?.let { isShowing ->
                if (isShowing) {
                } else {
                }
            }
        })

        viewModelphoto.getPhotoDetailsReceivedLiveData.observe(
            this,
            Observer { modelDetailsBook ->
                modelDetailsBook?.let { it ->
                    toast(it.photo.secret)
                }
            })

        viewModelphoto.errorModel.observe(this, Observer {
            it?.let {
                if (it.errorStatus == ErrorModel.ErrorStatus.NO_CONNECTION || it.errorStatus == ErrorModel.ErrorStatus.NOT_DEFINED) {
                    toast(it.getErrorMessage())
                } else {
                    toast(it.getErrorMessage())
                }
            }
        })
    }

    private fun initAdapterPhotos() {
        liManager = GridLayoutManager(this, calculateNoOfColumns(this))
        CollectionPhoto.layoutManager = liManager
        adapterFlickr = AdapterPhotoFlickr()
        CollectionPhoto.adapter = adapterFlickr.withLoadStateFooter(
            footer = FlickrLoadStateAdapter { adapterFlickr.retry() }
        )
        (CollectionPhoto.itemAnimator as DefaultItemAnimator).supportsChangeAnimations = false
        adapterFlickr.addLoadStateListener { loadState ->
            if (loadState.refresh is LoadState.Loading) {
                progressCollection.show()
            } else {
                progressCollection.gone()
                val errorState = when {
                    loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                    loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                    loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                    else -> null
                }
                errorState?.let {
                    println(it.error.message.toString())
                }
            }
        }
    }

    override fun onDestroy() {
        mDisposable.dispose()
        super.onDestroy()
    }

}