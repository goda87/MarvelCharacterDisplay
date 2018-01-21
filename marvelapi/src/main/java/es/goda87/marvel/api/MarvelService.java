package es.goda87.marvel.api;

import es.goda87.marvel.api.models.CharacterDataWrapper;
import es.goda87.marvel.api.models.ComicDataWrapper;
import es.goda87.marvel.api.models.EventDataWrapper;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import io.reactivex.Observable;

interface MarvelService {
    @GET("v1/public/characters")
    Observable<CharacterDataWrapper> getCharacters(
            @Query("apikey") String apikey,
            @Query("ts") String ts,
            @Query("hash") String hash,
            @Query("orderBy") String orderBy,
            @Query("limit") int limit,
            @Query("offset") int offset);

    @GET("v1/public/characters")
    Observable<CharacterDataWrapper> getCharacters(
            @Query("nameStartsWith") String nameStartsWith,
            @Query("apikey") String apikey,
            @Query("ts") String ts,
            @Query("hash") String hash,
            @Query("orderBy") String orderBy,
            @Query("limit") int limit,
            @Query("offset") int offset);

    @GET("/v1/public/characters/{characterId}/comics")
    Observable<ComicDataWrapper> getComics(
            @Path("characterId") int characterId,
            @Query("apikey") String apikey,
            @Query("ts") String ts,
            @Query("hash") String hash,
            @Query("orderBy") String orderBy,
            @Query("limit") int limit,
            @Query("offset") int offset);

    @GET("/v1/public/characters/{characterId}/events")
    Observable<EventDataWrapper> getEvents(
            @Path("characterId") int characterId,
            @Query("apikey") String apikey,
            @Query("ts") String ts,
            @Query("hash") String hash,
            @Query("orderBy") String orderBy,
            @Query("limit") int limit,
            @Query("offset") int offset);
}
