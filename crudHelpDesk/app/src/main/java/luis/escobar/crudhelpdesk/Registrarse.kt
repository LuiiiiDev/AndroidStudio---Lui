package luis.escobar.crudhelpdesk

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
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

class Registrarse : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registrarse)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val txtUsuarioReg = findViewById<EditText>(R.id.txtUsuarioReg)
        val txtContrasenaReg = findViewById<EditText>(R.id.txtContraseñaReg)
        val txtContrasenaReg2 = findViewById<EditText>(R.id.txtContrasenaReg2)
        val btnRegistrarseReg = findViewById<Button>(R.id.btnRegistrarseReg)

        val btnBack = findViewById<ImageView>(R.id.btnBackReg)

        btnBack.setOnClickListener{
            val intent = Intent(this@Registrarse, Login::class.java)
            startActivity(intent)
        }

        btnRegistrarseReg.setOnClickListener{
            GlobalScope.launch( Dispatchers.IO ){
                try {
                    val objConexion = ClaseConexion().cadenaConexion()

                    if (txtUsuarioReg.text.toString() == "" || txtContrasenaReg.text.toString() == "" || txtContrasenaReg2.text.toString() == ""){
                        withContext(Dispatchers.Main){
                            Toast.makeText(this@Registrarse, "Campos requeridos vacios completalos correctamente", Toast.LENGTH_LONG).show()
                        }
                    }
                    else if (txtContrasenaReg.text.toString() != txtContrasenaReg2.text.toString()){
                        withContext(Dispatchers.Main){
                            Toast.makeText(this@Registrarse, "Ingresa la misma contraseña en los dos campos", Toast.LENGTH_LONG).show()
                        }
                    }

                    else if (txtContrasenaReg.text.toString().length > 8 || txtContrasenaReg2.text.toString().length > 8 ){
                        withContext(Dispatchers.Main){
                            Toast.makeText(this@Registrarse, "La contraseña excede los digitos requeridos", Toast.LENGTH_LONG).show()
                        }
                    }
                    else{

                        val crearUsuario = objConexion?.prepareStatement("insert into Usuario (uuidUsuario, usuario, contrasena) values (?, ?, ?)")!!
                        crearUsuario.setString(1, UUID.randomUUID().toString())
                        crearUsuario.setString(2, txtUsuarioReg.text.toString())
                        crearUsuario.setString(3, txtContrasenaReg2.text.toString())
                        crearUsuario.executeUpdate()
                        withContext(Dispatchers.Main){
                            Toast.makeText(this@Registrarse, "Usuario Creado", Toast.LENGTH_LONG).show()

                            txtUsuarioReg.setText("")
                            txtContrasenaReg.setText("")
                            txtContrasenaReg2.setText("")
                            val loginIntent = Intent(this@Registrarse, Login::class.java)
                            startActivity(loginIntent)
                        }
                    }
                }catch (ex: Exception){
                    withContext(Dispatchers.Main){
                        Toast.makeText(this@Registrarse, ex.message, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
}