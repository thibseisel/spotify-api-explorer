package com.github.thibseisel.sfyxplor

import org.intellij.lang.annotations.Language

const val TEST_TOKEN_STRING = "MTQ0NjJkZmQ5OTM2NDE1ZTZjNGZmZjI3"

@Language("JSON")
val AUTH_TOKEN = """{
    "access_token": "MTQ0NjJkZmQ5OTM2NDE1ZTZjNGZmZjI3",
    "token_type": "bearer",
    "expires_in": 3600
}""".trimIndent()

@Language("JSON")
val SINGLE_ARTIST = """{
  "external_urls": {
    "spotify": "https://open.spotify.com/artist/12Chz98pHFMPJEknJQMWvI"
  },
  "followers": {
    "href": null,
    "total": 4961259
  },
  "genres": [
    "modern rock",
    "permanent wave",
    "piano rock",
    "post-grunge",
    "rock"
  ],
  "href": "https://api.spotify.com/v1/artists/12Chz98pHFMPJEknJQMWvI",
  "id": "12Chz98pHFMPJEknJQMWvI",
  "images": [
    {
      "height": 320,
      "url": "https://i.scdn.co/image/17f00ec7613d733f2dd88de8f2c1628ea5f9adde",
      "width": 320
    }
  ],
  "name": "Muse",
  "popularity": 82,
  "type": "artist",
  "uri": "spotify:artist:12Chz98pHFMPJEknJQMWvI"
}""".trimIndent()

@Language("JSON")
val MULTIPLE_ARTISTS = """{
  "artists": [
    {
      "external_urls": {
        "spotify": "https://open.spotify.com/artist/12Chz98pHFMPJEknJQMWvI"
      },
      "followers": {
        "href": null,
        "total": 4961358
      },
      "genres": [
        "modern rock",
        "permanent wave",
        "piano rock",
        "post-grunge",
        "rock"
      ],
      "href": "https://api.spotify.com/v1/artists/12Chz98pHFMPJEknJQMWvI",
      "id": "12Chz98pHFMPJEknJQMWvI",
      "images": [
        {
          "height": 320,
          "url": "https://i.scdn.co/image/17f00ec7613d733f2dd88de8f2c1628ea5f9adde",
          "width": 320
        }
      ],
      "name": "Muse",
      "popularity": 82,
      "type": "artist",
      "uri": "spotify:artist:12Chz98pHFMPJEknJQMWvI"
    },
    {
      "external_urls": {
        "spotify": "https://open.spotify.com/artist/7jy3rLJdDQY21OgRLCZ9sD"
      },
      "followers": {
        "href": null,
        "total": 6381609
      },
      "genres": [
        "alternative metal",
        "alternative rock",
        "modern rock",
        "permanent wave",
        "post-grunge",
        "rock"
      ],
      "href": "https://api.spotify.com/v1/artists/7jy3rLJdDQY21OgRLCZ9sD",
      "id": "7jy3rLJdDQY21OgRLCZ9sD",
      "images": [
        {
          "height": 320,
          "url": "https://i.scdn.co/image/c508060cb93f3d2f43ad0dc38602eebcbe39d16d",
          "width": 320
        }
      ],
      "name": "Foo Fighters",
      "popularity": 82,
      "type": "artist",
      "uri": "spotify:artist:7jy3rLJdDQY21OgRLCZ9sD"
    }
  ]
}""".trimIndent()

val ARTIST_ALBUMS: String get() = TODO("Incorrect JSON String")

