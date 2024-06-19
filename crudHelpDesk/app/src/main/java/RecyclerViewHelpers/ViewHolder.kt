package RecyclerViewHelpers

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import luis.escobar.crudhelpdesk.R


class ViewHolder(view: View): RecyclerView.ViewHolder(view){

        val txtNombreTicket : TextView = view.findViewById(R.id.txtNombreTicket)
        val imgBorrar: ImageView = view.findViewById(R.id.imgBorrar)
        val imgEditar: ImageView = view.findViewById(R.id.imgEditar)
        val txtEstado: TextView = view.findViewById(R.id.txtEstado)
    }
