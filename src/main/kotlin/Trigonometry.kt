// https://ru.wikipedia.org/wiki/%D0%A0%D1%8F%D0%B4_%D0%A2%D0%B5%D0%B9%D0%BB%D0%BE%D1%80%D0%B0#%D0%A0%D1%8F%D0%B4%D1%8B_%D0%9C%D0%B0%D0%BA%D0%BB%D0%BE%D1%80%D0%B5%D0%BD%D0%B0_%D0%BD%D0%B5%D0%BA%D0%BE%D1%82%D0%BE%D1%80%D1%8B%D1%85_%D1%84%D1%83%D0%BD%D0%BA%D1%86%D0%B8%D0%B9
// https://ru.wikipedia.org/wiki/%D0%A7%D0%B8%D1%81%D0%BB%D0%B0_%D0%91%D0%B5%D1%80%D0%BD%D1%83%D0%BB%D0%BB%D0%B8

val ks = sequenceOf(1.0 / 3.0, 2.0 / 15.0, 17.0 / 315.0, 62.0 / 2835.0, 1382.0 / 155925.0)

fun tg(x: Double): Double {
    val square = x * x
    var element = x
    var result = element
    for (k in ks) {
        element *= square
        result += element * k
    }
    return result
}
