package es.goda87.marvel.api;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import es.goda87.marvel.api.models.CharacterDataWrapper;
import es.goda87.marvel.api.models.ComicDataWrapper;
import es.goda87.marvel.api.models.EventDataWrapper;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import io.reactivex.Observable;
import retrofit2.converter.gson.GsonConverterFactory;


public class MarvelApi {

    private static final int PAGE_SIZE = 30;

    private final String publicKey, privateKey;

    MarvelService service;

    public MarvelApi(String publicKey, String privateKey) {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://gateway.marvel.com:443/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        service = retrofit.create(MarvelService.class);

        this.publicKey = publicKey;
        this.privateKey = privateKey;
    }

    /**
     * Gets 20 ordered by name characters from marvel
     * @param offset 0 based offset to get the characters from.
     * @return Data wrapper with the obtained information.
     */
    public Observable<CharacterDataWrapper> getCharacters(final int offset) {
        String ts = "" + System.currentTimeMillis();
        return service.getCharacters(
                publicKey, ts, Utils.hash(ts, publicKey, privateKey),
                "name", PAGE_SIZE, offset);
    }

    /**
     * Gets 20 ordered by name characters from marvel
     * @param offset 0 based offset to get the characters from.
     * @return Data wrapper with the obtained information.
     */
    public Observable<CharacterDataWrapper> getCharacters(final String nameStartsWith, final int offset) {
        String ts = "" + System.currentTimeMillis();
        return service.getCharacters(nameStartsWith,
                publicKey, ts, Utils.hash(ts, publicKey, privateKey),
                "name", PAGE_SIZE, offset);
    }

    /**
     * Gets 20 ordered by date comics from marvel, order by onsaleDate
     * @param offset 0 based offset to get the characters from.
     * @return Data wrapper with the obtained information.
     */
    public Observable<ComicDataWrapper> getComics(final int characterId, final int offset) {
        String ts = "" + System.currentTimeMillis();
        return service.getComics(characterId,
                publicKey, ts, Utils.hash(ts, publicKey, privateKey),
                "onsaleDate", PAGE_SIZE, offset);
    }

    /**
     * Gets 20 ordered by date events from marvel
     * @param offset 0 based offset to get the characters from.
     * @return Data wrapper with the obtained information.
     */
    public Observable<EventDataWrapper> getEvents(final int characterId, final int offset) {
        String ts = "" + System.currentTimeMillis();
        return service.getEvents(characterId,
                publicKey, ts, Utils.hash(ts, publicKey, privateKey),
                "startDate", PAGE_SIZE, offset);
    }
}
