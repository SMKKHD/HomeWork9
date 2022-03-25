package com.example.homework9;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.telephony.TelephonyCallback;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.security.ProviderInstaller;
import com.google.android.material.textfield.TextInputEditText;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class MainActivity extends AppCompatActivity {

    private Spinner spinner;
    TextInputEditText textInputEditText;
    EditText PMV, MovieName, Time;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        spinner = findViewById(R.id.spinner2);
        PMV = findViewById(R.id.PMV);
        Document document = readerXML("https://www.finnkino.fi/xml/TheatreAreas/");
        spinnerMaker(document);
    }


    public Document readerXML(String urlString) {

        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.parse(urlString);
            doc.getDocumentElement().normalize();
            return doc;
            //System.out.println("Root element: "+ doc.getDocumentElement().getNodeName());


        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void spinnerMaker(Document doc) {
        NodeList nodeList = doc.getDocumentElement().getElementsByTagName("TheatreArea");
        TheaterList tList = TheaterList.getInstance();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            System.out.println("Element is this: " + node.getNodeName());

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                Theatre theatre = new Theatre();
                //System.out.println("ID");
                //System.out.println(element.getElementsByTagName("ID").item(0).getTextContent());
                theatre.setTheatreID(element.getElementsByTagName("ID").item(0).getTextContent());

                //System.out.println("Name");
                //System.out.println(element.getElementsByTagName("Name").item(0).getTextContent());
                theatre.setPlace(element.getElementsByTagName("Name").item(0).getTextContent());
                tList.addList(theatre);
            }

        }
        ArrayList<String> List = new ArrayList<String>();
        for (int j = 0; j < tList.getTList().size(); j++) {
            List.add(tList.getTList().get(j).getPlace());
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, List);
        spinner.setAdapter(arrayAdapter);
    }

    public void urlChecker(View view){
        String theatre_name = spinner.getSelectedItem().toString();
        TheaterList list = TheaterList.getInstance();
        String id = list.loopForList(theatre_name);
        String date = PMV.getText().toString();
        String urlMovies = "https://www.finnkino.fi/xml/Schedule/?area=" + id +  "&dt=" + date;
        Document docMoviesURL = readerXML(urlMovies);

        NodeList nodeList = docMoviesURL.getDocumentElement().getElementsByTagName("Show");


        // Päivämäärät
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat new_simpleDateFormat = new SimpleDateFormat("HH:mm");
        Date date1 = null;
        Date date2 = null;
        MovieList movieList = MovieList.getSingleton();
        movieList.clearList();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;

                String title = element.getElementsByTagName("Title").item(0).getTextContent().toString();
                String stime = element.getElementsByTagName("dttmShowStart").item(0).getTextContent().toString();
                String etime = element.getElementsByTagName("dttmShowEnd").item(0).getTextContent().toString();

                try {
                    date1 = simpleDateFormat.parse(stime);
                    date2 = simpleDateFormat.parse(etime);
                }
                 catch (ParseException e) {
                    e.printStackTrace();
                }

                stime = new_simpleDateFormat.format(date1);
                etime = new_simpleDateFormat.format(date2);

                String allDetails = title +" " + stime +" - "+ etime;
                movieList.setMovies(allDetails);


            }



        }
        // uusi ikkuna leffalistalle
        openNewWindow();
    }
    public void openNewWindow(){
        Intent intent = new Intent(this, MainActivity2.class);
        startActivity(intent);

    }



}