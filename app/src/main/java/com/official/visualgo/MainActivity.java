package com.official.visualgo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.makeramen.roundedimageview.RoundedImageView;
import com.official.visualgo.adapters.code_analysis_vs_adapter;
import com.official.visualgo.adapters.graph_vs_adapter;
import com.official.visualgo.adapters.points_vs_adapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;

public class MainActivity extends AppCompatActivity {

    //    declaring variables
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;
    String name, url;
    FrameLayout container;
    DatabaseReference databaseReference;
    RoundedImageView profile;
    FirebaseAuth auth;
    Intent i;
    List<inputlist> inputlists;
    int flag = 0;
    List<Integer> array_items;
    int speedvar=2000;
    List<String> algo;
    EditText input;
    LinearLayout isempty;


    TextView valuebars;
    List<Integer> sortpart;
    boolean isconsoleopen=true;
    boolean isspeedopen=false;
    boolean iscodeanalysisopen=false;

    int temp = 0;
    boolean issorting = false;
    LinearLayout sorting, graphbase;
    ImageButton sort, plot, clear,minimize;
    FirebaseAuth mAuth;
    com.official.visualgo.adapters.graph_vs_adapter graph_vs_adapter;
    points_vs_adapter points_vs_adapter;
    inputlist inputlist;
    RecyclerView graph_vs_rv, graphpoints_vs_rv, inputpalet,code_analysis_rv;
    ProgressBar progressBar;
    palet_adapter palet_adapter;
    LinearLayout console,consolepalet,speed,speedbox,code_analysis,code_analysis_box;
    SeekBar speedcontroller;
    code_analysis_vs_adapter code_analysis_vs_adapter;

    Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        getting instances


        LinearLayoutManager linearLayoutManager4 = new LinearLayoutManager(MainActivity.this, RecyclerView.VERTICAL, false);
        inputpalet = findViewById(R.id.inputpalet);
        inputpalet.setLayoutManager(linearLayoutManager4);
        container = findViewById(R.id.fragment_container);
        speed=findViewById(R.id.speed);
        code_analysis_rv=findViewById(R.id.code_analysis_rv);
        code_analysis_box=findViewById(R.id.code_analysis_box);
        auth = FirebaseAuth.getInstance();
        profile = findViewById(R.id.profile);
        code_analysis=findViewById(R.id.code_analysis);
        algo=new ArrayList<>();
        isempty=findViewById(R.id.isempty);
        speedcontroller=findViewById(R.id.speed_controller);
        sortpart = new ArrayList<>();
        clear = findViewById(R.id.clear);
        speedbox=findViewById(R.id.speed_control_box);
        timer = new Timer();
        console=findViewById(R.id.console);
        consolepalet=findViewById(R.id.consolepalet);
        graphbase = findViewById(R.id.graphbase);
        sorting = findViewById(R.id.sorting);
        input = findViewById(R.id.input);
        sort = findViewById(R.id.sort);

        valuebars = findViewById(R.id.valuebars);
        minimize=findViewById(R.id.minimize);
        graphpoints_vs_rv = findViewById(R.id.graphpoints_vs_rv);
        plot = findViewById(R.id.run);
        databaseReference = FirebaseDatabase.getInstance("https://visualgo-716db-default-rtdb.firebaseio.com/").getReference("userdata");
        mAuth = FirebaseAuth.getInstance();
        graph_vs_rv = findViewById(R.id.graph_vs_rv);
        array_items = new ArrayList<>();
        inputlists = new ArrayList<>();



        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this, RecyclerView.VERTICAL, false);
        graph_vs_rv.setLayoutManager(linearLayoutManager);
        LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(MainActivity.this, RecyclerView.VERTICAL, false);
        graphpoints_vs_rv.setLayoutManager(linearLayoutManager3);
        LinearLayoutManager linearLayoutManager5 = new LinearLayoutManager(MainActivity.this, RecyclerView.VERTICAL, false);
        code_analysis_rv.setLayoutManager(linearLayoutManager5);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            input.setFocusedByDefault(true);
        }

        inputlist = new inputlist("", "Enter integer values in the box below");
        inputlists.add(inputlist);
        palet_adapter = new palet_adapter(MainActivity.this, inputlists);
        inputpalet.setAdapter(palet_adapter);


        valuebars.setText("Value bars " + "(" + array_items.size() + ")");

        graphbase.setVisibility(View.GONE);



//-------------------------------------------------------------------------------------------
//permission for write external storage

        ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        }, 1);

//--------------------------------------------------------------------------------------------
//        setting toolbar

        setuptoolbar();

