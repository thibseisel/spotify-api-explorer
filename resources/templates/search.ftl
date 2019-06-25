<#include "head.ftl"/>
<h3 class="mdc-typography--headline3">Search results for "${query}"</h3>
<ul>
    <#list results.items as result>
        <li>
            <a href="/artists/${result.id}">${result.name}</a>
        </li>
    </#list>
</ul>
<#include "foot.ftl"/>