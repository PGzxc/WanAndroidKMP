package bean

import androidx.compose.ui.graphics.Color
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MeCoinInfoResp(
    @SerialName("data") val data: CoinInfo? = null,
    @SerialName("errorCode") val errorCode: Long,
    @SerialName("errorMsg") val errorMsg: String
)

@Serializable
data class CoinInfo(
    @SerialName("coinCount") val coinCount: Long = 0,
    @SerialName("level") val level: Long = 0,
    @SerialName("nickname") val nickname: String = "",
    @SerialName("rank") val rank: String = "",
    @SerialName("userId") val userId: Long = 0,
    @SerialName("username") val username: String = ""
)

@Serializable
data class MeInfoBean(
    @SerialName("name") var name: String? = null,
    @SerialName("value") var value: String? = null
) {
}

data class MeToolBean(val id: Long, val name: String, val color: Color) {
}