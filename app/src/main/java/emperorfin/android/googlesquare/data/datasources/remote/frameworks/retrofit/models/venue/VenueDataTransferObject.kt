package emperorfin.android.googlesquare.data.datasources.remote.frameworks.retrofit.models.venue

data class VenueDataTransferObject(
    val categories: List<Category>,
    val distance: Int,
    val geocodes: GeoCode,
    val location: Location,
    val name: String,
    val timezone: String,
)