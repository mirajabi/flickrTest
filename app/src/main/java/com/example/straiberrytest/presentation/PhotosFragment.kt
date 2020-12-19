package com.example.straiberrytest.presentation


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.paging.LoadState
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.straiberrytest.R
import com.example.straiberrytest.domain.model.ErrorModel
import com.example.straiberrytest.presentation.main.adapter.AdapterPhotoFlickr
import com.example.straiberrytest.presentation.main.adapter.FlickrLoadStateAdapter
import com.example.straiberrytest.util.calculateNoOfColumns
import com.example.straiberrytest.util.gone
import com.example.straiberrytest.util.show
import com.example.straiberrytest.util.toast
import com.pnikosis.materialishprogress.ProgressWheel
import dagger.android.support.DaggerFragment
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject


class PhotosFragment : DaggerFragment() {

    private lateinit var root: View
    private val mDisposable = CompositeDisposable()
    private lateinit var adapterFlickr: AdapterPhotoFlickr
    private lateinit var liManager: LinearLayoutManager
    private lateinit var progressCollection: ProgressWheel
    private lateinit var CollectionPhoto: RecyclerView
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModelphoto: StrAiBerryViewModel by lazy {
        ViewModelProvider(requireActivity(), viewModelFactory)
            .get(StrAiBerryViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.photo_fragment, container, false)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        progressCollection = root.findViewById(R.id.progressCollection)
        CollectionPhoto = root.findViewById(R.id.CollectionPhoto)

        initAdapterPhotos()
        initDetailsPhoto()

        mDisposable.add(viewModelphoto.loadFlickrDefaultPhotos().subscribe {
            adapterFlickr.submitData(lifecycle, it)
        })

    }


    private fun initDetailsPhoto(){
        viewModelphoto.loadPhotoDetails("50724746773")
        viewModelphoto.isLoad.observe(requireActivity(), Observer {
            it?.let { isShowing ->
                if (isShowing) {
                } else {
                }
            }
        })

        viewModelphoto.getPhotoDetailsReceivedLiveData.observe(
            requireActivity(),
            Observer { modelDetailsBook ->
                modelDetailsBook?.let { it ->
                }
            })

        viewModelphoto.errorModel.observe(requireActivity(), Observer {
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
        liManager = GridLayoutManager(requireActivity(), calculateNoOfColumns(requireActivity()))
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

    companion object {
        val KEY_ALBUM_ID = "KEY_ALBUM_ID"

        @JvmStatic
        fun newInstance() =
            PhotosFragment().apply {
                arguments = Bundle().apply {
//                    putLong(KEY_ALBUM_ID, id)
                }
            }
    }

}