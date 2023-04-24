package com.anywhere.takehome.ui.topics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.anywhere.takehome.databinding.TopicsFragmentBinding
import com.anywhere.takehome.ui.main.MainActivity
import com.anywhere.takehome.ui.main.MainViewModel

class TopicsFragment : Fragment() {

    private lateinit var binding: TopicsFragmentBinding
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = TopicsFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.adapter = TopicsAdapter(onItemClicked = {
            viewModel.updateCurrentTopic(it)

            (requireActivity() as MainActivity).openPane()
        })

        binding.mainViewModel = viewModel

        viewModel.mediatorLiveData.observe(this.viewLifecycleOwner) {
            (binding.adapter as TopicsAdapter ).submitTopics(it)
        }
    }
}