package com.a.b.sqllite

import android.os.Bundle
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavArgs
import java.lang.IllegalArgumentException
import kotlin.Int
import kotlin.String
import kotlin.jvm.JvmStatic

public data class NavGraphArgs(
  public val bilgi: String,
  public val id: Int,
) : NavArgs {
  public fun toBundle(): Bundle {
    val result = Bundle()
    result.putString("bilgi", this.bilgi)
    result.putInt("id", this.id)
    return result
  }

  public fun toSavedStateHandle(): SavedStateHandle {
    val result = SavedStateHandle()
    result.set("bilgi", this.bilgi)
    result.set("id", this.id)
    return result
  }

  public companion object {
    @JvmStatic
    public fun fromBundle(bundle: Bundle): NavGraphArgs {
      bundle.setClassLoader(NavGraphArgs::class.java.classLoader)
      val __bilgi : String?
      if (bundle.containsKey("bilgi")) {
        __bilgi = bundle.getString("bilgi")
        if (__bilgi == null) {
          throw IllegalArgumentException("Argument \"bilgi\" is marked as non-null but was passed a null value.")
        }
      } else {
        throw IllegalArgumentException("Required argument \"bilgi\" is missing and does not have an android:defaultValue")
      }
      val __id : Int
      if (bundle.containsKey("id")) {
        __id = bundle.getInt("id")
      } else {
        throw IllegalArgumentException("Required argument \"id\" is missing and does not have an android:defaultValue")
      }
      return NavGraphArgs(__bilgi, __id)
    }

    @JvmStatic
    public fun fromSavedStateHandle(savedStateHandle: SavedStateHandle): NavGraphArgs {
      val __bilgi : String?
      if (savedStateHandle.contains("bilgi")) {
        __bilgi = savedStateHandle["bilgi"]
        if (__bilgi == null) {
          throw IllegalArgumentException("Argument \"bilgi\" is marked as non-null but was passed a null value")
        }
      } else {
        throw IllegalArgumentException("Required argument \"bilgi\" is missing and does not have an android:defaultValue")
      }
      val __id : Int?
      if (savedStateHandle.contains("id")) {
        __id = savedStateHandle["id"]
        if (__id == null) {
          throw IllegalArgumentException("Argument \"id\" of type integer does not support null values")
        }
      } else {
        throw IllegalArgumentException("Required argument \"id\" is missing and does not have an android:defaultValue")
      }
      return NavGraphArgs(__bilgi, __id)
    }
  }
}
