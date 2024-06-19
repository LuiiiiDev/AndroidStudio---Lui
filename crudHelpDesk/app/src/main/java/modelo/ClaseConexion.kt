package modelo

import java.sql.Connection
import java.sql.DriverManager

class ClaseConexion {

    fun cadenaConexion(): Connection?{
        try {
            val ip = "jdbc:oracle:thin:@192.168.1.13:1521:xe"
            val usuario = "LuiDev"
            val contrasena = "210823"

            val conexion = DriverManager.getConnection(ip, usuario, contrasena)
            return conexion
        }catch (ex: Exception){
            println("Este es el error: $ex")
            return null
        }
    }
}