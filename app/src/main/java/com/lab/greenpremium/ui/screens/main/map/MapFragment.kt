package com.lab.greenpremium.ui.screens.main.map

import android.os.Bundle
import android.view.View
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.lab.greenpremium.App
import com.lab.greenpremium.GP_OFFICE_LOCATION
import com.lab.greenpremium.R
import com.lab.greenpremium.data.entity.Feature
import com.lab.greenpremium.ui.screens.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_map.*
import javax.inject.Inject


class MapFragment : BaseFragment(), MapContract.View {

    @Inject
    internal lateinit var presenter: MapPresenter

    companion object {
        fun newInstance() = MapFragment()
    }

    override fun initializeDaggerComponent() {
        DaggerMapComponent.builder()
                .appComponent((activity?.application as App).component)
                .mapModule(MapModule(this))
                .build()
                .inject(this)
    }

    override fun layoutResId(): Int {
        return R.layout.fragment_map
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        map_view.onCreate(savedInstanceState)
        map_view.onResume()

        initializeMap()

        super.onViewCreated(view, savedInstanceState)
    }

    private fun initializeMap() {
        try {
            MapsInitializer.initialize(activity!!.applicationContext)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        map_view.getMapAsync { map ->

            // For dropping a marker at a point on the Map
            map.addMarker(MarkerOptions()
                    .position(GP_OFFICE_LOCATION)
                    .title("Офис Green Premium")
                    .snippet("Бизнес-центр «Manhattan», офис 211")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_map_marker)))

            /* Доп. инфо:
            * "Бизнес-центр «Manhattan», офис 211\n" +
                    "Адрес: 105066, г. Москва, ул. Нижняя Красносельская, д. 35, стр. 9\n" +
                    "Режим работы: пн-пт с 10 до 19 часов\n" +
                    "Телефон: +7 495 380-39-59, 8-800-775-70-75\n" +
                    "Факс: +7 495 380-39-59\n" +
                    "E-mail: sale@greenpremium.ru\n" +
                    "https://greenpremium.ru/"
            * */

            // For zooming automatically to the location of the marker
            val cameraPosition = CameraPosition.Builder().target(GP_OFFICE_LOCATION).zoom(15f).build()
            map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))

            presenter.updateMapObjects()
        }
    }

    override fun placeMarkers(objects: List<Feature>) {
        objects.forEach {
            map_view.getMapAsync { map ->
                val coordinates = it.geometry.coordinates
                map.addMarker(MarkerOptions()
                        .position(LatLng(coordinates[0], coordinates[1]))
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_map_marker)))
            }
        }
    }

    override fun initViews() {
        //ignore
    }

    override fun onResume() {
        super.onResume()
        map_view.onResume()
    }

    override fun onPause() {
        super.onPause()
        map_view.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        map_view.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        map_view.onLowMemory()
    }
}