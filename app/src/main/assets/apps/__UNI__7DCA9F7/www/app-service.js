(this["webpackJsonp"]=this["webpackJsonp"]||[]).push([["app-service"],{"0c03":function(e,t,n){"use strict";n.d(t,"b",(function(){return r})),n.d(t,"c",(function(){return o})),n.d(t,"a",(function(){}));var r=function(){var e=this.$createElement,t=this._self._c||e;return t("div",{staticClass:this._$s(0,"sc","ephemeral"),attrs:{_i:0}},[t("button",{attrs:{_i:1},on:{click:this.showWebview}})])},o=[]},"0de9":function(e,t,n){"use strict";function r(e){var t=Object.prototype.toString.call(e);return t.substring(8,t.length-1)}function o(){return"string"===typeof __channelId__&&__channelId__}function i(e,t){switch(r(t)){case"Function":return"function() { [native code] }";default:return t}}function a(e){for(var t=arguments.length,n=new Array(t>1?t-1:0),r=1;r<t;r++)n[r-1]=arguments[r];console[e].apply(console,n)}function u(){for(var e=arguments.length,t=new Array(e),n=0;n<e;n++)t[n]=arguments[n];var a=t.shift();if(o())return t.push(t.pop().replace("at ","uni-app:///")),console[a].apply(console,t);var u=t.map((function(e){var t=Object.prototype.toString.call(e).toLowerCase();if("[object object]"===t||"[object array]"===t)try{e="---BEGIN:JSON---"+JSON.stringify(e,i)+"---END:JSON---"}catch(o){e=t}else if(null===e)e="---NULL---";else if(void 0===e)e="---UNDEFINED---";else{var n=r(e).toUpperCase();e="NUMBER"===n||"BOOLEAN"===n?"---BEGIN:"+n+"---"+e+"---END:"+n+"---":String(e)}return e})),c="";if(u.length>1){var f=u.pop();c=u.join("---COMMA---"),0===f.indexOf(" at ")?c+=f:c+="---COMMA---"+f}else c=u[0];console[a](c)}n.r(t),n.d(t,"log",(function(){return a})),n.d(t,"default",(function(){return u}))},"353d":function(e,t,n){"use strict";n.r(t);var r=n("c587"),o=n.n(r);for(var i in r)["default"].indexOf(i)<0&&function(e){n.d(t,e,(function(){return r[e]}))}(i);t["default"]=o.a},"48cd":function(e,t,n){"use strict";n.r(t);var r=n("0c03"),o=n("b091");for(var i in o)["default"].indexOf(i)<0&&function(e){n.d(t,e,(function(){return o[e]}))}(i);var a=n("f0c5"),u=Object(a["a"])(o["default"],r["b"],r["c"],!1,null,"461dc767",null,!1,r["a"],void 0);t["default"]=u.exports},"4ea4":function(e,t){e.exports=function(e){return e&&e.__esModule?e:{default:e}},e.exports.__esModule=!0,e.exports["default"]=e.exports},"51f9":function(e,t,n){"use strict";n.r(t);var r=n("f0e5"),o=n.n(r);for(var i in r)["default"].indexOf(i)<0&&function(e){n.d(t,e,(function(){return r[e]}))}(i);t["default"]=o.a},7037:function(e,t){function n(t){return e.exports=n="function"==typeof Symbol&&"symbol"==typeof Symbol.iterator?function(e){return typeof e}:function(e){return e&&"function"==typeof Symbol&&e.constructor===Symbol&&e!==Symbol.prototype?"symbol":typeof e},e.exports.__esModule=!0,e.exports["default"]=e.exports,n(t)}e.exports=n,e.exports.__esModule=!0,e.exports["default"]=e.exports},"89a6":function(e,t,n){"use strict";n.r(t);var r=n("ee2e"),o=n("51f9");for(var i in o)["default"].indexOf(i)<0&&function(e){n.d(t,e,(function(){return o[e]}))}(i);var a=n("f0c5"),u=Object(a["a"])(o["default"],r["b"],r["c"],!1,null,null,null,!1,r["a"],void 0);t["default"]=u.exports},"8bbf":function(e,t){e.exports=Vue},9523:function(e,t,n){var r=n("a395");e.exports=function(e,t,n){return t=r(t),t in e?Object.defineProperty(e,t,{value:n,enumerable:!0,configurable:!0,writable:!0}):e[t]=n,e},e.exports.__esModule=!0,e.exports["default"]=e.exports},a395:function(e,t,n){var r=n("7037")["default"],o=n("e50d");e.exports=function(e){var t=o(e,"string");return"symbol"===r(t)?t:String(t)},e.exports.__esModule=!0,e.exports["default"]=e.exports},b091:function(e,t,n){"use strict";n.r(t);var r=n("d1ec"),o=n.n(r);for(var i in r)["default"].indexOf(i)<0&&function(e){n.d(t,e,(function(){return r[e]}))}(i);t["default"]=o.a},b99c:function(e,t,n){var r=n("7037");uni.addInterceptor({returnValue:function(e){return!e||"object"!==r(e)&&"function"!==typeof e||"function"!==typeof e.then?e:new Promise((function(t,n){e.then((function(e){return e[0]?n(e[0]):t(e[1])}))}))}})},c587:function(e,t,n){"use strict";(function(e){Object.defineProperty(t,"__esModule",{value:!0}),t.default=void 0;var n={onLaunch:function(){e("log","App Launch"," at App.vue:4")},onShow:function(){e("log","App Show"," at App.vue:7")},onHide:function(){e("log","App Hide"," at App.vue:10")},methods:{}};t.default=n}).call(this,n("0de9")["default"])},d1ec:function(e,t,n){"use strict";var r=n("4ea4");Object.defineProperty(t,"__esModule",{value:!0}),t.default=void 0;var o=r(n("8bbf")),i=o.default.extend({components:{},data:function(){return{}},computed:{},methods:{showWebview:function(){}},watch:{},onLoad:function(){},onReady:function(){},onShow:function(){},onHide:function(){},onUnload:function(){}});t.default=i},dd4a:function(e,t,n){"use strict";var r=n("4ea4"),o=r(n("9523"));n("f637");var i=r(n("f9da")),a=r(n("8bbf"));function u(e,t){var n=Object.keys(e);if(Object.getOwnPropertySymbols){var r=Object.getOwnPropertySymbols(e);t&&(r=r.filter((function(t){return Object.getOwnPropertyDescriptor(e,t).enumerable}))),n.push.apply(n,r)}return n}n("b99c"),a.default.config.productionTip=!1,i.default.mpType="app";var c=new a.default(function(e){for(var t=1;t<arguments.length;t++){var n=null!=arguments[t]?arguments[t]:{};t%2?u(Object(n),!0).forEach((function(t){(0,o.default)(e,t,n[t])})):Object.getOwnPropertyDescriptors?Object.defineProperties(e,Object.getOwnPropertyDescriptors(n)):u(Object(n)).forEach((function(t){Object.defineProperty(e,t,Object.getOwnPropertyDescriptor(n,t))}))}return e}({},i.default));c.$mount()},e50d:function(e,t,n){var r=n("7037")["default"];e.exports=function(e,t){if("object"!==r(e)||null===e)return e;var n=e[Symbol.toPrimitive];if(void 0!==n){var o=n.call(e,t||"default");if("object"!==r(o))return o;throw new TypeError("@@toPrimitive must return a primitive value.")}return("string"===t?String:Number)(e)},e.exports.__esModule=!0,e.exports["default"]=e.exports},ee2e:function(e,t,n){"use strict";n.d(t,"b",(function(){return r})),n.d(t,"c",(function(){return o})),n.d(t,"a",(function(){}));var r=function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("view",{staticClass:e._$s(0,"sc","content"),attrs:{_i:0}},[n("button",{attrs:{_i:1},on:{click:e.handleStartCamera}}),n("button",{attrs:{_i:2},on:{click:e.handleSaveImage}}),n("button",{attrs:{_i:3},on:{click:e.handleNotify}}),n("view",[n("text"),e._v(e._$s(4,"t1-0",e._s(e.imageFilePath)))]),n("view",[n("text"),e._v(e._$s(6,"t1-0",e._s(this.savedFilePath)))]),e._l(e._$s(8,"f",{forItems:e.urls}),(function(t,r,o,i){return n("view",{key:e._$s(8,"f",{forIndex:o,key:"url"})},[n("image",{attrs:{src:e._$s("9-"+i,"a-src",t),_i:"9-"+i}})])})),n("view",[e._v(e._$s(10,"t0-0",e._s(e.urls[0])))]),n("view",[e._v(e._$s(11,"t0-0",e._s(e.error)))])],2)},o=[]},f0c5:function(e,t,n){"use strict";function r(e,t,n,r,o,i,a,u,c,f){var s,l="function"===typeof e?e.options:e;if(c){l.components||(l.components={});var d=Object.prototype.hasOwnProperty;for(var p in c)d.call(c,p)&&!d.call(l.components,p)&&(l.components[p]=c[p])}if(f&&("function"===typeof f.beforeCreate&&(f.beforeCreate=[f.beforeCreate]),(f.beforeCreate||(f.beforeCreate=[])).unshift((function(){this[f.__module]=this})),(l.mixins||(l.mixins=[])).push(f)),t&&(l.render=t,l.staticRenderFns=n,l._compiled=!0),r&&(l.functional=!0),i&&(l._scopeId="data-v-"+i),a?(s=function(e){e=e||this.$vnode&&this.$vnode.ssrContext||this.parent&&this.parent.$vnode&&this.parent.$vnode.ssrContext,e||"undefined"===typeof __VUE_SSR_CONTEXT__||(e=__VUE_SSR_CONTEXT__),o&&o.call(this,e),e&&e._registeredComponents&&e._registeredComponents.add(a)},l._ssrRegister=s):o&&(s=u?function(){o.call(this,this.$root.$options.shadowRoot)}:o),s)if(l.functional){l._injectStyles=s;var v=l.render;l.render=function(e,t){return s.call(t),v(e,t)}}else{var y=l.beforeCreate;l.beforeCreate=y?[].concat(y,s):[s]}return{exports:e,options:l}}n.d(t,"a",(function(){return r}))},f0e5:function(e,t,n){"use strict";Object.defineProperty(t,"__esModule",{value:!0}),t.default=void 0;var r={data:function(){return{title:"index",imageFilePath:"",savedFilePath:"",urls:[],error:"",notifyTimer:null}},onLoad:function(){},methods:{handleStartCamera:function(){var e=this;try{var t=uni.requireNativePlugin("CameraX");t.capture({emojiPath:"/storage/emulated/0/Android/data/com.android.simple/apps/__UNI__7DCA9F7/doc/uniapp_save/"},(function(t){try{e.urls=[t.imageFilePath]}catch(n){e.error=n.message}}))}catch(n){}},handleSaveImage:function(){var e=this;uni.chooseImage({sizeType:["original","compressed"],sourceType:["album","camera"],success:function(t){var n=t.tempFilePaths;t.tempFiles;e.imageFilePath=n[0],uni.saveFile({tempFilePath:e.imageFilePath,success:function(t){e.savedFilePath=t.savedFilePath}})},fail:function(e){}})},handleNotify:function(){if(null!==this.notifyTimer)return clearInterval(this.notifyTimer),this.notifyTimer=null;var e=uni.requireNativePlugin("NotifyModule");this.notifyTimer=setInterval((function(){e.transmitNotify()}),5e3)}}};t.default=r},f637:function(e,t,n){if("undefined"===typeof Promise||Promise.prototype.finally||(Promise.prototype.finally=function(e){var t=this.constructor;return this.then((function(n){return t.resolve(e()).then((function(){return n}))}),(function(n){return t.resolve(e()).then((function(){throw n}))}))}),"undefined"!==typeof uni&&uni&&uni.requireGlobal){var r=uni.requireGlobal();ArrayBuffer=r.ArrayBuffer,Int8Array=r.Int8Array,Uint8Array=r.Uint8Array,Uint8ClampedArray=r.Uint8ClampedArray,Int16Array=r.Int16Array,Uint16Array=r.Uint16Array,Int32Array=r.Int32Array,Uint32Array=r.Uint32Array,Float32Array=r.Float32Array,Float64Array=r.Float64Array,BigInt64Array=r.BigInt64Array,BigUint64Array=r.BigUint64Array}uni.restoreGlobal&&uni.restoreGlobal(weex,plus,setTimeout,clearTimeout,setInterval,clearInterval),__definePage("pages/index/index",(function(){return Vue.extend(n("89a6").default)})),__definePage("pages/ephemeral/ephemeral",(function(){return Vue.extend(n("48cd").default)}))},f9da:function(e,t,n){"use strict";n.r(t);var r=n("353d");for(var o in r)["default"].indexOf(o)<0&&function(e){n.d(t,e,(function(){return r[e]}))}(o);var i=n("f0c5"),a=Object(i["a"])(r["default"],void 0,void 0,!1,null,null,null,!1,void 0,void 0);t["default"]=a.exports}},[["dd4a","app-config"]]]);