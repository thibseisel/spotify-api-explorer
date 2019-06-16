<html>
    <#include "head.ftl"/>
    <body class="mdc-typography">
        <h6 class="mdc-typography--headline6">Spotify Web API Explorer</h1>
        <form action="/search">
            <select required>
                <option value="artist" selected>Artist</option>
                <option value="album">Album</option>
                <option value="track">Track</option>
            </select>
            <input type="text" name="q" required />
            <input type="submit" value="Search" />
        </form>
        <#include "foot.ftl"/>
    </body>
</html>
