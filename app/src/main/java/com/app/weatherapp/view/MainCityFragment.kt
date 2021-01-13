package com.app.weatherapp.view


import android.Manifest
import android.app.Activity
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
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.app.weatherapp.R
import com.app.weatherapp.model.MainCity
import com.app.weatherapp.utils.DateUtil
import com.app.weatherapp.viewmodel.MainCityViewModel
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import java.util.*


class MainCityFragment : Fragment() {

    private lateinit var mainCityViewModel: MainCityViewModel
    private val LOCATION_PERMISSION_CODE = 1002
    private val LOCATION_REQUEST_CODE_ = 1003
    lateinit var mLocationRequest:LocationRequest

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainCityViewModel = ViewModelProvider(this).get(MainCityViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_main_city, container, false)
        val textView: TextView = root.findViewById(R.id.section_label)
        textView.text = "MainCityFragment"

        mainCityViewModel.getMainCity().observe(viewLifecycleOwner, { mainCity ->

            mainCity?.let {
                Log.d("EEE", "mainCity update ok")
                    startLoadWeatherBD()

            } ?: run {
                Log.d("EEE", "mainCity update null")

                checkLocation()
            }
        })

        return root
    }

    private fun startLoadWeatherBD() {
        var util = DateUtil()
        mainCityViewModel.getWeatherDayBD().observe(viewLifecycleOwner, { weatherDay ->

            weatherDay?.let {
                mainCityViewModel.weatherDay = weatherDay
                Log.d("EEE", "weatherDay not null time update")
                if(util.isNeedUpdate(mainCityViewModel.weatherDay!!.timeUpdate!!)){


                    Log.d("EEE", "check time update true time Update  =  " + weatherDay.timeUpdate!!)
                    checkIdCity()
                } else {

                }

            } ?: run {
                checkIdCity()
            }

        })
    }

    private fun checkIdCity(){
        if(mainCityViewModel.liveDataMainCity.value!!.mainCityId != null){
            Log.d("EEE", "answer from bd weather day mainCityId != null")
            getWeatherDayApi()

        } else {
            getIdCity()
            Log.d("EEE", "answer from bd weather day mainCityId == null")
        }
    }

    private fun getIdCity(){

     //   mainCityViewModel.weatherDay.let {

    //    } ?: run {
            mainCityViewModel.getIdCity(mainCityViewModel.liveDataMainCity.value!!.mainCity!!)
                .observe(viewLifecycleOwner, {
                    if(it.error == null){
                        val mainCity = MainCity()
                        mainCity.mainCityId = it.id
                        mainCity.mainCity = mainCityViewModel.liveDataMainCity.value!!.mainCity
                        mainCityViewModel.insertMainCity(mainCity)
                    } else {
                        Log.d("EEE", "load api id city error")

                    }
                })
     //   }
    }

    private fun getWeatherDayApi(){
        mainCityViewModel.getWeatherDayAPI().observe(viewLifecycleOwner, { weatherDay ->
            Log.d("EEE", "insertWeatherDay in BD")
            weatherDay?.let {
                mainCityViewModel.insertWeatherDay(weatherDay)
            }

        })
    }




    private fun checkLocation() {
        if (checkPermission())  {
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


    private fun displayLocationSettingsRequest(){
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
                Log.d("EEE", "isLocationPresent is true")

            }
        }
        result.addOnFailureListener { e ->
            if (e is ResolvableApiException) {
                try {
                       val resApiException = e as ResolvableApiException
                     this.startIntentSenderForResult(resApiException.resolution.intentSender,
                         LOCATION_REQUEST_CODE_, null, 0,
                         0, 0, null)

                } catch (sendEx: IntentSender.SendIntentException) { }
            }
        }


    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (LOCATION_REQUEST_CODE_ == requestCode) {
            if(resultCode == Activity.RESULT_OK){
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

    private fun getLocation(){

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
                        if(city.equals(mainCityViewModel.liveDataMainCity.value?.mainCity)){

                        } else {
                            val mainCity = MainCity()
                            mainCity.mainCity = city
                            mainCity.prevCity = mainCityViewModel.liveDataMainCity.value?.mainCity
                            mainCityViewModel.insertMainCity(mainCity)
                            Log.d("EEE", "location is $city")
                        }

                    }
                }
            }
        }
        fusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, null)
    }

}