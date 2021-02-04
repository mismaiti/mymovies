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

        fragmentBinding.rlSuccessInputName.visibility = View.GONE
        fragmentBinding.ivCheck.visibility = View.INVISIBLE
        fragmentBinding.tvWarningMessage.visibility = View.GONE

        fragmentBinding.etInputNickname.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // do nothing
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s != null) {
                    when (s.length) {
                        in 0..5 -> {
                            fragmentBinding.tvWarningMessage.visibility = View.VISIBLE
                            fragmentBinding.rlSuccessInputName.visibility = View.GONE
                            fragmentBinding.ivCheck.visibility = View.INVISIBLE
                        }
                        else -> {
                            fragmentBinding.tvWarningMessage.visibility = View.GONE
                            fragmentBinding.ivCheck.visibility = View.VISIBLE
                            fragmentBinding.rlSuccessInputName.visibility = View.VISIBLE
                            fragmentBinding.tvSuccessGreeting.text =
                                getString(R.string.nice_to_meet_you_jarvis_now_you_can_continue,
                                    fragmentBinding.etInputNickname.text.toString())

                        }
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) {
                // do nothing
            }

        })
        fragmentBinding.btnContinue.setOnClickListener{
             val nickname = fragmentBinding.etInputNickname.text.toString()
             val action = IntroductionFragmentDirections.actionNavigationIntroductionToNavigationDashboard(nickname)
             Navigation.findNavController(requireView()).navigate(action)
        }
    }


}