package org.um.ziga.lzprojekt

import android.app.Activity
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v7.appcompat.R.id.image
import android.util.Log
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_zemljevid.*
import java.io.IOException


class Zemljevid : AppCompatActivity(), OnMapReadyCallback,
    OnMarkerClickListener {

    override fun onMarkerClick(p0: Marker?) = false

    private lateinit var map: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var lastLocation: Location // spremenljivka za pridobivanje trenutne pozicije

    // 1
    private lateinit var locationCallback: LocationCallback
    // 2
    private lateinit var locationRequest: LocationRequest
    private var locationUpdateState = false

    companion object { // za permission
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
        // 3
        private const val REQUEST_CHECK_SETTINGS = 2
    }

    val REQUEST_IMAGE_CAPTURE = 1 // za kamero

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_zemljevid)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(p0: LocationResult) {
                super.onLocationResult(p0)

                lastLocation = p0.lastLocation
                placeMarkerOnMap(LatLng(lastLocation.latitude, lastLocation.longitude))
            }
        }
        createLocationRequest()

        slikaj.setOnClickListener(){
            var i = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(i,123)
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        // Add a marker in Sydney and move the camera
        /*val sydney = LatLng(-34.0, 151.0)
        map.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        map.moveCamera(CameraUpdateFactory.newLatLng(sydney))*/

        val slomskovTrg = LatLng(46.5596944,15.6428725)
        val mariborGrad = LatLng(46.5595225,15.6429587)
        val muzejOsvoboditve = LatLng(46.5595909,15.6438641)
        val sodniStolp = LatLng(46.5595909,15.6438641)
        val vodniStolp = LatLng(46.5566302,15.648396)
        val kuznoZnamenje = LatLng(46.558123,15.6451389)

        //val titleStr = getAddress(slomskovTrg)  // dodamo naslov zraven markerja
        map.addMarker(MarkerOptions()
            .position(slomskovTrg).title("Slomškov trg")
            .icon(BitmapDescriptorFactory.fromResource(R.drawable.flag))
        )
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(slomskovTrg, 12.0f))

        map.addMarker(MarkerOptions()
            .position(mariborGrad).title("Mariborski Castle")
            .icon(BitmapDescriptorFactory.fromResource(R.drawable.flag))
        )
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(mariborGrad, 12.0f))

        map.addMarker(MarkerOptions()
            .position(kuznoZnamenje).title("Kužno znamenje")
            .icon(BitmapDescriptorFactory.fromResource(R.drawable.flag))
        )
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(kuznoZnamenje, 12.0f))

        map.addMarker(MarkerOptions()
            .position(vodniStolp).title("Vodni stolp")
            .icon(BitmapDescriptorFactory.fromResource(R.drawable.flag))
        )
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(vodniStolp, 12.0f))

        map.getUiSettings().isZoomControlsEnabled = true
        map.setOnMarkerClickListener(this)

        map.uiSettings.isZoomControlsEnabled = true
        map.setOnMarkerClickListener(this)

        setUpMap()

    }

    private fun setUpMap() { // To imamo da vprašamo uporabnika če nam dovoli dostop do lokacije uporabljene naprave
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE
            )
            return
        }
        /*
        // 1
        map.isMyLocationEnabled = true // to nam omogoča da je na zemljevidu naša lokacija obarvana z modro piko
        // naredi nam tudi gumb v desnem zgornjem kotu, ki nam omogoča da ob kliku centriramo na našo lokacijo

        // 2 to nam da zadnjo trenutno lokacijo
        fusedLocationClient.lastLocation.addOnSuccessListener(this) { location ->
            // Got last known location. In some rare situations this can be null.
            // 3 če smo uspešno pridobili najbolj trenutno lokacijo uporabnika, se premaknemo na to lokacijo
            if (location != null) {
                lastLocation = location
                val currentLatLng = LatLng(location.latitude, location.longitude)
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 12f))
            }
        }*/

        map.isMyLocationEnabled = true

        fusedLocationClient.lastLocation.addOnSuccessListener(this) { location ->
            // Got last known location. In some rare situations this can be null.
            if (location != null) {
                lastLocation = location
                val currentLatLng = LatLng(location.latitude, location.longitude)
                placeMarkerOnMap(currentLatLng) // kličemo metodo s katermo dodamo naš marker na zemljevid
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 12f))
            }
        }

    }

    private fun placeMarkerOnMap(location: LatLng) {
        // 1 naredimo objekt tipa markerOptions in pozicija markerja je trenutna pozicija uporabnika
        val markerOptions = MarkerOptions().position(location)
        /*markerOptions.icon( // dodamo svojo ikono za marker
            BitmapDescriptorFactory.fromBitmap(
            BitmapFactory.decodeResource(resources, R.drawable.user_icon)))*/
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA))

        val titleStr = getAddress(location)  // dodamo naslov zraven markerja
        markerOptions.title(titleStr)

        // 2 dodamo naš marker na zemljevid

        map.addMarker(markerOptions)
    }

    private fun getAddress(latLng: LatLng): String {
        // 1 naredimo geocoder objekt ki pretvori longitude in latitude koordinate v prave naslove in tudi obratno
        val geocoder = Geocoder(this)
        val addresses: List<Address>?
        val address: Address?
        var addressText = ""

        try {
            // 2 pridobimo naslov
            addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
            // 3 če smo dobili nazaj naslov ga prikažemo
            if (null != addresses && !addresses.isEmpty()) {
                address = addresses[0]
                for (i in 0 until address.maxAddressLineIndex) {
                    addressText += if (i == 0) address.getAddressLine(i) else "\n" + address.getAddressLine(i)
                }
            }
        } catch (e: IOException) {
            Log.e("MapsActivity", e.localizedMessage)
        }

        return addressText
    }


    private fun startLocationUpdates() {
        //1
        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE)
            return
        }
        //2
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null /* Looper */)
    }

    private fun createLocationRequest() {
        // 1
        locationRequest = LocationRequest()
        // 2 interval nam pove pri kakšni hitrosti hočemo preverjati našo trenutno lokacijo
        locationRequest.interval = 9000
        // 3 najhitrješe možno posodabljanje naše lokacije
        locationRequest.fastestInterval = 5000
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)

        // 4
        val client = LocationServices.getSettingsClient(this)
        val task = client.checkLocationSettings(builder.build())

        // 5
        task.addOnSuccessListener {
            locationUpdateState = true
            startLocationUpdates()
        }
        task.addOnFailureListener { e ->
            // 6
            if (e is ResolvableApiException) {
                // Location settings are not satisfied, but this can be fixed
                // by showing the user a dialog.
                try {
                    // Show the dialog by calling startResolutionForResult(),
                    // and check the result in onActivityResult().
                    e.startResolutionForResult(this@Zemljevid,
                        REQUEST_CHECK_SETTINGS)
                } catch (sendEx: IntentSender.SendIntentException) {
                    // Ignore the error.
                }
            }
        }
    }

    // 1
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CHECK_SETTINGS) {
            if (resultCode == Activity.RESULT_OK) {
                locationUpdateState = true
                startLocationUpdates()
            }
        }

        /*if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                CAMERA_REQUEST_CODE -> {

                    val extras = data?.getExtras()
                    val imageBitmap = extras?.get("data") as Bitmap
                    image.setImageBitmap(imageBitmap)

                }
            }
        }*/

        if(requestCode == 123){
            var bmp = data.extras.get("data") as Bitmap

        }

    }

    // 2 da ustavimo posodabljanje lokacije
    override fun onPause() {
        super.onPause()
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    // 3 naprej posodabljamo našo lokacijo
    public override fun onResume() {
        super.onResume()
        if (!locationUpdateState) {
            startLocationUpdates()
        }
    }



}