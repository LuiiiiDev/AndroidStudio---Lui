package luis.escobar.crudhelpdesk

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnVerTicket = findViewById<Button>(R.id.btnVerTicket)
        val btnCrearTicket = findViewById<Button>(R.id.btnCrearTicket)
        val usuario = intent.getStringExtra("usuario")

        btnCrearTicket.setOnClickListener{
            val intent = Intent(this@MainActivity, CrearTicket::class.java)
            intent.putExtra("Autor", usuario)
            startActivity(intent)
        }

        btnVerTicket.setOnClickListener{
            val intent = Intent(this@MainActivity, ListaTickets::class.java)
            intent.putExtra("Autor", usuario)
            startActivity(intent)
        }

        val btnBackMain = findViewById<ImageView>(R.id.btnBackMain)

        btnBackMain.setOnClickListener{
            val intent = Intent(this@MainActivity, Login::class.java)
            startActivity(intent)
        }
    }
}