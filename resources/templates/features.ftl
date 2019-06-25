<#include "head.ftl"/>
<h3 class="mdc-typography--headline3">Track Audio Features</h3>
<#if features??>
    <table>
        <tr>
            <th>Key</th>
            <td>${(features.key)!"Unknown"}</td>
        </tr>
        <tr>
            <th>Musical mode</th>
            <td>${features.mode}</td>
        </tr>
        <tr>
            <th>Tempo</th>
            <td>${features.tempo}</td>
        </tr>
        <tr>
            <th>Time signature</th>
            <td>${features.signature}</td>
        </tr>
        <tr>
            <th>Loudness</th>
            <td>${features.loudness}</td>
        </tr>
        <tr>
            <th>Accoustiness</th>
            <td>${features.accousticness}</td>
        </tr>
        <tr>
            <th>Danceability</th>
            <td>${features.danceability}</td>
        </tr>
        <tr>
            <th>Energy</th>
            <td>${features.energy}</td>
        </tr>
        <tr>
            <th>Liveness</th>
            <td>${features.liveness}</td>
        </tr>
        <tr>
            <th>Speechiness</th>
            <td>${features.speechiness}</td>
        </tr>
        <tr>
            <th>Valence</th>
            <td>${features.valence}</td>
        </tr>
    </table>
<#else>
    <p>Cannot get audio features of this track.</p>
</#if>
<#include "foot.ftl"/>