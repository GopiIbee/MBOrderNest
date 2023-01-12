package com.ibee.mbordernest.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ibee.mbordernest.R;
import com.ibee.mbordernest.printer.EscPosPrinter;
import com.ibee.mbordernest.printer.tcp.TcpConnection;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TestPrintActivity extends AppCompatActivity {

    String formattedDate = "";
    Calendar c;
    String formattedTime = "";
    String[] name = {"Chapati", "Chicken Chettinad Full"};
    int[] price = {70, 130};
    int[] quantity = {1, 1};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_print);

        EditText editText = (EditText) findViewById(R.id.edittest);
        EditText porttest = (EditText) findViewById(R.id.porttest);
        TextView textview = (TextView) findViewById(R.id.textview);

        textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editText.getText().toString().equals("")) {
                    Toast.makeText(TestPrintActivity.this, "Plase Enter Ip address Ex; 192.168.0.1", Toast.LENGTH_LONG).show();
                } else {
                    new Thread(new Runnable() {
                        public void run() {
                            try {
                                String textData = null;
//                                int portnumber = Integer.parseInt(porttest.getText().toString());
                                EscPosPrinter printer = new EscPosPrinter(new TcpConnection(editText.getText().toString(), 9100, 15), 203, 48f, 32);
                                {
                                    c = Calendar.getInstance();

                                    SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                                    SimpleDateFormat tf = new SimpleDateFormat("HH:mm");

                                    formattedDate = df.format(c.getTime());
                                    formattedTime = tf.format(c.getTime());


                                    textData = ("[C]<font size='big'>Date      : " + formattedDate + "</font>");
                                    textData = textData + ("[L]Date      : " + formattedDate);
                                    textData = textData + ("[R]Time  : " + formattedTime + "\n");

                                    textData = textData + ("[L]Order By  : " + "username");
                                    textData = textData + ("[R]KOT   : " + "001" + "\n");
                                    textData = textData + ("[L]Bill No: " + "001" + " \n");

                                    textData = textData + ("[L]Order Type: " + "orderName");
                                    textData = textData + ("[R]Table : " + "T1" + "\n");

                                    String guestName = "";
                                /*    if (guest_name.getText().toString().isEmpty()) {
                                        guestName = "Guest";
                                    } else {
                                        guestName = guest_name.getText().toString();

                                    }*/
                                    textData = textData + ("[C]Guest Name: " + guestName + "\n");

                                    textData = textData + ("[C]----------------------------------------------\n");

                                    textData = textData + ("[L]Item Name");
                                    textData = textData + ("[R][R]Qty 2\n");
                                    textData = textData + ("[C]----------------------------------------------\n");
                                    String res = "";
                                    for (int i = 0; i < name.length; ++i) {
                                        res += String.format("%2d. %-23s %4d %4d %4d%n", i + 1, name[i],
                                                price[i], quantity[i], price[i] * quantity[i]);
                                    }
                                    textData = textData + ("[C]" + res + "\n\n");


                                }

                                printer.printFormattedTextAndCut((textData));

/*
                                printer.printFormattedText(
//                                    "[C]<img>" + PrinterTextParserImg.bitmapToHexadecimalString(printer, getApplicationContext().getResources().getDrawableForDensity(R.drawable.logo, DisplayMetrics.DENSITY_MEDIUM)) + "</img>\n" +
//                                            "[L]\n" +
                                        "[C]<u><font size='big'>" + "StoreName" + "</font></u>\n" +
                                                "[L]\n" +
                                                "[C]==========================================\n" +
                                                "[L]\n" +
                                                "[L]<b>BEAUTIFUL SHIRT</b>[R]9.99e\n" +
                                                "[L]  + Size : S\n" +
                                                "[L]\n" +
                                                "[L]<b>AWESOME HAT</b>[R]24.99e\n" +
                                                "[L]  + Size : 57/58\n" +
                                                "[L]\n" +
                                                "[C]------------------------------------------\n" +
                                                "[R]TOTAL PRICE :[R]34.98e\n" +
                                                "[R]TAX :[R]4.23e\n" +
                                                "[L]\n" +
                                                "[C]==========================================\n" +
                                                "[L]\n" +
                                                "[L]<font size='tall'>Customer :</font>\n" +
                                                "[L]Raymond DUPONT\n" +
                                                "[L]5 rue des girafes\n" +
                                                "[L]31547 PERPETES\n" +
                                                "[L]Tel : +33801201456\n"
                                );
*/
                            } catch (Exception e) {
                                e.printStackTrace();
                                System.out.println(e.getMessage());
//                                Toast.makeText(TestPrintActivity.this,""+e.getMessage(),Toast.LENGTH_LONG);
                            }
                        }
                    }).start();
                }

            }
        });

    }
}