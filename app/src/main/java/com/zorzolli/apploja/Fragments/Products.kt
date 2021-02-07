package com.zorzolli.apploja.Fragments

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.afollestad.materialdialogs.MaterialDialog
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import com.zorzolli.apploja.Model.Data
import com.zorzolli.apploja.R
import kotlinx.android.synthetic.main.fragment_products.*
import kotlinx.android.synthetic.main.list_products.*
import kotlinx.android.synthetic.main.list_products.view.*
import kotlinx.android.synthetic.main.payment.*


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class Products : Fragment() {

    private lateinit var Adapter: GroupAdapter<ViewHolder>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_products, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Adapter = GroupAdapter()
        recycler_products.adapter = Adapter
        Adapter.setOnItemClickListener { item, view ->
            val DialogView = LayoutInflater.from(context).inflate(R.layout.payment, null)
            val builder = AlertDialog.Builder(context)
                .setView(DialogView)
                .setTitle(R.string.payment_methods.toString())
            val mAlertDialog = builder.show()
            mAlertDialog.bt_pay.setOnClickListener {
                mAlertDialog.dismiss()
                val payment = mAlertDialog.fm_payment.text.toString()
                val mPrice = priceProduct.text.toString()

                if (payment == mPrice) {
                    MaterialDialog.Builder(this!!.requireContext())
                        .title(R.string.payment_completed.toString())
                        .content(R.string.thanks_for_shopping.toString())
                        .show()

                } else {
                    Toast.makeText(context, R.string.payment_declined.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        }

        SearchProducts()

    }

    private inner class ProductsItem(internal val adProducts: Data) : Item<ViewHolder>() {
        override fun getLayout(): Int {
            return R.layout.list_products

        }

        override fun bind(viewHolder: ViewHolder, position: Int) {
            viewHolder.itemView.nameProduct.text = adProducts.name
            viewHolder.itemView.priceProduct.text = adProducts.price
            Picasso.get().load(adProducts.uid).into(viewHolder.itemView.photoProduct)
        }
    }

    private fun SearchProducts() {
        FirebaseFirestore.getInstance().collection("Products")
            .addSnapshotListener { snapshot, exception ->
                exception?.let {
                    return@addSnapshotListener
                }

                snapshot?.let {
                    for (doc in snapshot) {
                        val products = doc.toObject(Data::class.java)
                        Adapter.add(ProductsItem(products))
                    }
                }

            }
    }

}