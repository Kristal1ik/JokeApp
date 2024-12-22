import android.content.Context
import com.kristallik.jokeapp.data.source.local.JokeDatabase
import com.kristallik.jokeapp.data.repository.JokeRepository
import com.kristallik.jokeapp.data.repository.JokeRepositoryImpl
import com.kristallik.jokeapp.data.source.remote.JokeApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideJokeDatabase(@ApplicationContext context: Context): JokeDatabase {
        return JokeDatabase.getDatabase(context)
    }

    @Provides
    @Singleton

    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://v2.jokeapi.dev")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideJokeApiService(retrofit: Retrofit): JokeApiService {
        return retrofit.create(JokeApiService::class.java)
    }
}
