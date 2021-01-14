package com.app.weatherapp.view


import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.app.weatherapp.R
import com.app.weatherapp.model.MainCity
import com.app.weatherapp.model.weatherday.WeatherDay
import com.app.weatherapp.utils.DateUtil
import com.app.weatherapp.viewmodel.MainCityViewModel
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import java.util.*


class MainCityFragment : Fragment() {

    private lateinit var mainCityViewModel: MainCityViewModel
    private val LOCATION_PERMISSION_CODE = 1002
    private val LOCATION_REQUEST_CODE_ = 1003
    lateinit var mLocationRequest: LocationRequest
    lateinit var textViewCityName: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainCityViewModel = ViewModelProvider(this).get(MainCityViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_main_city, container, false)
        val fab: FloatingActionButton = root.findViewById(R.id.fab)

        textViewCityName = root.findViewById<TextView>(R.id.text_view_name_city)
        val buttonLocate = root.findViewById<Button>(R.id.buttonLocate)
        buttonLocate.setOnClickListener {
            checkLocation()
        }

        val buttonFav = root.findViewById<Button>(R.id.buttonFav)

        buttonFav.setOnClickListener {

            mainCityViewModel.liveDataMainCity.value?.isFav?.let {
                if(mainCityViewModel.liveDataMainCity.value?.isFav!!){
                    mainCityViewModel.liveDataMainCity.value!!.isFav = false
                    buttonFav.text = context!!.resources.getString(R.string.btn_to_fav)
                    Log.d("EEE", "click  false")
                } else {
                    mainCityViewModel.liveDataMainCity.value!!.isFav = true
                    buttonFav.text = context!!.resources.getString(R.string.btn_from_fav)
                    Log.d("EEE", "click  true")
                }
            }
        }

        val buttonPickCity = root.findViewById<Button>(R.id.buttonPickCity)
        buttonPickCity.setOnClickListener {
            createDialog()
        }


        mainCityViewModel.getMainCity().observe(viewLifecycleOwner, { mainCity ->

            mainCity?.let {
                Log.d("EEE", "mainCity update ok")
                startLoadWeatherBD()

                textViewCityName.text = mainCity.mainCity

                if(mainCityViewModel.liveDataMainCity.value?.isFav!!){
                    buttonFav.text = context!!.resources.getString(R.string.btn_from_fav)
                    Log.d("EEE", "from BD true")
                } else {
                    buttonFav.text = context!!.resources.getString(R.string.btn_to_fav)
                    Log.d("EEE", "from BD false")
                }


            } ?: run {
                Log.d("EEE", "mainCity update null")
                checkLocation()
            }
        })

        mainCityViewModel.getWeatherDayApiOb().observe(viewLifecycleOwner, { weatherDay ->
            weatherDay?.let {

                if(weatherDay.error == null){
                    mainCityViewModel.insertWeatherDay(weatherDay)

                    Log.d("EEE", "insertWeatherDay in BD")
                } else {
                    Toast.makeText(context, context!!.resources.getString(R.string.error_loading),
                        Toast.LENGTH_SHORT).show()
                }

            }

        })

        fab.setOnClickListener { view ->
            checkIdCity()
        }


