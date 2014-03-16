package fr.upem.android.deadhal.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.xml.transform.TransformerException;

import android.os.Environment;
import android.util.Log;

import fr.upem.android.deadhal.maze.XMLWriter;

public class ExternalStorageIO
{
    public static void save(XMLWriter writer) throws TransformerException, IOException, RuntimeException
    {
        String state            = Environment.getExternalStorageState();
        if (!Environment.MEDIA_MOUNTED.equals(state))
            throw new RuntimeException("SDCard not mounted");

        File file           = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), writer.getFileName());
        FileOutputStream fp = new FileOutputStream(file);
        Log.e("DH", "Save to " + writer.getFileName());
        Log.e("DH", writer.getContent());
        fp.write(writer.getContent().getBytes());
        fp.close();
    }

    public static String load(String levelName) throws IOException, RuntimeException
    {
        String temp;
        String state            = Environment.getExternalStorageState();
        if (!Environment.MEDIA_MOUNTED.equals(state)
                && !Environment.MEDIA_MOUNTED_READ_ONLY.equals(state))
            throw new RuntimeException("SDCard not mounted");

        File file               = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), levelName);
        FileInputStream fp      = new FileInputStream(file);
        InputStreamReader isr   = new InputStreamReader(fp);
        BufferedReader br       = new BufferedReader(isr);
        StringBuilder content   = new StringBuilder();

        while ((temp = br.readLine()) != null)
            content.append(temp);
        br.close();
        
        return content.toString();
    }
}
