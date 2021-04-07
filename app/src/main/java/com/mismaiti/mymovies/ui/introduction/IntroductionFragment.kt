package com.mismaiti.mymovies.ui.introduction

import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.mismaiti.mymovies.R
import com.mismaiti.mymovies.databinding.FragmentIntroductionBinding
import com.mismaiti.mymovies.util.*
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class IntroductionFragment : DaggerFragment() {

    private lateinit var fragmentBinding: FragmentIntroductionBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentBinding = FragmentIntroductionBinding.inflate(inflater, container, false)
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(fragmentBinding) {
            rlSuccessInputName.show()
            ivCheck.show()
            tvWarningMessage.hide()
            etInputNickname.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                    // do nothing
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (s != null) {
                        when (s.length) {
                            in 0..5 -> {
                                tvWarningMessage.show()
                                rlSuccessInputName.hide()
                                ivCheck.invisible()
                                tvSuccessGreeting.hide()

                            }
                            else -> {
                                tvWarningMessage.hide()
                                ivCheck.show()
                                rlSuccessInputName.show()
                                tvSuccessGreeting.show()
                                tvSuccessGreeting.set(
                                    getString(R.string.nice_to_meet_you_jarvis_now_you_can_continue,
                                        etInputNickname.get())
                                )

                            }
                        }
                    }
                }

                override fun afterTextChanged(s: Editable?) {
                    // do nothing
                }

            })
            btnContinue.setOnClickListener{
                val nickname = etInputNickname.get()
                val action = IntroductionFragmentDirections.actionNavigationIntroductionToNavigationDashboard(nickname)
                Navigation.findNavController(requireView()).navigate(action)
            }
        }

    }


}