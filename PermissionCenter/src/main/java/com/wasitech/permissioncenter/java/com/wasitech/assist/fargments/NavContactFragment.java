package com.wasitech.permissioncenter.java.com.wasitech.assist.fargments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wasitech.assist.R;
import com.wasitech.contact.adapter.ContactAdapter;
import com.wasitech.basics.app.ProcessApp;
import com.wasitech.contact.classes.Contact;

public class NavContactFragment extends Fragment {
    private FragmentParse parse;
    private final boolean search;
    protected ContactAdapter adapter;

    public NavContactFragment(boolean search) {
        this.search = search;
    }

    public static NavContactFragment getInstance(boolean search) {
        return new NavContactFragment(search);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_contact_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {
            setHasOptionsMenu(true);
            RecyclerView recyclerView = view.findViewById(R.id.list);
            adapter = new ContactAdapter(ProcessApp.cList) {
                @Override
                public void onClick(Contact contact) {
                    parse.onSelected(contact);
                }
            };
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(adapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void search(String query) {
        if (adapter != null && query != null)
            adapter.getFilter().filter(query);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        if (search && getContext() != null) {
            menu.clear();
            inflater.inflate(R.menu.only_search_menu, menu);
            MenuItem item = menu.findItem(R.id.search_item);
            SearchView searchView = new SearchView(getContext());
            item.setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItem.SHOW_AS_ACTION_IF_ROOM);
            item.setActionView(searchView);
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    search(query);
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    search(newText);
                    return false;
                }
            });
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof FragmentParse) {
            parse = (FragmentParse) context;
        }
    }

    public interface FragmentParse {
        void onSelected(Contact contact);
    }
}