@Language("JSON")
val SINGLE_ALBUM = """{
  "album_type": "album",
  "artists": [
    {
      "external_urls": {
        "spotify": "https://open.spotify.com/artist/7jy3rLJdDQY21OgRLCZ9sD"
      },
      "href": "https://api.spotify.com/v1/artists/7jy3rLJdDQY21OgRLCZ9sD",
      "id": "7jy3rLJdDQY21OgRLCZ9sD",
      "name": "Foo Fighters",
      "type": "artist",
      "uri": "spotify:artist:7jy3rLJdDQY21OgRLCZ9sD"
    }
  ],
  "copyrights": [
    {
      "text": "(P) 2017 Roswell Records, Inc. under license to RCA Records, a unit of Sony Music Entertainment",
      "type": "P"
    }
  ],
  "external_ids": {
    "upc": "886446547176"
  },
  "external_urls": {
    "spotify": "https://open.spotify.com/album/6KMkuqIwKkwUhUYRPL6dUc"
  },
  "genres": [],
  "href": "https://api.spotify.com/v1/albums/6KMkuqIwKkwUhUYRPL6dUc",
  "id": "6KMkuqIwKkwUhUYRPL6dUc",
  "images": [
    {
      "height": 300,
      "url": "https://i.scdn.co/image/466a21e8c6f72e540392ae76a94e01c876a8f193",
      "width": 300
    }
  ],
  "label": "RCA Records Label",
  "name": "Concrete and Gold",
  "popularity": 68,
  "release_date": "2017-09-15",
  "release_date_precision": "day",
  "total_tracks": 11,
  "tracks": {
    "href": "https://api.spotify.com/v1/albums/6KMkuqIwKkwUhUYRPL6dUc/tracks?offset=0&limit=50&market=FR",
    "items": [
      {
        "artists": [
          {
            "external_urls": {
              "spotify": "https://open.spotify.com/artist/7jy3rLJdDQY21OgRLCZ9sD"
            },
            "href": "https://api.spotify.com/v1/artists/7jy3rLJdDQY21OgRLCZ9sD",
            "id": "7jy3rLJdDQY21OgRLCZ9sD",
            "name": "Foo Fighters",
            "type": "artist",
            "uri": "spotify:artist:7jy3rLJdDQY21OgRLCZ9sD"
          }
        ],
        "disc_number": 1,
        "duration_ms": 320880,
        "explicit": false,
        "external_urls": {
          "spotify": "https://open.spotify.com/track/5lnsL7pCg0fQKcWnlkD1F0"
        },
        "href": "https://api.spotify.com/v1/tracks/5lnsL7pCg0fQKcWnlkD1F0",
        "id": "5lnsL7pCg0fQKcWnlkD1F0",
        "is_local": false,
        "is_playable": true,
        "name": "Dirty Water",
        "preview_url": "https://p.scdn.co/mp3-preview/165855a9cf8021df6e4d8dbf51cb92c5939ea8cd?cid=774b29d4f13844c495f206cafdad9c86",
        "track_number": 6,
        "type": "track",
        "uri": "spotify:track:5lnsL7pCg0fQKcWnlkD1F0"
      }
    ],
    "limit": 50,
    "next": null,
    "offset": 0,
    "previous": null,
    "total": 1
  },
  "type": "album",
  "uri": "spotify:album:6KMkuqIwKkwUhUYRPL6dUc"
}""".trimIndent()

