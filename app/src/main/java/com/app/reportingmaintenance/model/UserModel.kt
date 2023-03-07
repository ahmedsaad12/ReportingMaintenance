package com.app.reportingmaintenance.model

import java.io.Serializable

class UserModel : Serializable {

    var email: String? = null
    var password: String? = null


    constructor() {}
    constructor(email: String?, name: String?) {
        this.email = email
        this.password = password

    }
}