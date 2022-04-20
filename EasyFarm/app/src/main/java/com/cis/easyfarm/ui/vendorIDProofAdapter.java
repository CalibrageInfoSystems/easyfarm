package com.cis.easyfarm.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cis.easyfarm.Objects.IdentityProof;
import com.cis.easyfarm.R;
import com.cis.easyfarm.common.ImageUtil;
import com.cis.easyfarm.ui.userAccount.buyerIDProofAdapter;

import java.util.LinkedHashMap;
import java.util.List;

public class vendorIDProofAdapter extends RecyclerView.Adapter<vendorIDProofAdapter.MyHolder>{

    private Context mContext;
    private List<IdentityProof> landlordIdProofList;
    private vendorIDProofAdapter.idProofsClickListener idProofsClickListener;
    private LinkedHashMap<String, String> idProofsData;


    public vendorIDProofAdapter(Context mContext, List<IdentityProof> idProofsPair, LinkedHashMap<String, String> idProofsData) {
        this.mContext = mContext;
        this.landlordIdProofList = idProofsPair;
        this.idProofsData = idProofsData;
    }
    @Override
    public vendorIDProofAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View bookingView = inflater.inflate(R.layout.vendoridproofadapter, null);
        vendorIDProofAdapter.MyHolder myHolder = new vendorIDProofAdapter.MyHolder(bookingView);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(vendorIDProofAdapter.MyHolder holder, final int position) {
        holder.idProofName.setText(idProofsData.get(String.valueOf(landlordIdProofList.get(position).getIdProofTypeId())));

        android.util.Log.d("IdproofTypeId", landlordIdProofList.get(position).getIdProofTypeId() + "");
        holder.idProofNo.setText(landlordIdProofList.get(position).getIdProofNumber());
        Log.e("image=====",landlordIdProofList.get(position).getFileLocation());

        if(landlordIdProofList.get(position).getFileBytes()!=null) {
            Bitmap bitmap = ImageUtil.convert(landlordIdProofList.get(position).getFileBytes());
            holder.imageView.setImageBitmap(bitmap);

        }
        else {
            Glide.with(mContext).load(landlordIdProofList.get(position).getFileLocation()+"/"+landlordIdProofList.get(position).getFileName()+landlordIdProofList.get(position).getFileExtension()).into(holder.imageView);
        }


        holder.deleteView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                idProofsClickListener.onDeleteClicked(position);
            }
        });

        if(landlordIdProofList.get(position).getIdProofTypeId() ==6)
        {
            holder.deleteView.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return landlordIdProofList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView idProofName, idProofNo;
        ImageView editView, deleteView,imageView;

        public MyHolder(View itemView) {
            super(itemView);
            idProofName = (TextView) itemView.findViewById(R.id.name);
            idProofNo = (TextView) itemView.findViewById(R.id.num);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            deleteView = (ImageView) itemView.findViewById(R.id.trashIcon);
        }
    }

    public void setIdProofsClickListener(vendorIDProofAdapter.idProofsClickListener idProofsClickListener) {
        this.idProofsClickListener = idProofsClickListener;
    }
    public interface idProofsClickListener {
        void onEditClicked(int position);
        void onDeleteClicked(int position);
    }

    public void updateData(List<IdentityProof> idProofsPair, LinkedHashMap<String, String> idProofsData) {
        this.landlordIdProofList = idProofsPair;
        this.idProofsData = idProofsData;
        notifyDataSetChanged();
    }

}
