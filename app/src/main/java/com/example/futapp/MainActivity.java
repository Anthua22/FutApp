package com.example.futapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Toast;

import com.example.futapp.VistasFragments.DatosBasicosFragment;
import com.example.futapp.VistasFragments.DialogoEventoFragment;
import com.example.futapp.VistasFragments.DialogoGolFragment;
import com.example.futapp.VistasFragments.FaltasTMLocalesVisitantesFragment;
import com.example.futapp.VistasFragments.IncidenciasFragment;
import com.example.futapp.VistasFragments.InformacionPartidoFragment;
import com.example.futapp.VistasFragments.JugadoresLocalesVisitantesFragment;
import com.example.futapp.VistasFragments.LoginFragment;
import com.example.futapp.VistasFragments.ResultadoPartidoFragment;
import com.example.futapp.VistasFragments.StaffsLocalesVisitantesFragment;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ResultadoPartidoFragment.EnviarInformacion, JugadoresLocalesVisitantesFragment.EnviarAlineaciones, DialogoEventoFragment.EnviarSanciones, DialogoGolFragment.EnviarGolesInterface, StaffsLocalesVisitantesFragment.EnviarAsistenciaStaff,
        FaltasTMLocalesVisitantesFragment.EnviarFaltasYTM, IncidenciasFragment.EnviarIncidencias, DatosBasicosFragment.EnviarDatosdelPartido {


    String text;
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

    public static void generaArchivo(Context context){

        String archivo ="acta.txt";
        FileOutputStream FO;


        try {
            if(encabezado!=null){
                FO = context.openFileOutput(archivo,Context.MODE_PRIVATE);
                FO.write(encabezado.getBytes());
                if(resultado !=null){
                    String result;
                    if(suspension!=null){
                        result = "Resultado: "+resultado+'\n'+"Suspendido por: "+suspension+'\n';
                    }
                    else{
                        result ="Resultado: "+resultado+'\n';
                    }

                    FO.write(result.getBytes());
                }
                if(asistenciajugadores!=null){
                    String asistencia = obtnerAsistenciaJugadores();
                    FO.write(asistencia.getBytes());
                }
                if(asistenciastaff!=null){
                    FO.write(obtenerAsistenciaStaffs().getBytes());
                }
                if(golesmarcados.size()>0){
                    String res = "Goles Marcados:\n";
                    FO.write(res.getBytes());
                    for(String x: golesmarcados){
                        FO.write(x.getBytes());
                    }
                }if(faltascometidas.size()>0)
                {
                    String res ="Sanciones/Lesiones:\n";
                    FO.write(res.getBytes());
                    for(String x:faltascometidas){
                        FO.write(x.getBytes());
                    }
                }if(faltas !=null){
                    FO.write(faltas.getBytes());
                }if(incidencias!=null){
                    FO.write(incidencias.getBytes());
                }
                FO.close();

            }else{
                Toast.makeText(context, "Falta por pasar los datos básicos del partido",Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static String obtnerAsistenciaJugadores(){
        String result ="";
        String[] jugaodresequipos = asistenciajugadores.split(":");
        String[] jugaodreslocales = jugaodresequipos[0].split("\n");
        String[] jugadoresvisitantes = jugaodresequipos[1].split("\n");

        result="Jugadores Locales Titulares:\n";
        for(String x : jugaodreslocales){
            String[] propiedadesjugadors = x.split(",");
            if(propiedadesjugadors[2].equals("true")){
                result+=" "+propiedadesjugadors[0]+" con el dorsal "+propiedadesjugadors[1]+'\n';
            }
        }

        result+="Jugadores Locales Suplentes:\n";
        for(String x : jugaodreslocales){
            String[] propiedadesjugadors = x.split(",");
            if(propiedadesjugadors[3].equals("true")){
                result+=" "+propiedadesjugadors[0]+" con el dorsal "+propiedadesjugadors[1]+'\n';
            }
        }

        result+="Jugadores Visitantes Titulares:\n";
        for(String x : jugadoresvisitantes){
            String[] propiedadesjugadors = x.split(",");
            if(propiedadesjugadors[2].equals("true")){
                result+=" "+propiedadesjugadors[0]+" con el dorsal "+propiedadesjugadors[1]+'\n';
            }
        }

        result+="Jugadores Visitantes Suplentes:\n";
        for(String x : jugadoresvisitantes){
            String[] propiedadesjugadors = x.split(",");
            if(propiedadesjugadors[3].equals("true")){
                result+=" "+propiedadesjugadors[0]+" con el dorsal "+propiedadesjugadors[1]+'\n';
            }
        }

        return result;
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
