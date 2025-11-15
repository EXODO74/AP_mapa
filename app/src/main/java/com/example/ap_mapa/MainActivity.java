package com.example.ap_mapa;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

public class MainActivity extends AppCompatActivity {

    // Declara la variable para la vista del mapa
    private MapView map = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Obtiene el contexto de la aplicación
        Context ctx = getApplicationContext();
        // Carga la configuración de OSMDroid
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));
        // Establece el User Agent con el nombre del paquete de la app
        Configuration.getInstance().setUserAgentValue(getPackageName());

        // layout de la actividad, que contiene el MapView
        setContentView(R.layout.activity_main);

        // Vincula la variable del mapa con el componente MapView del layout
        map = (MapView) findViewById(R.id.map);
        // Establece el origen de los mapas . MAPNIK es el estilo estándar de OpenStreetMap.
        map.setTileSource(TileSourceFactory.MAPNIK);

        // Habilita los controles de zoom con gestos
        map.setMultiTouchControls(true);

        // Obtiene el controlador del mapa para poder manipular la cámara
        IMapController mapController = map.getController();
        // Establece un nivel de zoom inicial, un valor más alto es mas cerca.
        mapController.setZoom(14.0);

        // coordenadas
        GeoPoint coord1 = new GeoPoint(-17.781916745109175, -63.189223551120655);
        GeoPoint coord2 = new GeoPoint(-17.78577921118331, -63.188474196981794);
        GeoPoint coord3 = new GeoPoint(-17.791827631358117, -63.174432220977636);
        GeoPoint coord4 = new GeoPoint(-17.77463892975279, -63.17544572575334);

        // Carga el icono rojo desde la carpeta .
        Drawable rojo = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_rojo, null);
        // Añade cada marcador al mapa usando.
        addMarker(coord1, "Ubicación 1", rojo);
        addMarker(coord2, "Ubicación 2", rojo);
        addMarker(coord3, "Ubicación 3", rojo);
        addMarker(coord4, "Ubicación 4", rojo);


        //coordenadas azules.
        GeoPoint blueCoord1 = new GeoPoint(-17.79446823884921, -63.15858810924311);
        GeoPoint blueCoord2 = new GeoPoint(-17.81259337601185, -63.17193147526529);
        GeoPoint blueCoord3 = new GeoPoint(-17.7587557140637, -63.178025253023876);
        GeoPoint blueCoord4 = new GeoPoint(-17.759322382331252, -63.174306362769926);

        // Carga el icono azul.
        Drawable azul = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_azul, null);

        // Añade los marcadores azules al mapa.
        addMarker(blueCoord1, "Ubicación Azul 1", azul);
        addMarker(blueCoord2, "Ubicación Azul 2", azul);
        addMarker(blueCoord3, "Ubicación Azul 3", azul);
        addMarker(blueCoord4, "Ubicación Azul 4", azul);

        // Centra el mapa en la primera coordenada al iniciar
        mapController.setCenter(coord1);
    }

    private void addMarker(GeoPoint center, String title, Drawable icon) {
        // Crea un nuevo objeto Marcador .
        Marker marker = new Marker(map);
        // Establece la posición geográfica del marcador
        marker.setPosition(center);
        // se establece el ancla del icono para que la punta del marcador señale la coordenada
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        // Asigna el título al marcador.
        marker.setTitle(title);
        // Comprueba si se ha proporcionado un icono personalizado
        if (icon != null) {
            // Si hay un icono, lo asigna al marcador
            marker.setIcon(icon);
        } else {
            // Si no hay icono, asigna el marcador por defecto de la librería OSMDroid
            marker.setIcon(ResourcesCompat.getDrawable(getResources(), org.osmdroid.library.R.drawable.marker_default, null));
        }
        // Añade el marcador a la capa de superposiciones del mapa para que sea visible
        map.getOverlays().add(marker);
    }

    // Es necesario llamar a onResume() del mapa para que se redibuje correctamente al volver a la app
    public void onResume(){
        super.onResume();
        map.onResume(); // Esencial para overlays como la brújula o la ubicación actual
    }

    // Es necesario llamar a onPause() del mapa para ahorrar recursos cuando la app no está en primer plano
    public void onPause(){
        super.onPause();
        map.onPause();  // Detiene el renderizado del mapa y otras tareas en segundo plano
    }
}
