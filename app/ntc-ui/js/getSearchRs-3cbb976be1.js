var pageSize=10,pageNum=1,count=0,sortBy="",text="",lang=localStorage.getItem("lang")||"",render=function(e,a){$(e).html(""),$(e).append(a)},spliceContent=function(e,a){return e.length>a?e.slice(0,a)+"...":e},filters=function(e){if(e)return e=(e=(e=e.replace(/<[^>]+>|&[^>]+;/g,"").trim()).replace(/\s{2,}/g,"")).replace(/\\n/g,"")},getSearchRsData=function(e,a){return axios.get($baseUrl+"/news/matchNews",{params:{words:e,pageSize:pageSize,pageNum:pageNum,sort:a}})},getSearchRs=function r(s,i){getSearchRsData(s,i).then(function(e){var a=e.data.data.content,t=10<e.data.data.totalElements?10*Math.floor(e.data.data.totalElements/10):e.data.data.totalElements;count||(count=e.data.data.totalPages,$(".pagination").pagination({pageCount:count,coping:!1,prevContent:"上一页",nextContent:"下一页",callback:function(e){pageNum=e.getCurrent(),r(s,i),$("html, body").animate({scrollTop:0},500)}}));var n="";a.forEach(function(e){var a="";switch(e.type){case"技术专题":a=("en"===lang?"en":"")+"/page/research/subject-article/?id="+e.id;break;case"中心动态":a=("en"===lang?"en":"")+"/page/news/news-article/?id="+e.id;break;case"图片新闻":a=("en"===lang?"en":"")+"/page/news/pic-news-article/?id="+e.id;break;case"通知公告":a=("en"===lang?"en":"")+"/page/news/notice-article/?id="+e.id;break;case"行业资讯":a=("en"===lang?"en":"")+"/page/news/infos-article/?id="+e.id;break;default:a=e.url}n+='<div class="info-item">\n        <a href="'+a+'">\n          <h2 class="title">'+e.title+'</h2>\n          <h5 class="text">'+e.tabloid+"</h5>\n          <span>国家远程医疗中心 - "+e.type+"&nbsp;&nbsp;&nbsp;&nbsp;</span>\n          <span>"+e.update_date+"</span>\n        </a>\n      </div>"}),render("#rs-list",n),render("#rs-text","找到约 "+t+" 条搜索结果")})},parseSearch=function(e){if(e){var a=(e=e.substr(1)).split("&"),t={},n=[];return a.map(function(e){void 0!==(n=e.split("="))[0]&&(t[n[0]]=n[1])}),t}};$(function(){$(".search-input").keypress(function(e){13===e.which&&getSearchRs($(".search-input").val())}),text=decodeURI(parseSearch(window.location.search).words),$(".search-input").val(text),getSearchRs(text),$("#sortBy").on("change",function(){sortBy=$(this).val(),getSearchRs(text,sortBy)})});