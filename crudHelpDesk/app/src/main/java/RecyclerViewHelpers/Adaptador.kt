package RecyclerViewHelpers

import android.app.AlertDialog
import android.content.Intent
import android.icu.text.IDNA.Info
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import luis.escobar.crudhelpdesk.InfoTicket
import luis.escobar.crudhelpdesk.R
import modelo.ClaseConexion
import modelo.Tickets

class Adaptador(var Datos: List<Tickets>): RecyclerView.Adapter<ViewHolder>() {

    fun actualizarTicket(nuevoEstado: String, uuid: String){
        GlobalScope.launch(Dispatchers.IO) {
            val objConexion = ClaseConexion().cadenaConexion()

            val actualizarTicket = objConexion?.prepareStatement("update Ticket set estado = ? where uuidTicket = ?")!!
            actualizarTicket.setString(1, nuevoEstado)
            actualizarTicket.setString(2, uuid)
            actualizarTicket.executeUpdate()

            val commit =objConexion.prepareStatement("commit")
            commit.executeUpdate()

            withContext(Dispatchers.Main){
                actualizarTicketDespuesdeEditar(uuid, nuevoEstado)
            }
        }
    }

    fun actualizarTicketDespuesdeEditar(uuid: String, nuevoEstado: String){
        val index =Datos.indexOfFirst { it.uuidTicket == uuid }
        Datos[index].estado = nuevoEstado
        notifyDataSetChanged()
        notifyItemChanged(index)
    }

    fun eliminarTicket(titulo: String, posicion: Int){
        val listaTickets = Datos.toMutableList()
        listaTickets.removeAt(posicion)

        GlobalScope.launch(Dispatchers.IO){
            val objConexion = ClaseConexion().cadenaConexion()

            val borrarTicket = objConexion?.prepareStatement("delete Ticket where titulo = ?")!!
            borrarTicket.setString(1, titulo)
            borrarTicket.executeUpdate()

            val commit = objConexion.prepareStatement("commit")
            commit.executeUpdate()

        }
        Datos = listaTickets.toList()
        notifyItemRemoved(posicion)
        notifyDataSetChanged()

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.activity_ticket_card, parent, false)
        return ViewHolder(vista)



    }

    override fun getItemCount()  = Datos.size




    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = Datos[position]
        holder.txtNombreTicket.text = item.titulo
        holder.txtEstado.text = item.estado


        holder.imgBorrar.setOnClickListener{

            val context = holder.itemView.context

            val builder = AlertDialog.Builder(context)

            builder.setTitle("Confirmacion")
            builder.setMessage("¿Estas seguro que quieres borrar el ticket?")

            builder.setPositiveButton("Si"){ dialog, wich ->
                eliminarTicket(item.titulo, position)

            }
            builder.setNegativeButton("No"){ dialog, wich ->
                dialog.dismiss()
            }
            val dialog = builder.create()
            dialog.show()
        }

        holder.imgEditar.setOnClickListener{
            val context = holder.itemView.context
            val builder = AlertDialog.Builder(context)

            builder.setTitle("Actualiza el estado del ticket")

            val spEstado = Spinner(context)
            val datosSpinner = listOf("Activo", "Inactivo")

            CoroutineScope(Dispatchers.IO).launch {
                withContext(Dispatchers.Main){
                    val myadapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, datosSpinner)
                    spEstado.adapter = myadapter
                }
            }

            builder.setView(spEstado)

            builder.setPositiveButton("Aceptar") { dialog, which ->
                actualizarTicket(spEstado.selectedItem.toString(), item.uuidTicket)
            }

            builder.setNegativeButton("Cancelar") { dialog, which ->
                dialog.dismiss()
            }
            val dialog = builder.create()
            dialog.show()
        }

        holder.itemView.setOnClickListener{
            val context = holder.itemView.context
            val intentInfo = Intent(context, InfoTicket::class.java)

            intentInfo.putExtra("uuidTicket", item.uuidTicket)
            intentInfo.putExtra("titulo", item.titulo)
            intentInfo.putExtra("descripcion", item.descripcion)
            intentInfo.putExtra("email", item.email)
            intentInfo.putExtra("autor", item.autor)
            intentInfo.putExtra("estado", item.estado)

            context.startActivity(intentInfo)
        }
    }
}
