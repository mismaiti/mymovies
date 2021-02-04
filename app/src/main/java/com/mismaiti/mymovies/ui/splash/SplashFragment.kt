package com.mismaiti.mymovies.ui.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.mismaiti.mymovies.BuildConfig
import com.mismaiti.mymovies.R
import kotlin.concurrent.fixedRateTimer

class SplashFragment : Fragment() {

    private lateinit var navController: NavController

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_splash, container, false)
        val textView: TextView = root.findViewById(R.id.text_app_version)
        textView.text = getString(R.string.app_version, BuildConfig.VERSION_NAME)
        return root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fixedRateTimer("timer", false, 0, 2000) {
            this@SplashFragment.run {
                this@SplashFragment.view?.let {
                    val action = SplashFragmentDirections.actionNavigationSplashToNavigationIntroduction()
                    navController = Navigation.findNavController(requireView())
                    navController.navigate(action)
                }
            }
        }
    }
}