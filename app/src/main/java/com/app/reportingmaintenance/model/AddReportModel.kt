package com.app.reportingmaintenance.model

import android.content.Context
import android.util.Patterns
import android.widget.Toast
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.ObservableField
import com.app.reportingmaintenance.BR

class AddReportModel : BaseObservable() {

    var error_subject = ObservableField<String>()
    var error_desc = ObservableField<String>()
    lateinit var context: Context

    @get:Bindable
    var isvaild = false
    var iddis = ""
    var idfac = ""
    var idplace = ""
    var periority = ""
    var image = ""

    @get:Bindable
    var desc = ""
        set(value) {
            field = value
            validate()

            notifyPropertyChanged(BR.desc)

        }


    @get:Bindable
    var subject = ""
        set(value) {
            field = value
            validate()

            notifyPropertyChanged(BR.subject)

        }


     fun validate() {
        if (subject.isNotEmpty()
            && iddis.isNotEmpty()
            && idfac.isNotEmpty()
            && idplace.isNotEmpty()
            && periority.isNotEmpty()
            && desc.isNotEmpty()
            && image.isNotEmpty()
        ) {

            error_subject.set(null)
            error_desc.set(null)

            isvaild = true
            notifyPropertyChanged(BR.isvaild)
        } else {
            isvaild = false
            if (desc.isEmpty()) {
                error_desc.set("Field Required")
            } else {
                error_desc.set(null)
            }

            if (subject.isEmpty()) {
                error_subject.set("Field Required")
            } else {
                error_subject.set(null)
            }

            if (idplace.isEmpty()) {
                Toast.makeText(context, "Choose Place", Toast.LENGTH_LONG).show()
            }
            if (idfac.isEmpty()) {
                Toast.makeText(context, "Choose Faculty", Toast.LENGTH_LONG).show()
            }
            if (iddis.isEmpty()) {
                Toast.makeText(context, "Choose Disribution", Toast.LENGTH_LONG).show()
            }
            if (periority.isEmpty()) {
                Toast.makeText(context, "Choose Periority", Toast.LENGTH_LONG).show()
            }
            if (image.isEmpty()) {
                Toast.makeText(context, "Choose Image", Toast.LENGTH_LONG).show()
            }
            notifyPropertyChanged(BR.isvaild)
        }
        error_desc.notifyChange()
        error_subject.notifyChange()

    }


}