//-------------------------------------------------------------------------------------------
// Onclick_listeners

        speedcontroller.setProgress((int)(100-(speedvar/(float)4000)*100));

        speedcontroller.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                speedvar=4000-(int) ((speedcontroller.getProgress()/(float)100)*4000);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        code_analysis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iscodeanalysisopen=!iscodeanalysisopen;
                if(iscodeanalysisopen==true){
                    isspeedopen=false;
                    speedbox.setVisibility(View.GONE);
                    isconsoleopen=false;
                    code_analysis_box.setVisibility(View.VISIBLE);
                    consolepalet.setVisibility(View.GONE);
                }else {
                    code_analysis_box.setVisibility(View.GONE);
                }
            }
        });

        speed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isspeedopen=!isspeedopen;
                if(isspeedopen==true){
                    speedbox.setVisibility(View.VISIBLE);
                    isconsoleopen=false;
                    iscodeanalysisopen=false;
                    code_analysis_box.setVisibility(View.GONE);
                    consolepalet.setVisibility(View.GONE);
                }else{
                    speedbox.setVisibility(View.GONE);
                }
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                issorting = false;
                isempty.setVisibility(View.VISIBLE);
                graphbase.setVisibility(View.GONE);
                array_items.clear();
                sortpart.clear();
                flag = 0;
                algo.clear();
                sorting.setVisibility(View.GONE);
                valuebars.setText("Value bars " + "(" + array_items.size() + ")");
                inputlists.clear();
                inputlist = new inputlist("", "Console cleaned by user");
                inputlists.add(inputlist);

                inputlist = new inputlist("", "Enter integer values in the box below");
                inputlists.add(inputlist);
                palet_adapter = new palet_adapter(MainActivity.this, inputlists);
                inputpalet.setAdapter(palet_adapter);

                graph_vs_adapter = new graph_vs_adapter(MainActivity.this, array_items, true, -1, -1);
                graph_vs_rv.setAdapter(graph_vs_adapter);
                graph_vs_adapter.notifyDataSetChanged();


            }
        });

        minimize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                consolepalet.setVisibility(View.GONE);
                isconsoleopen=false;
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, profile_editor.class);
                i.putExtra("name", name);
                i.putExtra("url", url);
                startActivity(i);
                finish();

            }


        });



        console.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                isconsoleopen=!isconsoleopen;

                if(isconsoleopen==true){
                    consolepalet.setVisibility(View.VISIBLE);
                    isspeedopen=false;
                    iscodeanalysisopen=false;
                    code_analysis_box.setVisibility(View.GONE);
                    speedbox.setVisibility(View.GONE);
                }else{
                    consolepalet.setVisibility(View.GONE);
                }
            }
        });



        plot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if (issorting == true) {
                    inputlist = new inputlist("", "Already a sorting is in process!");
                    inputlists.add(inputlist);
                    palet_adapter = new palet_adapter(MainActivity.this, inputlists);
                    inputpalet.setAdapter(palet_adapter);
                    inputpalet.smoothScrollToPosition(inputlists.size()-1);

                } else if (TextUtils.isEmpty(input.getText().toString())) {
                    inputlist = new inputlist("", "Atleast Two values Are required to plot!");
                    inputlists.add(inputlist);
                    palet_adapter = new palet_adapter(MainActivity.this, inputlists);
                    inputpalet.setAdapter(palet_adapter);
                    inputpalet.smoothScrollToPosition(inputlists.size()-1);

                } else {
                    loadalgo(0);
                    isempty.setVisibility(View.GONE);
                    graphbase.setVisibility(View.VISIBLE);
                    array_items.clear();
                    sortpart.clear();
                    flag = 0;
                    listejector(input.getText().toString());

                    inputlist = new inputlist(array_items.toString(), "Entered Values");
                    inputlists.add(inputlist);
                    palet_adapter = new palet_adapter(MainActivity.this, inputlists);
                    inputpalet.setAdapter(palet_adapter);
                    inputpalet.smoothScrollToPosition(inputlists.size()-1);


                    valuebars.setText("Value bars " + "(" + array_items.size() + ")");
                    graph_vs_adapter = new graph_vs_adapter(MainActivity.this, array_items, false, 0, 0);
                    graph_vs_rv.setAdapter(graph_vs_adapter);


                    points_vs_adapter = new points_vs_adapter(MainActivity.this, array_items,-1,-1);
                    graphpoints_vs_rv.setAdapter(points_vs_adapter);


                }
            }
        });


        sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (issorting == true) {
                    inputlist = new inputlist("", "Already a sorting is in process!");
                    inputlists.add(inputlist);
                    palet_adapter = new palet_adapter(MainActivity.this, inputlists);

                    inputpalet.setAdapter(palet_adapter);

                    inputpalet.smoothScrollToPosition(inputlists.size()-1);
                } else if (array_items.size() < 2) {

                        inputlist = new inputlist("", "Atleast Two values Are required to plot!");
                        inputlists.add(inputlist);
                        palet_adapter = new palet_adapter(MainActivity.this, inputlists);
                        inputpalet.setAdapter(palet_adapter);
                    inputpalet.smoothScrollToPosition(inputlists.size()-1);

                    } else if(array_items.size()>0){
                    code_analysis_vs_adapter=new code_analysis_vs_adapter(MainActivity.this,algo,1);
                    code_analysis_rv.setAdapter(code_analysis_vs_adapter);

                        issorting = true;
                        sorting.setVisibility(View.VISIBLE);
                        inputlist = new inputlist("", "Sorting...");
                        inputlists.add(inputlist);
                        palet_adapter = new palet_adapter(MainActivity.this, inputlists);
                        inputpalet.setAdapter(palet_adapter);
                    inputpalet.smoothScrollToPosition(inputlists.size()-1);


                        forone(0);

                    }


            }
        });


