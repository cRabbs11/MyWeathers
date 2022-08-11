package com.ekochkov.myweathers.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ekochkov.myweathers.databinding.FragmentAddPointBinding

class AddPointFragment: Fragment() {

    private lateinit var binding: FragmentAddPointBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddPointBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        //val ADDING_METHODS = arrayOf(
        //    getString(R.string.add_by_auto_coords),
        //    getString(R.string.add_by_manual_coords),
        //    getString(R.string.add_by_input_city),
        //    getString(R.string.add_by_google_map)
        //)
//
        //val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, ADDING_METHODS)
        //val text = binding.addingMethods.editText as AutoCompleteTextView
        //text.setAdapter(adapter)
    }
}