@Language("JSON")
val MULTIPLE_ALBUMS = """{
  "albums": [
    {
      "album_type": "album",
      "artists": [
        {
          "external_urls": {
            "spotify": "https://open.spotify.com/artist/12Chz98pHFMPJEknJQMWvI"
          },
          "href": "https://api.spotify.com/v1/artists/12Chz98pHFMPJEknJQMWvI",
          "id": "12Chz98pHFMPJEknJQMWvI",
          "name": "Muse",
          "type": "artist",
          "uri": "spotify:artist:12Chz98pHFMPJEknJQMWvI"
        }
      ],
      "copyrights": [
        {
          "text": "2018, Muse under exclusive licence to Warner Music UK Limited",
          "type": "C"
        },
        {
          "text": "2018, Muse under exclusive licence to Warner Music UK Limited with the exception of track 10 2017, Muse under exclusive licence to Warner Music UK",
          "type": "P"
        }
      ],
      "external_ids": {
        "upc": "190295559205"
      },
      "external_urls": {
        "spotify": "https://open.spotify.com/album/5OZgDtx180ZZPMpm36J2zC"
      },
      "genres": [],
      "href": "https://api.spotify.com/v1/albums/5OZgDtx180ZZPMpm36J2zC",
      "id": "5OZgDtx180ZZPMpm36J2zC",
      "images": [
        {
          "height": 300,
          "url": "https://i.scdn.co/image/0b2a261f7bec0ed109a149316d116c15ca72e5ef",
          "width": 300
        }
      ],
      "label": "Warner Bros.",
      "name": "Simulation Theory (Super Deluxe)",
      "popularity": 73,
      "release_date": "2018-11-09",
      "release_date_precision": "day",
      "total_tracks": 21,
      "tracks": {
        "href": "https://api.spotify.com/v1/albums/5OZgDtx180ZZPMpm36J2zC/tracks?offset=0&limit=50&market=FR",
        "items": [
          {
            "artists": [
              {
                "external_urls": {
                  "spotify": "https://open.spotify.com/artist/12Chz98pHFMPJEknJQMWvI"
                },
                "href": "https://api.spotify.com/v1/artists/12Chz98pHFMPJEknJQMWvI",
                "id": "12Chz98pHFMPJEknJQMWvI",
                "name": "Muse",
                "type": "artist",
                "uri": "spotify:artist:12Chz98pHFMPJEknJQMWvI"
              }
            ],
            "disc_number": 1,
            "duration_ms": 245960,
            "explicit": false,
            "external_urls": {
              "spotify": "https://open.spotify.com/track/7f0vVL3xi4i78Rv5Ptn2s1"
            },
            "href": "https://api.spotify.com/v1/tracks/7f0vVL3xi4i78Rv5Ptn2s1",
            "id": "7f0vVL3xi4i78Rv5Ptn2s1",
            "is_local": false,
            "is_playable": true,
            "name": "Algorithm",
            "preview_url": "https://p.scdn.co/mp3-preview/00d386644c07673f05878e337f977911c0ba740b?cid=774b29d4f13844c495f206cafdad9c86",
            "track_number": 1,
            "type": "track",
            "uri": "spotify:track:7f0vVL3xi4i78Rv5Ptn2s1"
          }
        ],
        "limit": 50,
        "next": null,
        "offset": 0,
        "previous": null,
        "total": 1
      },
      "type": "album",
      "uri": "spotify:album:5OZgDtx180ZZPMpm36J2zC"
    },
    {
      "album_type": "album",
      "artists": [
        {
          "external_urls": {
            "spotify": "https://open.spotify.com/artist/7jy3rLJdDQY21OgRLCZ9sD"
          },
          "href": "https://api.spotify.com/v1/artists/7jy3rLJdDQY21OgRLCZ9sD",
          "id": "7jy3rLJdDQY21OgRLCZ9sD",
          "name": "Foo Fighters",
          "type": "artist",
          "uri": "spotify:artist:7jy3rLJdDQY21OgRLCZ9sD"
        }
      ],
      "copyrights": [
        {
          "text": "(P) 2017 Roswell Records, Inc. under license to RCA Records, a unit of Sony Music Entertainment",
          "type": "P"
        }
      ],
      "external_ids": {
        "upc": "886446547176"
      },
      "external_urls": {
        "spotify": "https://open.spotify.com/album/6KMkuqIwKkwUhUYRPL6dUc"
      },
      "genres": [],
      "href": "https://api.spotify.com/v1/albums/6KMkuqIwKkwUhUYRPL6dUc",
      "id": "6KMkuqIwKkwUhUYRPL6dUc",
      "images": [
        {
          "height": 300,
          "url": "https://i.scdn.co/image/466a21e8c6f72e540392ae76a94e01c876a8f193",
          "width": 300
        }
      ],
      "label": "RCA Records Label",
      "name": "Concrete and Gold",
      "popularity": 68,
      "release_date": "2017-09-15",
      "release_date_precision": "day",
      "total_tracks": 11,
      "tracks": {
        "href": "https://api.spotify.com/v1/albums/6KMkuqIwKkwUhUYRPL6dUc/tracks?offset=0&limit=50&market=FR",
        "items": [
          {
            "artists": [
              {
                "external_urls": {
                  "spotify": "https://open.spotify.com/artist/7jy3rLJdDQY21OgRLCZ9sD"
                },
                "href": "https://api.spotify.com/v1/artists/7jy3rLJdDQY21OgRLCZ9sD",
                "id": "7jy3rLJdDQY21OgRLCZ9sD",
                "name": "Foo Fighters",
                "type": "artist",
                "uri": "spotify:artist:7jy3rLJdDQY21OgRLCZ9sD"
              }
            ],
            "disc_number": 1,
            "duration_ms": 323373,
            "explicit": false,
            "external_urls": {
              "spotify": "https://open.spotify.com/track/1wLQwg0mloy3yXjL0jPE0N"
            },
            "href": "https://api.spotify.com/v1/tracks/1wLQwg0mloy3yXjL0jPE0N",
            "id": "1wLQwg0mloy3yXjL0jPE0N",
            "is_local": false,
            "is_playable": true,
            "name": "Run",
            "preview_url": "https://p.scdn.co/mp3-preview/fded09b73c6308a8e8dccfad257bee228848481a?cid=774b29d4f13844c495f206cafdad9c86",
            "track_number": 2,
            "type": "track",
            "uri": "spotify:track:1wLQwg0mloy3yXjL0jPE0N"
          }
        ],
        "limit": 50,
        "next": null,
        "offset": 0,
        "previous": null,
        "total": 1
      },
      "type": "album",
      "uri": "spotify:album:6KMkuqIwKkwUhUYRPL6dUc"
    }
  ]
}""".trimIndent()