//---------------------------------------------------------------------------------------------
//  ejecting data from inputstring


//     --------------------------------------------------------------------------------------

//setting navigation bar of drawerlayout

        toolbar.getOverflowIcon().setColorFilter(getResources().getColor(R.color.grey), PorterDuff.Mode.SRC_ATOP);

        navigationView = findViewById(R.id.navigationview_menu);
        navigationView.setItemIconTintList(null);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                for (int i = getSupportFragmentManager().getBackStackEntryCount(); i > 1; i--) {
                    getSupportFragmentManager().popBackStack();
                }

                switch (menuItem.getItemId()) {
                    case R.id.nav_home:


                        break;


                }

                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });

//-----------------------------------------------------------------------------------------
//  calling databasereference for name and url of user

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    if (postSnapshot.getKey().equals(auth.getCurrentUser().getPhoneNumber())) {
                        name = postSnapshot.child("name").getValue().toString();
                        url = postSnapshot.child("url").getValue().toString();
                        try {
                            if (profile.getDrawable() == null)
                                Picasso.with(MainActivity.this).load(url).placeholder(R.mipmap.profile).fit().centerCrop().into(profile);
                        } catch (Exception e) {
                        }


                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
//---------------------------------------------------------------------------------------------------------

    }

    //    three dot menu inflator
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_menu, menu);


        return true;
    }

    //    ----------------------------------------------------------------------------------
//    on item click listener of menu
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {

            case R.id.logout:
                AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
                builder2.setTitle("Logout")
                        .setMessage("Really want to Logout?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                FirebaseAuth.getInstance().signOut();
                                Intent k = new Intent(MainActivity.this, login.class);
                                startActivity(k);
                                finish();
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog dialog2 = builder2.create();
                dialog2.show();

        }
        return true;
    }

