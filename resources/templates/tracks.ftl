<#include "head.ftl"/>
<h3 class="mdc-typography--headline3">Tracks from album</h3>
<ul>
    <#list tracks as track>
        <li>
            <a href="/tracks/${track.id}">${track.name}</a>
        </li>
    </#list>
</ul>
<#include "foot.ftl"/>