package com.example.hypermarket.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hypermarket.R;
import com.example.hypermarket.model.Category;
import com.example.hypermarket.view.activity.CategoryDetailActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends  RecyclerView.Adapter<CategoryAdapter.CategoryHolder>{

    private List<Category> categoryList = new ArrayList<>();
    private AppCompatActivity mActivity ;

    public CategoryAdapter(AppCompatActivity mActivity) {
        this.mActivity = mActivity;
    }

    public void setCategories(List<Category> categories) {
        categoryList = categories;
    }

    @NonNull
    @Override
    public CategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Activity activity = (Activity) parent.getContext();
        View view = activity.getLayoutInflater().inflate(R.layout.item_ctergory_list, parent, false);
        return new CategoryHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryHolder holder, int position) {
        Category category = categoryList.get(position);
        holder.bind(category);
    }


    @Override
    public int getItemCount() {

        return categoryList == null ? 0 : categoryList.size();
    }



    public class CategoryHolder extends RecyclerView.ViewHolder {
        private TextView mTextViewTitle;
        private ImageView imageView ;
        private Category category;
        public CategoryHolder(@NonNull final View itemView) {
            super(itemView);
            mTextViewTitle = itemView.findViewById(R.id.category_title_textView);
            imageView = itemView.findViewById(R.id.category_imageView);
        }

        public void bind(final Category category) {

            itemView.setOnClickListener(view -> mActivity.startActivity(CategoryDetailActivity.newIntent(mActivity , category.getId())));

            mTextViewTitle.setText(category.getName());
            if(category.getImage()!=null)
                Picasso.get().load(category.getImage().getSrc()).fit().into(imageView);
            this.category = category;

        }
    }

}
