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
import android.widget.Toast
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
import kotlinx.android.synthetic.main.custom_toast.*
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.net.Socket
import java.util.*
import java.util.zip.GZIPInputStream
import java.util.zip.GZIPOutputStream
import kotlin.collections.ArrayList


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

    var trenutnaLokacija: LatLng = LatLng(0.0,0.0)

    lateinit var dodajMarker: Marker

    var stevec = 0

    var rezultatSU = 0

    var prag = 65

    val slomskovTrg = LatLng(46.5590834,15.6442211)
    val mariborGrad = LatLng(46.5607299,15.6486848)
    val muzejOsvoboditve = LatLng(46.5627246,15.647904)
    val sodniStolp = LatLng(46.5568654,15.6410986)
    val vodniStolp = LatLng(46.5564089,15.6483579)
    val kuznoZnamenje = LatLng(46.5576136,15.6451555)

    var polje: ArrayList<LatLng> = arrayListOf(slomskovTrg,mariborGrad,muzejOsvoboditve,sodniStolp,vodniStolp,kuznoZnamenje)

    companion object { // za permission
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
        // 3
        private const val REQUEST_CHECK_SETTINGS = 2
    }

    val REQUEST_IMAGE_CAPTURE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_zemljevid)

        //val socket = Socket("192.168.1.101",8080)
        //socket.use {
        //    rezultatSU = it.getInputStream().toString().toInt()

        //}

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
            dispatchTakePictureIntent()
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

        fun IntRange.random() =
            Random().nextInt((endInclusive + 1) - start) +  start

        val ran = (0..5).random()

        stevec = ran


        val lat:Double = trenutnaLokacija.latitude
        val lng:Double = trenutnaLokacija.longitude

        if(ran == 0){
            dodajMarker = map.addMarker(MarkerOptions()
                .position(slomskovTrg).title("Znana zgodovinska oseba")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.flag))
            )
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(slomskovTrg, 12.0f))
        }
        else if(ran == 1){
            dodajMarker = map.addMarker(MarkerOptions()
                .position(mariborGrad).title("Stavba starega časa")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.flag))
            )
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(mariborGrad, 12.0f))
        }
        else if(ran == 2){
            dodajMarker = map.addMarker(MarkerOptions()
                .position(muzejOsvoboditve).title("Svoboda je veliko vredna")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.flag))
            )
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(muzejOsvoboditve, 12.0f))
        }
        else if(ran == 3){
            dodajMarker = map.addMarker(MarkerOptions()
                .position(sodniStolp).title("Sodba je bila kruta")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.flag))
            )
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(sodniStolp, 12.0f))
        }
        else if(ran == 4){
            dodajMarker = map.addMarker(MarkerOptions()
                .position(vodniStolp).title("Stolp ob reki")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.flag))
            )
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(vodniStolp, 12.0f))
        }
        else if(ran == 5){
            dodajMarker = map.addMarker(MarkerOptions()
                .position(kuznoZnamenje).title("Čudni kip...")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.flag))
            )
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(kuznoZnamenje, 12.0f))
        }

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
                trenutnaLokacija = currentLatLng
                placeMarkerOnMap(currentLatLng) // kličemo metodo s katermo dodamo naš marker na zemljevid
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 12f))
            }
        }

    }

    private fun placeMarkerOnMap(location: LatLng) {
        if(dodajMarker != null){
            //dodajMarker.remove()
        }
        // 1 naredimo objekt tipa markerOptions in pozicija markerja je trenutna pozicija uporabnika
        val markerOptions = MarkerOptions().position(location)
        /*markerOptions.icon( // dodamo svojo ikono za marker
            BitmapDescriptorFactory.fromBitmap(
            BitmapFactory.decodeResource(resources, R.drawable.user_icon)))*/
        //markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA))

        val titleStr = getAddress(location)  // dodamo naslov zraven markerja
        markerOptions.title(titleStr)

        // 2 dodamo naš marker na zemljevid
        //dodajMarker = map.addMarker(markerOptions)
        //map.addMarker(markerOptions)
    }

    private fun uspesenZadetek(){
        val toast = Toast.makeText(applicationContext, "Pravilno", Toast.LENGTH_SHORT)
        toast.show()

        dodajMarker.remove()
        polje.removeAt(stevec)

        fun IntRange.random() =
            Random().nextInt((endInclusive + 1) - start) +  start

        var ran = (0..polje.size).random()

        if(ran == 0 && prag > rezultatSU){
            dodajMarker = map.addMarker(MarkerOptions()
                .position(slomskovTrg).title("Znana zgodovinska oseba")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.flag))
            )
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(slomskovTrg, 12.0f))
        }
        else if(ran == 1 && prag > rezultatSU){
            dodajMarker = map.addMarker(MarkerOptions()
                .position(mariborGrad).title("Stavba starega časa")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.flag))
            )
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(mariborGrad, 12.0f))
        }
        else if(ran == 2 && prag > rezultatSU){
            dodajMarker = map.addMarker(MarkerOptions()
                .position(muzejOsvoboditve).title("Svoboda je veliko vredna")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.flag))
            )
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(muzejOsvoboditve, 12.0f))
        }
        else if(ran == 3 && prag > rezultatSU){
            dodajMarker = map.addMarker(MarkerOptions()
                .position(sodniStolp).title("Sodba je bila kruta")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.flag))
            )
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(sodniStolp, 12.0f))
        }
        else if(ran == 4 && prag > rezultatSU){
            dodajMarker = map.addMarker(MarkerOptions()
                .position(vodniStolp).title("Stolp ob reki")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.flag))
            )
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(vodniStolp, 12.0f))
        }
        else if(ran == 5 && prag > rezultatSU){
            dodajMarker = map.addMarker(MarkerOptions()
                .position(kuznoZnamenje).title("Čudni kip...")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.flag))
            )
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(kuznoZnamenje, 12.0f))
        }

        map.uiSettings.isZoomControlsEnabled = true
        map.setOnMarkerClickListener(this)

        setUpMap()
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

        try{
            if (requestCode == REQUEST_CHECK_SETTINGS) {
                if (resultCode == Activity.RESULT_OK) {
                    locationUpdateState = true
                    startLocationUpdates()
                }
            }

            if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
                val imageBitmap = data.extras.get("data") as Bitmap
                //mImageView.setImageBitmap(imageBitmap)

                val scaledImage = Bitmap.createScaledBitmap(imageBitmap,64,128,true) // skaliramo sliko
                //val stisjenaSlika = kompresijaSlike(scaledImage,90)
                var stream:ByteArrayOutputStream? = ByteArrayOutputStream()
                try{
                    var gzipOstream:GZIPOutputStream? = null
                    try{
                        gzipOstream = GZIPOutputStream(stream)
                        scaledImage.compress(Bitmap.CompressFormat.JPEG,100,gzipOstream)
                        gzipOstream.flush()
                    }
                    finally {
                        gzipOstream?.close()
                        stream?.flush()
                    }
                }
                catch(sendEx: IntentSender.SendIntentException){
                    stream = null
                }
                if(stream != null) run {
                    var byteArray: ByteArray = stream.toByteArray()

                }
                /*val socket = Socket("192.168.1.101",8080)
                socket.use {
                    it.outputStream.write("Uspešno sem slikal sliko".toByteArray())
                    var msg = it.getInputStream().read().toString()
                    if(msg == "ne"){
                        val toast = Toast.makeText(applicationContext, "Ni pravilni objekt", Toast.LENGTH_SHORT)
                        toast.show()
                    }
                    else if(msg == "da"){
                        uspesenZadetek()
                    }
                }*/
                KlientPridobi().execute()
                //MessageSender().execute()
            }
        }
        catch (sendEx: IntentSender.SendIntentException){
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

    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }

    private fun scaleImage(bitmap:Bitmap, maxsize:Int): Bitmap? {
        var width: Int = bitmap.width
        var height: Int = bitmap.height

        var bitmapRatio: Float = width as Float /height as Float
        if(bitmapRatio > 1){
            width = maxsize
            height = (width / bitmapRatio) as Int
        }
        else{
            height = maxsize
            width = (height * bitmapRatio) as Int
        }
        return Bitmap.createScaledBitmap(bitmap,width,height,true)
    }

    // Za kompresijo slik
    private fun kompresijaSlike(bitmap:Bitmap, quality:Int):Bitmap{
        // Initialize a new ByteArrayStream
        val stream = ByteArrayOutputStream()

        /*
            **** reference source developer.android.com ***

            public boolean compress (Bitmap.CompressFormat format, int quality, OutputStream stream)
                Write a compressed version of the bitmap to the specified outputstream.
                If this returns true, the bitmap can be reconstructed by passing a
                corresponding inputstream to BitmapFactory.decodeStream().

                Note: not all Formats support all bitmap configs directly, so it is possible
                that the returned bitmap from BitmapFactory could be in a different bitdepth,
                and/or may have lost per-pixel alpha (e.g. JPEG only supports opaque pixels).

                Parameters
                format : The format of the compressed image
                quality : Hint to the compressor, 0-100. 0 meaning compress for small size,
                    100 meaning compress for max quality. Some formats,
                    like PNG which is lossless, will ignore the quality setting
                stream: The outputstream to write the compressed data.

                Returns
                    true if successfully compressed to the specified stream.


            Bitmap.CompressFormat
                Specifies the known formats a bitmap can be compressed into.

                    Bitmap.CompressFormat  JPEG
                    Bitmap.CompressFormat  PNG
                    Bitmap.CompressFormat  WEBP
        */

        // Compress the bitmap with JPEG format and quality 50%
        //bitmap.compress(Bitmap.CompressFormat.JPEG, quality, stream)
        bitmap.compress(Bitmap.CompressFormat.PNG, quality, stream)

        val byteArray = stream.toByteArray()

        // Vrnemo kompresiran Bitmap
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
    }



}