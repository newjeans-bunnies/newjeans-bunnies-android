package newjeans.bunnies.network.auth.dto.reqeust

data class CertificationVerifyRequestDto(
    val phoneNumber: String,
    val certificationNumber: String
)
