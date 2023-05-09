package com.app.reportingmaintenance.model

import android.content.Context
import android.util.Patterns
import android.widget.Toast
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.ObservableField
import com.app.reportingmaintenance.BR

class ForgotPasswordModel : BaseObservable() {

    var error_email = ObservableField<String>()
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




    private fun validate() {
        if (Patterns.EMAIL_ADDRESS.matcher(email).matches()&&email.endsWith("@taibahu.edu.sa")

        ) {

            error_email.set(null)
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




            notifyPropertyChanged(BR.isvaild)
        }
        error_email.notifyChange()

    }


}