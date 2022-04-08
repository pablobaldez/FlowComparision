package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.myapplication.databinding.FragmentFirstBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class StateFragment : Fragment(){

    private var _binding: FragmentFirstBinding? = null
    private val viewModel: StateViewModel by viewModels()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonFirst.setOnClickListener {
            viewModel.nextRandom()
        }
        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.viewModelState.flowWithLifecycle(viewLifecycleOwner.lifecycle).collect {
                binding.bind(it)
            }
        }
    }

    private fun FragmentFirstBinding.bind(state: UiState) {
        if (state.loading) {
            progressCircular.visibility = View.VISIBLE
            textviewIsEven.visibility = View.INVISIBLE
        } else {
            progressCircular.visibility = View.INVISIBLE
            textviewIsEven.visibility = View.VISIBLE
        }
        textviewValue.text = state.value.toString()
        textviewIsEven.text = state.isEven
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}