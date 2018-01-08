(function(){'use strict';
var e="object"===typeof __ScalaJSEnv&&__ScalaJSEnv?__ScalaJSEnv:{},k="object"===typeof e.global&&e.global?e.global:"object"===typeof global&&global&&global.Object===Object?global:this;e.global=k;e.exportsNamespace="object"===typeof e.exportsNamespace&&e.exportsNamespace?e.exportsNamespace:k;k.Object.freeze(e);var m={envInfo:e,semantics:{asInstanceOfs:2,arrayIndexOutOfBounds:2,moduleInit:2,strictFloats:!1,productionMode:!0},assumingES6:!1,linkerVersion:"0.6.21",globalThis:this};k.Object.freeze(m);
k.Object.freeze(m.semantics);var n=k.Math.imul||function(a,b){var c=a&65535,d=b&65535;return c*d+((a>>>16&65535)*d+c*(b>>>16&65535)<<16>>>0)|0},p=k.Math.clz32||function(a){if(0===a)return 32;var b=1;0===(a&4294901760)&&(a<<=16,b+=16);0===(a&4278190080)&&(a<<=8,b+=8);0===(a&4026531840)&&(a<<=4,b+=4);0===(a&3221225472)&&(a<<=2,b+=2);return b+(a>>31)},q=0,r=k.WeakMap?new k.WeakMap:null;function u(a){return function(b,c){return!(!b||!b.$classData||b.$classData.k!==c||b.$classData.i!==a)}}
function aa(a){for(var b in a)return b}function v(a,b,c){var d=new a.x(b[c]);if(c<b.length-1){a=a.l;c+=1;for(var f=d.B,g=0;g<f.length;g++)f[g]=v(a,b,c)}return d}function ba(a){switch(typeof a){case "string":return w(x);case "number":var b=a|0;return b===a?y(b)?w(z):A(b)?w(B):w(C):"number"===typeof a?w(D):w(E);case "boolean":return w(F);case "undefined":return w(ca);default:return null===a?a.V():a&&a.$classData&&a.$classData.f.z?w(da):a&&a.$classData?w(a.$classData):null}}
function ea(a){switch(typeof a){case "string":fa||(fa=(new G).d());for(var b=0,c=1,d=-1+(a.length|0)|0;0<=d;)b=b+n(65535&(a.charCodeAt(d)|0),c)|0,c=n(31,c),d=-1+d|0;return b;case "number":H||(H=(new I).d());b=H;c=a|0;if(c===a&&-Infinity!==1/a)b=c;else{if(b.g)b.D[0]=a,b=J(b.s[b.F]|0,b.s[b.E]|0);else{if(a!==a)b=!1,a=2047,c=+k.Math.pow(2,51);else if(Infinity===a||-Infinity===a)b=0>a,a=2047,c=0;else if(0===a)b=-Infinity===1/a,c=a=0;else if(d=(b=0>a)?-a:a,d>=+k.Math.pow(2,-1022)){a=+k.Math.pow(2,52);var c=
+k.Math.log(d)/.6931471805599453,c=+k.Math.floor(c)|0,c=1023>c?c:1023,f=+k.Math.pow(2,c);f>d&&(c=-1+c|0,f/=2);f=d/f*a;d=+k.Math.floor(f);f-=d;d=.5>f?d:.5<f?1+d:0!==d%2?1+d:d;2<=d/a&&(c=1+c|0,d=1);1023<c?(c=2047,d=0):(c=1023+c|0,d-=a);a=c;c=d}else a=d/+k.Math.pow(2,-1074),c=+k.Math.floor(a),d=a-c,a=0,c=.5>d?c:.5<d?1+c:0!==c%2?1+c:c;c=+c;b=J(c|0,(b?-2147483648:0)|(a|0)<<20|c/4294967296|0)}b=b.t^b.r}return b;case "boolean":return a?1231:1237;case "undefined":return 0;default:return a&&a.$classData||
null===a?a.y():null===r?42:ha(a)}}function ia(a,b){var c=k.Object.getPrototypeOf,d=k.Object.getOwnPropertyDescriptor;for(a=c(a);null!==a;){var f=d(a,b);if(void 0!==f)return f;a=c(a)}}function ja(a,b,c){a=ia(a,c);if(void 0!==a)return c=a.get,void 0!==c?c.call(b):a.value}function ka(a,b,c,d){a=ia(a,c);if(void 0!==a&&(a=a.set,void 0!==a)){a.call(b,d);return}throw new k.TypeError("super has no setter '"+c+"'.");}
var ha=null!==r?function(a){switch(typeof a){case "string":case "number":case "boolean":case "undefined":return ea(a);default:if(null===a)return 0;var b=r.get(a);void 0===b&&(q=b=q+1|0,r.set(a,b));return b}}:function(a){if(a&&a.$classData){var b=a.$idHashCode$0;if(void 0!==b)return b;if(k.Object.isSealed(a))return 42;q=b=q+1|0;return a.$idHashCode$0=b}return null===a?0:ea(a)};function y(a){return"number"===typeof a&&a<<24>>24===a&&1/a!==1/-0}
function A(a){return"number"===typeof a&&a<<16>>16===a&&1/a!==1/-0}function K(){this.u=this.x=void 0;this.i=this.l=this.f=null;this.k=0;this.C=null;this.q="";this.b=this.o=this.p=void 0;this.name="";this.isRawJSType=this.isArrayClass=this.isInterface=this.isPrimitive=!1;this.isInstance=void 0}function L(a,b,c){var d=new K;d.f={};d.l=null;d.C=a;d.q=b;d.b=function(){return!1};d.name=c;d.isPrimitive=!0;d.isInstance=function(){return!1};return d}
function M(a,b,c,d,f,g,l){var h=new K,t=aa(a);g=g||function(a){return!!(a&&a.$classData&&a.$classData.f[t])};l=l||function(a,b){return!!(a&&a.$classData&&a.$classData.k===b&&a.$classData.i.f[t])};h.u=f;h.f=c;h.q="L"+b+";";h.b=l;h.name=b;h.isInterface=!1;h.isRawJSType=!!d;h.isInstance=g;return h}
function la(a){function b(a){if("number"===typeof a){this.B=Array(a);for(var b=0;b<a;b++)this.B[b]=f}else this.B=a}var c=new K,d=a.C,f="longZero"==d?N().v:d;b.prototype=new O;b.prototype.constructor=b;b.prototype.$classData=c;var d="["+a.q,g=a.i||a,l=a.k+1;c.x=b;c.u=ma;c.f={a:1,X:1,c:1};c.l=a;c.i=g;c.k=l;c.C=null;c.q=d;c.p=void 0;c.o=void 0;c.b=void 0;c.name=d;c.isPrimitive=!1;c.isInterface=!1;c.isArrayClass=!0;c.isInstance=function(a){return g.b(a,l)};return c}
function w(a){if(!a.p){var b=new P;b.m=a;a.p=b}return a.p}function na(a){a.o||(a.o=la(a));return a.o}K.prototype.getFakeInstance=function(){return this===x?"some string":this===F?!1:this===z||this===B||this===C||this===D||this===E?0:this===da?N().v:this===ca?void 0:{$classData:this}};K.prototype.getSuperclass=function(){return this.u?w(this.u):null};K.prototype.getComponentType=function(){return this.l?w(this.l):null};
K.prototype.newArrayOfThisClass=function(a){for(var b=this,c=0;c<a.length;c++)b=na(b);return v(b,a,0)};var oa=L(!1,"Z","boolean"),pa=L(0,"C","char"),qa=L(0,"B","byte"),ra=L(0,"S","short"),sa=L(0,"I","int"),ta=L("longZero","J","long"),ua=L(0,"F","float"),va=L(0,"D","double");oa.b=u(oa);pa.b=u(pa);qa.b=u(qa);ra.b=u(ra);sa.b=u(sa);ta.b=u(ta);ua.b=u(ua);va.b=u(va);function Q(){}function O(){}O.prototype=Q.prototype;Q.prototype.d=function(){return this};Q.prototype.A=function(){var a=ba(this).m.name,b=(+(this.y()>>>0)).toString(16);return a+"@"+b};Q.prototype.y=function(){return ha(this)};Q.prototype.toString=function(){return this.A()};var ma=M({a:0},"java.lang.Object",{a:1},void 0,void 0,function(a){return null!==a},function(a,b){if(a=a&&a.$classData){var c=a.k||0;return!(c<b)&&(c>b||!a.i.isPrimitive)}return!1});Q.prototype.$classData=ma;function R(){}
R.prototype=new O;R.prototype.constructor=R;R.prototype.d=function(){return this};R.prototype.$classData=M({G:0},"ExcelerJS$",{G:1,a:1});var S=void 0;function wa(){S||(S=(new R).d());return S}function T(){this.n=null}T.prototype=new O;T.prototype.constructor=T;T.prototype.d=function(){U=this;this.n=k.jQuery;return this};T.prototype.$classData=M({H:0},"org.scalajs.jquery.package$",{H:1,a:1});var U=void 0;function V(){U||(U=(new T).d());return U}function P(){this.m=null}P.prototype=new O;
P.prototype.constructor=P;P.prototype.A=function(){return(this.m.isInterface?"interface ":this.m.isPrimitive?"":"class ")+this.m.name};P.prototype.$classData=M({L:0},"java.lang.Class",{L:1,a:1});function I(){this.g=!1;this.D=this.s=this.j=null;this.w=!1;this.F=this.E=0}I.prototype=new O;I.prototype.constructor=I;
I.prototype.d=function(){H=this;this.j=(this.g=!!(k.ArrayBuffer&&k.Int32Array&&k.Float32Array&&k.Float64Array))?new k.ArrayBuffer(8):null;this.s=this.g?new k.Int32Array(this.j,0,2):null;this.g&&new k.Float32Array(this.j,0,2);this.D=this.g?new k.Float64Array(this.j,0,1):null;if(this.g)this.s[0]=16909060,a=1===((new k.Int8Array(this.j,0,8))[0]|0);else var a=!0;this.E=(this.w=a)?0:1;this.F=this.w?1:0;return this};I.prototype.$classData=M({R:0},"scala.scalajs.runtime.Bits$",{R:1,a:1});var H=void 0;
function G(){}G.prototype=new O;G.prototype.constructor=G;G.prototype.d=function(){return this};G.prototype.$classData=M({T:0},"scala.scalajs.runtime.RuntimeString$",{T:1,a:1});var fa=void 0;function W(){}W.prototype=new O;W.prototype.constructor=W;function ya(){}ya.prototype=W.prototype;var ca=M({U:0},"scala.runtime.BoxedUnit",{U:1,a:1,c:1},void 0,void 0,function(a){return void 0===a}),F=M({J:0},"java.lang.Boolean",{J:1,a:1,c:1,e:1},void 0,void 0,function(a){return"boolean"===typeof a});
function X(){this.v=null}X.prototype=new O;X.prototype.constructor=X;X.prototype.d=function(){Y=this;this.v=J(0,0);return this};
function za(a,b){if(0===(-2097152&b))b=""+(4294967296*b+ +(a>>>0));else{var c=(32+p(1E9)|0)-(0!==b?p(b):32+p(a)|0)|0,d=c,f=0===(32&d)?1E9<<d:0,d=0===(32&d)?5E8>>>(31-d|0)|0|0<<d:1E9<<d,g=a,l=b;for(a=b=0;0<=c&&0!==(-2097152&l);){var h=g,t=l,xa=f,ga=d;if(t===ga?(-2147483648^h)>=(-2147483648^xa):(-2147483648^t)>=(-2147483648^ga))h=l,t=d,l=g-f|0,h=(-2147483648^l)>(-2147483648^g)?-1+(h-t|0)|0:h-t|0,g=l,l=h,32>c?b|=1<<c:a|=1<<c;c=-1+c|0;h=d>>>1|0;f=f>>>1|0|d<<31;d=h}c=l;if(0===c?-1147483648<=(-2147483648^
g):-2147483648<=(-2147483648^c))c=4294967296*l+ +(g>>>0),g=c/1E9,f=g/4294967296|0,d=b,b=g=d+(g|0)|0,a=(-2147483648^g)<(-2147483648^d)?1+(a+f|0)|0:a+f|0,g=c%1E9|0;c=""+g;b=""+(4294967296*a+ +(b>>>0))+"000000000".substring(c.length|0)+c}return b}X.prototype.$classData=M({S:0},"scala.scalajs.runtime.RuntimeLong$",{S:1,a:1,Y:1,c:1});var Y=void 0;function N(){Y||(Y=(new X).d());return Y}
var x=M({I:0},"java.lang.String",{I:1,a:1,c:1,W:1,e:1},void 0,void 0,function(a){return"string"===typeof a}),z=M({K:0},"java.lang.Byte",{K:1,h:1,a:1,c:1,e:1},void 0,void 0,function(a){return y(a)}),E=M({M:0},"java.lang.Double",{M:1,h:1,a:1,c:1,e:1},void 0,void 0,function(a){return"number"===typeof a}),D=M({N:0},"java.lang.Float",{N:1,h:1,a:1,c:1,e:1},void 0,void 0,function(a){return"number"===typeof a}),C=M({O:0},"java.lang.Integer",{O:1,h:1,a:1,c:1,e:1},void 0,void 0,function(a){return"number"===
typeof a&&(a|0)===a&&1/a!==1/-0}),da=M({P:0},"java.lang.Long",{P:1,h:1,a:1,c:1,e:1},void 0,void 0,function(a){return!!(a&&a.$classData&&a.$classData.f.z)}),B=M({Q:0},"java.lang.Short",{Q:1,h:1,a:1,c:1,e:1},void 0,void 0,function(a){return A(a)});function Z(){this.r=this.t=0}Z.prototype=new ya;Z.prototype.constructor=Z;Z.prototype.A=function(){N();var a=this.t,b=this.r;return b===a>>31?""+a:0>b?"-"+za(-a|0,0!==a?~b:-b|0):za(a,b)};function J(a,b){var c=new Z;c.t=a;c.r=b;return c}
Z.prototype.y=function(){return this.t^this.r};Z.prototype.$classData=M({z:0},"scala.scalajs.runtime.RuntimeLong",{z:1,h:1,a:1,c:1,e:1});var Aa=wa(),Ba;Ba=new (na(x).x)([]);(function(a){V().n.ajax({url:"http://127.0.0.1:8080/api",type:"GET"}).done(function(){return function(a,c,d){a=V().n.parseXML(d.responseText);(0,V().n)(a).find("book").each(function(){wa();var a=(0,V().n)("#main"),b="\x3cli\x3e\x3cp\x3e"+this.getAttribute("name")+"\x3c/p\x3e\x3c/li\x3e";return a.append(b)})}}(a))})(Aa,Ba);
}).call(this);
//# sourceMappingURL=exceler-opt.js.map