@Language("JSON")
val ALBUM_TRACKS = """{
  "href": "https://api.spotify.com/v1/albums/5OZgDtx180ZZPMpm36J2zC/tracks?offset=0&limit=2&market=FR",
  "items": [
    {
      "artists": [
        {
          "external_urls": {
            "spotify": "https://open.spotify.com/artist/12Chz98pHFMPJEknJQMWvI"
          },
          "href": "https://api.spotify.com/v1/artists/12Chz98pHFMPJEknJQMWvI",
          "id": "12Chz98pHFMPJEknJQMWvI",
          "name": "Muse",
          "type": "artist",
          "uri": "spotify:artist:12Chz98pHFMPJEknJQMWvI"
        }
      ],
      "disc_number": 1,
      "duration_ms": 245960,
      "explicit": false,
      "external_urls": {
        "spotify": "https://open.spotify.com/track/7f0vVL3xi4i78Rv5Ptn2s1"
      },
      "href": "https://api.spotify.com/v1/tracks/7f0vVL3xi4i78Rv5Ptn2s1",
      "id": "7f0vVL3xi4i78Rv5Ptn2s1",
      "is_local": false,
      "is_playable": true,
      "name": "Algorithm",
      "preview_url": "https://p.scdn.co/mp3-preview/00d386644c07673f05878e337f977911c0ba740b?cid=774b29d4f13844c495f206cafdad9c86",
      "track_number": 1,
      "type": "track",
      "uri": "spotify:track:7f0vVL3xi4i78Rv5Ptn2s1"
    },
    {
      "artists": [
        {
          "external_urls": {
            "spotify": "https://open.spotify.com/artist/12Chz98pHFMPJEknJQMWvI"
          },
          "href": "https://api.spotify.com/v1/artists/12Chz98pHFMPJEknJQMWvI",
          "id": "12Chz98pHFMPJEknJQMWvI",
          "name": "Muse",
          "type": "artist",
          "uri": "spotify:artist:12Chz98pHFMPJEknJQMWvI"
        }
      ],
      "disc_number": 1,
      "duration_ms": 227213,
      "explicit": false,
      "external_urls": {
        "spotify": "https://open.spotify.com/track/0dMYPDqcI4ca4cjqlmp9mE"
      },
      "href": "https://api.spotify.com/v1/tracks/0dMYPDqcI4ca4cjqlmp9mE",
      "id": "0dMYPDqcI4ca4cjqlmp9mE",
      "is_local": false,
      "is_playable": true,
      "name": "The Dark Side",
      "preview_url": "https://p.scdn.co/mp3-preview/5995cbc458c74a9f4beb50ca250a9920d3b1d8ab?cid=774b29d4f13844c495f206cafdad9c86",
      "track_number": 2,
      "type": "track",
      "uri": "spotify:track:0dMYPDqcI4ca4cjqlmp9mE"
    }
  ],
  "limit": 2,
  "next": "https://api.spotify.com/v1/albums/5OZgDtx180ZZPMpm36J2zC/tracks?offset=2&limit=2&market=FR",
  "offset": 0,
  "previous": null,
  "total": 21
}"""

