var pageSize=20,lang=localStorage.getItem("lang")||"",pageNum=1,count=0,filters=function(e){if(e)return e=(e=(e=e.replace(/<[^>]+>|&[^>]+;/g,"").trim()).replace(/\s{2,}/g,"")).replace(/\\n/g,"")},getNewsList=function(e){return axios.get($baseUrl+"/news/findAllByType",{params:{pageSize:pageSize,pageNum:pageNum,type:e}})},render=function(e,n){$(e).html(""),$(e).append(n)},getList=function a(i,s){getNewsList(s).then(function(e){var n=e.data.data.content;count||(count=e.data.data.totalPages,$(".pagination").pagination({pageCount:count,coping:!1,prevContent:"上一页",nextContent:"下一页",callback:function(e){pageNum=e.getCurrent(),a(i,s),$("html, body").animate({scrollTop:0},500)}}));var t="";n.forEach(function(e){e.image?t+='<div class="col-25">\n          <a href="'+("en"===lang?"en":"")+"/page/news/"+i+"-article/?id="+e.id+'">\n            <div><img src="http://www.htcc.org.cn'+e.image+'" height="180" width="280"/></div>\n            <h5>'+e.title+'</h5>\n            <div class="center-item-bottom">\n              <span>'+e.update_date+'</span>\n              <span class="news-type">'+s+"</span>\n            </div>\n          </a>\n        </div>":t+='<div class="col-25">\n          <a href="'+("en"===lang?"en":"")+"/page/news/"+i+"-article/?id="+e.id+'">\n          <div style="height: 0px"></div>\n            <h5>'+e.title+"</h5>\n            <p>"+filters(e.tabloid)+'</p>\n            <div class="center-item-bottom">\n              <span>'+e.update_date+'</span>\n              <span class="news-type">'+s+"</span>\n            </div>\n          </a>\n        </div>"}),render("#content-list",t),"notice"===i&&$(".col-25>a>div:first-of-type").addClass("mask-image")})};$(function(){var e=window.location.pathname.split("/")[3].split(".")[0]||"",n="";switch(e){case"infos":n="行业资讯";break;case"news":n="中心动态";break;case"notice":n="通知公告";break;case"pic-news":n="图片新闻"}getList(e,n)});