package com.example.futapp;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.futapp.ClasesPojos.Arbitros;
import com.example.futapp.ClasesPojos.Partidos;
import com.example.futapp.Servicios.BlobStorage;
import com.example.futapp.Servicios.FireBaseServicio;
import com.example.futapp.Servicios.ServicioApiRestUtilidades;
import com.example.futapp.VistasFragments.DatosBasicosFragment;
import com.example.futapp.VistasFragments.DialogoEventoFragment;
import com.example.futapp.VistasFragments.DialogoGolFragment;
import com.example.futapp.VistasFragments.FaltasTMLocalesVisitantesFragment;
import com.example.futapp.VistasFragments.IncidenciasFragment;
import com.example.futapp.VistasFragments.JugadoresLocalesVisitantesFragment;
import com.example.futapp.VistasFragments.LoginFragment;
import com.example.futapp.VistasFragments.ResultadoPartidoFragment;
import com.example.futapp.VistasFragments.StaffsLocalesVisitantesFragment;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements ResultadoPartidoFragment.EnviarInformacion, JugadoresLocalesVisitantesFragment.EnviarAlineaciones,
        DialogoEventoFragment.EnviarSanciones, DialogoGolFragment.EnviarGolesInterface, StaffsLocalesVisitantesFragment.EnviarAsistenciaStaff,
        FaltasTMLocalesVisitantesFragment.EnviarFaltasYTM, IncidenciasFragment.EnviarIncidencias, DatosBasicosFragment.EnviarDatosdelPartido {

    String text;
    static final String PROYECTO = "data/data/com.example.futapp/files/";
    static String encabezado;
    static String resultado, suspension, asistenciajugadores, asistenciastaff, faltas, incidencias;
    static ArrayList<String> golesmarcados = new ArrayList<>();
    static ArrayList<String> faltascometidas = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentManager FM = getSupportFragmentManager();
        FragmentTransaction FT= FM.beginTransaction();


        Fragment fragment = new LoginFragment();
        FT.add(R.id.principal, fragment);
        FT.commit();
    }

    public static String obtenerNombreArchivo(){

        if(encabezado!=null){

            String[] items = encabezado.split("\n");
            String file = items[0]+".txt";

            return  items[0]+".txt";


        }
        return null;
    }




    public static int generaArchivo(Context context, Partidos partidos){

        FileOutputStream FO;
        try {
            //Se comprueba que haya recibido informacion para generar el archivo
            if(comprobarDatosRequeridos()){
                //Cabecera
                String ruta = obtenerNombreArchivo();
                FO = context.openFileOutput(ruta,Context.MODE_PRIVATE);
                FO.write(encabezado.getBytes());


                //Body
                String result;
                if(suspension!=null){
                    result = "Resultado: "+resultado+'\n'+"Suspendido por: "+suspension+'\n';
                }
                else{
                    result ="Resultado: "+resultado+'\n';
                }
                FO.write(result.getBytes());


                FO.write(asistenciajugadores.getBytes());
                if(asistenciastaff!=null){
                    FO.write(obtenerAsistenciaStaffs().getBytes());
                }
                String res = "Goles Marcados:\n";
                FO.write(res.getBytes());
                for(String x: golesmarcados){
                    FO.write(x.getBytes());
                }


                if(faltascometidas.size()>0)
                {
                    String resfa ="Sanciones/Lesiones:\n";
                    FO.write(resfa.getBytes());
                    for(String x:faltascometidas){
                        FO.write(x.getBytes());
                    }
                }if(faltas !=null){
                    FO.write(faltas.getBytes());
                }if(incidencias!=null){
                    FO.write(incidencias.getBytes());
                }
                FO.close();
                partidos.setActa(ruta);
                return 1;
            }else{
                return -1;
            }


        } catch (IOException e) {
            Toast.makeText(context, e.getMessage(),Toast.LENGTH_SHORT).show();
        }

        return 0;
    }

    public static boolean comprobarDatosRequeridos(){
        return (encabezado!=null) && (resultado !=null) && (asistenciajugadores!=null)&& (golesmarcados.size()>0);
    }

    public static boolean cerrarPartido(Partidos partidos, Context context){

        if(partidos.getActa()!=null && partidos.getActa().length()>0){
            if(resultado!=null){
                FireBaseServicio fireBaseServicio = new FireBaseServicio();
                StorageReference storageReference = FirebaseStorage.getInstance().getReference();
                fireBaseServicio.subirArchivo(obtenerNombreArchivo(),storageReference, context, partidos);
                partidos.setResultado(resultado);
                partidos.setDisputado(1);
                ServicioApiRestUtilidades servicioApiRestUtilidades = new ServicioApiRestUtilidades();
                Call<Partidos> response =servicioApiRestUtilidades.servicioApiRest.updatePartido(partidos.getIdPartido(), partidos);
                response.enqueue(new Callback<Partidos>() {
                    @Override
                    public void onResponse(Call<Partidos> call, Response<Partidos> response) {
                        if(response.isSuccessful()){
                            return;
                        }
                    }

                    @Override
                    public void onFailure(Call<Partidos> call, Throwable t) {
                        return;
                    }
                });

                return true;
            }


        }

        return false;
    }



    public static String obtenerAsistenciaStaffs()
    {
        String result="";
        String[] staffsequipos = asistenciastaff.split(":");
        String[] staffslocales = staffsequipos[0].split("\n");
        String[] staffsvisitantes = staffsequipos[1].split("\n");

        result+="Staffs Locales:\n";
        for(String x : staffslocales){
            String[] propiedadesstaffs = x.split(",");
            result += propiedadesstaffs[0]+" como "+propiedadesstaffs[1]+'\n';
        }
        result+="Staff Visitantes:\n";
        for(String x : staffsvisitantes){
            String[] propiedadesstaffs = x.split(",");
            result += propiedadesstaffs[0]+" como "+propiedadesstaffs[1]+'\n';
        }
        return result;
    }



    @Override
    public void Enviar(String resultado, String d) {
        text = resultado;

        if(d.length()>0){
            suspension = d;
        }
        this.resultado=resultado;
        Toast.makeText(this,"Se ha enviado correctamente los datos",Toast.LENGTH_SHORT).show();


    }


    @Override
    public void Enviar(String alineacion) {
        asistenciajugadores = alineacion;
        Toast.makeText(this,"Se ha enviado correctamente los datos",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void EnvioSanciones(String string) {
        faltascometidas.add(string);
        Toast.makeText(this,"Se ha enviado correctamente los datos",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void EnviarGoles(String te) {
        // la variable te es el String que manda el fragment a la activity

        golesmarcados.add(te);
        Toast.makeText(this,"Se ha enviado correctamente los datos",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void EnviarStaff(String str) {

        asistenciastaff = str;
        Toast.makeText(this,"Se ha enviado correctamente los datos",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void EnvioFaltasyTiempos(String st) {
        faltas = st;
        Toast.makeText(this,"Se ha enviado correctamente los datos",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void EnvioIncidencias(String inc) {
        incidencias =inc;
        Toast.makeText(this,"Se ha enviado correctamente los datos",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void EnviarDatosBasicos(String st) {
        encabezado = st;
        Toast.makeText(this,"Se ha enviado correctamente los datos",Toast.LENGTH_SHORT).show();
    }
}
