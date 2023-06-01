package com.example.grocerryapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class Adapter(var list: List<GrocerryItems>, val grocerryItemClickInterface: GrocerryItemClickInterface) :
    RecyclerView.Adapter<Adapter.myViewHolder>() {

    inner class myViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
            val name = itemView.findViewById<TextView>(R.id.name)
            val quantity = itemView.findViewById<TextView>(R.id.quantity)
            val rate = itemView.findViewById<TextView>(R.id.rate)
            val totalcost = itemView.findViewById<TextView>(R.id.totalCost)
            val amount = itemView.findViewById<TextView>(R.id.amount)
            val delete = itemView.findViewById<ImageView>(R.id.delteBtn)
    }




    interface GrocerryItemClickInterface{
        fun onItemClick(grocerryItems: GrocerryItems)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.single_row, parent, false)
        return myViewHolder(view)
    }

    override fun onBindViewHolder(holder: myViewHolder, position: Int) {
        holder.name.text = list.get(position).itemName
        holder.rate.text = list.get(position).itemPrice.toString()
        holder.quantity.text = list.get(position).itemQuantity.toString()
        val itemTotal : Int = list.get(position).itemPrice * list.get(position).itemQuantity

        holder.totalcost.text = "Rs. " + itemTotal.toString()
        holder.delete.setOnClickListener(){
            grocerryItemClickInterface.onItemClick(list.get(position))
        }
    }

    override fun getItemCount(): Int {
       return list.size
    }
}