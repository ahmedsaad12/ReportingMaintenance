package com.app.reportingmaintenance.model

import android.content.Context
import android.util.Patterns
import android.widget.Toast
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.ObservableField
import com.app.reportingmaintenance.BR

class AddAdminModel : BaseObservable() {

    var error_email = ObservableField<String>()
    var error_password = ObservableField<String>()
    lateinit var  context:Context
    @get:Bindable
    var isvaild = false

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



    private fun validate() {
        if (Patterns.EMAIL_ADDRESS.matcher(email).matches()&&email.endsWith("@taibahu.edu.sa")  && password.isNotEmpty()&&password.length>=6

        ) {

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



            notifyPropertyChanged(BR.isvaild)
        }
        error_password.notifyChange()
        error_email.notifyChange()

    }


}