package luis.escobar.crudhelpdesk

import RecyclerViewHelpers.Adaptador
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import modelo.ClaseConexion
import modelo.Tickets

class ListaTickets : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_lista_tickets)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val rcvTickets = findViewById<RecyclerView>(R.id.rcvTickets)

        rcvTickets.layoutManager = LinearLayoutManager(this)

        val autorRecibido =  intent.getStringExtra("Autor")

        fun mostrarTickets(): List<Tickets> {

            val objConexion = ClaseConexion().cadenaConexion()
            val statement = objConexion?.createStatement()
            val resultSet =statement?.executeQuery("select * from Ticket")!!

            val ticketlistado = mutableListOf<Tickets>()

            while(resultSet.next()){
                        val uuidTicket = resultSet.getString("uuidTicket")
                        val titulo = resultSet.getString("titulo")
                        val descripcion = resultSet.getString("descripcion")
                        val autor = resultSet.getString("autor")
                        val email = resultSet.getString("email")
                        val estado = resultSet.getString("estado")

                        val ticketCompleto = Tickets(uuidTicket, titulo, descripcion, autor, email, estado)
                        ticketlistado.add(ticketCompleto)

            }

            return ticketlistado

        }

        CoroutineScope(Dispatchers.IO).launch {
            val  ticketDB = mostrarTickets()
            withContext(Dispatchers.Main){
                val adapter = Adaptador(ticketDB)
                rcvTickets.adapter = adapter
            }
        }

        val btnBackList = findViewById<ImageView>(R.id.btnBackList)

        btnBackList.setOnClickListener{
            val intent = Intent(this@ListaTickets, MainActivity::class.java)
            startActivity(intent)
        }

    }
}