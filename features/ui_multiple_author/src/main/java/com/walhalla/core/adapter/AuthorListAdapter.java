package com.walhalla.core.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.walhalla.core.domain.Const;
import com.walhalla.core.domain.entity.Author;

import com.walhalla.uimultiple.databinding.ItemAuthorBinding;

import com.walhalla.view.adapter.EmptyViewHolder;
import com.walhalla.view.adapter.EmptyViewModel;
import com.walhalla.view.adapter.ViewModel;
import com.walhalla.view.databinding.RowEmptyBinding;

import java.util.ArrayList;
import java.util.List;

public class AuthorListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    public interface AuthorListCallback {
        void onItemClick(Author dictionary);
    }

    private final AuthorListCallback callback;

    public static final int VIEW_TYPE_EMPTY = 30;

    private final List<ViewModel> list = new ArrayList<>();

    public int getPositionFromData(String character) {
        int position = 0;
        for (ViewModel viewModel : list) {
            if (viewModel instanceof Author) {
                String letter = "" + ((Author) viewModel).getName().charAt(0);
                if (letter.equalsIgnoreCase("" + character)) {
                    return position;
                }
                position++;
            }
        }
        return 0;
    }

    public AuthorListAdapter(AuthorListCallback callback) {
        this.callback = callback;
    }


//    public void setItems(List<Author> newAuthors) {
//        if (items == null) {
//            items = newAuthors;
//            notifyItemRangeInserted(0, newAuthors.size());
//        } else {
//            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new AuthorDiffCallback(items, newAuthors), true);
//            items = newAuthors;
//            result.dispatchUpdatesTo(this);
//        }
//    }

    public void setList(List<Author> message) {
        List<ViewModel> newList = new ArrayList<>(message);
        newList.addAll(message);
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new AuthorDiffCallback(list, newList));
        this.list.clear();
        this.list.addAll(newList);
        diffResult.dispatchUpdatesTo(this);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == Const.TYPE_AUTHOR) {
            ItemAuthorBinding binding = ItemAuthorBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            viewHolder = new AuthorViewHolder(binding);
        } else if (viewType == VIEW_TYPE_EMPTY) {
            @NonNull RowEmptyBinding view1 = RowEmptyBinding.inflate(inflater, parent, false);
            viewHolder = new EmptyViewHolder(view1);
        } else {
            @NonNull RowEmptyBinding view1 = RowEmptyBinding.inflate(inflater, parent, false);
            viewHolder = new EmptyViewHolder(view1);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == Const.TYPE_AUTHOR) {
            Author author = (Author) list.get(position);
            AuthorViewHolder mm = ((AuthorViewHolder) holder);
            mm.bind(author);
            mm.binding.getRoot().setOnClickListener(v -> {
                callback.onItemClick(author);
            });
        } else {
            EmptyViewHolder vh2 = (EmptyViewHolder) holder;
            vh2.bind(list.get(position));
        }
    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position).getItemType();
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public void swap(EmptyViewModel emptyViewModel) {
        List<ViewModel> newList = new ArrayList<>();
        newList.add(emptyViewModel);
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new AuthorDiffCallback(list, newList));
        this.list.clear();
        this.list.addAll(newList);
        diffResult.dispatchUpdatesTo(this);
    }

    static class AuthorViewHolder extends RecyclerView.ViewHolder {
        private final ItemAuthorBinding binding;

        public AuthorViewHolder(@NonNull ItemAuthorBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Author author) {
            binding.authorName.setText(author.getName());
            //binding.executePendingBindings();
        }
    }


    public static class AuthorDiffCallback extends DiffUtil.Callback {
        private final List<ViewModel> oldAuthors;
        private final List<ViewModel> newAuthors;

        public AuthorDiffCallback(List<ViewModel> oldAuthors, List<ViewModel> newAuthors) {
            this.oldAuthors = oldAuthors;
            this.newAuthors = newAuthors;
        }

        @Override
        public int getOldListSize() {
            return oldAuthors.size();
        }

        @Override
        public int getNewListSize() {
            return newAuthors.size();
        }

        @Override
        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
            Object oldItem = oldAuthors.get(oldItemPosition);
            Object newItem = newAuthors.get(newItemPosition);
            if (oldItem instanceof Author && newItem instanceof Author) {
                return oldItem.equals(newItem);
            } else if (oldItem instanceof EmptyViewModel && newItem instanceof EmptyViewModel) {
                return true;
            }
            return false;
        }

        @Override
        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
            Object oldItem = oldAuthors.get(oldItemPosition);
            Object newItem = newAuthors.get(newItemPosition);
            if (oldItem instanceof Author && newItem instanceof Author) {
                return oldItem.equals(newItem);
            } else if (oldItem instanceof EmptyViewModel && newItem instanceof EmptyViewModel) {
                return true;
            }
            return false;
        }
    }

}
