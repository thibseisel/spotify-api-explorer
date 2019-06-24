<#include "head.ftl"/>
<h6 class="mdc-typography--headline6">Search results for "${query}"</h6>
<ul>
    <#list results.items as result>
        <li>
            <a href="/artists/${result.id}">${result.name}</a>
        </li>
    </#list>
</ul>
<#include "foot.ftl"/>