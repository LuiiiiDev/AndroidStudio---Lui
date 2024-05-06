package Modelo

import java.sql.Connection
import java.sql.Driver
import java.sql.DriverManager

class Conexion {
    fun cadenaConexion(): Connection? {
        try {
            val ip = "jdbc:oracle:thin:@10.10.1.124:1521:xe"
            val usuario = "system"
            val contraseña = "desarrollo"

            val conexion = DriverManager.getConnection(ip, usuario, contraseña)
            return conexion
        } catch (e: Exception) {
            println("Este es el error: $e")
            return null
        }
    }
}