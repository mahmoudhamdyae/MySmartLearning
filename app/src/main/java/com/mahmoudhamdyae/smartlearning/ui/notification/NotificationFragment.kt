package com.mahmoudhamdyae.smartlearning.ui.notification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.mahmoudhamdyae.smartlearning.base.BaseFragment
import com.mahmoudhamdyae.smartlearning.data.repository.FirebaseRepository
import com.mahmoudhamdyae.smartlearning.databinding.FrgmentNotificationBinding

class NotificationFragment: BaseFragment() {

    private lateinit var binding: FrgmentNotificationBinding
    override val viewModel: NotificationViewModel by activityViewModels {
        NotificationViewModelFactory(FirebaseRepository())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FrgmentNotificationBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.notificationsList.layoutManager = GridLayoutManager(context, 1)
        binding.notificationsList.adapter = NotificationAdapter(NotificationAdapter.OnClickListener{
            Toast.makeText(context, it.text, Toast.LENGTH_SHORT).show()
        })
    }
}