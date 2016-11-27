package ru.medyannikov.videoviewer.ui.base

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import butterknife.ButterKnife
import butterknife.Unbinder

abstract class BaseActivity : AppCompatActivity() {

  private var unbinder: Unbinder? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(getLayout())
    unbinder = ButterKnife.bind(this)
  }

  override fun onDestroy() {
    super.onDestroy()
    unbinder?.unbind()
  }

  abstract fun getLayout() : Int
}