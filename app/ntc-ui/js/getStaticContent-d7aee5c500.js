var getArticle=function(t){return axios.get($baseUrl+"/news/getNewsById",{params:{id:t}})},filters=function(t){return t=t.replace(/\\n/g,"")};$(function(){var t=$static_article_id;getArticle(t).then(function(t){var a=t.data.data,e=filters(a.content),n="";if(a.attachments&&a.attachments.length){var r=a.attachments;n='<div class="ql-editor"><br>',r.map(function(t,a){n+='<p><strong><a href="'+t.url+'" download="'+t.name+'">'+t.name+"</a></strong></p>"}),e+=n+="</div>"}$("#article>pre").append(e)})});