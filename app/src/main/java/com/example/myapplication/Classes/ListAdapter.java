package com.example.myapplication.Classes;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.myapplication.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    private LayoutInflater mInflater;
    private Context mContext;
    private List<ClsRegistrationGetSet> mList = new ArrayList<>();
    private OnClickListener mOnClickListener;
    private OnDeleteInterface mOnDeleteInterface;

    public ListAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void AddItems(List<ClsRegistrationGetSet> mList) {
        if (mList != null) {
            this.mList = mList;
            notifyDataSetChanged();
        }
    }


    public void SetOnClickListener(OnClickListener mOnClickListener) {
        this.mOnClickListener = mOnClickListener;
    }


    public void ClickOnDelete(OnDeleteInterface mOnDeleteInterface) {
        this.mOnDeleteInterface = mOnDeleteInterface;
    }

    public void ClickOnEdit(OnClickListener mOnClickListener) {
        this.mOnClickListener = mOnClickListener;
    }

    public void RemoveItem(int position) {
        mList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, getItemCount());
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ClsRegistrationGetSet currentObj = mList.get(position);
//        holder.iv_profile.setImageResource(Integer.parseInt(currentObj.getReg_photo()));


        getProfile(currentObj.getReg_photo(), holder.iv_profile);

        holder.txt_name.setText(currentObj.getReg_name());
        holder.txt_mobile.setText(currentObj.getReg_phone());
        holder.txt_email.setText(currentObj.getReg_email());

        holder.BindEdit(mList.get(position), mOnClickListener);
        holder.BindDelete(mList.get(position), mOnDeleteInterface, position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txt_name, txt_mobile, txt_email;
        private ImageView iv_profile, iv_delete, iv_edit;
        private LinearLayout ll_header;

        public ViewHolder(View itemView) {
            super(itemView);
            ll_header = itemView.findViewById(R.id.ll_header);
            iv_delete = itemView.findViewById(R.id.iv_delete);
            iv_profile = itemView.findViewById(R.id.iv_profile);
            txt_name = itemView.findViewById(R.id.txt_name);
            txt_mobile = itemView.findViewById(R.id.txt_mobile);
            iv_edit = itemView.findViewById(R.id.iv_edit);
            txt_email = itemView.findViewById(R.id.txt_email);
        }

        void BindEdit(final ClsRegistrationGetSet obj,
                      OnClickListener mOnClickListener) {
            iv_edit.setOnClickListener(v ->
                    mOnClickListener.OnItemClick(obj));
        }

        void BindDelete(final ClsRegistrationGetSet obj,
                        OnDeleteInterface mOnDeleteInterface, int position) {
            iv_delete.setOnClickListener(v ->
                    mOnDeleteInterface.OnDeleteItemClick(obj, position));
        }

    }

    private void getProfile(String path, ImageView imageView) {
        File imgFile = new File(path);
        Log.d("--regID--", "imgFile: " + imgFile);

        if (imgFile.exists()) {
            Log.d("--regID--", "exists: " + imgFile);
            Bitmap bmp = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            Log.d("--regID--", "bmp: " + bmp);
            imageView.setImageBitmap(bmp);
        }
    }

}
