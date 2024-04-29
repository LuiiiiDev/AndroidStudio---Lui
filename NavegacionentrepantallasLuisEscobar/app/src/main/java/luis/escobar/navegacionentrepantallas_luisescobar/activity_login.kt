package luis.escobar.navegacionentrepantallas_luisescobar

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class activity_login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //1- Mandar a llamar los elementos de la pantalla al codigo
        val txtCorreo = findViewById<EditText>(R.id.txtUsuario)
        val txtContraseña = findViewById<EditText>(R.id.txtContraseña)
        val btnIngresar = findViewById<Button>(R.id.btnIngresar)

        btnIngresar.setOnClickListener {
            //Navgacion entre pantallas
            val pantallaMenu = Intent(this, MainActivity:: class.java)
            startActivity(pantallaMenu)
        }
    }
}