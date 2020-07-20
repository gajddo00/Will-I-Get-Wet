package com.example.willigetwet.model

import com.google.gson.annotations.SerializedName
import java.util.*

class WeatherResponse {
    @SerializedName("cod")
    var cod: Int = 0
    @SerializedName("message")
    var message: Int = 0
    @SerializedName("cnt")
    var cnt: Int = 0
    @SerializedName("list")
    var list: Array<WeatherForecast>? = null
    @SerializedName("city")
    var city: City? = null
}

class WeatherForecast {
    @SerializedName("weather")
    var weather: Array<Weather>? = null
    @SerializedName("main")
    var main: Main? = null
    @SerializedName("wind")
    var wind: Wind? = null
    @SerializedName("clouds")
    var clouds: Clouds? = null
    @SerializedName("dt_txt")
    var dtTxt: String = ""
    @SerializedName("dt")
    var dt: Int = 0
    @SerializedName("sys")
    var sys: Sys? = null
    @SerializedName("rain")
    var rain: Rain? = null
}

class Rain {
    @SerializedName("3h")
    var rainValue: Float = 0.toFloat()
}

class Coord {
    @SerializedName("lon")
    var lon: Float = 0.toFloat()
    @SerializedName("lat")
    var lat: Float = 0.toFloat()
}

class Weather {
    @SerializedName("id")
    var id: Int = 0
    @SerializedName("main")
    var main: String = ""
    @SerializedName("description")
    var description: String = ""
    @SerializedName("icon")
    var icon: String = ""
}

class Main {
    @SerializedName("temp")
    var temp: Float = 0.toFloat()
    @SerializedName("feels_like")
    var feelsLike: Float = 0.toFloat()
    @SerializedName("temp_min")
    var tempMin: Float = 0.toFloat()
    @SerializedName("temp_max")
    var tempMax: Float = 0.toFloat()
    @SerializedName("pressure")
    var pressure: Float = 0.toFloat()
    @SerializedName("sea_level")
    var seaLevel: Float = 0.toFloat()
    @SerializedName("grnd_level")
    var groundLevel: Float = 0.toFloat()
    @SerializedName("humidity")
    var humidity: Float = 0.toFloat()
    @SerializedName("temp_kf")
    var tempKf: Float = 0.toFloat()
}

class Wind {
    @SerializedName("speed")
    var speed: Float = 0.toFloat()
    @SerializedName("Deg")
    var deg: Float = 0.toFloat()
}

class Clouds {
    @SerializedName("all")
    var all: Float = 0.toFloat()
}

class Sys {
    @SerializedName("pod")
    var pod: String = ""
}

class City {
    @SerializedName("id")
    var id: Int = 0
    @SerializedName("name")
    var name: String = ""
    @SerializedName("coord")
    var coord: Coord? = null
    @SerializedName("country")
    var country: String = ""
    @SerializedName("timezone")
    var timezone: Float = 0.toFloat()
    @SerializedName("sunrise")
    var sunrise: Long = 0
    @SerializedName("sunset")
    var sunset: Long = 0
}



