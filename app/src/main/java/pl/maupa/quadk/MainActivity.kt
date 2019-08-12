package pl.maupa.quadk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        text.text = try {
            if (Quadk().isOpened()) {
                "TAK!"
            } else {
                "NIE!"
            }
        } catch (e : NumberFormatException) {
            "Zły format daty"
        }

}
}