@Language("JSON")
val SINGLE_TRACK = """{
  "album": {
    "album_type": "album",
    "artists": [
      {
        "external_urls": {
          "spotify": "https://open.spotify.com/artist/12Chz98pHFMPJEknJQMWvI"
        },
        "href": "https://api.spotify.com/v1/artists/12Chz98pHFMPJEknJQMWvI",
        "id": "12Chz98pHFMPJEknJQMWvI",
        "name": "Muse",
        "type": "artist",
        "uri": "spotify:artist:12Chz98pHFMPJEknJQMWvI"
      }
    ],
    "external_urls": {
      "spotify": "https://open.spotify.com/album/5OZgDtx180ZZPMpm36J2zC"
    },
    "href": "https://api.spotify.com/v1/albums/5OZgDtx180ZZPMpm36J2zC",
    "id": "5OZgDtx180ZZPMpm36J2zC",
    "images": [
      {
        "height": 300,
        "url": "https://i.scdn.co/image/0b2a261f7bec0ed109a149316d116c15ca72e5ef",
        "width": 300
      }
    ],
    "name": "Simulation Theory (Super Deluxe)",
    "release_date": "2018-11-09",
    "release_date_precision": "day",
    "total_tracks": 21,
    "type": "album",
    "uri": "spotify:album:5OZgDtx180ZZPMpm36J2zC"
  },
  "artists": [
    {
      "external_urls": {
        "spotify": "https://open.spotify.com/artist/12Chz98pHFMPJEknJQMWvI"
      },
      "href": "https://api.spotify.com/v1/artists/12Chz98pHFMPJEknJQMWvI",
      "id": "12Chz98pHFMPJEknJQMWvI",
      "name": "Muse",
      "type": "artist",
      "uri": "spotify:artist:12Chz98pHFMPJEknJQMWvI"
    }
  ],
  "disc_number": 1,
  "duration_ms": 245960,
  "explicit": false,
  "external_ids": {
    "isrc": "GBAHT1800406"
  },
  "external_urls": {
    "spotify": "https://open.spotify.com/track/7f0vVL3xi4i78Rv5Ptn2s1"
  },
  "href": "https://api.spotify.com/v1/tracks/7f0vVL3xi4i78Rv5Ptn2s1",
  "id": "7f0vVL3xi4i78Rv5Ptn2s1",
  "is_local": false,
  "is_playable": true,
  "name": "Algorithm",
  "popularity": 60,
  "preview_url": "https://p.scdn.co/mp3-preview/00d386644c07673f05878e337f977911c0ba740b?cid=774b29d4f13844c495f206cafdad9c86",
  "track_number": 1,
  "type": "track",
  "uri": "spotify:track:7f0vVL3xi4i78Rv5Ptn2s1"
}""".trimIndent()

