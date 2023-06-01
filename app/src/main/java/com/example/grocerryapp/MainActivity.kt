package com.example.grocerryapp

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(), Adapter.GrocerryItemClickInterface {
    lateinit var recyclerView: RecyclerView
    lateinit var addFAB: FloatingActionButton
    lateinit var list: List<GrocerryItems>
    lateinit var adapter: Adapter
    lateinit var GViewModel : GrocerryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        addFAB = findViewById(R.id.floatingActionButton)
        list = ArrayList<GrocerryItems>()
        adapter = Adapter(list, this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        val grocerryRepository  = GrocerryRepository(GrocerryDatabase(this))
        val factory  = GrocerryViewModelFactory(grocerryRepository)
        GViewModel = ViewModelProvider(this, factory).get(GrocerryViewModel::class.java)
        GViewModel.getAllGrocerryItems().observe(this, Observer {
            adapter.list = it
            adapter.notifyDataSetChanged()
        })

        addFAB.setOnClickListener {
                openDialog()
        }


    }

    fun openDialog(){
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.add_dialog)
        val cancelBtn = dialog.findViewById<Button>(R.id.cancelBtn)
        val addBtn = dialog.findViewById<Button>(R.id.AddBtn)

        val itemName = dialog.findViewById<EditText>(R.id.ETname)
        val itemQuantity = dialog.findViewById<EditText>(R.id.ETquantity)
        val itemPrice = dialog.findViewById<EditText>(R.id.ETprice)

        cancelBtn.setOnClickListener{
            dialog.dismiss()
        }

        addBtn.setOnClickListener{
            val name : String = itemName.text.toString()
            val quantity : String = itemQuantity.text.toString()
            val price : String = itemPrice.text.toString()



            if(name.isNotEmpty() && price.isNotEmpty() && quantity.isNotEmpty()){
                val qty : Int = quantity.toInt()
                val pr : Int = price.toInt()
                val items = GrocerryItems(name, qty, pr)
                GViewModel.insert(items)
                Toast.makeText(applicationContext, "Item inserted successfully", Toast.LENGTH_SHORT).show()
                adapter.notifyDataSetChanged()
                dialog.dismiss()
            }else{
                Toast.makeText(applicationContext, "Plese enter all the data", Toast.LENGTH_SHORT).show()

            }
        }

        dialog.show()

    }

    override fun onItemClick(grocerryItems: GrocerryItems) {
        GViewModel.delete(grocerryItems)
        adapter.notifyDataSetChanged()
        Toast.makeText(applicationContext, "Item deleted successfully", Toast.LENGTH_SHORT).show()
    }


}