<br/>
<p align="center">
  <a href="https://github.com/MFKRengifoc/MFComponents">
    <img src="mf.png" alt="Logo" width="80" height="80">
  </a>

  <h3 align="center">MFComponents</h3>

  <p align="center">
    Components I use in my own projects
    <br/>
    <br/>
  </p>
</p>



## Built With

* [Kotlin](https://kotlinlang.org/)
* [EasyWayLocation](https://github.com/prabhat1707/EasyWayLocation)

## Usage
    - MFChip
    <com.manoffocus.mfcomponentsnav.components.mfchip.MFChip
        android:layout_width="match_parent"
        style="@style/MFChip.large"
        app:mf_chip_title_text="Titulo"
        app:mf_chip_value_text="Contenido"
        app:mf_chip_icon_startIcon="@drawable/ic_location"
        app:mf_chip_color_value_text="?primary"
        app:mf_chip_background_color="?primary_onBackground" />

    - MFButtonTextIcon
    <com.manoffocus.mfcomponentsnav.components.mfbuttontexticon.MFButtonTextIcon
        android:id="@+id/main_activity_extralarge_but"
        style="@style/MFButtonTextIcon.extraLarge"
        android:layout_marginVertical="5dp"
        app:mf_button_icon="@drawable/mf_imageview_bg_error_loading_min"
        app:mf_button_text="Boton Extra Largo" />

    - MFLabel
    <com.manoffocus.mfcomponentsnav.components.mflabel.MFLabel
        style="@style/MFLabel.extraLarge"
        app:mf_label_formattedText="Extra large"
        android:layout_marginVertical="5dp"/>

    - MFImageView
    <com.manoffocus.mfcomponentsnav.components.mfimageview.MFImageView
        style="@style/MFImageView.medium"
        app:mf_image_draw="@drawable/ic_money"/>

    - MFMaps
    - This should be in a fragment or activity and each one must implements MapsContract.View and MapsContract.Maps
    - and 
        val locationPresenter = MFLocationManager(this) --> For Locations and Gps
        val mapsPresenter = MapsPresenter(this) --> Maps
    <fragment
        android:id="@+id/map"
        android:name="com.manoffocus.mfcomponentsnav.components.mfmaps.MFMaps"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />
    

## Authors

* [Kevin Rengifo](https://github.com/MFKRengifoc) - *Android Developer*
