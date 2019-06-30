package com.github.thibseisel.sfyxplor

import org.intellij.lang.annotations.Language

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
      "height": 640,
      "url": "https://i.scdn.co/image/12450535621500d6e519275f2c52d49c00a0168f",
      "width": 640
    },
    {
      "height": 320,
      "url": "https://i.scdn.co/image/17f00ec7613d733f2dd88de8f2c1628ea5f9adde",
      "width": 320
    },
    {
      "height": 160,
      "url": "https://i.scdn.co/image/2da69b7920c065afc835124c4786025820adab8c",
      "width": 160
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
          "height": 640,
          "url": "https://i.scdn.co/image/12450535621500d6e519275f2c52d49c00a0168f",
          "width": 640
        },
        {
          "height": 320,
          "url": "https://i.scdn.co/image/17f00ec7613d733f2dd88de8f2c1628ea5f9adde",
          "width": 320
        },
        {
          "height": 160,
          "url": "https://i.scdn.co/image/2da69b7920c065afc835124c4786025820adab8c",
          "width": 160
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
          "height": 640,
          "url": "https://i.scdn.co/image/4357f7b293c92d3a70a087552a27f69f431e16ae",
          "width": 640
        },
        {
          "height": 320,
          "url": "https://i.scdn.co/image/c508060cb93f3d2f43ad0dc38602eebcbe39d16d",
          "width": 320
        },
        {
          "height": 160,
          "url": "https://i.scdn.co/image/086a436ec6830acef5db63d526cb86e066e652de",
          "width": 160
        }
      ],
      "name": "Foo Fighters",
      "popularity": 82,
      "type": "artist",
      "uri": "spotify:artist:7jy3rLJdDQY21OgRLCZ9sD"
    }
  ]
}""".trimIndent()

@Language("JSON")
val ARTIST_ALBUMS = """{
  "href": "https://api.spotify.com/v1/artists/12Chz98pHFMPJEknJQMWvI/albums?offset=0&limit=2&include_groups=album,single&market=FR",
  "items": [
    {
      "album_group": "album",
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
          "height": 640,
          "url": "https://i.scdn.co/image/9a0ef2cbc0388e12b08a9f7915011440ee223835",
          "width": 640
        },
        {
          "height": 300,
          "url": "https://i.scdn.co/image/0b2a261f7bec0ed109a149316d116c15ca72e5ef",
          "width": 300
        },
        {
          "height": 64,
          "url": "https://i.scdn.co/image/28a8487234c901ae9fe127d1d0eef738a91e46d6",
          "width": 64
        }
      ],
      "name": "Simulation Theory (Super Deluxe)",
      "release_date": "2018-11-09",
      "release_date_precision": "day",
      "total_tracks": 21,
      "type": "album",
      "uri": "spotify:album:5OZgDtx180ZZPMpm36J2zC"
    },
    {
      "album_group": "album",
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
        "spotify": "https://open.spotify.com/album/2wart5Qjnvx1fd7LPdQxgJ"
      },
      "href": "https://api.spotify.com/v1/albums/2wart5Qjnvx1fd7LPdQxgJ",
      "id": "2wart5Qjnvx1fd7LPdQxgJ",
      "images": [
        {
          "height": 640,
          "url": "https://i.scdn.co/image/849eecf3c9df835181c2970c435ac2d008346ea3",
          "width": 640
        },
        {
          "height": 300,
          "url": "https://i.scdn.co/image/8b6392caa83625135f0f53d6e2b0631bbe4c4c0b",
          "width": 300
        },
        {
          "height": 64,
          "url": "https://i.scdn.co/image/8978459cc3ad68b39bd6dbda418625b06ba5d80c",
          "width": 64
        }
      ],
      "name": "Drones",
      "release_date": "2015-06-04",
      "release_date_precision": "day",
      "total_tracks": 12,
      "type": "album",
      "uri": "spotify:album:2wart5Qjnvx1fd7LPdQxgJ"
    }
  ],
  "limit": 2,
  "next": "https://api.spotify.com/v1/artists/12Chz98pHFMPJEknJQMWvI/albums?offset=2&limit=2&include_groups=album,single&market=FR",
  "offset": 0,
  "previous": null,
  "total": 46
}"""

@Language("JSON")
val ALBUM_DETAIL = """{
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
      "text": "2015 Muse, under exclusive licence to Warner Music UK Limited",
      "type": "C"
    },
    {
      "text": "2015 Muse, under exclusive licence to Warner Music UK Limited",
      "type": "P"
    }
  ],
  "external_ids": {
    "upc": "825646076246"
  },
  "external_urls": {
    "spotify": "https://open.spotify.com/album/2wart5Qjnvx1fd7LPdQxgJ"
  },
  "genres": [],
  "href": "https://api.spotify.com/v1/albums/2wart5Qjnvx1fd7LPdQxgJ",
  "id": "2wart5Qjnvx1fd7LPdQxgJ",
  "images": [
    {
      "height": 640,
      "url": "https://i.scdn.co/image/849eecf3c9df835181c2970c435ac2d008346ea3",
      "width": 640
    },
    {
      "height": 300,
      "url": "https://i.scdn.co/image/8b6392caa83625135f0f53d6e2b0631bbe4c4c0b",
      "width": 300
    },
    {
      "height": 64,
      "url": "https://i.scdn.co/image/8978459cc3ad68b39bd6dbda418625b06ba5d80c",
      "width": 64
    }
  ],
  "label": "Warner Records",
  "name": "Drones",
  "popularity": 67,
  "release_date": "2015-06-04",
  "release_date_precision": "day",
  "total_tracks": 12,
  "tracks": {
    "href": "https://api.spotify.com/v1/albums/2wart5Qjnvx1fd7LPdQxgJ/tracks?offset=0&limit=50&market=FR",
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
        "duration_ms": 262947,
        "explicit": false,
        "external_urls": {
          "spotify": "https://open.spotify.com/track/2daZovie6pc2ZK7StayD1K"
        },
        "href": "https://api.spotify.com/v1/tracks/2daZovie6pc2ZK7StayD1K",
        "id": "2daZovie6pc2ZK7StayD1K",
        "is_local": false,
        "is_playable": true,
        "name": "Dead Inside",
        "preview_url": "https://p.scdn.co/mp3-preview/f3dd36859bbae1f625386ec902fdf2d6682d2bbb?cid=774b29d4f13844c495f206cafdad9c86",
        "track_number": 1,
        "type": "track",
        "uri": "spotify:track:2daZovie6pc2ZK7StayD1K"
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
        "duration_ms": 21148,
        "explicit": false,
        "external_urls": {
          "spotify": "https://open.spotify.com/track/2E5tWJSusHxpaksg1yfsIu"
        },
        "href": "https://api.spotify.com/v1/tracks/2E5tWJSusHxpaksg1yfsIu",
        "id": "2E5tWJSusHxpaksg1yfsIu",
        "is_local": false,
        "is_playable": true,
        "name": "(Drill Sergeant)",
        "preview_url": "https://p.scdn.co/mp3-preview/c87f359113346a5bf1f9a523d2e6af1a272e2385?cid=774b29d4f13844c495f206cafdad9c86",
        "track_number": 2,
        "type": "track",
        "uri": "spotify:track:2E5tWJSusHxpaksg1yfsIu"
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
        "duration_ms": 316717,
        "explicit": true,
        "external_urls": {
          "spotify": "https://open.spotify.com/track/383QXk8nb2YrARMUwDdjQS"
        },
        "href": "https://api.spotify.com/v1/tracks/383QXk8nb2YrARMUwDdjQS",
        "id": "383QXk8nb2YrARMUwDdjQS",
        "is_local": false,
        "is_playable": true,
        "name": "Psycho",
        "preview_url": "https://p.scdn.co/mp3-preview/9825aad9fc20222cfd2db3eba32469181b90c8d8?cid=774b29d4f13844c495f206cafdad9c86",
        "track_number": 3,
        "type": "track",
        "uri": "spotify:track:383QXk8nb2YrARMUwDdjQS"
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
        "duration_ms": 231973,
        "explicit": false,
        "external_urls": {
          "spotify": "https://open.spotify.com/track/2qkmPUG7ARsRwhVICQVwQS"
        },
        "href": "https://api.spotify.com/v1/tracks/2qkmPUG7ARsRwhVICQVwQS",
        "id": "2qkmPUG7ARsRwhVICQVwQS",
        "is_local": false,
        "is_playable": true,
        "name": "Mercy",
        "preview_url": "https://p.scdn.co/mp3-preview/4032f82781095d6fe3c45da6ea81b50c38b505bc?cid=774b29d4f13844c495f206cafdad9c86",
        "track_number": 4,
        "type": "track",
        "uri": "spotify:track:2qkmPUG7ARsRwhVICQVwQS"
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
        "duration_ms": 359520,
        "explicit": false,
        "external_urls": {
          "spotify": "https://open.spotify.com/track/1244xKUG27TnmQhUJlo3gU"
        },
        "href": "https://api.spotify.com/v1/tracks/1244xKUG27TnmQhUJlo3gU",
        "id": "1244xKUG27TnmQhUJlo3gU",
        "is_local": false,
        "is_playable": true,
        "name": "Reapers",
        "preview_url": "https://p.scdn.co/mp3-preview/79b0e76ba6261987e9809210b912f8f2100b6d85?cid=774b29d4f13844c495f206cafdad9c86",
        "track_number": 5,
        "type": "track",
        "uri": "spotify:track:1244xKUG27TnmQhUJlo3gU"
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
        "duration_ms": 273760,
        "explicit": false,
        "external_urls": {
          "spotify": "https://open.spotify.com/track/6xq3Bd7MvZVa7pda9tC4MW"
        },
        "href": "https://api.spotify.com/v1/tracks/6xq3Bd7MvZVa7pda9tC4MW",
        "id": "6xq3Bd7MvZVa7pda9tC4MW",
        "is_local": false,
        "is_playable": true,
        "name": "The Handler",
        "preview_url": "https://p.scdn.co/mp3-preview/3e2c164c46aefaa97ea7a9a50fc744400c4266af?cid=774b29d4f13844c495f206cafdad9c86",
        "track_number": 6,
        "type": "track",
        "uri": "spotify:track:6xq3Bd7MvZVa7pda9tC4MW"
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
        "duration_ms": 54620,
        "explicit": false,
        "external_urls": {
          "spotify": "https://open.spotify.com/track/3KNnA3zH8wInPB48DRQ6Lg"
        },
        "href": "https://api.spotify.com/v1/tracks/3KNnA3zH8wInPB48DRQ6Lg",
        "id": "3KNnA3zH8wInPB48DRQ6Lg",
        "is_local": false,
        "is_playable": true,
        "name": "(JFK)",
        "preview_url": "https://p.scdn.co/mp3-preview/906ac507d6ff46c106a1c50626c1d15cb875de90?cid=774b29d4f13844c495f206cafdad9c86",
        "track_number": 7,
        "type": "track",
        "uri": "spotify:track:3KNnA3zH8wInPB48DRQ6Lg"
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
        "duration_ms": 273004,
        "explicit": false,
        "external_urls": {
          "spotify": "https://open.spotify.com/track/4FnAEvbT3mjxpkUKpGwXYM"
        },
        "href": "https://api.spotify.com/v1/tracks/4FnAEvbT3mjxpkUKpGwXYM",
        "id": "4FnAEvbT3mjxpkUKpGwXYM",
        "is_local": false,
        "is_playable": true,
        "name": "Defector",
        "preview_url": "https://p.scdn.co/mp3-preview/b3bf154969f106d27768dabbbbceade538717bf1?cid=774b29d4f13844c495f206cafdad9c86",
        "track_number": 8,
        "type": "track",
        "uri": "spotify:track:4FnAEvbT3mjxpkUKpGwXYM"
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
        "duration_ms": 245710,
        "explicit": false,
        "external_urls": {
          "spotify": "https://open.spotify.com/track/7BGg9wSF98j6FzvHGkq3f0"
        },
        "href": "https://api.spotify.com/v1/tracks/7BGg9wSF98j6FzvHGkq3f0",
        "id": "7BGg9wSF98j6FzvHGkq3f0",
        "is_local": false,
        "is_playable": true,
        "name": "Revolt",
        "preview_url": "https://p.scdn.co/mp3-preview/feb665f18309dcfd148d2979e1a89c36c92784d7?cid=774b29d4f13844c495f206cafdad9c86",
        "track_number": 9,
        "type": "track",
        "uri": "spotify:track:7BGg9wSF98j6FzvHGkq3f0"
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
        "duration_ms": 347997,
        "explicit": false,
        "external_urls": {
          "spotify": "https://open.spotify.com/track/2z1D4PqjWYEsXeXxE6euQ2"
        },
        "href": "https://api.spotify.com/v1/tracks/2z1D4PqjWYEsXeXxE6euQ2",
        "id": "2z1D4PqjWYEsXeXxE6euQ2",
        "is_local": false,
        "is_playable": true,
        "name": "Aftermath",
        "preview_url": "https://p.scdn.co/mp3-preview/f8a4061bb8d750d947c34b87213a254c1124e0d7?cid=774b29d4f13844c495f206cafdad9c86",
        "track_number": 10,
        "type": "track",
        "uri": "spotify:track:2z1D4PqjWYEsXeXxE6euQ2"
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
        "duration_ms": 607282,
        "explicit": false,
        "external_urls": {
          "spotify": "https://open.spotify.com/track/6BGxbBw5J314z6BDxbEanm"
        },
        "href": "https://api.spotify.com/v1/tracks/6BGxbBw5J314z6BDxbEanm",
        "id": "6BGxbBw5J314z6BDxbEanm",
        "is_local": false,
        "is_playable": true,
        "name": "The Globalist",
        "preview_url": "https://p.scdn.co/mp3-preview/5d1b2e9063e75c5205e1911c0467a6c5041c9cda?cid=774b29d4f13844c495f206cafdad9c86",
        "track_number": 11,
        "type": "track",
        "uri": "spotify:track:6BGxbBw5J314z6BDxbEanm"
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
        "duration_ms": 169817,
        "explicit": false,
        "external_urls": {
          "spotify": "https://open.spotify.com/track/1MaxCaHwLDYS0uOQLjUry0"
        },
        "href": "https://api.spotify.com/v1/tracks/1MaxCaHwLDYS0uOQLjUry0",
        "id": "1MaxCaHwLDYS0uOQLjUry0",
        "is_local": false,
        "is_playable": true,
        "name": "Drones",
        "preview_url": "https://p.scdn.co/mp3-preview/771467bfcb533886bfe43a2e182ee26ecc59791b?cid=774b29d4f13844c495f206cafdad9c86",
        "track_number": 12,
        "type": "track",
        "uri": "spotify:track:1MaxCaHwLDYS0uOQLjUry0"
      }
    ],
    "limit": 50,
    "next": null,
    "offset": 0,
    "previous": null,
    "total": 12
  },
  "type": "album",
  "uri": "spotify:album:2wart5Qjnvx1fd7LPdQxgJ"
}"""

@Language("JSON")
val ALBUM_TRACKS = """{
  "href": "https://api.spotify.com/v1/albums/2wart5Qjnvx1fd7LPdQxgJ/tracks?offset=0&limit=2&market=FR",
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
      "duration_ms": 262947,
      "explicit": false,
      "external_urls": {
        "spotify": "https://open.spotify.com/track/2daZovie6pc2ZK7StayD1K"
      },
      "href": "https://api.spotify.com/v1/tracks/2daZovie6pc2ZK7StayD1K",
      "id": "2daZovie6pc2ZK7StayD1K",
      "is_local": false,
      "is_playable": true,
      "name": "Dead Inside",
      "preview_url": "https://p.scdn.co/mp3-preview/f3dd36859bbae1f625386ec902fdf2d6682d2bbb?cid=774b29d4f13844c495f206cafdad9c86",
      "track_number": 1,
      "type": "track",
      "uri": "spotify:track:2daZovie6pc2ZK7StayD1K"
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
      "duration_ms": 21148,
      "explicit": false,
      "external_urls": {
        "spotify": "https://open.spotify.com/track/2E5tWJSusHxpaksg1yfsIu"
      },
      "href": "https://api.spotify.com/v1/tracks/2E5tWJSusHxpaksg1yfsIu",
      "id": "2E5tWJSusHxpaksg1yfsIu",
      "is_local": false,
      "is_playable": true,
      "name": "(Drill Sergeant)",
      "preview_url": "https://p.scdn.co/mp3-preview/c87f359113346a5bf1f9a523d2e6af1a272e2385?cid=774b29d4f13844c495f206cafdad9c86",
      "track_number": 2,
      "type": "track",
      "uri": "spotify:track:2E5tWJSusHxpaksg1yfsIu"
    }
  ],
  "limit": 2,
  "next": "https://api.spotify.com/v1/albums/2wart5Qjnvx1fd7LPdQxgJ/tracks?offset=2&limit=2&market=FR",
  "offset": 0,
  "previous": null,
  "total": 12
}"""