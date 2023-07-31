package opa.http

enum class HttpMethod {
    Get {
        override fun asString(): String {
            return "get"
        }
    },
    Post {
        override fun asString(): String {
            return "post"
        }
    };

    abstract fun asString(): String

    fun fromString(value: String): HttpMethod? {
        return HttpMethod.values().filter{v -> value.lowercase().equals(v)}.firstOrNull()
    } 
}
