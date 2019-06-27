package org.superbiz.moviefun.moviesapi;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.superbiz.moviefun.albumsapi.AlbumFixtures;
import org.superbiz.moviefun.albumsapi.AlbumInfo;
import org.superbiz.moviefun.albumsapi.AlbumsClient;


import java.util.Map;

@Controller
public class SetupController {
    private final MovieFixtures movieFixtures;
    private final MoviesClient moviesClient;

    private final AlbumFixtures albumFixtures;
    private final AlbumsClient albumsClient;

    public SetupController(MovieFixtures movieFixtures, MoviesClient moviesClient, AlbumFixtures albumFixtures, AlbumsClient albumsClient) {
        this.movieFixtures = movieFixtures;
        this.moviesClient = moviesClient;
        this.albumFixtures = albumFixtures;
        this.albumsClient = albumsClient;
    }

    @GetMapping("/setup")
    public String setup(Map<String, Object> model) {
        for (MovieInfo movie : movieFixtures.load()) {
         }

        for (AlbumInfo albumInfo : albumFixtures.load()) {
            albumsClient.addAlbum(albumInfo);
        }

        model.put("movies", moviesClient.getMovies());
        model.put("albums", albumsClient.getAlbums());

        return "setup";
    }
}
