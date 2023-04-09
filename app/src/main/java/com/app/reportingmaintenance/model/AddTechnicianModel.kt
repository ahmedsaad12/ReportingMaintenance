package com.app.reportingmaintenance.model

import android.content.Context
import android.util.Patterns
import android.widget.Toast
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.ObservableField
import com.app.reportingmaintenance.BR

class AddTechnicianModel : BaseObservable() {

    var error_name = ObservableField<String>()
    var error_email = ObservableField<String>()
    var error_password = ObservableField<String>()
    lateinit var  context:Context
    @get:Bindable
    var isvaild = false
    var disid = ""
    @get:Bindable
    var email = ""
        set(value) {
            field = value
            validate()

            notifyPropertyChanged(BR.email)

        }

    @get:Bindable
    var password = ""
        set(value) {
            field = value
            validate()
            notifyPropertyChanged(BR.password)
        }
    @get:Bindable
    var name = ""
        set(value) {
            field = value
            validate()

            notifyPropertyChanged(BR.name)

        }


    private fun validate() {
        if (Patterns.EMAIL_ADDRESS.matcher(email).matches()&&email.endsWith("@taibahu.edu.sa")  && password.isNotEmpty()&&password.length>=6&&name.isNotEmpty()
            &&disid.isNotEmpty()
        ) {

            error_name.set(null)
            error_email.set(null)
            error_password.set(null)
            isvaild = true
            notifyPropertyChanged(BR.isvaild)
        } else {
            isvaild = false
            if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()||!email.endsWith("@taibahu.edu.sa") ){
                error_email.set("Invaild Email")
            }
            else{
                error_email.set(null)
            }
            if (password.isEmpty()||password.length<6){
                error_password.set("Incorrect Password")
            }
            else{
                error_password.set(null)
            }
            if (name.isEmpty()) {
                error_name.set("Field Required")
            } else {
                error_name.set(null)
            }

if(disid.isEmpty()){
    Toast.makeText(context,"Choose type",Toast.LENGTH_LONG).show()
}
            notifyPropertyChanged(BR.isvaild)
        }
        error_password.notifyChange()
        error_email.notifyChange()
        error_name.notifyChange()

    }


}