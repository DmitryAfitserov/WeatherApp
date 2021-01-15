package com.app.weatherapp.view.fragments


import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.app.weatherapp.R
import com.app.weatherapp.databinding.FragmentMainCityBinding
import com.app.weatherapp.databinding.FragmentSeveralDaysBinding
import com.app.weatherapp.model.MainCity
import com.app.weatherapp.utils.DateUtil
import com.app.weatherapp.viewmodel.MainCityViewModel
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import java.util.*


class MainCityFragment : Fragment() {

    private lateinit var mainCityViewModel: MainCityViewModel
    private val LOCATION_PERMISSION_CODE = 1002
    private val LOCATION_REQUEST_CODE_ = 1003
    lateinit var mLocationRequest: LocationRequest

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainCityViewModel = ViewModelProvider(this).get(MainCityViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentMainCityBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_main_city, container, false
        )

        binding.buttonLocate.setOnClickListener {
            checkLocation()
        }


        binding.buttonFav.setOnClickListener {

            mainCityViewModel.liveDataMainCity.value?.isFav?.let {
                if (mainCityViewModel.liveDataMainCity.value?.isFav!!) {
                    mainCityViewModel.liveDataMainCity.value!!.isFav = false
                    binding.buttonFav.text =
                        requireContext().resources.getString(R.string.btn_to_fav)

                } else {
                    mainCityViewModel.liveDataMainCity.value!!.isFav = true
                    binding.buttonFav.text =
                        requireContext().resources.getString(R.string.btn_from_fav)

                }
            }
        }

        binding.buttonPickCity.setOnClickListener {
            createDialog()
        }

        mainCityViewModel.liveDataWeatherMainCity.observe(viewLifecycleOwner, { data ->
            binding.textViewNameCity.text = data.name
            binding.textViewUpdate.text = context?.resources?.getString(
                R.string.city_last_update,
                mainCityViewModel.getLastUpdate()
            )

            binding.textViewDesc.text = data.weather!![0].description
            binding.textViewTemp.text =
                context?.resources?.getString(R.string.city_temp, data.main!!.temp)
            binding.textViewFeel.text =
                context?.resources?.getString(R.string.city_feel, data.main!!.feelsLike)
            binding.textViewMax.text =
                context?.resources?.getString(R.string.city_max, data.main!!.tempMax)
            binding.textViewMin.text =
                context?.resources?.getString(R.string.city_min, data.main!!.tempMin)
            binding.textViewHum.text =
                context?.resources?.getString(R.string.city_hum, data.main!!.humidity)
            binding.textViewVis.text =
                context?.resources?.getString(R.string.city_vis, data.visibility)



            mainCityViewModel.getWeatherSeveralDaysApi()
        })

        mainCityViewModel.getWeatherSeveralDaysApiOB().observe(viewLifecycleOwner, {
            it?.let {
                mainCityViewModel.insertWeatherSeveralDays()

            }
        })


        mainCityViewModel.getMainCity().observe(viewLifecycleOwner, { mainCity ->

            mainCity?.let {
                startLoadWeatherBD()

                if (mainCityViewModel.liveDataMainCity.value?.isFav!!) {
                    binding.buttonFav.text =
                        requireContext().resources.getString(R.string.btn_from_fav)
                    //  Log.d("EEE", "from BD true")
                } else {
                    binding.buttonFav.text =
                        requireContext().resources.getString(R.string.btn_to_fav)
                    //   Log.d("EEE", "from BD false")
                }


            } ?: run {
                checkLocation()
            }
        })

