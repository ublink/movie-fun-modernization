package org.superbiz.moviefun.moviesapi;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class HomeController {

//    private final MoviesRepository moviesRepository;
//    private final AlbumsBean albumsBean;
//    private final MovieFixtures movieFixtures;
//    private final AlbumFixtures albumFixtures;
//
//    public HomeController(MoviesRepository moviesBean, AlbumsBean albumsBean, MovieFixtures movieFixtures, AlbumFixtures albumFixtures) {
//        this.moviesRepository = moviesBean;
//        this.albumsBean = albumsBean;
//        this.movieFixtures = movieFixtures;
//        this.albumFixtures = albumFixtures;
//    }

    @GetMapping("/")
    public String index() {
        return "index";
    }


}