@Language("JSON")
val MULTIPLE_TRACKS = """{
  "tracks": [
    {
      "album": {
        "album_type": "album",
        "artists": [
          {
            "external_urls": {
              "spotify": "https://open.spotify.com/artist/12Chz98pHFMPJEknJQMWvI"
            },
            "href": "https://api.spotify.com/v1/artists/12Chz98pHFMPJEknJQMWvI",
            "id": "12Chz98pHFMPJEknJQMWvI",
            "name": "Muse",
            "type": "artist",
            "uri": "spotify:artist:12Chz98pHFMPJEknJQMWvI"
          }
        ],
        "external_urls": {
          "spotify": "https://open.spotify.com/album/5OZgDtx180ZZPMpm36J2zC"
        },
        "href": "https://api.spotify.com/v1/albums/5OZgDtx180ZZPMpm36J2zC",
        "id": "5OZgDtx180ZZPMpm36J2zC",
        "images": [
          {
            "height": 300,
            "url": "https://i.scdn.co/image/0b2a261f7bec0ed109a149316d116c15ca72e5ef",
            "width": 300
          }
        ],
        "name": "Simulation Theory (Super Deluxe)",
        "release_date": "2018-11-09",
        "release_date_precision": "day",
        "total_tracks": 21,
        "type": "album",
        "uri": "spotify:album:5OZgDtx180ZZPMpm36J2zC"
      },
      "artists": [
        {
          "external_urls": {
            "spotify": "https://open.spotify.com/artist/12Chz98pHFMPJEknJQMWvI"
          },
          "href": "https://api.spotify.com/v1/artists/12Chz98pHFMPJEknJQMWvI",
          "id": "12Chz98pHFMPJEknJQMWvI",
          "name": "Muse",
          "type": "artist",
          "uri": "spotify:artist:12Chz98pHFMPJEknJQMWvI"
        }
      ],
      "disc_number": 1,
      "duration_ms": 245960,
      "explicit": false,
      "external_ids": {
        "isrc": "GBAHT1800406"
      },
      "external_urls": {
        "spotify": "https://open.spotify.com/track/7f0vVL3xi4i78Rv5Ptn2s1"
      },
      "href": "https://api.spotify.com/v1/tracks/7f0vVL3xi4i78Rv5Ptn2s1",
      "id": "7f0vVL3xi4i78Rv5Ptn2s1",
      "is_local": false,
      "is_playable": true,
      "name": "Algorithm",
      "popularity": 60,
      "preview_url": "https://p.scdn.co/mp3-preview/00d386644c07673f05878e337f977911c0ba740b?cid=774b29d4f13844c495f206cafdad9c86",
      "track_number": 1,
      "type": "track",
      "uri": "spotify:track:7f0vVL3xi4i78Rv5Ptn2s1"
    },
    {
      "album": {
        "album_type": "album",
        "artists": [
          {
            "external_urls": {
              "spotify": "https://open.spotify.com/artist/12Chz98pHFMPJEknJQMWvI"
            },
            "href": "https://api.spotify.com/v1/artists/12Chz98pHFMPJEknJQMWvI",
            "id": "12Chz98pHFMPJEknJQMWvI",
            "name": "Muse",
            "type": "artist",
            "uri": "spotify:artist:12Chz98pHFMPJEknJQMWvI"
          }
        ],
        "external_urls": {
          "spotify": "https://open.spotify.com/album/5OZgDtx180ZZPMpm36J2zC"
        },
        "href": "https://api.spotify.com/v1/albums/5OZgDtx180ZZPMpm36J2zC",
        "id": "5OZgDtx180ZZPMpm36J2zC",
        "images": [
          {
            "height": 300,
            "url": "https://i.scdn.co/image/0b2a261f7bec0ed109a149316d116c15ca72e5ef",
            "width": 300
          }
        ],
        "name": "Simulation Theory (Super Deluxe)",
        "release_date": "2018-11-09",
        "release_date_precision": "day",
        "total_tracks": 21,
        "type": "album",
        "uri": "spotify:album:5OZgDtx180ZZPMpm36J2zC"
      },
      "artists": [
        {
          "external_urls": {
            "spotify": "https://open.spotify.com/artist/12Chz98pHFMPJEknJQMWvI"
          },
          "href": "https://api.spotify.com/v1/artists/12Chz98pHFMPJEknJQMWvI",
          "id": "12Chz98pHFMPJEknJQMWvI",
          "name": "Muse",
          "type": "artist",
          "uri": "spotify:artist:12Chz98pHFMPJEknJQMWvI"
        }
      ],
      "disc_number": 1,
      "duration_ms": 227213,
      "explicit": false,
      "external_ids": {
        "isrc": "GBAHT1800404"
      },
      "external_urls": {
        "spotify": "https://open.spotify.com/track/0dMYPDqcI4ca4cjqlmp9mE"
      },
      "href": "https://api.spotify.com/v1/tracks/0dMYPDqcI4ca4cjqlmp9mE",
      "id": "0dMYPDqcI4ca4cjqlmp9mE",
      "is_local": false,
      "is_playable": true,
      "name": "The Dark Side",
      "popularity": 64,
      "preview_url": "https://p.scdn.co/mp3-preview/5995cbc458c74a9f4beb50ca250a9920d3b1d8ab?cid=774b29d4f13844c495f206cafdad9c86",
      "track_number": 2,
      "type": "track",
      "uri": "spotify:track:0dMYPDqcI4ca4cjqlmp9mE"
    }
  ]
}""".trimIndent()

