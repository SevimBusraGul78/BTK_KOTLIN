package com.a.b.sqllite

import android.os.Bundle
import androidx.navigation.NavDirections
import kotlin.Int
import kotlin.String

public class ListeFragmentDirections private constructor() {
  private data class ActionListeFragmentToTarifFragment(
    public val bilgi: String,
    public val id: Int,
  ) : NavDirections {
    public override val actionId: Int = R.id.action_listeFragment_to_tarifFragment

    public override val arguments: Bundle
      get() {
        val result = Bundle()
        result.putString("bilgi", this.bilgi)
        result.putInt("id", this.id)
        return result
      }
  }

  private data class ActionListeFragmentToTarifFragment2(
    public val bilgi: String,
    public val id: Int,
  ) : NavDirections {
    public override val actionId: Int = R.id.action_listeFragment_to_tarifFragment2

    public override val arguments: Bundle
      get() {
        val result = Bundle()
        result.putString("bilgi", this.bilgi)
        result.putInt("id", this.id)
        return result
      }
  }

  public companion object {
    public fun actionListeFragmentToTarifFragment(bilgi: String, id: Int): NavDirections =
        ActionListeFragmentToTarifFragment(bilgi, id)

    public fun actionListeFragmentToTarifFragment2(bilgi: String, id: Int): NavDirections =
        ActionListeFragmentToTarifFragment2(bilgi, id)
  }
}
