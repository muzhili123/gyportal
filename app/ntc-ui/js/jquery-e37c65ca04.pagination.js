var _typeof="function"==typeof Symbol&&"symbol"==typeof Symbol.iterator?function(t){return typeof t}:function(t){return t&&"function"==typeof Symbol&&t.constructor===Symbol&&t!==Symbol.prototype?"symbol":typeof t};!function(n){"function"!=typeof define||!define.amd&&!define.cmd||jQuery?"object"===("undefined"==typeof module?"undefined":_typeof(module))&&module.exports?module.exports=function(t,e){return void 0===e&&(e="undefined"!=typeof window?require("jquery"):require("jquery")(t)),n(e),e}:n(jQuery):define(["jquery"],n)}(function(s){var a={totalData:0,showData:0,pageCount:9,current:1,prevCls:"prev",nextCls:"next",prevContent:"<",nextContent:">",activeCls:"active",coping:!1,isHide:!1,homePage:"",endPage:"",keepShowPN:!1,mode:"unfixed",count:4,jump:!1,jumpIptCls:"jump-ipt",jumpBtnCls:"jump-btn",jumpBtn:"跳转",callback:function(){}},i=function(t,e){var o,p=e,i=s(document),r=s(t);this.setPageCount=function(t){return p.pageCount=t},this.getPageCount=function(){return p.totalData&&p.showData?Math.ceil(parseInt(p.totalData)/p.showData):p.pageCount},this.getCurrent=function(){return o},this.filling=function(t){var e="";o=parseInt(t)||parseInt(p.current);var n=this.getPageCount();switch(p.mode){case"fixed":if(e+='<a href="javascript:;" class="'+p.prevCls+'">'+p.prevContent+"</a>",p.coping)e+='<a href="javascript:;" data-page="1">'+(s=p.coping&&p.homePage?p.homePage:"1")+"</a>";for(var a=o>p.count-1?o+p.count-1>n?o-(p.count-(n-o)):o-2:1,i=o+p.count-1>n?n:a+p.count;a<=i;a++)e+=a!=o?'<a href="javascript:;" data-page="'+a+'">'+a+"</a>":'<span class="'+p.activeCls+'">'+a+"</span>";if(p.coping)e+='<a href="javascript:;" data-page="'+n+'">'+(p.coping&&p.endPage?p.endPage:n)+"</a>";e+='<a href="javascript:;" class="'+p.nextCls+'">'+p.nextContent+"</a>";break;case"unfixed":if(p.keepShowPN||1<o?e+='<a href="javascript:;" class="'+p.prevCls+'">'+p.prevContent+"</a>":0==p.keepShowPN&&r.find("."+p.prevCls)&&r.find("."+p.prevCls).remove(),o>=p.count+2&&1!=o&&n!=p.count){var s=p.coping&&p.homePage?p.homePage:"1";e+=p.coping?'<a href="javascript:;" data-page="1">'+s+"</a><span>...</span>":""}for(a=o-p.count<=1?1:o-p.count,i=o+p.count>=n?n:o+p.count;a<=i;a++)a<=n&&1<=a&&(e+=a!=o?'<a href="javascript:;" data-page="'+a+'">'+a+"</a>":'<span class="'+p.activeCls+'">'+a+"</span>");if(o+p.count<n&&1<=o&&n>p.count){i=p.coping&&p.endPage?p.endPage:n;e+=p.coping?'<span>...</span><a href="javascript:;" data-page="'+n+'">'+i+"</a>":""}p.keepShowPN||o<n?e+='<a href="javascript:;" class="'+p.nextCls+'">'+p.nextContent+"</a>":0==p.keepShowPN&&r.find("."+p.nextCls)&&r.find("."+p.nextCls).remove()}e+=p.jump?'<input type="text" class="'+p.jumpIptCls+'"><a href="javascript:;" class="'+p.jumpBtnCls+'">'+p.jumpBtn+"</a>":"",r.empty().html(e)},this.eventBind=function(){var n=this,a=n.getPageCount(),t=1;r.off().on("click","a",function(){if(s(this).hasClass(p.nextCls)){if(r.find("."+p.activeCls).text()>=a)return s(this).addClass("disabled"),!1;t=parseInt(r.find("."+p.activeCls).text())+1}else if(s(this).hasClass(p.prevCls)){if(r.find("."+p.activeCls).text()<=1)return s(this).addClass("disabled"),!1;t=parseInt(r.find("."+p.activeCls).text())-1}else if(s(this).hasClass(p.jumpBtnCls)){if(""===r.find("."+p.jumpIptCls).val())return;t=parseInt(r.find("."+p.jumpIptCls).val())}else t=parseInt(s(this).data("page"));n.filling(t),"function"==typeof p.callback&&p.callback(n)}),r.on("input propertychange","."+p.jumpIptCls,function(){var t=s(this),e=t.val(),n=/[^\d]/g;n.test(e)&&t.val(e.replace(n,"")),parseInt(e)>a&&t.val(a),0===parseInt(e)&&t.val(1)}),i.keydown(function(t){if(13==t.keyCode&&r.find("."+p.jumpIptCls).val()){var e=parseInt(r.find("."+p.jumpIptCls).val());n.filling(e),"function"==typeof p.callback&&p.callback(n)}})},this.init=function(){this.filling(p.current),this.eventBind(),p.isHide&&"1"==this.getPageCount()||"0"==this.getPageCount()?r.hide():r.show()},this.init()};s.fn.pagination=function(t,e){"function"==typeof t?(e=t,t={}):(t=t||{},e=e||function(){});var n=s.extend({},a,t);return this.each(function(){var t=new i(this,n);e(t)})}});