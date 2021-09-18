package com.apirest.webflux.EndPoint;

import com.apirest.webflux.document.Playlist;
import com.apirest.webflux.services.PlaylistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.time.Duration;

@RestController
public class PlaylistEndPoint {
    @Autowired
    PlaylistService service;

    @GetMapping(value = "/playlists")
    Flux<Playlist> getPlaylists() {
        return service.findAll();
    }

    @GetMapping(value = "/playlist/{id}")
    Mono<Playlist> getPlaylist(@PathVariable String id) {
        return service.findById(id);
    }

    @PostMapping(value = "/playlists")
    Mono<Playlist> sevePlaylist(@RequestBody Playlist playlist) {
        return service.save(playlist);
    }

    @GetMapping(value = "/playlist/events", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    Flux<Tuple2<Long, Playlist>> getPlaylistEvents() {
        Flux<Long> interval = Flux.interval(Duration.ofSeconds(10));
        Flux<Playlist> events = service.findAll();
        return Flux.zip(interval, events);
    }
}
