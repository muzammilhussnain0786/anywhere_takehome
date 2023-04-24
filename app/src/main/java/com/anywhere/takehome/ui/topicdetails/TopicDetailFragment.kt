package com.anywhere.takehome.ui.topicdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.anywhere.takehome.data.remote.models.RelatedTopic
import com.anywhere.takehome.databinding.TopicDetailFragmentBinding
import com.anywhere.takehome.ui.main.MainViewModel

class TopicDetailFragment : Fragment() {

    private lateinit var binding: TopicDetailFragmentBinding
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = TopicDetailFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.currentTopic.observe(viewLifecycleOwner){
            updateUi(it)
        }
    }

    private fun updateUi(_topic: RelatedTopic?) {
        _topic?.let {
            binding.apply {
                topic = it
                executePendingBindings()
            }
        }
    }

}