package com.app.reportingmaintenance.model

import android.util.Patterns
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.ObservableField
import com.app.reportingmaintenance.BR

class SignupModel : BaseObservable() {
    var error_email = ObservableField<String>()
    var error_password = ObservableField<String>()
    var error_name = ObservableField<String>()
    var error_number = ObservableField<String>()
    var error_phone = ObservableField<String>()
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
    @get:Bindable
    var name = ""
        set(value) {
            field = value
            validate()

            notifyPropertyChanged(BR.name)

        }

    @get:Bindable
    var phone = ""
        set(value) {
            field = value
            validate()
            notifyPropertyChanged(BR.phone)
        }
    @get:Bindable
    var studentNumber = ""
        set(value) {
            field = value
            validate()
            notifyPropertyChanged(BR.studentNumber)
        }

    private fun validate() {
        if (Patterns.EMAIL_ADDRESS.matcher(email).matches()&&email.endsWith("@taibahu.edu.sa") && password.isNotEmpty()&&password.length>=6&&name.isNotEmpty()
            &&phone.isNotEmpty()&&studentNumber.isNotEmpty()
        ) {
            error_email.set(null)
            error_password.set(null)
            error_number.set(null)
            error_phone.set(null)
            error_name.set(null)
            isvaild=true
            notifyPropertyChanged(BR.isvaild)
        } else {
            isvaild=false
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
            if (name.isEmpty()){
                error_name.set("Field Required")
            }
            else{
                error_name.set(null)
            }
            if (phone.isEmpty()){
                error_phone.set("Field Required")
            }
            else{
                error_phone.set(null)
            }
            if (studentNumber.isEmpty()){
                error_number.set("Field Required")
            }
            else{
                error_number.set(null)
            }

            notifyPropertyChanged(BR.isvaild)
        }
        error_password.notifyChange()
        error_email.notifyChange()
        error_name.notifyChange()
        error_phone.notifyChange()
        error_number.notifyChange()
    }


}