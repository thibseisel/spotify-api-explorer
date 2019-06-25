<#include "head.ftl"/>
<h3 class="mdc-typography--headline3">Albums of artist</h3>
<ul>
    <#list albums.items as album>
        <li>
            <a href="/albums/${album.id}">${album.name}</a>
        </li>
    </#list>
</ul>
<#include "foot.ftl"/>