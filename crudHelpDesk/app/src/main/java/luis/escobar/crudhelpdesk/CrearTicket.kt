package luis.escobar.crudhelpdesk

import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Im
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import modelo.ClaseConexion
import java.util.UUID

class CrearTicket : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_crear_ticket)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets

        }

        val autorRecibido =  intent.getStringExtra("Autor")


        val lblAutorTicket = findViewById<TextView>(R.id.lblAutorTicket)
        val txtTituloTicket = findViewById<EditText>(R.id.txtTituloTicket)
        val txtDescripcionTicket = findViewById<EditText>(R.id.txtDescripcionTicket)
        val txtEmailAutor = findViewById<EditText>(R.id.txtEmailAutor)
        val btnAgregarTicket = findViewById<Button>(R.id.btnAgregarTicket)

        val spEstadoTicket = findViewById<Spinner>(R.id.spEstadoTicket)
        val datosSpinner = listOf("Activo", "Inactivo")

        lblAutorTicket.text = autorRecibido

        val btnBackCrear = findViewById<ImageView>(R.id.bntBackCrear)

        btnBackCrear.setOnClickListener{
            val intent = Intent(this@CrearTicket, MainActivity::class.java)
            startActivity(intent)
        }

        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.Main){
                val myadapter = ArrayAdapter(this@CrearTicket, android.R.layout.simple_spinner_item, datosSpinner)
                spEstadoTicket.adapter = myadapter
            }
        }

        btnAgregarTicket.setOnClickListener{
            GlobalScope.launch(Dispatchers.IO){
                try {
                    val objConexion = ClaseConexion().cadenaConexion()

                    if (txtTituloTicket.text.toString() == "" || txtDescripcionTicket.text.toString() == "" || txtEmailAutor.text.toString() == ""){
                        withContext(Dispatchers.Main){
                            Toast.makeText(this@CrearTicket, "Rellena todos los campos correctamente", Toast.LENGTH_LONG).show()
                        }
                    }
                    else {

                        val crearTicket = objConexion?.prepareStatement("insert into Ticket (uuidTicket, titulo, descripcion, autor, email, estado) values (?, ?, ?, ?, ?, ?)")!!
                        crearTicket.setString(1, UUID.randomUUID().toString())
                        crearTicket.setString(2, txtTituloTicket.text.toString())
                        crearTicket.setString(3, txtDescripcionTicket.text.toString())
                        crearTicket.setString(4, lblAutorTicket.text.toString())
                        crearTicket.setString(5, txtEmailAutor.text.toString())
                        crearTicket.setString(6, spEstadoTicket.selectedItem.toString())
                        crearTicket.executeQuery()

                        withContext(Dispatchers.Main){
                            Toast.makeText(this@CrearTicket, "Ticket creado correctamente", Toast.LENGTH_LONG).show()
                            txtTituloTicket.setText("")
                            txtDescripcionTicket.setText("")
                            txtEmailAutor.setText("")
                        }
                    }
                }catch (ex: Exception){
                    withContext(Dispatchers.Main){
                        Toast.makeText(this@CrearTicket, ex.message, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
}