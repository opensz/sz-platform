﻿/**
 * A jQuery plugin to style all form elements
 *
 * @package		Plum
 * @version		1.0
 * @author		RoboCréatif, LLC
 * @copyright	2011
 */
var plum = plum || {};
(function (a) {
    a.fn.plum = function (c, b) {
        return typeof plum[c] === "function" ? plum[c](this, b) : this
    };
    a.expr[":"].plum = function (b) {
        return a(b).parent().hasClass("plum")
    };
    plum.form = function (g, o) {
        o = a.extend(plum.form.options, o);
        var f = [],
            l = o.classes,
            k, e, b = a(),
            m = false,
            d = (g[0].tagName.toLowerCase() === "form" ? g : (d = g.find("form")) && !d.length ? g.closest("form") : d);
        d.bind("submit", function () {
            a(":submit", g).trigger("blur").attr("disabled", true);
            b = a("div.plum." + l.file, g);
            b.each(function () {
                a("ul." + l.filelist + " li", this).removeClass(l.waiting).addClass(l.loading)
            });
            typeof o.submit === "function" && o.submit();
            return this
        });
        if (o.ajax && !e) {
            e = a('<iframe name="plum-frame">').css({
                height: 0,
                left: "-9999em",
                position: "absolute",
                top: "-9999em",
                width: 0
            }).appendTo(d.attr("target", "plum-frame")).load(function (i) {
                if (m) {
                    var c = e.contents().find("body").text();
                    typeof o.complete === "function" && o.complete(o.json ? a.parseJSON(c) : c);
                    a(":submit", g).removeAttr("disabled");
                    b.each(function () {
                        var q = a("ul." + l.filelist + " li", this),
                            p = q.length - 1;
                        q.each(function (r) {
                            a(this).slideUp(300, function () {
                                r === p && q.parent().empty()
                            })
                        });
                        a(":file:eq(0)", this).css("zIndex", 999).val("").removeAttr("tabindex");
                        a(":file:gt(0)", this).remove()
                    })
                }
                m = true
            })
        }
        plum.form.elements = plum.form.elements.add(a(":input", g).not(":plum,:hidden").each(function () {
            var Y = this.tagName.toLowerCase(),
                ad = this.type,
                K = this.disabled ? " " + l.disabled : "",
                G = this.selected ? " " + l.selected : "",
                E = this.checked ? " " + l.checked : "",
                N = this.multiple ? " " + l.multiple : "",
                T = this.value,
                u = this.name,
                W = {
                    f: a(this).css("float"),
                    h: this.offsetHeight,
                    p: a(this).css("position"),
                    w: this.offsetWidth
                },
                v = a(this).wrap(a("<div>", {
                    "class": "plum " + l[Y] + (l[ad] && l[ad] !== l[Y] ? " " + l[ad] : "") + E + K + N + G,
                    title: this.title,
                    dir: this.dir,
                    css: {
                        "float": W.f,
                        position: W.p === "static" ? "relative" : W.p
                    }
                })).bind({
                    focus: function () {
                        switch (ad || Y) {
                        case "select-multiple":
                        case "select-one":
                            A.children("div:first-child").addClass(l.focus);
                            break;
                        case "file":
                            B.addClass(l.focus);
                            break;
                        default:
                            A.addClass(l.focus);
                            break
                        }
                    },
                    blur: function () {
                        switch (ad || Y) {
                        case "select-multiple":
                        case "select-one":
                            A.children("div:first-child").removeClass(l.focus).find("li").removeClass(l.focus);
                            break;
                        case "file":
                            B.removeClass(l.focus);
                            break;
                        default:
                            A.removeClass(l.focus);
                            break
                        }
                    }
                }),
                A = v.parent().bind({
                    click: function () {
                        v.trigger("focus")
                    },
                    mouseenter: function () {
                        A.addClass(l.hover)
                    },
                    mouseleave: function () {
                        A.removeClass(l.hover)
                    }
                });
            switch (ad || Y) {
            case "file":
                var J = 0,
                    I = 0,
                    F = {
                        B: o.maxSize,
                        K: o.maxSize / 1024,
                        M: o.maxSize / 1024 / 1024,
                        G: o.maxSize / 1024 / 1024 / 1024
                    },
                    w = a("<input>", {
                        tabindex: -1,
                        type: "button",
                        value: o.button
                    }).prependTo(A),
                    H = A.children(),
                    B = a("<div>", {
                        "class": "plum " + l.input + " " + l.button,
                        css: {
                            overflow: "hidden",
                            position: "relative"
                        }
                    }),
                    c = function (aj) {
                        if (o.maxFiles && I >= o.maxFiles) {
                            return false
                        }
                        var af, ag, ah, ae, ai;
                        if (this.files) {
                            af = this.files[0].name || this.files[0].fileName;
                            ag = this.files[0].size || this.files[0].fileSize;
                            ah = this.files[0].type || this.files[0].fileType;
                            ae = o.fileTypes.length && a.inArray(ah, o.fileTypes) < 0 ? "<div>" + o.messages.type + "</div>" : "";
                            ae = !ae && o.maxSize && ag > o.maxSize ? "<div>" + o.messages.size.replace(/{filesize}/g, F) + "</div>" : ae;
                            ag = {
                                B: ag
                            };
                            ag.K = ag.B / 1024;
                            ag.M = ag.K / 1024;
                            ag.G = ag.M / 1024;
                            if (ag.G > 1) {
                                ag = Math.round(ag.G) + " GB"
                            } else {
                                if (ag.M > 1) {
                                    ag = Math.round(ag.M) + " MB"
                                } else {
                                    if (ag.K > 1) {
                                        ag = Math.round(ag.K) + " KB"
                                    } else {
                                        ag = ag.B + " bytes"
                                    }
                                }
                            }
                        } else {
                            af = this.value;
                            ag = ah = ae = ""
                        }
                        a("<li>", {
                            "class": (ae ? l.error : l.waiting) + " plum-upload-" + J,
                            css: {
                                display: "none"
                            },
                            html: o.fileItem.replace(/{filename}/g, af).replace(/{filesize}/g, ag).replace(/{filetype}/g, ah) + ae
                        }).appendTo(L).slideDown(300).find("." + l.remove).bind("click", function () {
                            var ak = a(this).closest("li");
                            a(":file." + ak.attr("class").match(/\s*(plum-upload-\d+)/)[1]).remove();
                            ak.slideUp(300, function () {
                                a(this).remove()
                            })
                        });
                        J++;
                        v = v.unbind("change", c).clone().val("").removeClass("plum-upload-" + (J - 1)).addClass("plum-upload-" + J).bind("change", c).trigger("focus");
                        w.after(v);
                        v.next(":file").attr("tabindex", -1).css("zIndex", -999);
                        if (ae) {
                            v.next(":file").remove()
                        } else {
                            I++
                        }
                    },
                    L = a("<ul>", {
                        "class": l.filelist
                    }).appendTo(A);
                if (F.G > 1) {
                    F = Math.round(F.G) + " GB"
                } else {
                    if (F.M > 1) {
                        F = Math.round(F.M) + " MB"
                    } else {
                        if (F.K > 1) {
                            F = Math.round(F.K) + " KB"
                        } else {
                            F = F.B + " bytes"
                        }
                    }
                }
                v.addClass("plum-upload-" + J).css({
                    opacity: 0,
                    position: "absolute",
                    right: 0,
                    zIndex: 999
                }).bind("change", c);
                B = H.wrapAll(B).parent().bind({
                    mouseenter: function () {
                        B.toggleClass(l.hover)
                    },
                    mouseleave: function () {
                        B.toggleClass(l.hover)
                    },
                    mousemove: function (ae) {
                        a(":file:eq(0)", B).css({
                            top: ae.pageY - H.offset().top - 10,
                            right: B.width() / 2 - (ae.pageX - H.offset().left)
                        })
                    }
                });
                break;
            case "checkbox":
                var p = v.hasClass("check-all"),
                    x = p ? v.attr("class").match(/\s*group-(.+)/)[1] : this.name;
                typeof f[x] === "undefined" && (f[x] = [0, 0]);
                if (!K && !p) {
                    f[x][1]++;
                    if (!E) {
                        f[x][0] = f[x][0] === 0 ? 0 : 2
                    } else {
                        if (f[x][1] === 1) {
                            f[x][0] = 1
                        } else {
                            f[x][0] = f[x][0] === 1 ? 1 : 2
                        }
                    }
                }
                v.bind("click", function (af) {
                    var ae = a(this),
                        ai = ae.parent(),
                        ag = this.name,
                        ah = ae.hasClass("check-all") ? ae.attr("class").match(/\s*group-(.+)/)[1] : false;
                    groupBoxes = plum.form.elements.filter(function () {
                        return this.type === "checkbox" && !this.disabled && a(this).hasClass("group-" + (ah ? ah : ag))
                    }), groupTotal = plum.form.elements.filter(function () {
                        return this.type === "checkbox" && !this.disabled && !a(this).hasClass("check-all") && this.checked && this.name === ag
                    }).length;
                    ai.toggleClass(l.checked);
                    if (ah) {
                        x = plum.form.elements.filter(function () {
                            return this.type === "checkbox" && !this.disabled && (a(this).hasClass("check-all group-" + ah) || this.name === ah)
                        });
                        if (this.checked || ai.hasClass(l.mixed)) {
                            ai.addClass(l.checked);
                            x.attr("checked", true).parent().removeClass(l.mixed).addClass(l.checked)
                        } else {
                            ai.removeClass(l.checked + " " + l.mixed);
                            x.removeAttr("checked").parent().removeClass(l.checked)
                        }
                    } else {
                        if (this.checked) {
                            if (groupTotal === f[ag][1]) {
                                groupBoxes.attr("checked", true).parent().removeClass(l.mixed).addClass(l.checked)
                            } else {
                                groupBoxes.attr("checked", true).parent().addClass(l.checked + " " + l.mixed)
                            }
                        } else {
                            if (groupTotal) {
                                groupBoxes.attr("checked", true).parent().addClass(l.checked + " " + l.mixed)
                            } else {
                                groupBoxes.removeAttr("checked").parent().removeClass(l.checked + " " + l.mixed)
                            }
                        }
                    }
                }).css({
                    left: "50%",
                    marginLeft: -W.h / 2,
                    marginTop: -W.w / 2,
                    opacity: 0,
                    position: "absolute",
                    top: "50%"
                });
                break;
            case "radio":
                v.bind("click", function () {
                    plum.form.elements.filter(function () {
                        return this.type === "radio" && this.name === u
                    }).parent().removeClass(l.checked);
                    a(this).parent().addClass(l.checked)
                }).css({
                    left: "50%",
                    marginLeft: -W.h / 2,
                    marginTop: -W.w / 2,
                    opacity: 0,
                    position: "absolute",
                    top: "50%"
                });
                break;
            case "select-multiple":
            case "select-one":
                var z = "",
                    r = this.size || (N ? 5 : 10),
                    s = -parseInt(A.css("borderTopWidth")),
                    R = A.offset().top,
                    i = 0,
                    ab = null,
                    q = [],
                    y = false,
                    D = false,
                    V = function () {
                        S.fadeIn(200);
                        P.slideUp(150, function () {
                            A.css("zIndex", "");
                            P.css("marginTop", 0)
                        });
                        A.removeClass(l.open).addClass(l.closed)
                    },
                    aa = function (af) {
                        for (var ae in q) {
                            if (q[ae].substr(0, af.length) === af) {
                                return ae
                            }
                        }
                    },
                    C = function (ae) {
                        switch (ae.which) {
                        case 27:
                            v.trigger("blur");
                            break;
                        case 38:
                            N && ae.shiftKey && D === false && (D = J);
                            J = J - 1 < 0 ? 0 : J - 1;
                            O(ae, Q.eq(J)[0]);
                            break;
                        case 40:
                            N && ae.shiftKey && D === false && (D = J);
                            J = J + 1 >= I - 1 ? I - 1 : J + 1;
                            O(ae, Q.eq(J)[0]);
                            break
                        }
                    },
                    X = function (ae) {
                        if (ae.which === 0 || ae.which === 13) {
                            return this
                        }
                        ae.preventDefault();
                        if (ae.which === 8) {
                            ab = ab === null ? ab : ab.substring(0, ab.length - 1);
                            return this
                        }
                        var af = String.fromCharCode(ae.which);
                        ab = ab === null ? af : ab + af;
                        J = aa(ab) || J;
                        O(ae, Q.eq(J)[0])
                    },
                    O = function (ag, ae) {
                        var af = ae || this;
                        y = Q.index(af);
                        D = D !== false ? D : y;
                        P.hasClass(l.focus) || v.trigger("focus");
                        Q.removeClass(l.focus).eq(y).addClass(l.focus);
                        if (N && ag.shiftKey) {
                            var ai, ah;
                            if (y < D) {
                                ai = y;
                                ah = D + 1
                            } else {
                                ai = D;
                                ah = y + 1
                            }
                            Q.removeClass(l.selected);
                            Z.removeAttr("selected");
                            a("li." + l.option + ":not(." + l.disabled + ")", P).slice(ai, ah).addClass(l.selected);
                            a("option:not(:disabled)", v).slice(ai, ah).attr("selected", true)
                        } else {
                            if (N && ag.ctrlKey) {
                                D = y;
                                a(af).toggleClass(l.selected);
                                if (a(af).hasClass(l.selected)) {
                                    Z.eq(y).attr("selected", true)
                                } else {
                                    Z.eq(y).removeAttr("selected")
                                }
                            } else {
                                if (ae) {
                                    D = y = J
                                } else {
                                    ab = null;
                                    D = J = y
                                }
                                Q.removeClass(l.selected).eq(y).addClass(l.selected);
                                Z.removeAttr("selected").eq(y).attr("selected", true);
                                !N && t.text(Q.eq(y).text())
                            }
                        }
                    },
                    U = function (ag) {
                        if (N || a(ag.target).hasClass(l.disabled)) {
                            return this
                        }
                        if (!a(ag.target).closest("div.plum." + l.select).length || a(ag.target).closest("div.plum." + l.select, A[0]).length) {
                            V();
                            return this
                        }
                        A.toggleClass(l.closed).toggleClass(l.open);
                        if (A.hasClass(l.open)) {
                            var af = a(document).height(),
                                ae = R + i + 28 > af ? af - (R + i) - 28 : s;
                            ae = ae * -1 > R + 20 ? -R + 20 : ae;
                            A.css("zIndex", 999);
                            S.fadeOut(100);
                            P.animate({
                                marginTop: ae,
                                marginBottom: 25
                            }, 150).slideDown(150, function () {
                                a(this).css({
                                    overflowX: "hidden",
                                    overflowY: "scroll"
                                })
                            })
                        } else {
                            V()
                        }
                    },
                    ac = function () {
                        var ae = this.tagName.toLowerCase(),
                            ah = this.label || this.textContent || this.innerText,
                            ag = this.disabled ? " " + l.disabled : "",
                            af = this.selected ? " " + l.selected : "";
                        z += '<li class="' + l[ae] + ag + af + '">';
                        if (ae === "option") {
                            z += ah;
                            af && t.text(ah)
                        } else {
                            z += "<label>" + ah + "</label><ul>";
                            a(this).children().each(ac);
                            z += "</ul>"
                        }
                        z += "</li>"
                    },
                    M = a("<div>", {
                        "class": l.wrapper
                    }).prependTo(A.css("float", "left")),
                    S = N ? a() : a("<div>", {
                        "class": l.value,
                        css: {
                            position: "relative"
                        },
                        html: '<div></div><div class="' + l.arrow + '"></div>'
                    }).appendTo(M),
                    t = a("div:first-child", S),
                    P = a("<ul>", {
                        "class": l.container,
                        css: {
                            "white-space": "pre"
                        },
                        html: v.children().each(ac) && z
                    }).appendTo(M),
                    Q = P.find("li." + l.option + ":not(." + l.disabled + ")").bind("click", O).each(function (ae) {
                        var af = this.textContent || this.innerText;
                        q.push(af.toLowerCase())
                    }),
                    J = Q.index(Q.filter(function () {
                        return a(this).hasClass(l.selected)
                    })[0]),
                    Z = v.css({
                        left: "-9999em",
                        position: "absolute"
                    }).bind({
                        keydown: C,
                        keypress: X
                    }).find("option:not(:disabled)"),
                    I = Z.length;
                P.css({
                    maxHeight: a("li." + l.option, P).outerHeight() * r,
                    minWidth: W.w,
                    overflowX: "hidden",
                    overflowY: "scroll",
                    width: P.outerWidth() + a("div." + l.arrow, S).outerWidth() + 16
                });
                A.bind("mousedown", function (ae) {
                    ae.preventDefault()
                }).addClass(N ? l.open : l.single + " " + l.closed).css({
                    "float": W.f,
                    width: P.outerWidth()
                });
                i = P.outerHeight();
                !K && a(document).bind("click", U);
                if (N) {
                    break
                }
                P.hide();
                !t.text() && t.text(a("li." + l.option + ":eq(0)", P).text());
                A.css({
                    height: S.outerHeight()
                });
                M.css({
                    position: "absolute",
                    width: "100%"
                });
                break;
            case "textarea":
                v.css("resize", "none");
                break;
            default:
                break
            }
        }));
        k = a(":checkbox:not(:disabled)", g);
        if (k.length) {
            var j, n;
            for (var h in f) {
                j = k.filter(function () {
                    return this.name === h
                });
                n = k.filter(function () {
                    return a(this).hasClass("check-all group-" + h)
                });
                if (f[h][0] === 1) {
                    j.attr("checked", true).parent().addClass(l.checked);
                    n.attr("checked", true).parent().removeClass(l.mixed).addClass(l.checked)
                } else {
                    if (f[h][0] === 2) {
                        n.attr("checked", true).parent().addClass(l.checked + " " + l.mixed)
                    }
                }
            }
        }
        a("label", g).bind("mouseenter mouseleave", function (i) {
            var c;
            if (this.htmlFor) {
                c = a("#" + this.htmlFor).parent()
            } else {
                if ((c = a(this).find(":input")) && c.is(":plum")) {
                    c = c.parent()
                }
            }
            if (c) {
                if (i.type === "mouseenter") {
                    c.addClass(l.hover)
                } else {
                    c.removeClass(l.hover)
                }
            }
        });
        return g
    };
    plum.form.elements = a();
    plum.form.options = {
        ajax: true,
        action: null,
        json: false,
        button: "Choose a file...",
        maxFiles: 5,
        maxSize: 5242880,
        fileTypes: [],
        messages: {
            size: "Please choose a file smaller than {filesize}.",
            type: "This file type is not allowed."
        },
        fileItem: '<span class="filename">{filename}</span><span class="remove">&times;</span><span class="filesize">{filesize}</span>',
        submit: function () {},
        complete: function (b) {},
        classes: {
            arrow: "select-arrow",
            button: "button",
            checkbox: "checkbox",
            checked: "checked",
            closed: "closed",
            container: "select-container",
            disabled: "disabled",
            file: "file",
            filelist: "filelist",
            focus: "focus",
            error: "error",
            hover: "hover",
            input: "input",
            loading: "loading",
            mixed: "mixed",
            multiple: "multiple",
            open: "open",
            optgroup: "optgroup",
            option: "option",
            password: "password",
            radio: "radio",
            remove: "remove",
            reset: "reset",
            submit: "submit",
            text: "text",
            textarea: "textarea",
            select: "select",
            selected: "selected",
            single: "single",
            value: "select-value",
            waiting: "waiting",
            wrapper: "select-wrapper"
        }
    }
})(jQuery);