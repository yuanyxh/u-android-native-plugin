package com.yuanyxh.camera.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yuanyxh.camera.R;
import com.yuanyxh.camera.utils.BitmapBinder;
import com.yuanyxh.camera.utils.SharedData;

import java.io.File;
import java.util.ArrayList;

public class PhotoPickerAdapter extends RecyclerView.Adapter<PhotoPickerAdapter.ViewHolder> {
    private final Activity context;
    private final ArrayList<File> imageList;

    public PhotoPickerAdapter(Activity _context, ArrayList<File> list) {
        context = _context;
        imageList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.photo_picker_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (imageList != null && imageList.get(position) != null) {
            File file = imageList.get(position);
            Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            holder.photo_picker_item.setImageBitmap(bitmap);

            View.OnClickListener listener = view -> {
                Intent intent = new Intent();
                BitmapBinder binder = new BitmapBinder(bitmap);
                Bundle bundle = new Bundle();
                bundle.putBinder("emoji", binder);
                intent.putExtra("emoji_bundle", bundle);

                context.setResult(SharedData.ADD_EMOJI_RESULT, intent);

                context.finishAfterTransition();
                context.overridePendingTransition(R.anim.stay_in_place_medium, R.anim.slide_out_bottom_medium);
            };

            holder.photo_picker_item.setOnClickListener(listener);
        }
    }

    @Override
    public int getItemCount() {
        return imageList == null ? 0 : imageList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView photo_picker_item;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            photo_picker_item = itemView.findViewById(R.id.photo_picker_item);
        }
    }
}
