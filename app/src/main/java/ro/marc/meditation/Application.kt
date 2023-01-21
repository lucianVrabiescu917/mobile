package ro.marc.meditation

import android.app.Application
import androidx.room.Room
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.dsl.module
import ro.marc.meditation.data.db.Database
import ro.marc.meditation.data.repo.LocalSessionRepo
import ro.marc.meditation.data.repo.SessionsRepo
import ro.marc.meditation.data.service.SessionsService


class Application: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin()
    }

    private fun dbModules() = module {
        fun provideDatabase(application: Application): Database {
            return Room
                .databaseBuilder(application, Database::class.java, "marc_db")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build()
        }

        single { provideDatabase(androidApplication()) }
        single { get<Database>().sessionDAO() }
    }

    private fun repoModules() = module {
        single { LocalSessionRepo(get()) }
        single { SessionsRepo(Utils.getRetrofit("http://10.0.2.2:8080").create(SessionsService::class.java)) }
    }

    private fun vmModules() = module {
        viewModel {
            MainActivityVM(get(), get())
        }
    }

    private fun startKoin() {
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@Application)
            modules(dbModules(), repoModules(), vmModules())
        }
    }

}
