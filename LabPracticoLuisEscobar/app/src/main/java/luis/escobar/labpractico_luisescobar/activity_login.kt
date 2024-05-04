package luis.escobar.labpractico_luisescobar

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
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

        val txtCorreo = findViewById<TextView>(R.id.txtCorreo)
        val txtPass = findViewById<TextView>(R.id.txtPassword)
        val btnIngresar = findViewById<Button>(R.id.btnIngresar)

        btnIngresar.setOnClickListener {
            val correo = txtCorreo.text.toString()
            val pass = txtPass.text.toString()
            if ( correo == "" || pass == "")
            {
                Toast.makeText(this, "llena los campos", Toast.LENGTH_SHORT).show()
            }
            else{
                val pantallaMenu = Intent(this, MainActivity::class.java)
                startActivity(pantallaMenu)
            }

        }
    }
}