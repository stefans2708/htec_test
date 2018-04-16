package com.example.stefan.myapplication;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    ArrayList<Item> items;
    Context context;

    public ItemAdapter(Context context, ArrayList<Item> items){
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_item_layout,parent,false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        final Item item = this.items.get(position);

        holder.textViewTitle.setText(item.getTitle());
        holder.textViewDescription.setText(item.getDescription());

        holder.itemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SingleItem.class);
                intent.putExtra("title",item.getTitle());
                intent.putExtra("description",item.getDescription());
                intent.putExtra("image",item.getImageUrl());

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }



    public class ItemViewHolder extends RecyclerView.ViewHolder{

        TextView textViewTitle, textViewDescription;
        RelativeLayout itemLayout;

        public ItemViewHolder(View itemView) {
            super(itemView);

            textViewDescription = itemView.findViewById(R.id.txtDesc);
            textViewTitle = itemView.findViewById(R.id.txtTitle);
            itemLayout = itemView.findViewById(R.id.itemLayout);
        }
    }
}
