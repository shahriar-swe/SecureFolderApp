package com.example.securefolder;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

// 2nd step start

public class MyAdapter extends RecyclerView.Adapter<ImageViewHolder>{

    // 2.2 : declearation
    private Context context;
    private List<ImageData> myImageList;
    private int lastPosition = -1;



    // 2.3 : 3rd step : right click >> generate constructor
    public MyAdapter(Context context, List<ImageData> myImageList) {
        this.context = context;
        this.myImageList = myImageList;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_row_item,viewGroup,false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ImageViewHolder imageViewHolder, int position) {

        //gradle e shob dependency set korar por aita korte hobe

       Glide.with(context).load(myImageList.get(position).getItemImage()).into(imageViewHolder.imageView);
        //imageViewHolder.imageView.setImageResource(myImageList.get(position).getItemImage());
        imageViewHolder.mTitle.setText(myImageList.get(position).getItemName());
        imageViewHolder.mDescription.setText(myImageList.get(position).getItemDescription());
        imageViewHolder.mDate.setText(myImageList.get(position).getItemDate());

        imageViewHolder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context,DetailsActivity.class);

                intent.putExtra("image",myImageList.get(imageViewHolder.getAdapterPosition()).getItemImage());
                //intent.putExtra("image",myImageList.get(imageViewHolder.getAdapterPosition()).getItemImage());
                intent.putExtra("imageName",myImageList.get(imageViewHolder.getAdapterPosition()).getItemName());
                intent.putExtra("description",myImageList.get(imageViewHolder.getAdapterPosition()).getItemDescription());
                intent.putExtra("date",myImageList.get(imageViewHolder.getAdapterPosition()).getItemDate());
                intent.putExtra("keyValue",myImageList.get(imageViewHolder.getAdapterPosition()).getKey());
                context.startActivity(intent);
            }
        });

        setAnimation(imageViewHolder.itemView,position);

    }

    public void setAnimation(View viewToAnimate,int position){
        if(position>lastPosition){
            ScaleAnimation animation = new ScaleAnimation(0.0f,1.0f,0.0f,1.0f, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);

            animation.setDuration(1500);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    @Override
    public int getItemCount() {
        return myImageList.size();
    }

    // ei method ta esheche GalleryActivity.java class theke
    public void filteredList(ArrayList<ImageData> filterList) {
        myImageList = filterList;
        notifyDataSetChanged();
    }
}


//1st step start
class ImageViewHolder extends RecyclerView.ViewHolder{

    ImageView imageView;
    TextView mTitle,mDescription,mDate;
    CardView mCardView;

    public ImageViewHolder( View itemView) {
        super(itemView);

        //shob id recycler_row_item theke find korechi
        imageView = itemView.findViewById(R.id.imageViewId);
        mTitle = itemView.findViewById(R.id.titleTextViewId);
        mDescription = itemView.findViewById(R.id.descriptionTextViewId);
        mDate = itemView.findViewById(R.id.dateTextViewId);
        mCardView = itemView.findViewById(R.id.cardViewId);
    }

    //1st step finish
}
