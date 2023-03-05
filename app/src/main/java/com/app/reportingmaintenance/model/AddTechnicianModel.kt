package com.app.reportingmaintenance.model

import android.util.Patterns
import android.widget.Toast
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.ObservableField
import com.app.reportingmaintenance.BR

class AddTechnicianModel : BaseObservable() {

    var error_name = ObservableField<String>()

    @get:Bindable
    var isvaild = false
    var id = 0

    @get:Bindable
    var name = ""
        set(value) {
            field = value
            validate()

            notifyPropertyChanged(BR.name)

        }


    private fun validate() {
        if (name.isNotEmpty()
            &&id!=0
        ) {

            error_name.set(null)
            isvaild = true
            notifyPropertyChanged(BR.isvaild)
        } else {
            isvaild = false

            if (name.isEmpty()) {
                error_name.set("Field Required")
            } else {
                error_name.set(null)
            }

if(id==0){
    //Toast.makeText(get,"Choose type",Toast.LENGTH_LONG).show()
}
            notifyPropertyChanged(BR.isvaild)
        }

        error_name.notifyChange()

    }


}