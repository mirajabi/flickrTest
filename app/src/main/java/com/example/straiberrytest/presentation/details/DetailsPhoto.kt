package com.example.straiberrytest.presentation.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.straiberrytest.R
import com.example.straiberrytest.domain.model.ErrorModel
import com.example.straiberrytest.presentation.PhotosFragment
import com.example.straiberrytest.presentation.StrAiBerryViewModel
import com.example.straiberrytest.util.gone
import com.example.straiberrytest.util.loadImageFull
import com.example.straiberrytest.util.show
import com.example.straiberrytest.util.toast
import com.pnikosis.materialishprogress.ProgressWheel
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.bottom_detail_view.view.*
import kotlinx.android.synthetic.main.photo_detail.view.*
import javax.inject.Inject


class DetailsPhoto : DaggerFragment() {

    private var param1: String? = null
    private lateinit var root: View
    private lateinit var progressCollection: ProgressWheel
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModelphoto: StrAiBerryViewModel by lazy {
        ViewModelProvider(requireActivity(), viewModelFactory)
            .get(StrAiBerryViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.photo_detail, container, false)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        progressCollection = root.findViewById(R.id.progressCollection)

        initDetailsPhoto(view)

    }

    private fun initDetailsPhoto(view: View){
        viewModelphoto.loadPhotoDetails(param1)
        viewModelphoto.isLoad.observe(requireActivity(), Observer {
            it?.let { isShowing ->
                if (isShowing) {
                    view.bottom_sheet.gone()
                    view.iv_thumbnail_large.gone()
                    view.progressCollection.show()
                } else {
                    view.bottom_sheet.show()
                    view.iv_thumbnail_large.show()
                    view.progressCollection.gone()
                }
            }
        })

        viewModelphoto.getPhotoDetailsReceivedLiveData.observe(
            requireActivity(),
            Observer { modelDetailsBook ->
                modelDetailsBook?.let { it ->
                    view.iv_thumbnail_large.loadImageFull(it.photo.imageUrlLarge)
                    view.toolbar.title = it.photo.title.toString()
                    view.tv_description.text = it.photo.description.content
                    view.tv_owner.text = context?.resources?.getString(R.string.msg_owner_name, it.photo.owner.username)
                    view.tv_date.text = context?.resources?.getString(R.string.msg_post, it.photo.dates.taken)
                    view.tv_view_count.text = it.photo.views
                    view.tv_comment_count.text = it.photo.comments.content
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


    override fun onDestroy() {
        super.onDestroy()
    }

    companion object {
        private const val ARG_PARAM1 = "transaction"

        @JvmStatic
        fun newInstance() =
            PhotosFragment().apply {
                arguments = Bundle().apply {
//                    putLong(ARG_PARAM1, id)
                }
            }
    }

}