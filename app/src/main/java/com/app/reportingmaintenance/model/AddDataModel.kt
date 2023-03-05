package com.app.reportingmaintenance.model

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.ObservableField
import com.app.reportingmaintenance.BR

class AddDataModel : BaseObservable() {

    var error_name = ObservableField<String>()

    @get:Bindable
    var isvaild = false


    @get:Bindable
    var name = ""
        set(value) {
            field = value
            validate()

            notifyPropertyChanged(BR.name)

        }



    private fun validate() {
        if (name.isNotEmpty()

        ) {

            error_name.set(null)
            isvaild=true
            notifyPropertyChanged(BR.isvaild)
        } else {
            isvaild=false

            if (name.isEmpty()){
                error_name.set("Field Required")
            }
            else{
                error_name.set(null)
            }


            notifyPropertyChanged(BR.isvaild)
        }

        error_name.notifyChange()

    }


}