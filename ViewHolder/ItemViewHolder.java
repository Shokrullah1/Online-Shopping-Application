package com.example.dsapp.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dsapp.Interface.ItemClickListener;
import com.example.dsapp.R;

import org.w3c.dom.Text;

public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{

    public TextView txtProductName, txtProductDescription, txtProductPrice, txtProductStatus;
    public ImageView imageView;
    public ItemClickListener listener;

    public ItemViewHolder(@NonNull View itemView) {
        super(itemView);

        txtProductName = (TextView) itemView.findViewById(R.id.product_seller_name);
        imageView = (ImageView)itemView.findViewById(R.id.product_seller_image);
        txtProductPrice = (TextView) itemView.findViewById(R.id.product_seller_price);
        txtProductDescription = (TextView) itemView.findViewById(R.id.product_seller_description);
        txtProductStatus = (TextView) itemView.findViewById(R.id.product_seller_state);
    }

    public void setItemClickListener(ItemClickListener listener){
        this.listener = listener;
    }
    @Override
    public void onClick(View view) {
        listener.onClick(view, getAdapterPosition(), false);
    }
}
