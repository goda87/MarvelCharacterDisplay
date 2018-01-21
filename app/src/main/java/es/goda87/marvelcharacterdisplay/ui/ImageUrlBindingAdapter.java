package es.goda87.marvelcharacterdisplay.ui;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import es.goda87.marvelcharacterdisplay.R;

public class ImageUrlBindingAdapter {
    @BindingAdapter("imageUrl")
    public static void setImageUrl(ImageView view, String imageUrl) {
        Picasso.with(view.getContext())
                .load(imageUrl)
                .placeholder(R.mipmap.ic_launcher)
                .into(view);
    }
}
