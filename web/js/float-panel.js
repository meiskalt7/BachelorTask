var floatPanel = new McFloatPanel();
/* Float Panel v2016.4.3. Copyright www.menucool.com */
function McFloatPanel() {
    var m = [], e = "className", f = "length", h = "display", w = "transition", i = "style", v = "height", b = "scrollTop", l = "offsetHeight", c = document, a = document.documentElement, j = function (a, c, b) {
        if (a.addEventListener)a.addEventListener(c, b, false); else a.attachEvent && a.attachEvent("on" + c, b)
    }, s = function (b, c) {
        if (typeof getComputedStyle != "undefined")var a = getComputedStyle(b, null); else a = b.currentStyle;
        return a ? a[c] : "fixed"
    }, E = function () {
        var b = c.body;
        return Math.max(b.scrollHeight, b[l], a.clientHeight, a.scrollHeight, a[l])
    }, H = function (a, c) {
        var b = a[f];
        while (b--)if (a[b] === c)return true;
        return false
    }, g = function (b, a) {
        return H(b[e].split(" "), a)
    }, q = function (a, b) {
        if (!g(a, b))if (!a[e])a[e] = b; else a[e] += " " + b
    }, n = function (c, g) {
        if (c[e]) {
            for (var d = "", b = c[e].split(" "), a = 0, h = b[f]; a < h; a++)if (b[a] !== g)d += b[a] + " ";
            c[e] = d.replace(/^\s+|\s+$/g, "")
        }
    }, k = function () {
        return window.pageYOffset || a[b]
    }, F = function (a) {
        return a.getBoundingClientRect().top
    }, z = function (a) {
        var b = k();
        if (b > a.oS && !g(a, "fixed"))q(a, "fixed"); else g(a, "fixed") && b < a.oS && n(a, "fixed")
    }, t = function () {
        for (var a = 0; a < m[f]; a++)C(m[a])
    }, C = function (a) {
        if (a.oS) {
            a.fpTimer && clearTimeout(a.fpTimer);
            a.fpTimer = setTimeout(function () {
                if (a.alwaysFixed)z(a); else u(a)
            }, 50)
        } else u(a)
    }, u = function (b) {
        var m = F(b), d = b[l], f = b[i], c = b.pH[i], e = k();
        if (m < b.oT && e > b.oS && !g(b, "fixed") && (window.innerHeight || a.clientHeight) > d) {
            b.tP = e + m - b.oT;
            var o = E();
            if (d > o - b.tP - d)var j = d; else j = 0;
            c[h] = "block";
            c[w] = "none";
            c[v] = d + 1 + "px";
            b.pH[l];
            c[w] = "height .3s";
            c[v] = j + "px";
            q(b, "fixed");
            f.position = "fixed";
            f.top = b.oT + "px";
            if (s(b, "position") != "fixed")c[h] = "none"
        } else if (g(b, "fixed") && (e < b.tP || e < b.oS)) {
            n(b, "fixed");
            c[h] = "none";
            f.position = f.top = ""
        }
    }, B = function () {
        var d = [], b, a;
        if (c.getElementsByClassName)d = c.getElementsByClassName("float-panel"); else {
            var k = c.getElementsByTagName("*");
            b = k[f];
            while (b--)g(k[b], "float-panel") && d.push(k[b])
        }
        b = d[f];
        for (var e = 0; e < b; e++) {
            a = m[e] = d[e];
            a.oT = parseInt(a.getAttribute("data-top") || 0);
            a.oS = parseInt(a.getAttribute("data-scroll") || 0);
            if (a.oS > 20 && s(a, "position") == "fixed")a.alwaysFixed = 1;
            a.pH = c.createElement("div");
            a.pH[i].width = a.offsetWidth + "px";
            a.pH[i][h] = "none";
            a.parentNode.insertBefore(a.pH, a.nextSibling)
        }
        if (m[f]) {
            setTimeout(t, 160);
            j(window, "scroll", t)
        }
        G()
    };
    j(window, "load", B);
    var d, x = 200, y = 0, r, p, A = function () {
        return window.innerWidth || a.clientWidth || c.body.clientWidth
    };

    function D() {
        r = setInterval(function () {
            var d = c.body;
            if (d[b] < 3)d[b] = 0; else d[b] = d[b] / 1.4;
            if (a[b] < 3)a[b] = 0; else a[b] = a[b] / 1.4;
            if (!k()) {
                clearInterval(r);
                r = null
            }
        }, 12)
    }

    function o() {
        clearTimeout(p);
        if (k() > x && A() > y) {
            p = setTimeout(function () {
                n(d, "mcOut")
            }, 60);
            d[i][h] = "block"
        } else {
            q(d, "mcOut");
            p = setTimeout(function () {
                d[i][h] = "none"
            }, 500)
        }
    }

    var G = function () {
        d = c.getElementById("backtop");
        if (d) {
            var a = d.getAttribute("data-v-w");
            if (a) {
                a = a.replace(/\s/g, "").split(",");
                x = parseInt(a[0]);
                if (a[f] > 1)y = parseInt(a[1])
            }
            j(d, "click", D);
            j(window, "resize", o);
            j(window, "scroll", o);
            o()
        }
    }
}