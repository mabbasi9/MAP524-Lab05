package com.example.student.week5;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;










public class MainActivity extends ActionBarActivity {
    //step 1
    String[] colourNames;
    String[] colour;
    String myColor;
    RelativeLayout layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //step 2
        colourNames = getResources().getStringArray(R.array.listArray);

        colour = getResources().getStringArray(R.array.listValues);
        //step 3
        final ListView lv = (ListView) findViewById(R.id.listView);

        layout= (RelativeLayout) findViewById(R.id.main);
        //step 4
        ArrayAdapter aa = new ArrayAdapter(this, R.layout.activity_listview, colourNames);
        lv.setAdapter(aa);



        try {
            File myFile = new File("/sdcard/myApp/myFile.txt");
            if(myFile.exists()) {
                FileInputStream fIn = new FileInputStream(myFile);
                BufferedReader myReader = new BufferedReader(
                        new InputStreamReader(fIn));
                String aDataRow = "";
                String aBuffer = "";
                while ((aDataRow = myReader.readLine()) != null) {
                    aBuffer = aDataRow;
                }
                //txtData.setText(aBuffer);
                myReader.close();

                if (aBuffer == "")
                    layout.setBackgroundColor(Color.WHITE);
                else
                    layout.setBackgroundColor(Color.parseColor("#" + (aBuffer.substring(2))));
            }

        } catch (Exception e) {
            Toast.makeText(getBaseContext(), e.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }


        //final int size = colourNames.length;

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               // String selection = (String) parent.getItemAtPosition(position);
                //v.setBackgroundColor(getResources().getColor(pcolour[position]));
               // lv.setBackgroundColor(Color.CYAN);

               layout.setBackgroundColor(Color.parseColor("#" + (colour[position].substring(2))));
                //Toast.makeText(getApplicationContext(), [((TextView) view).getText(), Toast.LENGTH_SHORT).show();


             }
        });

        registerForContextMenu(lv);


    }

   @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
       AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
      myColor = colour[info.position];


        menu.setHeaderTitle("Select The Action");
        menu.add(0, v.getId(), 0, "write colour to SDCard");
        menu.add(0, v.getId(), 0, "read colour from SDCard");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getTitle() == "write colour to SDCard") {

           /*try {
                File myFile = new File("/sdcard/MyApp/myFile.txt");
                myFile.createNewFile();
                FileOutputStream fOut = new FileOutputStream(myFile);
                OutputStreamWriter myOutWriter =
                        new OutputStreamWriter(fOut);

                myOutWriter.append(myColor);
                myOutWriter.close();
                fOut.close();
                Toast.makeText(getBaseContext(),
                        "Done writing SD 'myFile.txt'",
                        Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(getBaseContext(), e.getMessage(),
                      Toast.LENGTH_SHORT).show();
            }*/


            try {
                File sdCard = Environment.getExternalStorageDirectory();
                File dir = new File(sdCard.getAbsolutePath() + "/myApp");
                dir.mkdir();
                File file = new File(dir, "myFile.txt");

                FileOutputStream f = new FileOutputStream(file);
                OutputStreamWriter fOut = new OutputStreamWriter(f);

                fOut.write(myColor);
                fOut.flush();
                fOut.close();
                Toast.makeText(getApplicationContext(), "Writing to SD is done", Toast.LENGTH_LONG).show();

            }catch (Exception e)
            {
                Toast.makeText(getApplicationContext(), e.getMessage(),Toast.LENGTH_SHORT).show();
            }


        } else if (item.getTitle() == "read colour from SDCard")
        {

            try {
                File myFile = new File("/sdcard/myApp/myFile.txt");
                FileInputStream fIn = new FileInputStream(myFile);
                BufferedReader myReader = new BufferedReader(
                        new InputStreamReader(fIn));
                String aDataRow = "";
                String aBuffer = "";
                while ((aDataRow = myReader.readLine()) != null) {
                    aBuffer = aDataRow;
                }
                //txtData.setText(aBuffer);

                layout.setBackgroundColor(Color.parseColor("#" + (aBuffer.substring(2))));
                myReader.close();
                Toast.makeText(getBaseContext(),
                        "Done reading SD 'myFile.txt'",
                        Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(getBaseContext(), e.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }


            /*****File sdcard = Environment.getExternalStorageDirectory();

            //Get the text file
            File file = new File(sdcard,"myFile.txt");

            //Read text from file
            StringBuilder text = new StringBuilder();

            try {
                BufferedReader br = new BufferedReader(new FileReader(file));
                String line;

                while ((line = br.readLine()) != null) {
                    myColor.append(line);
                    //text.append('\n');
                }
            }
            catch (IOException e) {
                //You'll need to add proper error handling here
            }*******/

           // Toast.makeText(getApplicationContext(), "Reading from SD is done", Toast.LENGTH_LONG).show();
        } else {
            return false;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
