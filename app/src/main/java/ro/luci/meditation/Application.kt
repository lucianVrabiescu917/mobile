package ro.luci.meditation

import android.app.Application
import androidx.room.Room
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.dsl.module
import ro.luci.meditation.data.db.Database
import ro.luci.meditation.data.repo.SessionRepo
import ro.luci.meditation.data.repo.impl.SessionRepoImpl


class Application: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin()
    }

    private fun dbModules() = module {
        fun provideDatabase(application: Application): Database {
            return Room
                .databaseBuilder(application, Database::class.java, "luci_db")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build()
        }

        single { provideDatabase(androidApplication()) }
        single { get<Database>().sessionDAO() }
    }

    private fun repoModules() = module {
        single<SessionRepo> { SessionRepoImpl(get()) }
    }

    private fun vmModules() = module {
        viewModel {
            MainActivityVM(get())
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
