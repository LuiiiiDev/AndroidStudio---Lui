package luis.escobar.crudhelpdesk

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
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

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val txtUsuarioLogin = findViewById<EditText>(R.id.txtUsuarioLogin)
        val txtContrasenaLogin = findViewById<EditText>(R.id.txtContrasenaLogin)
        val btnIngresarLogin = findViewById<Button>(R.id.btnIngresarLogin)
        val btnRegistrarseLogin = findViewById<Button>(R.id.btnRegistrarseLogin)


        btnIngresarLogin.setOnClickListener{


            GlobalScope.launch(Dispatchers.IO){
                try {
                    val objConexion = ClaseConexion().cadenaConexion()

                    val verificarUsuario = objConexion?.prepareStatement("select * from Usuario where usuario = ? and contrasena = ?")!!
                    verificarUsuario.setString(1, txtUsuarioLogin.text.toString())
                    verificarUsuario.setString(2, txtContrasenaLogin.text.toString())

                    val resultado = verificarUsuario.executeQuery()

                    if (resultado.next()){

                            val intentMain = Intent(this@Login, MainActivity::class.java)
                            intentMain.putExtra("usuario", txtUsuarioLogin.text.toString())
                            startActivity(intentMain)
                    }
                    else{
                        Toast.makeText(this@Login, "Usuario o contraseña incorrectos", Toast.LENGTH_LONG).show()
                    }

                }catch (ex: Exception){
                    withContext(Dispatchers.Main){
                        Toast.makeText(this@Login, ex.message, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }

        btnRegistrarseLogin.setOnClickListener{
            val intentRegistrarse = Intent(this@Login, Registrarse::class.java)
            startActivity(intentRegistrarse)
        }
    }
}