//    -------------------------------------------------------------------------------------


    @Override
    protected void onStart() {
        super.onStart();

        if (mAuth.getCurrentUser() == null) {
            Intent intent = new Intent(this, login.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }


    @SuppressLint("ResourceAsColor")
    private void setuptoolbar() {
        drawerLayout = findViewById(R.id.drawer);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("");
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        actionBarDrawerToggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.grey));


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    private void listejector(String input) {


        String[] splitString = input.split(",");
        for (int i = 0; i < splitString.length; i++) {

            if(!splitString[i].replace(" ","").equals("")){
            array_items.add(Integer.valueOf(splitString[i].replace(" ", "")));

            sortpart.add(Integer.valueOf(splitString[i].replace(" ", "")));

            }
        }


    }


    private void forone(final int i) {

        Log.d("truth", "forone: " + flag);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                code_analysis_vs_adapter=new code_analysis_vs_adapter(MainActivity.this,algo,1);
                code_analysis_rv.setAdapter(code_analysis_vs_adapter);
                if (i < array_items.size() - 1) {

                    fortwo(i, flag);
                }
                if (i + 1 < array_items.size() - flag -1) {

                    Log.d("iis", "run: " + i);
                    code_analysis_vs_adapter=new code_analysis_vs_adapter(MainActivity.this,algo,2);
                    code_analysis_rv.setAdapter(code_analysis_vs_adapter);
                    forone(i + 1);


                } else if (sort(array_items) == false) {
                    flag++;
                    code_analysis_vs_adapter=new code_analysis_vs_adapter(MainActivity.this,algo,2);
                    code_analysis_rv.setAdapter(code_analysis_vs_adapter);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            code_analysis_vs_adapter=new code_analysis_vs_adapter(MainActivity.this,algo,1);
                            code_analysis_rv.setAdapter(code_analysis_vs_adapter);
                            forone(0);
                        }
                    },speedvar);





                } else if (sort(array_items) == true && array_items.size() > 0) {
                    issorting = false;
                    sorting.setVisibility(View.GONE);

                    inputlist = new inputlist("", "List Has been Sorted!!");
                    inputlists.add(inputlist);
                    palet_adapter = new palet_adapter(MainActivity.this, inputlists);
                    inputpalet.setAdapter(palet_adapter);
                    inputpalet.smoothScrollToPosition(inputlists.size()-1);


                    code_analysis_vs_adapter=new code_analysis_vs_adapter(MainActivity.this,algo,-1);
                    code_analysis_rv.setAdapter(code_analysis_vs_adapter);
                } else {
                    return;
                }


            }
        }, speedvar);

    }


    private void fortwo(final int j, final int i) {


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {


                if (sort(array_items) == false && array_items.get(j + 1) < array_items.get(j)) {

                    temp = array_items.get(j + 1);
                    array_items.set(j + 1, array_items.get(j));
                    array_items.set(j, temp);

                    graph_vs_adapter = new graph_vs_adapter(MainActivity.this, array_items, true, j + 1, j);
                    graph_vs_rv.setAdapter(graph_vs_adapter);
                    graphpoints_vs_rv.smoothScrollToPosition(j+1);
                    graph_vs_adapter.notifyDataSetChanged();

                    points_vs_adapter = new points_vs_adapter(MainActivity.this, array_items,j+1,j);
                    graphpoints_vs_rv.setAdapter(points_vs_adapter);


                    code_analysis_vs_adapter=new code_analysis_vs_adapter(MainActivity.this,algo,2);
                    code_analysis_rv.setAdapter(code_analysis_vs_adapter);
                } else {
                    code_analysis_vs_adapter=new code_analysis_vs_adapter(MainActivity.this,algo,2);
                    code_analysis_rv.setAdapter(code_analysis_vs_adapter);

                    graph_vs_adapter = new graph_vs_adapter(MainActivity.this, array_items, true, j + 1, j);
                    graph_vs_rv.setAdapter(graph_vs_adapter);
                    graphpoints_vs_rv.smoothScrollToPosition(j+1);
                    graph_vs_adapter.notifyDataSetChanged();

                    points_vs_adapter = new points_vs_adapter(MainActivity.this, array_items,j+1,j);
                    graphpoints_vs_rv.setAdapter(points_vs_adapter);


                }

                if (j + 1 < array_items.size() - flag - 1 && sort(array_items) == false) {
                    fortwo(j + 1, i);

                } else {
                    code_analysis_vs_adapter=new code_analysis_vs_adapter(MainActivity.this,algo,-1);
                    code_analysis_rv.setAdapter(code_analysis_vs_adapter);

                    return;
                }
            }
        }, speedvar);
    }

    private boolean sort(List<Integer> list) {
        Collections.sort(sortpart);

        if (sortpart.equals(list)) {
            return true;
        } else {
            return false;
        }

    }


    private void Bubblesort(int[] arr){
        // initializing temperary variable
        int temp=0;
            // outer loop that will iterate through every single element
        for(int i=0;i<arr.length;i++){

            // inner loop that will compare weather the ith element is greater or jth
            for(int j=1;j<arr.length-i;j++){

                //comparing elements
                if(arr[i]>arr[j]){

                // Swappig elements
                    temp=arr[i];
                    arr[i]=arr[j];
                    arr[j]=temp;
                }

            }
        }
    }
    private void loadalgo(int a){
        algo.clear();
        if(a==0){
            algo.add(" private void Bubblesort(int[] arr){\n" +
                    "        // initializing temperary variable\n" +
                    "        int temp=0;");

            algo.add(" // outer loop that will iterate through every single element\n" +
                    "        for(int i=0;i<arr.length;i++){\n");

           algo.add("  for(int j=1;j<arr.length-i;j++){\n" +
                   "\n" +
                   "                //comparing elements\n" +
                   "                if(arr[i]>arr[j]){\n" +
                   "\n" +
                   "                // Swappig elements\n" +
                   "                    temp=arr[i];\n" +
                   "                    arr[i]=arr[j];\n" +
                   "                    arr[j]=temp;\n" +
                   "                }\n" +
                   "\n" +
                   "            }");


           algo.add(" }");

           algo.add("  }");

            code_analysis_vs_adapter=new code_analysis_vs_adapter(MainActivity.this,algo,-1);
            code_analysis_rv.setAdapter(code_analysis_vs_adapter);
        }
    }


}