        mainCityViewModel.getWeatherDayApiOb().observe(viewLifecycleOwner, { weatherDay ->
            weatherDay?.let {

                if (weatherDay.error == null) {
                    mainCityViewModel.insertWeatherDay(weatherDay)

                } else {
                    Toast.makeText(
                        context, requireContext().resources.getString(R.string.error_loading),
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }

        })

        binding.fab.setOnClickListener { view ->
            checkIdCity()
        }


        return binding.root
    }

    private fun startLoadWeatherBD() {

        if (mainCityViewModel.liveDataMainCity.value?.mainCityId == null) {
            getIdCity()
        } else {


            mainCityViewModel.liveDataWeatherDay.removeObservers(viewLifecycleOwner)
            val util = DateUtil()
            mainCityViewModel.getWeatherDayBD().observe(viewLifecycleOwner, { weatherDay ->

                weatherDay?.let {
                    //   mainCityViewModel.weatherDay = weatherDay

                    var isContain = false

                    if (mainCityViewModel.liveDataMainCity.value?.mainCityId != null) {

                        var mainCityPosition = -1
                        weatherDay.list?.forEachIndexed { index, element ->
                            if (element.id?.equals(mainCityViewModel.liveDataMainCity.value?.mainCityId)!!) {
                                isContain = true
                                mainCityPosition = index


                            }
                        }
                        if (isContain) {
                            mainCityViewModel.liveDataWeatherMainCity.value =
                                weatherDay.list!![mainCityPosition]
                        }


                        if (!isContain) {

                            getWeatherDayApi(true)
                        }

                    }


                    if (util.isNeedUpdate(weatherDay.timeUpdate!!) && isContain) {


                        getWeatherDayApi(false)
                    }


                } ?: run {
                    getWeatherDayApi(false)
                }

            })


        }


    }

    private fun checkIdCity() {
        mainCityViewModel.liveDataMainCity.value?.let {
            if (mainCityViewModel.liveDataMainCity.value!!.mainCityId != null) {

                getWeatherDayApi(false)

            } else {
                getIdCity()

            }
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
                    mainCity.prevCityId = mainCityViewModel.liveDataMainCity.value!!.prevCityId
                    mainCity.prevCity = mainCityViewModel.liveDataMainCity.value!!.prevCity
                    mainCity.isFav = mainCityViewModel.liveDataMainCity.value!!.isFav

                    mainCityViewModel.insertMainCity(mainCity)
                } else {
                    Toast.makeText(
                        context, requireContext().resources.getString(R.string.error_loading),
                        Toast.LENGTH_SHORT
                    ).show()

                }

            })
        //   }
    }


    private fun getWeatherDayApi(isNewCity: Boolean) {

        if (isNewCity) {
            if (!mainCityViewModel.liveDataMainCity.value!!.isFav
                && mainCityViewModel.liveDataMainCity.value!!.prevCityId != null
            ) {
                var deletePos: Int = -1
                mainCityViewModel.liveDataWeatherDay.value!!.list!!.forEachIndexed { index, element ->

                    if (element.id.equals(mainCityViewModel.liveDataMainCity.value!!.prevCityId)) {
                        deletePos = index
                    }
                }
                mainCityViewModel.liveDataWeatherDay.value!!.list!!.removeAt(deletePos)


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
                    mainCityViewModel.liveDataMainCity.value?.isFav?.let {
                        mainCity.isFav = mainCityViewModel.liveDataMainCity.value?.isFav!!
                    } ?: run {
                        mainCity.isFav = false
                    }


                    mainCityViewModel.insertMainCity(mainCity)
                    dialogBuilder.dismiss()
                } else {

                    editText.error = requireContext().getString(R.string.city_not_found)

                }
                progressBar.visibility = View.INVISIBLE
            })


        }

        dialogBuilder.setView(dialogView)
        dialogBuilder.show()
    }


    private fun checkLocation() {
        if (checkPermission()) {
            displayLocationSettingsRequest()

        }

    }

    private fun checkPermission(): Boolean {
        return if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(
                requireContext(),
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
        mLocationRequest.interval = 600000
        mLocationRequest.fastestInterval = 50000
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

        val builder = LocationSettingsRequest.Builder().addLocationRequest(mLocationRequest)

        val result =
            LocationServices.getSettingsClient(requireActivity())
                .checkLocationSettings(builder.build())

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

        val fusedLocationClient = LocationServices
            .getFusedLocationProviderClient(requireContext())
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
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
                            // Log.d("EEE", "location equals is $city")
                        } else {
                            val mainCity = MainCity()
                            mainCity.mainCity = city
                            mainCity.prevCity = mainCityViewModel.liveDataMainCity.value?.mainCity
                            mainCity.prevCityId =
                                mainCityViewModel.liveDataMainCity.value?.mainCityId
                            mainCityViewModel.liveDataMainCity.value?.isFav?.let {
                                mainCity.isFav = mainCityViewModel.liveDataMainCity.value!!.isFav
                            } ?: run {
                                mainCity.isFav = false
                            }

                            mainCityViewModel.insertMainCity(mainCity)
                            //  Log.d("EEE", "location is $city")
                        }
                    }
                }
            }
        }
        fusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, null)
    }

    override fun onPause() {

        super.onPause()
        mainCityViewModel.liveDataMainCity.value?.let {
            mainCityViewModel.insertMainCity(mainCityViewModel.liveDataMainCity.value!!)
        }
        mainCityViewModel.liveDataWeatherDay.value?.let {
            mainCityViewModel.insertWeatherDay(mainCityViewModel.liveDataWeatherDay.value!!)
        }
    }

}