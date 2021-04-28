package com.example.dsapp.Buyers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.dsapp.Model.Products;
import com.example.dsapp.Prevalent.Prevalent;
import com.example.dsapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class ProductDetailActivity extends AppCompatActivity {

    private Button addToCartButton;
    private TextView productNameDetail, productDescriptionDetail, productPriceDetail;
    private ImageView productImageDetail;
    private ElegantNumberButton numberButton;
    private String productID = "", state = "Normal";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        productID = getIntent().getStringExtra("pid");

        productNameDetail = (TextView)findViewById(R.id.product_name_detail);
        productDescriptionDetail = (TextView)findViewById(R.id.product_description_detail);
        productPriceDetail = (TextView)findViewById(R.id.product_price_detail);
        productImageDetail = (ImageView)findViewById(R.id.product_image_detail);
        numberButton = (ElegantNumberButton)findViewById(R.id.number_btn);
        addToCartButton = (Button)findViewById(R.id.pd_add_to_cart_button);
        
        addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(state.equals("Order Placed") || state.equals("Order Shipped"))
                {
                    Toast.makeText(ProductDetailActivity.this, "You can purchase more product, once you received your items", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    addingToCart();
                }
            }
        });
        
        
        getProductDetail(productID);

    }

    @Override
    protected void onStart() {
        super.onStart();
        checkOrderState();
    }

    private void addingToCart()
    {
        String saveCurrentTime, saveCurrentDate;
        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat simpleTimeFormat = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentTime = simpleTimeFormat.format(calendar.getTime());

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentDate = simpleDateFormat.format(calendar.getTime());

        final DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("Cart List");

        final HashMap<String, Object> cartMap = new HashMap<>();
        cartMap.put("pid", productID);
        cartMap.put("pname", productNameDetail.getText().toString());
        cartMap.put("price", productPriceDetail.getText().toString());
        cartMap.put("date",saveCurrentDate );
        cartMap.put("time", saveCurrentTime);
        cartMap.put("quantity", numberButton.getNumber());
        cartMap.put("discount", "");

        cartListRef.child("User View").child(Prevalent.currentOnlineUser.getPhone()).child("Products")
                .child(productID).updateChildren(cartMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                cartListRef.child("Admin View").child(Prevalent.currentOnlineUser.getPhone()).child("Products")
                                        .child(productID)
                                        .updateChildren(cartMap)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful()){
                                                    Toast.makeText(ProductDetailActivity.this, "Added To Cart List", Toast.LENGTH_SHORT).show();
                                                    Intent intent = new Intent(ProductDetailActivity.this, HomeActivity.class);
                                                    startActivity(intent);
                                                }
                                            }
                                        });
                            }
                    }
                });

    }


    private void getProductDetail(String productID) {

        DatabaseReference productRef = FirebaseDatabase.getInstance().getReference().child("Products");

        productRef.child(productID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Products products = snapshot.getValue(Products.class);

                    productNameDetail.setText(products.getPname());
                    productDescriptionDetail.setText(products.getDescription());
                    productPriceDetail.setText(products.getPrice());
                    Picasso.get().load(products.getImage()).into(productImageDetail);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void checkOrderState()
    {
        DatabaseReference orderRef = FirebaseDatabase.getInstance().getReference().child("Orders").child(Prevalent.currentOnlineUser.getPhone());

        orderRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    String shippingState = snapshot.child("state").getValue().toString();

                    if(shippingState.equals("shipped"))
                    {
                        state = "Order Shipped";

                    }
                    else if(shippingState.equals("not shipped"))
                    {
                        state = "Order Placed";
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }
}
