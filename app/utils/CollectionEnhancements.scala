package utils

/**
  * Created by Prashant S Khanwale @ Suveda LLC  on 5/7/16.
  */
object CollectionEnhancements {
  implicit class MapEnhanced[A,B] (val m:Map[A,B]){
    def ?(k:A) = m.contains(k)
  }
}
