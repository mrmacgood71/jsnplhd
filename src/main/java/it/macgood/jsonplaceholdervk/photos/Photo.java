package it.macgood.jsonplaceholdervk.photos;

public record Photo(
        Integer albumId,
        Integer id,
        String title,
        String url,
        String thumbnailUrl
) {
}

//"albumId": 100,
//        "id": 4999,
//        "title": "in voluptate sit officia non nesciunt quis",
//        "url": "https://via.placeholder.com/600/1b9d08",
//        "thumbnailUrl": "https://via.placeholder.com/150/1b9d08"