@Language("JSON")
val SINGLE_AUDIO_FEATURES = """{
  "danceability": 0.522,
  "energy": 0.923,
  "key": 2,
  "loudness": -4.56,
  "mode": 1,
  "speechiness": 0.0539,
  "acousticness": 0.0125,
  "instrumentalness": 0.017,
  "liveness": 0.0854,
  "valence": 0.595,
  "tempo": 170.057,
  "type": "audio_features",
  "id": "7f0vVL3xi4i78Rv5Ptn2s1",
  "uri": "spotify:track:7f0vVL3xi4i78Rv5Ptn2s1",
  "track_href": "https://api.spotify.com/v1/tracks/7f0vVL3xi4i78Rv5Ptn2s1",
  "analysis_url": "https://api.spotify.com/v1/audio-analysis/7f0vVL3xi4i78Rv5Ptn2s1",
  "duration_ms": 245960,
  "time_signature": 4
}""".trimIndent()

@Language("JSON")
val MULTIPLE_AUDIO_FEATURES = """{
  "audio_features": [
    {
      "danceability": 0.522,
      "energy": 0.923,
      "key": 2,
      "loudness": -4.56,
      "mode": 1,
      "speechiness": 0.0539,
      "acousticness": 0.0125,
      "instrumentalness": 0.017,
      "liveness": 0.0854,
      "valence": 0.595,
      "tempo": 170.057,
      "type": "audio_features",
      "id": "7f0vVL3xi4i78Rv5Ptn2s1",
      "uri": "spotify:track:7f0vVL3xi4i78Rv5Ptn2s1",
      "track_href": "https://api.spotify.com/v1/tracks/7f0vVL3xi4i78Rv5Ptn2s1",
      "analysis_url": "https://api.spotify.com/v1/audio-analysis/7f0vVL3xi4i78Rv5Ptn2s1",
      "duration_ms": 245960,
      "time_signature": 4
    },
    {
      "danceability": 0.324,
      "energy": 0.631,
      "key": 7,
      "loudness": -8.245,
      "mode": 1,
      "speechiness": 0.0407,
      "acousticness": 0.00365,
      "instrumentalness": 0.0459,
      "liveness": 0.221,
      "valence": 0.346,
      "tempo": 142.684,
      "type": "audio_features",
      "id": "5lnsL7pCg0fQKcWnlkD1F0",
      "uri": "spotify:track:5lnsL7pCg0fQKcWnlkD1F0",
      "track_href": "https://api.spotify.com/v1/tracks/5lnsL7pCg0fQKcWnlkD1F0",
      "analysis_url": "https://api.spotify.com/v1/audio-analysis/5lnsL7pCg0fQKcWnlkD1F0",
      "duration_ms": 320880,
      "time_signature": 4
    }
  ]
}""".trimIndent()

