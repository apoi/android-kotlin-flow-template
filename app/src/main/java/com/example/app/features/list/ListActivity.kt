package com.example.app.features.list

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.example.app.R
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.list_activity.*

class ListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.list_activity)
        setSupportActionBar(main_toolbar)

        main_fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .addToBackStack("main")
                .replace(R.id.main_container, ListFragment())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit()
        }
    }
}
