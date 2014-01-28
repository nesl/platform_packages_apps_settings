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
import android.widget.ArrayAdapter;
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

public class ManagePlayback extends ListFragment implements OnItemClickListener {
        List<Map<String, String>> li = new ArrayList<Map<String, String>>();
        private static String tag = "Li_activity";
        private PackageManager pm;
        ListView listview;
        ListAdapter adapter;

String[] myFrriends = new String[] { 
       "Sunil Gupta",
          "Abhishek Tripathi",
          "Awadhesh Diwakar",
          "Amit Verma",
          "Jitendra Singh",
          "Ravi Jhansi",
          "Ashish Jain",
          "Sandeep Pal",
          "Shishir Verma",
          "Ravi BBD"
      };
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                   Bundle savedInstanceState)
        {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(inflater.getContext(),
                    android.R.layout.simple_list_item_single_choice, myFrriends);
            setListAdapter(adapter);
            return super.onCreateView(inflater, container, savedInstanceState);
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState)
        {
            super.onActivityCreated(savedInstanceState);
            getListView().setOnItemClickListener(this);
        }

        @Override
        public void onItemClick(AdapterView adapter, View view, int position, long id)
        {
            Toast.makeText(getActivity().getBaseContext(), "Item clicked: " + myFrriends[position], Toast.LENGTH_LONG).show();
        }

/*
        private HashMap<String,String> create_planet(String key, String value)
        {
            HashMap<String, String> planet = new HashMap<String,String>();
            planet.put(key, value);
            return planet;
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

        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_list);
            listview = (ListView) findViewById(android.R.id.list);
            pm = getPackageManager();
            List<ApplicationInfo> la = pm.getInstalledApplications(0);
            Iterator<ApplicationInfo> it = la.listIterator();
            while (it.hasNext()) {
                    ApplicationInfo a = it.next();
                    Log.i(tag, "Package Name: " + a.loadLabel(pm));
                    init_package_list(a.loadLabel(pm).toString(), a.uid);
            }
            adapter = new SimpleAdapter(this, li, android.R.layout.simple_list_item_multiple_choice,
              new String[] {"packagename", "uid"},
              new int[] {android.R.id.text1, android.R.id.text2});

            listview.setAdapter(adapter);
            registerForContextMenu(listview);
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
        protected void onListItemClick (ListView l, View v, int position, long id)
        {
            HashMap<String, String> map = (HashMap<String, String>) getListView().getItemAtPosition(position);
            CheckedTextView check = (CheckedTextView)v;
              check.setChecked(!check.isChecked());
            if (check.isChecked()) {
                  Toast.makeText(this, "Selected", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Not Selected", Toast.LENGTH_SHORT).show();
            }
        }
*/
//      @Override
//      public boolean onCreateOptionsMenu(Menu menu) {
//          // Inflate the menu; this adds items to the action bar if it is present.
//          getMenuInflater().inflate(R.menu.li, menu);
//          return true;
//      }

}