@Language("JSON")
val SEARCH_RESULTS = """{
  "albums": {
    "href": "https://api.spotify.com/v1/search?query=rammstein&type=album&market=FR&offset=0&limit=10",
    "items": [
      {
        "album_type": "album",
        "artists": [
          {
            "external_urls": {
              "spotify": "https://open.spotify.com/artist/6wWVKhxIU2cEi0K81v7HvP"
            },
            "href": "https://api.spotify.com/v1/artists/6wWVKhxIU2cEi0K81v7HvP",
            "id": "6wWVKhxIU2cEi0K81v7HvP",
            "name": "Rammstein",
            "type": "artist",
            "uri": "spotify:artist:6wWVKhxIU2cEi0K81v7HvP"
          }
        ],
        "external_urls": {
          "spotify": "https://open.spotify.com/album/1LoyJQVHPLHE3fCCS8Juek"
        },
        "href": "https://api.spotify.com/v1/albums/1LoyJQVHPLHE3fCCS8Juek",
        "id": "1LoyJQVHPLHE3fCCS8Juek",
        "images": [
          {
            "height": 300,
            "url": "https://i.scdn.co/image/389c1df3f21fa93570dde0b75332e75ab91bd878",
            "width": 300
          }
        ],
        "name": "RAMMSTEIN",
        "release_date": "2019-05-17",
        "release_date_precision": "day",
        "total_tracks": 11,
        "type": "album",
        "uri": "spotify:album:1LoyJQVHPLHE3fCCS8Juek"
      }
    ],
    "limit": 10,
    "next": null,
    "offset": 0,
    "previous": null,
    "total": 1
  },
  "artists": {
    "href": "https://api.spotify.com/v1/search?query=rammstein&type=artist&market=FR&offset=0&limit=10",
    "items": [
      {
        "external_urls": {
          "spotify": "https://open.spotify.com/artist/6wWVKhxIU2cEi0K81v7HvP"
        },
        "followers": {
          "href": null,
          "total": 3031025
        },
        "genres": [
          "alternative metal",
          "german metal",
          "industrial",
          "industrial metal",
          "industrial rock",
          "neue deutsche harte"
        ],
        "href": "https://api.spotify.com/v1/artists/6wWVKhxIU2cEi0K81v7HvP",
        "id": "6wWVKhxIU2cEi0K81v7HvP",
        "images": [
          {
            "height": 320,
            "url": "https://i.scdn.co/image/d7bba2e8eb624d93d8cc7cb57d9ba5fb35f0f901",
            "width": 320
          }
        ],
        "name": "Rammstein",
        "popularity": 87,
        "type": "artist",
        "uri": "spotify:artist:6wWVKhxIU2cEi0K81v7HvP"
      }
    ],
    "limit": 10,
    "next": null,
    "offset": 0,
    "previous": null,
    "total": 1
  },
  "tracks": {
    "href": "https://api.spotify.com/v1/search?query=rammstein&type=track&market=FR&offset=0&limit=10",
    "items": [
      {
        "album": {
          "album_type": "album",
          "artists": [
            {
              "external_urls": {
                "spotify": "https://open.spotify.com/artist/6wWVKhxIU2cEi0K81v7HvP"
              },
              "href": "https://api.spotify.com/v1/artists/6wWVKhxIU2cEi0K81v7HvP",
              "id": "6wWVKhxIU2cEi0K81v7HvP",
              "name": "Rammstein",
              "type": "artist",
              "uri": "spotify:artist:6wWVKhxIU2cEi0K81v7HvP"
            }
          ],
          "external_urls": {
            "spotify": "https://open.spotify.com/album/1LoyJQVHPLHE3fCCS8Juek"
          },
          "href": "https://api.spotify.com/v1/albums/1LoyJQVHPLHE3fCCS8Juek",
          "id": "1LoyJQVHPLHE3fCCS8Juek",
          "images": [
            {
              "height": 300,
              "url": "https://i.scdn.co/image/389c1df3f21fa93570dde0b75332e75ab91bd878",
              "width": 300
            }
          ],
          "name": "RAMMSTEIN",
          "release_date": "2019-05-17",
          "release_date_precision": "day",
          "total_tracks": 11,
          "type": "album",
          "uri": "spotify:album:1LoyJQVHPLHE3fCCS8Juek"
        },
        "artists": [
          {
            "external_urls": {
              "spotify": "https://open.spotify.com/artist/6wWVKhxIU2cEi0K81v7HvP"
            },
            "href": "https://api.spotify.com/v1/artists/6wWVKhxIU2cEi0K81v7HvP",
            "id": "6wWVKhxIU2cEi0K81v7HvP",
            "name": "Rammstein",
            "type": "artist",
            "uri": "spotify:artist:6wWVKhxIU2cEi0K81v7HvP"
          }
        ],
        "disc_number": 1,
        "duration_ms": 277397,
        "explicit": false,
        "external_ids": {
          "isrc": "DEUM71900525"
        },
        "external_urls": {
          "spotify": "https://open.spotify.com/track/5vZ4IeUenK2cHub2d7yfWk"
        },
        "href": "https://api.spotify.com/v1/tracks/5vZ4IeUenK2cHub2d7yfWk",
        "id": "5vZ4IeUenK2cHub2d7yfWk",
        "is_local": false,
        "is_playable": true,
        "name": "RADIO",
        "popularity": 74,
        "preview_url": null,
        "track_number": 2,
        "type": "track",
        "uri": "spotify:track:5vZ4IeUenK2cHub2d7yfWk"
      }
    ],
    "limit": 10,
    "next": null,
    "offset": 0,
    "previous": null,
    "total": 1
  }
}""".trimIndent()