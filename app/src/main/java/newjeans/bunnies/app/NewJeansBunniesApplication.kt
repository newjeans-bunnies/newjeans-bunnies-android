package newjeans.bunnies.app


import android.app.Application

import io.realm.Realm
import io.realm.RealmConfiguration


class NewJeansBunniesApplication: Application(){

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)

        val config : RealmConfiguration = RealmConfiguration.Builder()
            .allowWritesOnUiThread(true)
            .name("") // 생성할 realm db 이름
            .deleteRealmIfMigrationNeeded()
            .build()

        Realm.setDefaultConfiguration(config)
    }

}