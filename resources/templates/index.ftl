<#include "head.ftl"/>
<h3 class="mdc-typography--headline3">Spotify Web API Explorer</h3>
<form action="/search">
    <select name="type" required>
        <option value="artist" selected>Artist</option>
        <option value="album">Album</option>
        <option value="track">Track</option>
    </select>
    <input type="text" name="q" required />
    <input type="submit" value="Search" />
</form>
<#include "foot.ftl"/>
