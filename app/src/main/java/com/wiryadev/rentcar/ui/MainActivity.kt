package com.wiryadev.rentcar.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.wiryadev.rentcar.databinding.ActivityMainBinding
import com.wiryadev.rentcar.model.GetAllCarResponseItem

class MainActivity : AppCompatActivity(), MainView {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainAdapter: MainAdapter
    private lateinit var presenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter = MainPresenterImpl(this)

        presenter.getList()

        mainAdapter = MainAdapter(object : MainAdapter.OnClickListener {
            override fun onItemClicked(data: GetAllCarResponseItem) {
                Toast.makeText(baseContext, data.name, Toast.LENGTH_LONG).show()
            }
        })

        binding.rvCars.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mainAdapter
        }
    }

    override fun onLoading() {
        binding.progressBar.visibility = View.VISIBLE
    }

    override fun onDismissLoading() {
        binding.progressBar.visibility = View.GONE
    }

    override fun onSuccess(response: List<GetAllCarResponseItem>) {
        mainAdapter.submitData(response)
    }

    override fun onFailed(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onUiDestroyed()
    }

}