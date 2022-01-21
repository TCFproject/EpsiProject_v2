package fr.epsi.epsiproject_v2.obj

class Marche(private val nom:String,private val desc:String,
             private val pic:String,private val addr:String,
             private val zipC:String,private val ville:String,
             private val long:Double,private val lat:Double) {
    fun getNom():String{
        return this.nom
    }
    fun getDesc():String{
        return this.desc
    }
    fun getPic():String{
        return this.pic
    }
    fun getAddr():String{
        return this.addr
    }
    fun getZip():String{
        return this.zipC
    }
    fun getVille():String{
        return this.ville
    }
    fun getLong():Double{
        return this.long
    }
    fun getLat():Double{
        return this.lat
    }
}