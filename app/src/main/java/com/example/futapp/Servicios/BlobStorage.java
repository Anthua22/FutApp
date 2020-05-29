package com.example.futapp.Servicios;

import android.content.Context;
import android.os.AsyncTask;

import com.example.futapp.ClasesPojos.Partidos;
import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.StorageCredentials;
import com.microsoft.azure.storage.StorageCredentialsAccountAndKey;
import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.blob.BlobOutputStream;
import com.microsoft.azure.storage.blob.CloudBlob;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.CloudBlockBlob;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.security.InvalidKeyException;

public class BlobStorage {
    static final String CREDENCIALES = "bVOOCgvwNx3sav8YOzrT8+zYJOn2p3LpcpVzcm9IHkbpQ6ZVOyXQdh6+IjXVajNYkak+0cLQUHCqjXWbC+c0hA==";
    static final String CONTENEDOR ="actas";
    static final String ACCOUNTNAME="imagenesproyectofutapp";

    StorageCredentials credentials;
    CloudStorageAccount cuenta;
    CloudBlobContainer contenedorArchivos;
    CloudBlobClient blobClient;


    public  BlobStorage(){
        try {
            credentials = new StorageCredentialsAccountAndKey(ACCOUNTNAME,CREDENCIALES);
            if(credentials!= null){
                cuenta = new CloudStorageAccount(credentials,true);
                blobClient = cuenta.createCloudBlobClient();
                contenedorArchivos = blobClient.getContainerReference(CONTENEDOR);
            }

        } catch (StorageException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public String subirArchivo(String nombrearchivo, Context c){
        String rutanueva="";
        try {
            CloudBlockBlob blockBlob = contenedorArchivos.getBlockBlobReference(nombrearchivo);



            BlobOutputStream fc =blockBlob.openOutputStream();
            File s = new File(c.getFilesDir(),nombrearchivo);
          /*  byte[] buffer = Files.readAllBytes( s.toPath());
            ByteArrayInputStream inputStream = new ByteArrayInputStream(buffer);
            int next = inputStream.read();
            while (next != -1) {
                fc.write(next);
                next = inputStream.read();
            }
            rutanueva= blockBlob.getUri().getPath();
            fc.close();*/
            Asincrona asincrona = new Asincrona(blockBlob,rutanueva,s.getAbsolutePath());
            asincrona.execute();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (StorageException e) {
            e.printStackTrace();
        }
        return rutanueva;
    }


    class Asincrona extends AsyncTask<Void, Void, String>{
        CloudBlockBlob blob;
        String url;
        String path;


        public Asincrona(CloudBlockBlob blob, String url,String path){
            this.blob = blob;
            this.url = url;
            this.path = path;
        }



        @Override
        protected String doInBackground(Void... voids) {
            try {
                blob.uploadFromFile(path);
                return blob.getUri().getPath();
            } catch (StorageException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            url = s;
        }
    }

}

