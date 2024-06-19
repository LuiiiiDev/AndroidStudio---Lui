package luis.escobar.crudhelpdesk

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class InfoTicket : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_info_ticket)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val uuidTicketRecibido = intent.getStringExtra("uuidTicket")
        val tituloRecibido = intent.getStringExtra("titulo")
        val descripcionRecibida = intent.getStringExtra("descripcion")
        val emailRecibido = intent.getStringExtra("email")
        val autorRecibido = intent.getStringExtra("autor")
        val estadoRecibido = intent.getStringExtra("estado")


        val lblUuidInfo = findViewById<TextView>(R.id.lblUuidInfo)
        val lblAutorInfo = findViewById<TextView>(R.id.lblAutorInfor)
        val lblTituloInfo = findViewById<TextView>(R.id.lblTituloInfo)
        val lblDescripcionInfo = findViewById<TextView>(R.id.lblDescripcionInfo)
        val lblEmailInfo = findViewById<TextView>(R.id.lblCorreoInfo)
        val lblEstadoInfo = findViewById<TextView>(R.id.lblEstadoInfo)

        lblUuidInfo.text = uuidTicketRecibido
        lblAutorInfo.text =autorRecibido
        lblTituloInfo.text = tituloRecibido
        lblDescripcionInfo.text = descripcionRecibida
        lblEmailInfo.text = emailRecibido
        lblEstadoInfo.text = estadoRecibido

        val btnBack = findViewById<ImageView>(R.id.btnBackInfo)

        btnBack.setOnClickListener{
            val intent = Intent(this@InfoTicket, ListaTickets::class.java)
            startActivity(intent)
        }
    }
}