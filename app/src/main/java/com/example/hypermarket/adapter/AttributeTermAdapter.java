package com.example.hypermarket.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hypermarket.R;
import com.example.hypermarket.model.Attribute;
import com.example.hypermarket.repositories.FilterRepository;

import java.util.ArrayList;
import java.util.List;

public class AttributeTermAdapter extends RecyclerView.Adapter<AttributeTermAdapter.AttributeTermHolder> {

    private List<Attribute.Term> attributeList = new ArrayList<>();
    private List<Attribute.Term> selectedTermList ;
    private AppCompatActivity mActivity;
    private Attribute mAttribute;

    public void setAttribute(Attribute mAttribute) {
        this.mAttribute = mAttribute;
    }

    public AttributeTermAdapter(AppCompatActivity mActivity) {
        this.mActivity = mActivity;
        selectedTermList = FilterRepository.getInstance(mActivity).getSelectedTerms().getValue();
    }

    public AttributeTermAdapter(AppCompatActivity mActivity, List<Attribute.Term> attributeList, Attribute mAttribute) {
        this.attributeList = attributeList;
        this.mActivity = mActivity;
        this.mAttribute = mAttribute;
    }

    public void setTerms(List<Attribute.Term> attributes) {
        attributeList = attributes;
    }

    @NonNull
    @Override
    public AttributeTermHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Activity activity = (Activity) parent.getContext();
        View view = activity.getLayoutInflater().inflate(R.layout.item_filter_term, parent, false);
        return new AttributeTermHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AttributeTermHolder holder, int position) {
        Attribute.Term term = attributeList.get(position);
        holder.bind(term);
    }


    @Override
    public int getItemCount() {
        return attributeList == null ? 0 : attributeList.size();
    }


    public class AttributeTermHolder extends RecyclerView.ViewHolder {
        private TextView mTextViewTitle;
        private Attribute.Term mTerm;

        private CheckBox mCheckbox;

        public AttributeTermHolder(@NonNull final View itemView) {
            super(itemView);
            mTextViewTitle = itemView.findViewById(R.id.term_title_text_view);
            mCheckbox = itemView.findViewById(R.id.term_checkbox);
        }

        public void bind(final Attribute.Term term) {

            mTextViewTitle.setText(term.getName());
            this.mTerm = term;
            term.setAttributeSlug(mAttribute.getSlug());

            if(selectedTermList != null && selectedTermList.contains(term))
                mCheckbox.setChecked(true);
            else
                mCheckbox.setChecked(false);

            itemView.setOnClickListener(view -> {
                if (mCheckbox.isChecked()) {
                    mCheckbox.setChecked(false);
                    FilterRepository.getInstance(mActivity).removeSelectedTerm(term);
                } else {
                    mCheckbox.setChecked(true);
                    FilterRepository.getInstance(mActivity).addSelectedTerm(term);
                }
            });
        }
    }

}