package com.example.futapp.Servicios;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.futapp.ClasesPojos.Partidos;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.UUID;

public class FireBaseServicio {

    FirebaseStorage storage;
    StorageReference reference;
    public FireBaseServicio() {
        storage = FirebaseStorage.getInstance();
        reference = storage.getReference();
        UUID.randomUUID();
    }

    public void subirArchivo(String archivo, StorageReference reference, Context context, Partidos partidos){
        final  StorageReference storageReference = reference.child("actas/");
        try {

            InputStream archivoin = new FileInputStream(archivo);
            storageReference.putStream(archivoin)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(context, "Se subió el archivo",Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(context, "No se pudo subir el archivo",Toast.LENGTH_SHORT).show();
                }
            });
            partidos.setActa(storageReference.getPath());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }

}