        return root
    }

    private fun startLoadWeatherBD() {

        mainCityViewModel.liveDataWeatherDay.removeObservers(viewLifecycleOwner)
        val util = DateUtil()
        mainCityViewModel.getWeatherDayBD().observe(viewLifecycleOwner, { weatherDay ->

            weatherDay?.let {
                mainCityViewModel.weatherDay = weatherDay

                var isContain = false

                if (mainCityViewModel.liveDataMainCity.value?.mainCityId != null) {


                    weatherDay.list?.forEach {
                        if (it.id?.equals(mainCityViewModel.liveDataMainCity.value?.mainCityId)!!) {
                            isContain = true
                            Log.d("EEE", " isContain = true")
                        }
                    }

                    Log.d("EEE", "weatherDay not null time update")
                    if (!isContain) {
                        Log.d("EEE", "add new city")
                        getWeatherDayApi(true)
                    }

                }


                if (util.isNeedUpdate(mainCityViewModel.weatherDay!!.timeUpdate!!) && isContain) {

                    Log.d(
                        "EEE",
                        "check time update true time Update  =  " + weatherDay.timeUpdate!!
                    )
                    checkIdCity()
                } else {

                }


            } ?: run {
                checkIdCity()
            }

        })
    }

    private fun checkIdCity() {
        if (mainCityViewModel.liveDataMainCity.value!!.mainCityId != null) {
            Log.d("EEE", "answer from bd weather day mainCityId != null")
            getWeatherDayApi(false)

        } else {
            getIdCity()
            Log.d("EEE", "answer from bd weather day mainCityId == null")
        }
    }

    private fun getIdCity() {

        mainCityViewModel.liveDataIdCity.removeObservers(viewLifecycleOwner)
        mainCityViewModel.getIdCity(mainCityViewModel.liveDataMainCity.value!!.mainCity!!)
            .observe(viewLifecycleOwner, {
                if (it.error == null) {
                    val mainCity = MainCity()
                    mainCity.mainCityId = it.id
                    mainCity.mainCity = mainCityViewModel.liveDataMainCity.value!!.mainCity
                    mainCityViewModel.insertMainCity(mainCity)
                } else {
                    Toast.makeText(context, context!!.resources.getString(R.string.error_loading),
                        Toast.LENGTH_SHORT).show()

                }

            })
        //   }
    }


    private fun getWeatherDayApi(isNewCity: Boolean) {

        if(isNewCity){
            if(!mainCityViewModel.liveDataMainCity.value!!.isFav){

                mainCityViewModel.liveDataWeatherDay.value!!.list!!.forEach {
                    if(it.id.equals(mainCityViewModel.liveDataMainCity.value!!.prevCityId)){
                        mainCityViewModel.liveDataWeatherDay.value!!.list!!.remove(it)
                    }
                }

            }

        }

        mainCityViewModel.getWeatherDayAPI(mainCityViewModel.liveDataWeatherDay.value)
    }


    private fun createDialog() {
        val dialogBuilder: AlertDialog = AlertDialog.Builder(this.context).create()
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.custom_dialog, null)

        val editText: EditText = dialogView.findViewById<View>(R.id.edit_text) as TextInputEditText
        val button1 = dialogView.findViewById<View>(R.id.buttonSubmit) as Button
        val button2 = dialogView.findViewById<View>(R.id.buttonCancel) as Button
        val progressBar = dialogView.findViewById<View>(R.id.progress_bar) as ProgressBar

        button2.setOnClickListener {
            dialogBuilder.dismiss()
        }
        button1.setOnClickListener {
            progressBar.visibility = View.VISIBLE
            val city = editText.text.toString()
            mainCityViewModel.liveDataIdCity.removeObservers(viewLifecycleOwner)
            mainCityViewModel.getIdCity(city).observe(viewLifecycleOwner, {

                if (it.error == null) {
                    val mainCity = MainCity()
                    mainCity.mainCityId = it.id
                    mainCity.mainCity = city
                    mainCity.prevCity = mainCityViewModel.liveDataMainCity.value?.mainCity
                    mainCity.prevCityId = mainCityViewModel.liveDataMainCity.value?.mainCityId
                    mainCity.isFav = mainCityViewModel.liveDataMainCity.value?.isFav!!

                    mainCityViewModel.insertMainCity(mainCity)
                    dialogBuilder.dismiss()
                } else {
                    Log.d("EEE", "dialog id city error")
                    editText.error = context!!.getString(R.string.city_not_found)

                }
                progressBar.visibility = View.INVISIBLE
            })
            Log.d("EEE", "input city " + editText.text.toString())


        }

        dialogBuilder.setView(dialogView)
        dialogBuilder.show()
    }


    private fun checkLocation() {
        if (checkPermission()) {
            Log.d("EEE", "checkPermission() is true")
            displayLocationSettingsRequest()

        }

    }

    private fun checkPermission(): Boolean {
        return if (ActivityCompat.checkSelfPermission(
                context!!,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(
                context!!,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                LOCATION_PERMISSION_CODE
            )

            false
        } else {
            true
        }
    }


    private fun displayLocationSettingsRequest() {
        mLocationRequest = LocationRequest.create()
        mLocationRequest.interval = 60000
        mLocationRequest.fastestInterval = 5000
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

        val builder = LocationSettingsRequest.Builder().addLocationRequest(mLocationRequest)

        val result =
            LocationServices.getSettingsClient(activity!!).checkLocationSettings(builder.build())

        result.addOnSuccessListener { response ->
            val states = response.locationSettingsStates
            if (states.isLocationPresent) {
                getLocation()
            }
        }
        result.addOnFailureListener { e ->
            if (e is ResolvableApiException) {
                try {
                    val resApiException = e as ResolvableApiException
                    this.startIntentSenderForResult(
                        resApiException.resolution.intentSender,
                        LOCATION_REQUEST_CODE_, null, 0,
                        0, 0, null
                    )

                } catch (sendEx: IntentSender.SendIntentException) {
                }
            }
        }


    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (LOCATION_REQUEST_CODE_ == requestCode) {
            if (resultCode == Activity.RESULT_OK) {
                getLocation()
            } else {

            }
        }

        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (LOCATION_PERMISSION_CODE == requestCode) {
            if (grantResults[0] == 0 && grantResults[1] == 0) {
                displayLocationSettingsRequest()
            } else {
                Toast.makeText(context, "noy", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun getLocation() {

        var addresses: List<Address>
        val geocoder = Geocoder(context, Locale.getDefault())

        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context!!)
        if (ActivityCompat.checkSelfPermission(
                context!!,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context!!,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        val mLocationCallback: LocationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {

                for (location in locationResult.locations) {
                    if (location != null) {
                        addresses =
                            geocoder.getFromLocation(location.latitude, location.longitude, 1)
                        val city: String = addresses[0].locality
                        if (city.equals(mainCityViewModel.liveDataMainCity.value?.mainCity)) {

                        } else {
                            val mainCity = MainCity()
                            mainCity.mainCity = city
                            mainCity.prevCity = mainCityViewModel.liveDataMainCity.value?.mainCity
                            mainCity.prevCityId = mainCityViewModel.liveDataMainCity.value?.mainCityId
                            mainCityViewModel.liveDataMainCity.value?.isFav?.let {
                                mainCity.isFav = mainCityViewModel.liveDataMainCity.value!!.isFav
                            }

                            mainCityViewModel.insertMainCity(mainCity)
                            Log.d("EEE", "location is $city")
                        }

                    }
                }
            }
        }
        fusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, null)
    }

    override fun onPause() {

        super.onPause()
        Log.d("FFF", "onPause")
        mainCityViewModel.liveDataMainCity.value?.let {
            mainCityViewModel.insertMainCity(mainCityViewModel.liveDataMainCity.value!!)
        }
        mainCityViewModel.liveDataWeatherDay.value?.let {
            mainCityViewModel.insertWeatherDay(mainCityViewModel.liveDataWeatherDay.value!!)
        }
    }

}