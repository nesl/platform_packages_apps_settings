/*
 * Copyright (C) 2006 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.settings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.CheckedTextView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.app.Fragment;
import android.app.ListFragment;
import android.widget.ArrayAdapter;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.view.LayoutInflater;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import android.os.Environment;

//Dialog fragment
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;


public class ManagePlayback extends ListFragment implements OnItemClickListener {
    List<Map<String, String>> li = new ArrayList<Map<String, String>>();
    private static String tag = "Li_activity";
    private PackageManager pm;
    SimpleAdapter adapter;
    String baseDirName = "/data/data/com.android.settings";
    ListView listview;
    public String message;
    public String uid;

    private class ConfirmPackage extends DialogFragment {
        ManagePlayback mplayback;

        public void init(ManagePlayback mp)
        {
            mplayback = mp;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(mplayback.message + " selected")
                   .setPositiveButton("confirm", new DialogInterface.OnClickListener() {
                       public void onClick(DialogInterface dialog, int id) {
                           mplayback.write_to_file(mplayback.uid);
                           getActivity().finish();
                       }
                   })
                   .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                       public void onClick(DialogInterface dialog, int id) {
                       }
                   });
            // Create the AlertDialog object and return it
            return builder.create();
        }
    }

    private HashMap<String,String>
    create_package_list(String key, String value, int uid)
    {
        HashMap<String, String> pkg = new HashMap<String,String>();
        pkg.put(key, value);
        pkg.put("uid", "" + uid);
        return pkg;
    }

    private void init_package_list(String name, int uid)
    {
        li.add(create_package_list("packagename", name, uid));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
               Bundle savedInstanceState)
    {
        listview = (ListView) getActivity().findViewById(android.R.id.list);
        pm = getActivity().getPackageManager();
        List<ApplicationInfo> la = pm.getInstalledApplications(0);
        Iterator<ApplicationInfo> it = la.listIterator();
        while (it.hasNext()) {
                ApplicationInfo a = it.next();
                Log.i(tag, "Package Name: " + a.loadLabel(pm));
                init_package_list(a.loadLabel(pm).toString(), a.uid);
        }
        adapter = new SimpleAdapter(getActivity(), li,
                android.R.layout.simple_list_item_single_choice,
          new String[] {"packagename", "uid"},
          new int[] {android.R.id.text1, android.R.id.text2});

        Log.i(tag, "storage directory: " + baseDirName);

        setListAdapter(adapter);
        listview.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);
        AdapterContextMenuInfo aInfo = (AdapterContextMenuInfo) menuInfo;
        HashMap map = (HashMap) adapter.getItem(aInfo.position);
        menu.setHeaderTitle(map.get("packagename") + "details");
        menu.add(1, 1, 1, "" + map.get("details"));
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        getListView().setOnItemClickListener(this);
    }

    private void write_to_file(String uid)
    {
            try {
            BufferedWriter uidw = new BufferedWriter(new FileWriter(baseDirName + "/uid.txt"));
                    uidw.write(uid);
                    uidw.close();
            } catch (IOException e) {
                    e.printStackTrace();
            }
    }

    @Override
    public void onItemClick(AdapterView adapter, View view, int position, long id)
    {
        CheckedTextView check;
        HashMap<String, String> map = (HashMap<String, String>)adapter.getItemAtPosition(position);
//        Toast.makeText(getActivity().getBaseContext(), "Item clicked: " + map.get("packagename"),  Toast.LENGTH_LONG).show();
        message = new String(map.get("packagename"));
        uid = new String(map.get("uid"));
        ConfirmPackage cp = new ConfirmPackage();
        cp.init(this);
        cp.show(getFragmentManager(), "package");
//        getActivity().finish();
    }
}

